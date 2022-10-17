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
        host="";
        port=4444;
    
    }
    public Connection(String host, int port){
        this.host= host;
        this.port=port;
    }
    
    public Socket connect(){
        Socket clientSocket = null;
        
        try {
            
             clientSocket = new Socket(getHost(), getPort());
            
             //out = new PrintWriter(clientSocket.getOutputStream(), true);
             //in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
             
              return clientSocket;
             
            
        } catch (IOException ex) {
            Logger.getLogger(Connection.class.getName()).log(Level.SEVERE, null, ex);
        }
       return clientSocket;
            
        
    }
    public void close(Socket socket){
        if (socket!=null) {
            try {
                socket.close();
            } catch (IOException ex) {
                Logger.getLogger(Connection.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
    }
    
}
