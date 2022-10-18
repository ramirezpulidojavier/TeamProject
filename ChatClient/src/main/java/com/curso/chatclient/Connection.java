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
 *
 * @author gruital
 *
 * Class that connects a Client to a Server
 */
public class Connection {

    private String host = "";
    private int port;
    private Socket mySocket;

    /**
     * Default Constructor it assign the default host and port
     */
    public Connection() {
        host = "192.168.3.215";
        port = 8080;

    }

    /**
     * Constructor to change values of host and port
     *
     * @param host
     * @param port
     */
    public Connection(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public Connection(Socket newSocket) {
        mySocket = newSocket;
    }

    public String getHost() {
        return host;
    }

    public int getPort() {
        return port;
    }

    /**
     *
     * @return Sockt that client is gonna use for the connection
     */
    public Socket connect() {
        if (mySocket == null) {
            try {
                mySocket = new Socket(getHost(), getPort());

            } catch (UnknownHostException ex1) {
                Logger.getLogger(Connection.class.getName()).log(Level.SEVERE, null, ex1);
            } catch (SecurityException | IllegalArgumentException | IOException ex2) {
                Logger.getLogger(Connection.class.getName()).log(Level.SEVERE, null, ex2);
            }

        }
        return mySocket;
    }

    /**
     *
     * @param socket needed the socket yhat is going to be closed
     * @return true if the socket was able to be closed
     */
    public boolean close(Socket socket) {
        if (socket != null) {
            try {
                socket.close();
                return true;
            } catch (UnknownHostException ex1) {
                Logger.getLogger(Connection.class.getName()).log(Level.SEVERE, null, ex1);
            } catch (SecurityException | IllegalArgumentException | IOException ex2) {
                Logger.getLogger(Connection.class.getName()).log(Level.SEVERE, null, ex2);
            }

        }
        return false;
    }

}
