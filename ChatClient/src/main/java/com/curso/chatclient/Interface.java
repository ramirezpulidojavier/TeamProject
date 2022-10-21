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
    
    public static final String ANSI_BOLD = "\u001B[1m";
    public static final String ANSI_UNDERLINED = "\u001B[4m";
    public static final String ANSI_HIGHLIGHT = "\u001B[7m";
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";

    public static final String[] FOREGROUNDS = {ANSI_RED, ANSI_GREEN};

    static int numberChar(String str, char character) {
        int counter = 0;
        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) == character) {
                counter++;
            }
        }

        return counter;
    }

    static String formatterText(String str) {
        if (numberChar(str, '_') >=2) {
            str = str.replaceFirst("_", ANSI_RED);
            str = str.replaceFirst("_", ANSI_RESET);
        }
        
        if (numberChar(str, '-') >= 2) {
            str = str.replaceFirst("-", ANSI_BOLD);
            str = str.replaceFirst("-", ANSI_RESET);
        }
        
        if (numberChar(str, '+') >= 2) {
            str = str.replaceFirst("\\Q+", ANSI_UNDERLINED);
            str = str.replaceFirst("\\Q+", ANSI_RESET);
        }
        
        if (numberChar(str, '*') >= 2) {
            str = str.replaceFirst("\\Q*", ANSI_HIGHLIGHT);
            str = str.replaceFirst("\\Q*", ANSI_RESET);
        }
        
        if (numberChar(str, '%') >= 2) {
            str = str.replaceFirst("%", ANSI_GREEN);
            str = str.replaceFirst("%", ANSI_RESET);
        }
        
        
        return str;
    }

    private final static Logger LOGGERINTERFACE = Logger.getLogger(Interface.class.getName());

    /**
     * Method used for all inputs and outputs.Defines the .start of this thread
     * The Thread keeps reading the next input that we recive from the server
     * and prints the message that gets
     *
     * To end the loop insert 'exit'
     *
     */
    public void run() {
        boolean running = true;
        Scanner sc = new Scanner(System.in);
        String msg = null;
        boolean logged = false;

        String ip;
        String port;

        System.out.println("Introduce hostname:");
        ip = sc.nextLine();
        System.out.println("Introduce port:");
        port = sc.nextLine();

        if (port.matches("[0-9]+")) {
            Connection conct = new Connection(ip, Integer.parseInt(port));
            Socket clientSocket = conct.connect();
            if (clientSocket.isConnected()) {

                try {
                    Client sender = new Client(clientSocket);
                    ListenThread listener = new ListenThread(clientSocket);
                    
                    logged = runAuthentication(sender);
                    listener.start();

                    

                    while (running && logged) {

                        System.out.print("> ");
                        try {
                            msg = sc.nextLine();
                        } catch (NoSuchElementException e) {
                            System.err.println(e);
                            LOGGERINTERFACE.log(Level.FINE, e.toString(), e);
                        }
                        if (msg.toLowerCase().equals("exit")) {
                            running = false;
                            try {
                                clientSocket.close();
                            } catch (IOException ex) {
                                Logger.getLogger(Interface.class.getName()).log(Level.SEVERE, null, ex);
                            }
                            listener.stopThread();
                            listener.interrupt();
                            
                        } else {
                            sender.sendMessage(formatterText(msg));
                        }

                    }
                } catch (ClientException CliExc) {
                    System.out.println(CliExc.getMessage());
                    System.out.print(">");
                }

            } else {
                System.out.println("Error: Server is not running.");
            }
        } else {
            System.out.println("Error: Incorrect port format ");
        }

    }

    /**
     * Method used for all inputs and outputs.Defines the .start of this thread
     * The Thread keeps reading the next input that we recive from the server
     * and prints the message that gets
     *
     * To end the loop insert 'exit'
     *
     * @param senderReceiver
     */
    public boolean runAuthentication(Client senderReceiver) {
        Scanner sc = new Scanner(System.in);
        String username = "";
        String password = "";
        String serverAnswer = "";
        String selectedOption = "";
        boolean logged = true;
        try {
            System.out.println("Welcome to T-Sysgram.");

            while (!serverAnswer.equals("true")) {

                System.out.println("Choose an option.\n'exit' for end the application.");
                System.out.println("1. Sign up");
                System.out.println("2. Log in");

                try {
                    selectedOption = sc.nextLine();
                } catch (NoSuchElementException e) {
                    System.err.println(e);
                    LOGGERINTERFACE.log(Level.FINE, e.toString(), e);
                }

                switch (selectedOption.toLowerCase()) {
                    case "1":
                        System.out.print("Username: ");
                        username = sc.nextLine();
                        System.out.print("Password: ");
                        password = sc.nextLine();
                        senderReceiver.sendMessage("R");
                        senderReceiver.sendMessage(username + "|" + getSecurePassword(password));
                        
                        serverAnswer = senderReceiver.getMessage();
                        break;
                    case "2":
                        System.out.print("Username: ");
                        username = sc.nextLine();
                        System.out.print("Password: ");
                        password = sc.nextLine();
                        senderReceiver.sendMessage("L");
                        senderReceiver.sendMessage(username + "|" + getSecurePassword(password));
                        serverAnswer = senderReceiver.getMessage();
                        break;
                    case "exit":
                        serverAnswer = "true";
                        logged = false;
                        break;
                    default:
                        System.out.println("Incorrect option");
                        logged = false;
                }

            }
        } catch (ClientException CliExp) {
            System.out.println(CliExp.getMessage());
        }
        
        return logged;

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
            LOGGERINTERFACE.log(Level.FINE, e.toString(), e);
        } catch (NoSuchProviderException e) {
            LOGGERINTERFACE.log(Level.FINE, e.toString(), e);
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
            LOGGERINTERFACE.log(Level.FINE, e.toString(), e);
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
