package org.example.service;

import org.example.model.ClientSocket;

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
    public List<ClientSocket> clients = new ArrayList<ClientSocket>();
    private List<HandlerClients> clientsFlow = new ArrayList<HandlerClients>();


    public ServerService() {

        try {
            serverSocket = new ServerSocket(8008);
            System.out.println("Server is started! Welcome!");

            while (true) {
                socket = serverSocket.accept();
                count++;
                HandlerClients handlerClients = new HandlerClients(socket, this);
                clients.add(new ClientSocket("User - " + count, LocalDateTime.now(), socket));
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
                throw new RuntimeException(e);
            }
        }
    }

    public void sendMassageForAllClients(String massage) {
        for (HandlerClients hc : clientsFlow) {
            hc.sendMassage(massage);
        }
    }

    public void removeClient(HandlerClients handlerClients, ClientSocket clientSocket) {
        clientsFlow.remove(handlerClients);
        clients.remove(clientSocket);
    }

}
