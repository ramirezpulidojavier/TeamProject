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
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author gruital
 * 
 * Class that connects a Client to a Server
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
    /**
     * @param host default host
     * @param port default port
     * Default Cosntructor it assign the default host and port
     */
    public Connection(){
        host="localhost";
        port=4444;
    
    }
    
    /**
     * Constructor to change values of host and port
     * @param host
     * @param port 
     */
    public Connection(String host, int port){
        this.host= host;
        this.port=port;
    }
    
    
    /**
     * 
     * @return Sockt that client is gonna use for the connection
     */
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
    
    /**
     * 
     * @param socket needed the socket yhat is going to be closed
     * @return true if the socket was able to be closed
     */
    public boolean close(Socket socket){
        if (socket!=null) {
            try {
                socket.close();
                return true;
            } catch (IOException ex) {
                Logger.getLogger(Connection.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
        return false;
    }
    
}
