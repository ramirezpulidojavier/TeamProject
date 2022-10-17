/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.curso.chatclient;

import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 *
 * @author josemrm30
 */
public class Interface {

    /**
     * Method used for all inputs and outputs.
     */
    public void run() {
        boolean running = true;
        Scanner sc = new Scanner(System.in);
        String msg = null;

        System.out.println("Welcome to T-Sysgram.");

        while (running) {
            System.out.println("Introduce your message.\n'exit' for end the application.");
            try {
                msg = sc.nextLine();
            } catch (NoSuchElementException e) {
                System.err.println(e);
            }
            if (msg.toLowerCase().equals("exit")) {
                running = false;
            }

        }
    }
}
