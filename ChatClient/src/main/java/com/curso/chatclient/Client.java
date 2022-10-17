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
import java.time.LocalDateTime;

/**
 * Class Client with the methods: sendMessage and getMessage
 * @author pcorrales2010
 */
public class Client {

    public Client() {
    }

    /**
     * Send the message and current date from client to server
     * @param message The message to send to server
     * @param socket Server's socket
     * @throws IOException
     */
    public void sendMessage(String message, Socket socket) throws IOException {
        LocalDateTime date = LocalDateTime.now();  
        message = message + " " + date.toString();
        PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
        writer.println(message);
    }

    /**
     * Get the message from server
     * @param socket Server's socket
     * @return Message sent by server
     * @throws IOException
     */
    public String getMessage(Socket socket) throws IOException {
        InputStream input = socket.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(input));
        String line = reader.readLine();
        return line;
    }

}
