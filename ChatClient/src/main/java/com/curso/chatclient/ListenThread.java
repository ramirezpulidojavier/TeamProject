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

    public ListenThread(Socket soc) {
        this.client = new Client(soc);
    }

    public void run() {

        while (true) {

            try {
                System.out.println(client.getMessage());
                //client.getMessage(socket);

            } catch (IOException ex) {
                Logger.getLogger(ListenThread.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

}
