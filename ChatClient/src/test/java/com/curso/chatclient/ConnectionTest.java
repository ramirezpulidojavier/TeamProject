/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package com.curso.chatclient;

import java.io.IOException;
import java.net.Socket;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author juacuadr
 */
public class ConnectionTest {
    
    public ConnectionTest() {
    }
    
    /**
    * Test of client socket connection with server host and server port.
    * Recently, host is google.com and port is 80
    * 
    * @throws IOException 
    */
    @Test
    public void testStartConnection() throws IOException {
        // GIVEN
        System.out.println("Get Connection Test");
        // Change variables host and port with real host and port
        Connection newConnection = new Connection("google.com", 80);
        
        // THEN
        Socket clientSocket = newConnection.connect();
        
        // EXPECT
        assertEquals(new Socket("google.com", 80), clientSocket);
    }
    
    /**
    * Test of close client socket with server host and server port.
    * Recently, host is google.com and port is 80
    */
    @Test
    public void testCloseConnection() {
        // GIVEN
        System.out.println("Close Connection Test");
        // Change variables host and port with real host and port
        Connection newConnection = new Connection("google.com", 80);
        Socket clientSocket = newConnection.connect();
        
        // THEN
        boolean res = newConnection.close(clientSocket);
        
        // EXPECT
        assertEquals(true, res);
    }
}
