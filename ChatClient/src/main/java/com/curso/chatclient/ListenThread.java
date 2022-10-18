/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.curso.chatclient;

import java.io.IOException;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author gruiztal
 */
public class ListenThread extends Thread {

    // CREATE LOGGER
    // private final Logger LOGGER = Logger.getLogger(Client.class.getName());
    
    Client client;

    /**
     * ListenThread constructor. It receives a socket of a new Client who is
     * connected.
     *
     * @param newSocket
     * @throws IOException
     */
    public ListenThread(Socket newSocket) throws IOException {
        try {
            this.client = new Client(newSocket);
        } catch (IOException ex) {
            // Program logger
            throw ex;
        }
    }

    /**
     * 
     * 
     */
    public void run() {
        while (true) {
            try {
                System.out.println(client.getMessage());
            } catch (IOException ex) {
                // Program logger
            }
        }
    }

}
