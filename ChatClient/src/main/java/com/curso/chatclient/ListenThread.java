/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.curso.chatclient;

import com.curso.exceptions.ClientException;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Class that acts as Thread for the main class. this class just listens as a
 * cliente with them socket and prints the messesages it gets
 *
 * @author gruiztal
 */
public class ListenThread extends Thread {

    Client client;
    private final static Logger LOGGER = Logger.getLogger(Client.class.getName());
    boolean stop;

    /**
     * ListenThread constructor.It receives a socket of a new Client who is
     * connected.
     *
     * @param soc Socket used to create a new client 
     * @throws com.curso.exceptions.ClientException when an error occurs while creating a new client
     */
    public ListenThread(Socket soc) throws ClientException {
        this.client = new Client(soc);
        this.stop = false;
        LOGGER.setLevel(Level.ALL);
    }

    /**
     * Method for stopping execution of the thread.
     */
    public void stopThread() {
        this.stop = true;
    }

    /**
     * Run method which is listening to new messages from different users.
     *
     */
    @Override
    public void run() {

        while (!this.stop) {
            try {
                String message = client.getMessage();
                
                if (message == null) {
                    System.out.println("You have been banned. Type 'exit' for stop chatting");
                    stopThread();
                    this.interrupt();
                    
                }else{
                    System.out.println(message);
                }
                
                
            } catch (ClientException ex) {
                LOGGER.log(Level.SEVERE, ex.toString(), ex);
            }
        }
    }

}
