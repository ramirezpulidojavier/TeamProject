/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.curso.chatclient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.time.LocalDateTime;

/**
 * Class Client with the methods: sendMessage and getMessage
 *
 * @author pcorrales2010
 */
public class Client {

    Socket mySocket;
    PrintWriter myWriter;
    BufferedReader myReader;

    public Client(Socket newSocket) {
        mySocket = newSocket;
        try {
            myWriter = new PrintWriter(newSocket.getOutputStream(), true);
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
        InputStream input;
        try {
            input = mySocket.getInputStream();
            myReader = new BufferedReader(new InputStreamReader(input));
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Client(Socket newSocket, PrintWriter newWriter, BufferedReader newReader) {
        mySocket = newSocket;
        myWriter = newWriter;
        myReader = newReader;
    }

    /**
     * Send the message and current date from client to server
     *
     * @param message The message to send to server
     * @throws IOException If an I/O error occurs
     */
    public void sendMessage(String message) throws IOException {
        myWriter.println(message);
    }

    /**
     * Get the message from server
     *
     * @return Message sent by server
     * @throws IOException If an I/O error occurs
     */
    public String getMessage() throws IOException {
        String line = myReader.readLine();
        return line;
    }

}
