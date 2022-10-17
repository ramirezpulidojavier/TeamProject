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
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;

/**
 * Class Client with the methods: sendMessage and getMessage
 * @author pcorrales2010
 */
public class Client {

    public Client() {
    }

    /**
     * Method 
     * @param message The string you want to sent + current date
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
     *  Method
     * @param socket
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
