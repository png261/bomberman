package com.png261.bomberman.networking;

public class Client {

    public String id;
    public String name;
    public boolean ready;

    public Client(final String id, final String name, final boolean ready) {
        this.id = id;
        this.name = name;
        this.ready = ready;
    }
}
