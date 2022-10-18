/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.curso.chatclient;

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

    /**
     * Method used for all inputs and outputs.
     */
    public void run() throws IOException {
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
                Logger.getLogger(e.getMessage()).log(Level.SEVERE, null, e);
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
