/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.curso.chatclient;

import com.curso.exceptions.ClientException;
import java.time.LocalDate;
import java.util.Random;
import java.util.logging.Logger;

/**
 * Represents the bot
 * @author juacuadr
 */
public class Bot {
   /**
    * Logger for the tracking messages
    */
    private final static Logger LOGGER = Logger.getLogger(Interface.class.getName());
    /**
     * Necessary client for sending and receiving messages
     */
    private Client botClient;
    /**
     * Stop condition for the listening loop
     */
    private boolean runningBot;

    /**
     * Constructor used every time a bot is created
     * @param newClient Client previously created with the necessary socket
     */
    public Bot(Client newClient) {
        this.botClient = newClient;
        this.runningBot = true;
    }

    /**
     * Waits listening messages and sends predefined answers 
     * 
     * @throws ClientException when getting the message there is an error reading the line 
     */
    public void listeningMessages() throws ClientException {

        while (runningBot) {

            String msgReaded = botClient.getMessage();
            System.out.println(msgReaded);
            String code = decodingMessage(msgReaded);
            String res = "";
            
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

    /**
     * Splits the input message to take the word after the character '/'
     * @param msg message to split and obtain the key word
     * @return the key word after '/' or null if the format is incorrect
     */
    public String decodingMessage(String msg) {
        String[] splitted = msg.split(": ");

        if (splitted.length == 2 && splitted[1].charAt(0) == '/') {
            return splitted[1];
        }
        return null;
    }

    /**
     * Bot menu
     * @return A string with the bot menu
     */
    public String botMenu() {
        return "ChatBot menu: /dumb /compatibility /deathDate /headsOrTails";
    }

    /**
     * Selects a random number from 0 to 10 that represents your stupidity level 
     * @return Random integer from 0 to 10
     */
    public String dumb() {
        return "You are " + new Random().nextInt(11) + " dumb on the international dumb scale. Congratulations";
    }

    /**
     * Selects a random number from 0 to 100 that represents your java programming level
     * @return Random integer from 0 to 100
     */
    public String compatibility() {
        return "You are " + new Random().nextInt(101) + "% compatible with java";
    }

    /**
     * Generate a random date from today to the year 2100 that represents yout death date
     * @return String with the message of your death date
     */
    public String deathDate() {
        Random random = new Random();
        LocalDate date = LocalDate.now();

        int year = random.nextInt(date.getYear() + 1, 2133);
        int month = random.nextInt(1, 13);
        int day = random.nextInt(1, 32);

        return Integer.toString(year) + "-" + Integer.toString(month) + "-" + Integer.toString(day);
    }

    /**
     * Generate a random number. If it is odd, it represents head. If it is even, it represents tail.
     * @return String with tail or head
     */
    public String headsOrTails() {
        Random random = new Random();
        
        if (random.nextInt(2) == 0) {
            return "head";
        }
        
        return"tail";
    }
}
