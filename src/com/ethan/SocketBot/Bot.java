package com.ethan.SocketBot;

public class Bot {
    
    public static void main(String[] args) throws Exception{
        Sockets sock = new Sockets();
         sock.connect("irc.cmpct.info", "Testie", "#bawt", 6667);
    }
}
