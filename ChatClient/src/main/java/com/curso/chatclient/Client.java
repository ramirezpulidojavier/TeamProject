/*
* Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
* Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.curso.chatclient;

import com.curso.exceptions.ClientException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;

import javax.crypto.Cipher;

/**
 * Class Client with the methods: sendMessage and getMessage
 *
 * @author pcorrales2010
 */
public class Client {
    
    Socket mySocket;
    PrintWriter myWriter;
    BufferedReader myReader;
    private final static Logger LOGGERCLIENT = Logger.getLogger(Client.class.getName());

    String input = "testing cypher";
    SecretKey key;
    IvParameterSpec ivParameterSpec = generateIv();
    String algorithm = "AES/CBC/PKCS5Padding";

    /**
     * Constructor that receive a Socket and fill myWriter and myReader private
     * variables.
     *
     * @param newSocket
     * @throws ClientException when an I/O error occurs while creating the
     * output/input stream.
     */
    public Client(Socket newSocket) throws ClientException, NoSuchAlgorithmException {
        if (newSocket != null) {
            mySocket = newSocket;
            InputStream input;
            OutputStream output;
            
            try {
                output = newSocket.getOutputStream();
            } catch (IOException ex) {
                throw new ClientException("Error creating the output stream: the socket could not be connected");
            }
            
            try {
                myWriter = new PrintWriter(output, true);
                input = mySocket.getInputStream();
            } catch (SecurityException | IllegalArgumentException | IOException ex) {
                LOGGERCLIENT.log(Level.FINE, ex.toString(), ex);
                throw new ClientException("Error creating the input stream: The socket is closed, not connected or the input has been shutdown");

            }

            myReader = new BufferedReader(new InputStreamReader(input));
            key = generateKey(128);
        }
    }

    /**
     * Parametrized constructor to inject variables. This constructor is being
     * used into Test files.
     *
     * @param newSocket
     * @param newWriter
     * @param newReader
     */
    public Client(Socket newSocket, PrintWriter newWriter, BufferedReader newReader) {
        mySocket = newSocket;
        myWriter = newWriter;
        myReader = newReader;
    }

    /**
     * Send the message and current date from client to server
     *
     * @param message The message to send to server
     */
    public void sendMessage(String message) {
        String[] strs = message.split("/secret ");
        if (strs.length != 1) {
            try {
                myWriter.println(encrypt(strs[1]));
            } catch (Exception ex) {
                LOGGERCLIENT.log(Level.FINE, ex.toString(), ex);
            }
        } else {
            myWriter.println(message);
        }
    }
    public String pickDates(){
         LocalDateTime timeNow = LocalDateTime.now();
         DateTimeFormatter formaterDate = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        String timeString = timeNow.format(formaterDate).toString();
        String strLenght = timeString.lenght();
        return timeString.substring(strLenght - 20, strLenght - 16);
        
    }

    public void sendChatMessage(String message) {
        LocalDateTime timeNow = LocalDateTime.now();
        DateTimeFormatter formaterDate = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        String timeString = timeNow.format(formaterDate).toString();
        String endString = timeString + "|" + message;
        
        sendMessage(endString);
    }

    /**
     * Get the message from server
     *
     * @return Message sent by server
     * @throws ClientException if an I/O error occurs when reading a line.
     */
    public String getMessage() throws ClientException {
        String line;

        try {
            line = myReader.readLine();
        } catch (IOException ex) {
            LOGGERCLIENT.log(Level.FINE, ex.toString(), ex);
            throw new ClientException("Error reading line.");
        }

        return line;
    }

    public void testencrypt() throws NoSuchAlgorithmException {
        String cipherText = null;
        String plainText = null;
        try {
            cipherText = encrypt(input);
        } catch (Exception ex) {
            LOGGERCLIENT.log(Level.FINE, ex.toString(), ex);
        }
        try {
            plainText = decrypt(cipherText);
        } catch (Exception ex) {
            LOGGERCLIENT.log(Level.FINE, ex.toString(), ex);
        }
        System.out.println(cipherText);
        System.out.println(plainText);
    }

    public SecretKey generateKey(int n) throws NoSuchAlgorithmException {
        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
        keyGenerator.init(n);
        return keyGenerator.generateKey();
    }

    public IvParameterSpec generateIv() {
        byte[] iv = new byte[16];
        new SecureRandom().nextBytes(iv);
        return new IvParameterSpec(iv);
    }

    public String encrypt(String input) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        Cipher cipher = Cipher.getInstance(algorithm);
        cipher.init(Cipher.ENCRYPT_MODE, key, ivParameterSpec);
        byte[] cipherText = cipher.doFinal(input.getBytes());
        return Base64.getEncoder()
                .encodeToString(cipherText);
    }

    public String decrypt(String cipherText) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        Cipher cipher = Cipher.getInstance(algorithm);
        cipher.init(Cipher.DECRYPT_MODE, key, ivParameterSpec);
        byte[] plainText = cipher.doFinal(Base64.getDecoder().decode(cipherText));
        return new String(plainText);
    }
}
