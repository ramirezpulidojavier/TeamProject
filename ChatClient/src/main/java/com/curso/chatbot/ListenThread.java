/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.curso.chatbot;

import com.curso.exceptions.ClientException;
import java.io.IOException;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *Class that acts as Thread for the main class.
 * this class just listens as a cliente with them socket and prints the messesages it gets
 * @author gruiztal
 */
public class ListenThread extends Thread {

    Client client;
    private final static Logger LOGGERTHREAD = Logger.getLogger(Client.class.getName());
    boolean stop;

    /**
     * ListenThread constructor.It receives a socket of a new Client who is
 connected.
     *
     * @param soc
     * @throws com.curso.exceptions.ClientException
     */
    public ListenThread(Socket soc) throws ClientException {
        this.client = new Client(soc);
        this.stop = false;
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
                System.out.println(client.getMessage());
            } catch (ClientException ex) {
                LOGGERTHREAD.log(Level.FINE, ex.toString(), ex);
            }
        }
    }

}
