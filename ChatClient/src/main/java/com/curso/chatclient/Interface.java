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
 *
 * @author josemrm30
 */
public class Interface {

    private final static Logger LOGGERINTERFACE = Logger.getLogger(Interface.class.getName());

    /**
     * Method used for all inputs and outputs. Defines the .start of this thread
     * The Thread keeps reading the next input that we recive from the server
     * and prints the message that gets
     *
     * to end the loop pulse enter
     *
     */
    public void run() throws IOException, ClientException {
        boolean running = true;
        Scanner sc = new Scanner(System.in);
        String msg = null;
        Connection conct = new Connection();
        Socket clientSocket = conct.connect();
        Client sender = new Client(clientSocket);
        ListenThread listener = new ListenThread(clientSocket);
        System.out.println("Welcome to T-Sysgram.");
        listener.start();

        while (running) {

            System.out.println("Introduce your message.\n'exit' for end the application.");
            try {
                msg = sc.nextLine();
            } catch (NoSuchElementException e) {
                System.err.println(e);
                LOGGERINTERFACE.log(Level.FINE, e.toString(), e);
            }
            if (msg.toLowerCase().equals("exit")) {
                running = false;
                listener.stopThread();
            } else {
                sender.sendMessage(msg);
            }

        }
    }

    /**
     * Get Secure Password that receives a password introduced by an user and a
     * salt generation for hashing the user password.
     *
     * @param   passwordToHash
     * @param   salt
     * @return  A hashed password
     */
    public String getSecurePassword(String passwordToHash, String salt) {
        String generatedPassword = null;
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
     * @return  A chain of bytes in String type variable.
     * @throws  NoSuchProviderException
     */
    public String getSalt() throws NoSuchAlgorithmException, NoSuchProviderException {
        SecureRandom sr = SecureRandom.getInstance("SHA1PRNG", "SUN");
        byte[] salt = new byte[16];
        sr.nextBytes(salt);
        return salt.toString();
    }
}
