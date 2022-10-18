/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.curso.chatclient;

import com.curso.exceptions.ClientException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Class Client with the methods: sendMessage and getMessage
 *
 * @author pcorrales2010
 */
public class Client {

    // CREATE LOGGER
    // private final Logger LOGGER = Logger.getLogger(Client.class.getName());

    Socket mySocket;
    PrintWriter myWriter;
    BufferedReader myReader;

    /**
     * Constructor that receive a Socket and fill myWriter and myReader private
     * variables.
     *
     * @param newSocket
     * @throws ClientException when an I/O error occurs while creating the output/input stream.
     */
    public Client(Socket newSocket) throws ClientException {
        if (newSocket != null) {
            mySocket = newSocket;
            InputStream input;
            OutputStream output;

            try {
                output = newSocket.getOutputStream();
            } catch (IOException ex) {
                // Program logger
                throw new ClientException("Error creating the output stream: the socket could not be connected");
            }

            try {
                myWriter = new PrintWriter(output, true);
                input = mySocket.getInputStream();
            } catch (IOException ex) {
                // Program logger
                throw new ClientException("Error creating the input stream: The socket is closed, not connected or the input has been shutdown");
                
                
            }
            
            myReader = new BufferedReader(new InputStreamReader(input));
        }
    }


    /**
     * Parametrized constructor to inject variables. This constructor is being
     * used into Test files.
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
     */
    public void sendMessage(String message) {
        myWriter.println(message);
    }

    /**
     * Get the message from server
     *
     * @return Message sent by server
     * @throws ClientException if an I/O error occurs when reading a line.
     */
    public String getMessage() throws ClientException {
        String line;
        
        try {
            line = myReader.readLine();
        } catch (IOException ex) {
            // Program logger
            throw new ClientException("Error reading line.");
        }
        
        return line;
    }

}
