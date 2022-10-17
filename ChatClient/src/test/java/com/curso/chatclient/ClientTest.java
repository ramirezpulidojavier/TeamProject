/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package com.curso.chatclient;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.junit.Assert.*;

import static org.mockito.Mockito.when;



/**
 *
 * @author juacuadr
 */
public class ClientTest {
    
    public ClientTest() {
    }

    /**
     * 
     */
    @Test
    public void testSendMessage() throws Exception {
        // GIVEN
        
        // Primero creamos el mock
        Socket testSocket = Mockito.mock(Socket.class);
        BufferedReader testReader = Mockito.mock(BufferedReader.class);
        PrintWriter testWriter = Mockito.mock(PrintWriter.class);
        
        
        // Usamos el mock
        Client client = new Client(testSocket, testWriter, testReader);
        

        // THEN
        client.sendMessage("This is my message");

        // EXPECT
        verify(testWriter, times(1)).println("This is my message");
        verify(testReader, times(0)).readLine();
    }    
    
    @Test
    public void testGetMessage() throws Exception {
            // Primero creamos el mock
        Socket testSocket = Mockito.mock(Socket.class);
        BufferedReader testReader = Mockito.mock(BufferedReader.class);
        PrintWriter testWriter = Mockito.mock(PrintWriter.class);
        
        when(testReader.readLine()).thenReturn("cosica");
        
        // Usamos el mock
        Client client = new Client(testSocket, testWriter, testReader);
        
        String msg = client.getMessage();
        assertEquals(msg, "cosica");
    }
    
}
