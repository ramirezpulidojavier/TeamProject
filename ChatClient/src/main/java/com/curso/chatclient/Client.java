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
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Base64;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

/**
 * Class Client with the methods: sendMessage and getMessage
 *
 * @author pcorrales2010
 */
public class Client {

    // CREATE LOGGER
    // private final Logger LOGGER = Logger.getLogger(Client.class.getName());
    Socket mySocket;
    PrintWriter myWriter;
    BufferedReader myReader;

    private SecretKeySpec secretKey;
    private String secret = "m93s75&ps3c1";
    private byte[] key;

    private final static Logger LOGGERCLIENT = Logger.getLogger(Client.class.getName());

    /**
     * Constructor that receive a Socket and fill myWriter and myReader private
     * variables.
     *
     * @param newSocket
     * @throws ClientException when an I/O error occurs while creating the
     * output/input stream.
     */
    public Client(Socket newSocket) throws ClientException {
        if (newSocket != null) {
            mySocket = newSocket;
            InputStream input;
            OutputStream output;

            try {
                output = newSocket.getOutputStream();
            } catch (IOException ex) {
                // Program logger
                throw new ClientException("Error creating the output stream: the socket could not be connected");
            }

            try {
                myWriter = new PrintWriter(output, true);
                input = mySocket.getInputStream();
            } catch (SecurityException | IllegalArgumentException | IOException ex) {
                LOGGERCLIENT.log(Level.FINE, ex.toString(), ex);
                // Program logger
                throw new ClientException("Error creating the input stream: The socket is closed, not connected or the input has been shutdown");

            }

            myReader = new BufferedReader(new InputStreamReader(input));
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

        myWriter.println(encrypt(message));
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
            // Program logger
            throw new ClientException("Error reading line.");
        }

        return decrypt(line);
    }

    public void setKey(String myKey) {
        MessageDigest sha = null;
        try {
            key = myKey.getBytes("UTF-8");
            sha = MessageDigest.getInstance("SHA-1");
            key = sha.digest(key);
            key = Arrays.copyOf(key, 16);
            secretKey = new SecretKeySpec(key, "AES");
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    public String encrypt(String strToEncrypt) {
        String[] msg = strToEncrypt.split("/secret ");

        if (msg[1] != null) {
            try {
                setKey(secret);
                Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
                cipher.init(Cipher.ENCRYPT_MODE, secretKey);
                return Base64.getEncoder()
                        .encodeToString(cipher.doFinal(msg[1].getBytes("UTF-8")));
            } catch (Exception e) {
                System.out.println("Error while encrypting: " + e.toString());
            }
        }
        return strToEncrypt;
    }

    public String decrypt(String strToDecrypt) {
        try {
            setKey(secret);
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5PADDING");
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            return new String(cipher.doFinal(Base64.getDecoder()
                    .decode(strToDecrypt)));
        } catch (Exception e) {
            System.out.println("Error while decrypting: " + e.toString());
        }
        return null;
    }

}
