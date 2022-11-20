/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package conn;

import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

/**
 *
 * @author INTEL
 */
public class Client {
    private static Client instance;
    private Socket socketConn;
    private InputStreamReader isr;
    private OutputStreamWriter osw;
    
    public static Client getInstance() {
        if(instance == null) {
            instance = new Client();
        }
        return instance;
    }
    
    public void connectToServer() throws Exception {
        socketConn = new Socket("localhost", 3535);
        isr = new InputStreamReader(socketConn.getInputStream());
        osw = new OutputStreamWriter(socketConn.getOutputStream());
        
        System.out.println("Connected to server");
        listenForMessages();
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
