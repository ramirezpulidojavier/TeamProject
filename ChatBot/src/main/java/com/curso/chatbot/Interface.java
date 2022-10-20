/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.curso.chatbot;

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

        String ip = "192.168.3.215";
        String port = "2525";

        Connection conct = new Connection(ip, Integer.parseInt(port));
        Socket clientSocket = conct.connect();
        if (clientSocket.isConnected()) {
            try {
                Client sender = new Client(clientSocket);
                ListenThread listener = new ListenThread(clientSocket);
                runAuthentication(sender);
                listener.start();
                while (running) {
                    msg = sender.getMessage();
                    switch (msg) {
                        case "hola":
                            break;
                        default:
                            throw new AssertionError();
                    }
                }
            } catch (ClientException CliExc) {
                System.out.println(CliExc.getMessage());
            }
        } else {
            System.out.println("Error: Server is not running.");
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
    public void runAuthentication(Client senderReceiver) {
        Scanner sc = new Scanner(System.in);
        String username = "bot";
        String password = "bot";
        String serverAnswer = "";
        try {
            while (!serverAnswer.equals("true")) {
                senderReceiver.sendMessage("L");
                senderReceiver.sendMessage(username + "|" + getSecurePassword(password));
                serverAnswer = senderReceiver.getMessage();
            }
        } catch (ClientException CliExp) {
            System.out.println(CliExp.getMessage());
        }
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
