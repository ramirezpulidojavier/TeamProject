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

    
    public ListenThread(Socket soc) {
        try {
            this.client = new Client(soc);
        } catch (IOException ex) {
            LOGGERTHREAD.log(Level.FINE, ex.toString(), ex);
        }
    }

    @Override
    public void run() {

        while (true) {

            try {
                System.out.println(client.getMessage());
                //client.getMessage(socket);

            } catch (IOException ex) {
                LOGGERTHREAD.log(Level.FINE, ex.toString(), ex);
            }
        }

    }

}
