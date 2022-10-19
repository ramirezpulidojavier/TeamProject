/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.curso.chatclient;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *Class that connects a Client to a Server.
 * @author gruital.
 *
 * 
 */
public class Connection {

    private String host = "";
    private int port;
    private Socket mySocket;
    private final static Logger LOGGERCONNECTION = Logger.getLogger(Connection.class.getName());

    /**
     * Default Constructor it assign the default host and port.
     */
    public Connection() {
        host = "192.168.3.215";
        port = 8080;

    }

    /**
     * Constructor to change values of host and port.
     *
     * @param host.
     * @param port.
     */
    public Connection(String host, int port) {
        this.host = host;
        this.port = port;
    }
    
    /**
     * Constructor to creare a new object given a socket .
     * @param newSocket .
     */

    public Connection(Socket newSocket) {
        mySocket = newSocket;
    }

    /**
     * This method returns the values of host in the object.
     * @return host of the object.
     */
    public String getHost() {
        return host;
    }

    /**
     * This method returns the values of port in the object.
     * @return port of the object.
     */
    public int getPort() {
        return port;
    }

    /**
     *this method connects the user with its socket to the server.
     * @return Sockt that client is gonna use for the connection.
     */
    public Socket connect() {
        if (mySocket == null) {
            try {
                mySocket = new Socket(getHost(), getPort());

            } catch (SecurityException | IllegalArgumentException | IOException ex) {
                //Logger.getLogger(Connection.class.getName()).log(Level.SEVERE, null, ex);
                LOGGERCONNECTION.log(Level.FINE, ex.toString(), ex);
            }

        }
        return mySocket;
    }

    /**
     *
     * @param socket needed the socket yhat is going to be closed.
     * @return true if the socket was able to be closed.
     */
    public boolean close(Socket socket) {
        if (socket != null) {
            try {
                socket.close();
                return true;
            } catch (SecurityException | IllegalArgumentException | IOException ex) {
                //Logger.getLogger(Connection.class.getName()).log(Level.SEVERE, null, ex);
                LOGGERCONNECTION.log(Level.FINE, ex.toString(), ex);
            }

        }
        return false;
    }

}
