package org.example.service;

import org.example.model.ClientSocket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.time.LocalDateTime;
import java.util.*;

public class HandlerClients implements Runnable {

    private ServerService server;

    private List<ClientSocket> clients = new ArrayList<ClientSocket>();
    private Socket socket;
    private BufferedReader inMassage;
    private PrintWriter outMassage;
    private int count;

    public HandlerClients(Socket socket, ServerService server) {
        try {
            count++;
            this.socket = socket;
            this.server = server;
            this.inMassage = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.outMassage = new PrintWriter(socket.getOutputStream(), true);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void run() {
        try {
            int counter = server.count;
            String name = server.clients.get(counter - 1).getName();
            String clientWord;
            server.sendMassageForAllClients("New Client " + name + " is add.");
            System.out.println("New Client " + name + " is add.");
            while (!(clientWord = inMassage.readLine()).equals("exit")) {
                System.out.println(name + ": " + clientWord);
                server.sendMassageForAllClients(name + ": " + clientWord);
            }
            server.sendMassageForAllClients("Client " + name + " is leave.");
            System.out.println("Client " + name + " is leave.");
            this.close(this, server.clients.get(counter - 1));
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void sendMassage(String massage) {
        outMassage.println(massage);
    }

    public void close(HandlerClients handlerClients, ClientSocket clientSocket) {
        server.removeClient(handlerClients, clientSocket);
        count--;
        server.count--;
    }

}

