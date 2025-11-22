/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package live.chat.client;
import java.io.*;
import java.net.*;


public class LiveChatClient {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException{
        // the client to connect to the server port and send messages while message is not \bye and while condition is socket !isClosed()
        // if message is \bye sent the message and close the connection
        System.out.println("welcome to the early version of live chat app v0.1");
        BufferedReader buffer = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("give server port :");
        int port = Integer.parseInt(buffer.readLine());
        System.out.println("port is "+port);
        System.out.println("give a username :");
        String username = buffer.readLine();
        System.out.println("username is : "+username);
        Socket connectToServer = new Socket("localhost",port);
        InputStream serverIn = connectToServer.getInputStream();
        OutputStream serverOut = connectToServer.getOutputStream();
        System.out.println("connected successfully to the server@"+connectToServer.getInetAddress().toString());
        ClientInputHandler handler = new ClientInputHandler(connectToServer);
        String line = "";
        String msgStr = "";
        int data;
        Thread handleClientInput = new Thread(handler);
        handleClientInput.start();
        while(connectToServer.isConnected() && !connectToServer.isClosed()){
            
            try{
            while((data = serverIn.read()) != 4){ // it's ok if no msg its cooming it does not hang here
                msgStr+=(char)data;
            }}catch(SocketException ex){
                System.out.println("socket is closed");
                break;
            }
            System.out.println(msgStr);
            msgStr = "";
        }
        System.out.println("Disconnected from the server.");
        System.out.println("Run the program again if you want to reconnect.");
        System.exit(0);
    }
    
}
