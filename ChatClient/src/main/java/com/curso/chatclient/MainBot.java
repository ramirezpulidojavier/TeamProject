/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.curso.chatclient;

import com.curso.exceptions.ClientException;

/**
 *
 * @author juacuadr
 */
public class MainBot {
    public static void main(String[] args) throws ClientException {
        Interface menu = new Interface();
        
        // Run for a client
        menu.run(0);
    }
}
