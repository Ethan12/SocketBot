//  ____             _        _   ____        _   
// / ___|  ___   ___| | _____| |_| __ )  ___ | |_ 
// \___ \ / _ \ / __| |/ / _ \ __|  _ \ / _ \| __|
//  ___) | (_) | (__|   <  __/ |_| |_) | (_) | |_ 
// |____/ \___/ \___|_|\_\___|\__|____/ \___/ \__|
//                               By Ethan.

package com.ethan.SocketBot;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Bot {
     
    public static void main(String[] args) throws Exception, IOException{
        Properties prop = new Properties();
        InputStream in = Bot.class.getResourceAsStream("config.properties");
        prop.load(in);
        
        Sockets sock = new Sockets();
         sock.connect(prop.getProperty("server"),
          prop.getProperty("nick"),
           prop.getProperty("channel"),
            Integer.parseInt(prop.getProperty("port")));
    }
}
