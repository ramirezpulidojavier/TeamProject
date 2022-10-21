/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.curso.chatclient;

import com.curso.exceptions.ClientException;
import java.io.IOException;
import java.net.Socket;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This class works getting data in and out of the clients
 *
 * @author josemrm30
 */
public class Interface {

    private final static Logger LOGGER = Logger.getLogger(Interface.class.getName());
    private boolean reading = true;
    private boolean logged = false;
    private String msg = null;
    private Socket mySocket;
    private Client sender;
    private ListenThread listener;
    Connection conct;
    private Scanner sc;

    public Interface() {
        sc = new Scanner(System.in);
        LOGGER.setLevel(Level.ALL);
    }

    /**
     *
     */
    public void entryMessageByUser() {

        while (reading && logged) {

            System.out.println("> ");

            // Check message mode
            try {
                msg = sc.nextLine();
            } catch (NoSuchElementException e) {
                LOGGER.log(Level.SEVERE, e.toString(), e);
            }

            // If user writes exit
            if (msg.toLowerCase().equals("exit")) {
                reading = false;

                // Close socket connection
                conct.close();

                // Stop boolean variable and then, interrupt the thread execution
                listener.stopThread();
                listener.interrupt();

            } else {
                sender.sendMessage(msg);
            }

        }
    }

    /**
     *
     * @return @throws ClientException
     */
    public Socket stablishConnection() throws ClientException {

        String ip;
        String port;

        System.out.println("Introduce hostname:");
        ip = sc.nextLine();
        System.out.println("Introduce port:");
        port = sc.nextLine();

        if (port.matches("[0-9]+")) {
            conct = new Connection(ip, Integer.parseInt(port));
            mySocket = conct.connect();

            // Check if socket is connected successfully
            if (mySocket != null) {
                if (mySocket.isConnected()) {
                    return mySocket;
                } else {
                    throw new ClientException("Error: Socket connection could not be stablished.");
                }
            } else {
                throw new ClientException("Error: Server is not running.");

            }
        } else {
            throw new ClientException("Error: Incorrect port format.");
        }
    }

    /**
     * Method used for all inputs and outputs.Defines the .start of this thread
     * The Thread keeps reading the next input that we recive from the server
     * and prints the message that gets
     *
     * To end the loop insert 'exit'
     *
     * @param isClient
     * @throws com.curso.exceptions.ClientException
     * @throws java.lang.InterruptedException
     */
    public void run(int isClient) throws ClientException, InterruptedException {
        boolean running = true;

        // Stablish socket connection
        while (running) {
            try {
                mySocket = stablishConnection();
                running = false;
            } catch (ClientException e) {
                LOGGER.log(Level.SEVERE, e.toString(), e);
            }
        }

        // Initialize new instance of Client named sender
        try {
            sender = new Client(mySocket);
        } catch (ClientException e) {
            LOGGER.log(Level.SEVERE, e.toString(), e);
        }

        // Client authentication
        logged = runAuthentication(isClient);

        // Client run
        if (isClient == 1) {
            // Initialize new instance of ListenThred name listener
            try {
                listener = new ListenThread(mySocket);
            } catch (ClientException e) {
                LOGGER.log(Level.SEVERE, e.toString(), e);
            }

            listener.start();

            // Initialize a subroutine for sending messages
            entryMessageByUser();

            // Bot run
        } else {
            Bot myBot = new Bot(sender);
            myBot.listeningMessages();
        }

        // Close scanner
        sc.close();
    }

    /**
     * Method used for all inputs and outputs.Defines the .start of this thread
     * The Thread keeps reading the next input that we recive from the server
     * and prints the message that gets
     *
     * To end the loop insert 'exit'
     * @param isClient
     * @throws java.lang.InterruptedException
     */
    public boolean runAuthentication(int isClient) throws InterruptedException {
        String serverAnswer = "";
        String selectedOption = "";

        try {
            System.out.println("Welcome to T-Sysgram.");

            while (!serverAnswer.equals("true")) {

                // Check if it is a client
                if (isClient == 1) {
                    System.out.println("Choose an option.\n'exit' for end the application.");
                    System.out.println("1. Sign up");
                    System.out.println("2. Log in");

                    try {
                        selectedOption = sc.nextLine();
                    } catch (NoSuchElementException e) {
                        LOGGER.log(Level.SEVERE, e.toString(), e);
                    }
                } else {

                    // 2 option if it is a bot
                    selectedOption = "2";
                }

                // Checking selectedOption value
                switch (selectedOption.toLowerCase()) {
                    case "1" -> {
                        inputUsernamePassword("R", isClient);
                        serverAnswer = sender.getMessage();
                        
                        if (serverAnswer.equals("true")) {
                            logged = true;
                        }
                    }
                    case "2" -> {
                        inputUsernamePassword("L", isClient);
                        serverAnswer = sender.getMessage();
                        
                        if (serverAnswer.equals("true")) {
                            logged = true;
                        }
                    }
                    case "exit" -> {
                        serverAnswer = "true";
                        logged = false;
                    }
                    default -> {
                        System.out.println("Incorrect option");
                        logged = false;
                    }
                }
            }
        } catch (ClientException CliExp) {
            System.out.println(CliExp.getMessage());
        }
        return logged;
    }

    /**
     *
     * @param mode
     * @param isClient
     * @throws java.lang.InterruptedException
     */
    public void inputUsernamePassword(String mode, int isClient) throws InterruptedException {
        String username;
        String password;

        if (isClient == 1) {
            System.out.print("Username: ");
            username = sc.nextLine();
            System.out.print("Password: ");
            password = sc.nextLine();
        } else {
            Thread.sleep(500);
            username = "bot";
            Thread.sleep(500);
            password = "bot";
        }
        

        sender.sendMessage(mode);
        sender.sendMessage(username + "|" + getSecurePassword(password));
    }

    /**
     * Get Secure Password that receives a password introduced by an user for
     * hashing the user password.
     *
     * @param passwordToHash
     * @return A hashed password
     */
    public String getSecurePassword(String passwordToHash) {
        String generatedPassword = null;
        String salt = null;
        try {
            salt = getSalt();
        } catch (NoSuchAlgorithmException e) {
            LOGGER.log(Level.FINE, e.toString(), e);
        } catch (NoSuchProviderException e) {
            LOGGER.log(Level.FINE, e.toString(), e);
        }
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");

            // Add password bytes to digest
            md.update(salt.getBytes());

            // Get the hash's bytes
            byte[] bytes = md.digest(passwordToHash.getBytes());
            StringBuilder sb = new StringBuilder();

            for (int i = 0; i < bytes.length; i++) {
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }

            generatedPassword = sb.toString();
        } catch (NoSuchAlgorithmException e) {
            LOGGER.log(Level.FINE, e.toString(), e);
        }
        return generatedPassword;
    }

    /**
     * This salt is pruposed to add complexity to the key
     *
     * @return A chain of bytes in String type variable.
     * @throws NoSuchProviderException
     */
    public String getSalt() throws NoSuchAlgorithmException, NoSuchProviderException {
        SecureRandom sr = SecureRandom.getInstance("SHA1PRNG", "SUN");
        byte[] salt = new byte[16];
        sr.nextBytes(salt);
        return salt.toString();
    }
}
