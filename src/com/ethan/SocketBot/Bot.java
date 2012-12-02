package com.ethan.SocketBot;

public class Bot {
    
    public static void main(String[] args) throws Exception{
        Sockets sock = new Sockets();
        // e.g sock.connect("localhos", "Nick", "#channel", 6667);
         sock.connect("", "", "", 6667);
    }
}
