/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.curso.chatclient;

import com.curso.exceptions.ClientException;
import java.time.LocalDate;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author juacuadr
 */
public class Bot {

    private final static Logger LOGGER = Logger.getLogger(Interface.class.getName());

    private Client botClient;
    private boolean runningBot;

    public Bot(Client newClient) {
        this.botClient = newClient;
        this.runningBot = true;
    }

    public void listeningMessages() throws ClientException {

        while (runningBot) {

            String msgReaded = botClient.getMessage();
            System.out.println(msgReaded);
            String code = decodingMessage(msgReaded);
            String res = "";

            System.out.println("Printing code:");
            System.out.println(code);
            
            if (code != null) {

                switch (code) {
                    // Showing menu
                    case "/menu":
                        res = botMenu();
                        break;

                    // Dumb option
                    case "/dumb":
                        res = dumb();
                        break;

                    // Compatibility option
                    case "/compatibility":
                        res = compatibility();
                        break;

                    // Death date
                    case "/deathDate":
                        res = deathDate();
                        break;

                    // Head or tail
                    case "/headsOrTails":
                        res = headsOrTails();
                        break;
                }
                // Sending message response
                if (!"".equals(res)) {
                    botClient.sendMessage(res);
                }
            }
        }
    }

    public String decodingMessage(String msg) {
        String[] splitted = msg.split(": ");

        if (splitted.length == 2 && splitted[1].charAt(0) == '/') {
            return splitted[1];
        }
        return null;
    }

    public String botMenu() {
        return "ChatBot menu: /dumb /compatibility /deathDate /headsOrTails";
    }

    public String dumb() {
        return "You are " + new Random().nextInt(11) + " dumb on the international dumb scale. Congratulations";
    }

    public String compatibility() {
        return "You are " + new Random().nextInt(101) + "% compatible with java";
    }

    public String deathDate() {
        Random random = new Random();
        LocalDate date = LocalDate.now();

        int year = random.nextInt(date.getYear() + 1, 2133);
        int month = random.nextInt(1, 13);
        int day = random.nextInt(1, 32);

        return Integer.toString(year) + "-" + Integer.toString(month) + "-" + Integer.toString(day);
    }

    public String headsOrTails() {
        Random random = new Random();
        
        if (random.nextInt(2) == 0) {
            return "head";
        }
        
        return"tail";
    }
}
