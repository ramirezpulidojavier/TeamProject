/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Project/Maven2/JavaApp/src/main/java/${packagePath}/${mainClassName}.java to edit this template
 */
package com.curso.chatclient;

import com.curso.exceptions.ClientException;
import java.awt.Font;
import java.io.IOException;

/**
 *
 * @author jramir14
 */
public class Main {

    

    public static void main(String[] args) throws IOException, ClientException {
        Interface menu = new Interface();
        menu.run();
        
    }
}
