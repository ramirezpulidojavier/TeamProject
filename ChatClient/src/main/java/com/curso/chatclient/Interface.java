/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.curso.chatclient;

import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 *
 * @author jramir14
 */
public class Interface {

    public void run() {
        boolean running = true;
        Scanner sc = new Scanner(System.in);
        String msg;

        System.out.println("Welcome to T-Sysgram.");

        while (running) {
            try {
                System.out.println("Introduce your message.\n'exit' for end the application.");
                msg = sc.next();
                if(msg.equals("exit")){
                    running = false;
                }
            } catch (NoSuchElementException e) {
                System.err.println(e);
            }

        }
    }
}
