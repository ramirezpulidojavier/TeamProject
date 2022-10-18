/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.curso.chatclient;

import com.curso.exceptions.ClientException;
import java.io.IOException;
import java.net.Socket;
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
     * Method used for all inputs and outputs.
     * Defines the .start of this thread
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
}
