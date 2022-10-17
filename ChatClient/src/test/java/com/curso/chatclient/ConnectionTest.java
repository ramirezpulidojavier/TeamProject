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

    @Test
    public void testGetConnection() throws IOException {
        // GIVEN
        System.out.println("Get Connection Test");
        Connection newConnection = new Connection("google.com", 80);
        Socket client = new Socket("google.com", 80);
        
        // THEN
        
        
        // EXPECT
    
    }
    
}
