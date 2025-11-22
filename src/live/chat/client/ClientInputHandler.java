/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package live.chat.client;

import java.net.Socket;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 *
 * @author panos
 */
public class ClientInputHandler implements Runnable {

    private Socket serverInstance;
    private InputStream serverIn;
    private OutputStream serverOut;
    private BufferedReader buffer;
    private String line;
    private boolean stopExecution;

    public ClientInputHandler(Socket serverInstance) throws IOException {
        this.serverInstance = serverInstance;
        this.serverIn = this.serverInstance.getInputStream();
        this.serverOut = this.serverInstance.getOutputStream();
        this.buffer = new BufferedReader(new InputStreamReader(System.in));
        this.line = "";
        this.stopExecution = false;
    }

    public void run() {
        try {
            while (!this.serverInstance.isClosed()) {
                System.out.println("give input");
                line = buffer.readLine(); //do it in a thread it hangs here and wait for input and wont print incoming messages
                byte[] msg = line.getBytes();
                serverOut.write(msg); // write -1 does not work because it is 255 maybe it is the representation using 2's complement
                serverOut.write(4);
                if (line.equals("\\bye")) {
                    this.serverInstance.close();
                    this.serverIn.close();
                    this.serverOut.close();
                    this.buffer.close();
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
            System.exit(1);
        }
    }

    public boolean isStopExecution() {
        return stopExecution;
    }

    public void setStopExecution(boolean stopExecution) {
        this.stopExecution = stopExecution;
    }
    
    
}
