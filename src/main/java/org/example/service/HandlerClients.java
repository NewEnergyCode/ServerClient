package org.example.service;

import org.example.model.Client;

import java.io.*;
import java.net.Socket;
import java.util.*;

public class HandlerClients implements Runnable {

    private ServerService server;

    private List<Client> clients = new ArrayList<Client>();
    private Socket socket;
    private BufferedReader inMassage;
    private PrintWriter outMassage;
    private FileInputStream fileIn;
    private FileOutputStream fileOut;
    private int count;

    public HandlerClients(Socket socket, ServerService server) {
        try {
            count++;
            this.socket = socket;
            this.server = server;
            this.inMassage = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.outMassage = new PrintWriter(socket.getOutputStream(), true);
        } catch (IOException e) {
            System.out.println("Connected is down.");
        }
    }

    public void run() {
        try {
            int counter = server.count;
            String name = server.clients.get(counter - 1).getName();
            String clientWord;
            String clientPath;
            server.sendMassageForAllClients("New Client " + name + " is add.");
            System.out.println("New Client " + name + " is add.");

            while (true){
                clientWord = inMassage.readLine();
                switch (clientWord) {
                    case "exit":
                        server.sendMassageForAllClients("Client " + name + " is leave.");
                        System.out.println("Client " + name + " is leave.");
                        this.close(this, server.clients.get(counter - 1));
                    case "-file":
                        fileSaver(name);
                    default:
                        System.out.println(name + ": " + clientWord);
                        server.sendMassageForAllClients(name + ": " + clientWord);
            }
            }
        } catch (Exception e) {
            System.out.println("Connected is down.");
        }
    }

    public void sendMassage(String massage) {
        outMassage.println(massage);
    }

    public void close(HandlerClients handlerClients, Client clientSocket) {
        server.removeClient(handlerClients, clientSocket);
        count--;
        server.count--;
    }

    public void fileSaver(String name) {
        server.sendMassageForAllClients(name + ", enter path: ");
        try {
            fileIn = new FileInputStream(inMassage.readLine());
            fileOut = new FileOutputStream(new File("src/main/java/org/example/files/" + name + ".txt"),true);

            while (fileIn.available() > 0) {
                int oneByte = fileIn.read();
                fileOut.write(oneByte);

            }
            fileIn.close();
            fileOut.close();

        } catch (IOException e) {
            System.out.println("File is not found.");
        }

    }

}

