/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package com.curso.chatclient;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.net.Socket;
import org.junit.Test;
import static org.junit.Assert.*;
import org.mockito.Mockito;

/**
 *
 * @author juacuadr
 */
public class BotTest {
    
    public BotTest() {
    }

    @Test
    public void testListeningMessages() throws Exception {
        // GIVEN
        Socket testSocket = Mockito.mock(Socket.class);
        BufferedReader testReader = Mockito.mock(BufferedReader.class);
        PrintWriter testWriter = Mockito.mock(PrintWriter.class);

        Client botClient = new Client(testSocket, testWriter, testReader);
        Bot instance = new Bot(botClient);
        
        // THEN
        instance.listeningMessages();
        
        // EXPECT
    }

    @Test
    public void testDecodingMessage() {
        // GIVEN
        Socket testSocket = Mockito.mock(Socket.class);
        BufferedReader testReader = Mockito.mock(BufferedReader.class);
        PrintWriter testWriter = Mockito.mock(PrintWriter.class);

        Client botClient = new Client(testSocket, testWriter, testReader);
        
        String msg = "";
        Bot instance = new Bot(botClient);
        
        String expResult = "";
        String result = instance.decodingMessage(msg);
        assertEquals(expResult, result);
    }

    @Test
    public void testBotMenu() {
        // GIVEN
        Socket testSocket = Mockito.mock(Socket.class);
        BufferedReader testReader = Mockito.mock(BufferedReader.class);
        PrintWriter testWriter = Mockito.mock(PrintWriter.class);

        Client botClient = new Client(testSocket, testWriter, testReader);
        
        Bot instance = new Bot(botClient);
        
        String expResult = "";
        String result = instance.botMenu();
        assertEquals(expResult, result);
    }

    @Test
    public void testDumb() {
        // GIVEN
        Socket testSocket = Mockito.mock(Socket.class);
        BufferedReader testReader = Mockito.mock(BufferedReader.class);
        PrintWriter testWriter = Mockito.mock(PrintWriter.class);

        Client botClient = new Client(testSocket, testWriter, testReader);
        
        Bot instance = new Bot(botClient);
        
        String expResult = "";
        String result = instance.dumb();
        assertEquals(expResult, result);
    }

    @Test
    public void testCompatibility() {
        // GIVEN
        Socket testSocket = Mockito.mock(Socket.class);
        BufferedReader testReader = Mockito.mock(BufferedReader.class);
        PrintWriter testWriter = Mockito.mock(PrintWriter.class);

        Client botClient = new Client(testSocket, testWriter, testReader);
        
        Bot instance = new Bot(botClient);
        
        String expResult = "";
        String result = instance.compatibility();
        assertEquals(expResult, result);
    }

    @Test
    public void testDeathDate() {
        // GIVEN
        Socket testSocket = Mockito.mock(Socket.class);
        BufferedReader testReader = Mockito.mock(BufferedReader.class);
        PrintWriter testWriter = Mockito.mock(PrintWriter.class);

        Client botClient = new Client(testSocket, testWriter, testReader);
        
        Bot instance = new Bot(botClient);
        
        String expResult = "";
        String result = instance.deathDate();
        assertEquals(expResult, result);
    }

    @Test
    public void testHeadsOrTails() {
        // GIVEN
        Socket testSocket = Mockito.mock(Socket.class);
        BufferedReader testReader = Mockito.mock(BufferedReader.class);
        PrintWriter testWriter = Mockito.mock(PrintWriter.class);

        Client botClient = new Client(testSocket, testWriter, testReader);
        
        Bot instance = new Bot(botClient);
        
        String expResult = "";
        String result = instance.headsOrTails();
        assertEquals(expResult, result);
    }
    
}
