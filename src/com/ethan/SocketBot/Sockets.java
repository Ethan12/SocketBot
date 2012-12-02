package com.ethan.SocketBot;

import com.google.gson.Gson;
import java.io.*;
import java.net.Socket;
import java.net.URL;
import java.net.URLEncoder;
import java.util.List;

public class Sockets {
    
    public String line = null;
    public String joinMSG = "Hey";
    public BufferedWriter in;
    public BufferedReader out;
    
    public void sendMessage(String channel, String message) throws IOException{
        in.write("PRIVMSG " + channel + " : " + message + "\r\n");
        in.flush();
    }

      public void connect(String server, String nick, String channel, Integer port) throws Exception {

        
        Socket socket = new Socket(server, port);
        in = new BufferedWriter(
                new OutputStreamWriter(socket.getOutputStream( )));
        out = new BufferedReader(
                new InputStreamReader(socket.getInputStream( )));
        
        in.write("NICK " + nick + "\r\n");
        in.write("USER " + nick + " " + nick + " " + nick + ": " + nick + "\r\n");
        in.flush();
        
        
        while ((line = out.readLine( )) != null) {
            if (line.indexOf("004") >= 0) {
                break;
            }
            else if (line.indexOf("433") >= 0) {
                System.out.println("Nickname already taken.");
                return;
            }
        }
        
        in.write("JOIN " + channel + "\r\n");
        in.write("PRIVMSG " + channel + " : " + joinMSG +  "\r\n");
        in.flush( );
        
        while ((line = out.readLine()) != null) {
            if (line.toLowerCase().startsWith("PING ")) {
                in.write("PONG " + line.substring(5) + "\r\n");
                in.write("PRIVMSG " + channel + " :ping!\r\n");
                in.flush();
            }else if (line.contains("PRIVMSG " + channel)){
             String[] data = line.split(" ");
             String cmdChar = data[3].substring(1);
             String strArgument = "";
             for(String e : data){
                 if(e != null){
                 strArgument = e;
             }else{
                strArgument = null;
               }
             }
                switch (cmdChar.toUpperCase()) {
                  case "!SAY":
                      in.write("PRIVMSG " + channel + " : " + strArgument + "\r\n");
                      in.flush();
                      break;
                  case "!SEARCH":
                      String google = "http://ajax.googleapis.com/ajax/services/search/web?v=1.0&q=";
                      String search = strArgument;
                      String charset = "UTF-8";

                      URL url = new URL(google + URLEncoder.encode(search, charset));
                      Reader reader = new InputStreamReader(url.openStream(), charset);
                      GoogleResults results = new Gson().fromJson(reader, GoogleResults.class);

                      String title = results.getResponseData().getResults().get(0).getTitle();
                      String urld = results.getResponseData().getResults().get(0).getUrl();
                      sendMessage(channel, "Results of Google Search , Title: " + title + "URL:" + urld);
                      break;
                  default:
                      System.out.println(line);
                      break;
                }
            }else {
                System.out.println(line);
            }
        }
    }

}

  class GoogleResults {

    private ResponseData responseData;
    public ResponseData getResponseData() { return responseData; }
    public void setResponseData(ResponseData responseData) { this.responseData = responseData; }
    @Override
    public String toString() { return "ResponseData[" + responseData + "]"; }

    static class ResponseData {
        private List<Result> results;
        public List<Result> getResults() { return results; }
        public void setResults(List<Result> results) { this.results = results; }
        @Override
        public String toString() { return "Results[" + results + "]"; }
    }

    static class Result {
        private String url;
        private String title;
        public String getUrl() { return url; }
        public String getTitle() { return title; }
        public void setUrl(String url) { this.url = url; }
        public void setTitle(String title) { this.title = title; }
        @Override
        public String toString() { return "Result[url:" + url +",title:" + title + "]"; }
    }

}
