package org.example.service;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class ClientService {


    private Socket serverSocket;
    private BufferedReader inMassage;
    private PrintWriter outMassage;
    private Scanner scanner;

    public ClientService() {

        try {
            serverSocket = new Socket("localhost", 8008);
            inMassage = new BufferedReader(new InputStreamReader(serverSocket.getInputStream()));
            outMassage = new PrintWriter(new OutputStreamWriter(serverSocket.getOutputStream()), true);
            scanner = new Scanner(System.in);
            new Thread(new Runnable() {
                @Override
                public void run() {
                    while (true) {
                        try {
                            System.out.println(inMassage.readLine());
                        } catch (IOException e) {
                            System.out.println("Connected is down.");
                        }
                    }
                }
            }).start();

            while (scanner.hasNext()) {
                printMsg();
            }
            if (scanner.nextLine().equals("exit")) {
                serverSocket.close();
            }
        } catch (IOException e) {
            System.out.println("Connected is down.");
        }
    }

    public void printMsg() {
        outMassage.println(scanner.nextLine());
    }
}
