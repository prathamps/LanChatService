/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package server.conn;

import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * @author INTEL
 */
public class Server {
    public static Server instance;
    private ServerSocket serverSock;
    private Socket socket;
    private InputStreamReader isr;
    private OutputStreamWriter osw;
    
    public static Server getInstance() {
        if(instance == null) {
            instance = new Server();
        }
        return instance;
    }
    
    public void startServer () throws Exception {
        serverSock = new ServerSocket(3535);
        new Thread(new Runnable() {
            @Override
            public void run() {
                while(true) {
                    try {
                        System.out.println("Server online...");
                        socket = serverSock.accept();
                        isr = new InputStreamReader(socket.getInputStream());
                        osw = new OutputStreamWriter(socket.getOutputStream());
                        System.out.println("Client Server Connection OK");
                        listenForMessages();
                        break;
                    } catch (Exception e) {
                         System.err.println("Server Listening Error!");
                    }
                }
            }
        }).start();
    }
    
    
    public void listenForMessages() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while(true) {
                    try {
                        char[] charMessage = new char[10];
                        if(isr.read(charMessage, 0, charMessage.length) != -1) {
                            String message = new String(charMessage);
                            System.out.println(message);
                        }                       
                    } catch (Exception e) {
                        System.err.println(e.getMessage());
                    }
                }
            }
        }).start();
    }
    
    public void sendMessage(String message) throws Exception {
        osw.write(message);
        osw.flush();
    }
}
