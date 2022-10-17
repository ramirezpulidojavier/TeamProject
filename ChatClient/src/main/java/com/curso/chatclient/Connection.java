/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.curso.chatclient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author jramir14
 */
public class Connection {
    
    
    private String host="";
    private int port ;

    public String getHost() {
        return host;
    }

    public int getPort() {
        return port;
    }
    
    //private PrintWriter out;
    //private BufferedReader in;
    //ServerSocket serverSocket;
    
    public Connection(){
    
    }
    public Connection(String host, int port){
    }
    
    public Socket connect(){
        Socket clientSocket;
        
        try {
            
             clientSocket = new Socket(host, port);
            
             //out = new PrintWriter(clientSocket.getOutputStream(), true);
             //in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
             
             
             
            
        } catch (IOException ex) {
            Logger.getLogger(Connection.class.getName()).log(Level.SEVERE, null, ex);
        }
        return clientSocket;
            
        
    }
    
}
