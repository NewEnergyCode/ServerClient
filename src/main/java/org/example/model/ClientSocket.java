package org.example.model;

import java.net.Socket;
import java.time.LocalDateTime;

public class ClientSocket {

    private  String name;
    private LocalDateTime localDateTimeClientAdd;
    private Socket socket;

    public ClientSocket(String name, LocalDateTime localDateTimeClientAdd, Socket socket) {
        this.name = name;
        this.localDateTimeClientAdd = localDateTimeClientAdd;
        this.socket = socket;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDateTime getLocalDateTimeClientAdd() {
        return localDateTimeClientAdd;
    }

    public void setLocalDateTimeClientAdd(LocalDateTime localDateTimeClientAdd) {
        this.localDateTimeClientAdd = localDateTimeClientAdd;
    }

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    @Override
    public String toString() {
        return "ClientSocket{" +
                "name='" + name + '\'' +
                ", localDateTimeClientAdd=" + localDateTimeClientAdd +
                ", socket=" + socket +
                '}';
    }
}
