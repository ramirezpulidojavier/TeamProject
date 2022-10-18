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

    Client client;
    private final static Logger LOGGERTHREAD = Logger.getLogger(Client.class.getName());
    boolean stop;
    
     /**
     * ListenThread constructor. It receives a socket of a new Client who is
     * connected.
     *
     * @param newSocket
     * @throws IOException
     */
    public ListenThread(Socket soc) {
        try {
            this.client = new Client(soc);
            this.stop = false; 
        } catch (IOException ex) {
            LOGGERTHREAD.log(Level.FINE, ex.toString(), ex);
            throw ex;
        }
        
    public void stopThread(){
        this.stop = false;
    }
    
    /**
     * Run method which is listening to new messages from different users.
     *
     */
    @Override
    public void run() {

        while (this.stop) {
            try {
                System.out.println(client.getMessage());
            } catch (IOException ex) {
                LOGGERTHREAD.log(Level.FINE, ex.toString(), ex);
            }
        }
    }

}
