package org.example.service;

import org.example.model.Client;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ServerService {

    private ServerSocket serverSocket;
    private Socket socket;
    public int count = 0;
    public List<Client> clients = new ArrayList<Client>();
    private List<HandlerClients> clientsFlow = new ArrayList<HandlerClients>();


    public ServerService() {

        try {
            serverSocket = new ServerSocket(8008);
            System.out.println("Server is started! Welcome!");

            while (true) {
                socket = serverSocket.accept();
                count++;
                HandlerClients handlerClients = new HandlerClients(socket, this);
                clients.add(new Client("User - " + count, LocalDateTime.now(), socket));
                clientsFlow.add(handlerClients);
                new Thread(handlerClients).start();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                socket.close();
                System.out.println("Server is  stopped!");
                serverSocket.close();
            } catch (IOException e) {
                System.out.println("Connected is down.");
            }
        }
    }

    public void sendMassageForAllClients(String massage) {
        for (HandlerClients hc : clientsFlow) {
            hc.sendMassage(massage);
        }
    }

    public void removeClient(HandlerClients handlerClients, Client clientSocket) {
        clientsFlow.remove(handlerClients);
        clients.remove(clientSocket);
    }

}
