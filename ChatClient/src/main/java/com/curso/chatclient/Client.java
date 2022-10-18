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

    /**
     * Constructor that receive a Socket and fill myWriter and myReader private variables.
     * 
     * @param newSocket
     * @throws IOException when an input output error is detected
     */
    public Client(Socket newSocket) throws IOException {
        if (newSocket != null) {
            mySocket = newSocket;
            try {
                InputStream input;
                myWriter = new PrintWriter(newSocket.getOutputStream(), true);
                input = mySocket.getInputStream();
                myReader = new BufferedReader(new InputStreamReader(input));
            } catch (IOException ex) {
                Logger.getLogger(Client.class.getName()).log(Level.SEVERE, "IOException occurred", ex);
                throw ex;
            }
        }
    }
    
    /**
     * Parametrized constructor to inject variables. This constructor is being used into Test files.
     * 
     * @param newSocket
     * @param newWriter
     * @param newReader 
     */
    public Client(Socket newSocket, PrintWriter newWriter, BufferedReader newReader) {
        mySocket = newSocket;
        myWriter = newWriter;
        myReader = newReader;
    }

    /**
     * Send the message and current date from client to server
     *
     * @param message The message to send to server
     * @throws IOException when input output error is detected.
     */
    public void sendMessage(String message) throws IOException {
        myWriter.println(message);
    }

    /**
     * Get the message from server
     *
     * @return Message sent by server
     * @throws IOException when input output error is detected.
     */
    public String getMessage() throws IOException {
        String line = myReader.readLine();
        return line;
    }

}
