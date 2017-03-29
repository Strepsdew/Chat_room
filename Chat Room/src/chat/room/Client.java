package chat.room;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.net.*;
import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class Client {
    
     private ArrayList<Log> messageLog = new ArrayList<Log>();

    private ObjectInputStream sInput;
    private ObjectOutputStream sOutput;
    private Socket socket;
    private Chat gui;

    private String server = "localhost", username;
    private int port = 1500;
    Database k = new Database();
    
    Client(String server, int port, String username) {
		// which calls the common constructor with the GUI set to null
		this(server, port, username, null);
	}

    Client(String server, int port, String username, Chat gui) {
        this.server = server;
        this.port = port;
        this.username = username;
        this.gui =gui;
    }
    
    public void getUsername(int id){
        username = k.getNicknameById(id);
       new Client(server,port,username,gui);
    }

    public boolean start() {
        try {
            socket = new Socket(server, port);
        } catch (Exception ec) {
            display("Error connecting to server: " + ec);
            return false;
        }
        String msg = "Connection accepted " + socket.getInetAddress() + ":" + socket.getPort();
        display(msg);
        try {
            sInput = new ObjectInputStream(socket.getInputStream());
            sOutput = new ObjectOutputStream(socket.getOutputStream());
        } catch (IOException elO) {
            display("Exception creating new Inut/output Streams: " + elO);
            return false;
        }
        new ListenFromServer().start();
        try {
            sOutput.writeObject(username);
        } catch (IOException elO) {
            display("Exception doing login : " + elO);
            disconnect();
            return false;
        }
        return true;
    }

    private void display(String msg) {
      if(gui == null){
          System.out.println(msg);
      }else{
          System.out.println(msg);
      }
    }

    void sendMessage(ChatMessage msg) {
        try {
            sOutput.writeObject(msg);
        } catch (IOException e) {
            display("Exception writing to server: " + e);
        }
    }

    private void disconnect() {
        try {
            if (sInput != null) {
                sInput.close();
            }
        } catch (Exception e) {
        }
        try {
            if (sOutput != null) {
                sOutput.close();
            }
        } catch (Exception e) {
        }
        try {
            if (socket != null) {
                socket.close();
            }
        } catch (Exception e) {
        }
    }

    public static void main(String[] args) {
        int portNumber = 1500;
        String serverAddress = "localhost";
        String userName = "Anonymous";

        switch (args.length) {
            case 3:
                serverAddress = args[2];
            case 2:
                try {
                    portNumber = Integer.parseInt(args[1]);
                } catch (Exception e) {
                    System.out.println("Invalid port number.");
                    System.out.println("Usage is: > java Client [username] [portNumber] [serverAddress] ");
                    return;
                }
            case 1:
                userName = args[0];

            case 0:
                break;

            default:
                System.out.println("USage is : > java Client[username] [portNumber] [serverAddress]");
                return;
        }
        Client client = new Client(serverAddress, portNumber, userName);

        if (!client.start()) {
            return;
        }

        Scanner scan = new Scanner(System.in);

        while (true) {
            System.out.println("> ");
            String msg = scan.nextLine();
            if (msg.equalsIgnoreCase("LOGOUT")) {
                client.sendMessage(new ChatMessage(ChatMessage.LOGOUT, ""));
                break;
            } else if (msg.equalsIgnoreCase("WHOISIN")) {
                client.sendMessage(new ChatMessage(ChatMessage.WHOISIN, ""));
            } else {
                client.sendMessage(new ChatMessage(ChatMessage.MESSAGE, msg));
            }
        }
        client.disconnect();
    }

    class ListenFromServer extends Thread {

        public void run() {
            while (true) {
                try {
                    String msg = (String) sInput.readObject();
                    if(gui == null){
                        
                    
                    System.out.println(msg);
                    System.out.println("> ");
                    }else{
                        gui.append(msg);
                    }
                } catch (IOException e) {
                    display("Server has close the connection: " + e);
                    break;
                } catch (ClassNotFoundException e2) {
                }
            }
        }
//        
//        public boolean WriteToFile() throws IOException {
//        
//        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");
//        LocalDate localDate = LocalDate.now();
//        
//        Gson gson = new GsonBuilder().setPrettyPrinting().create();
//
//        String json = gson.toJson(messageLog);
//
//        try (FileWriter writer = new FileWriter("Single"+ localDate+".json")) {
//            writer.write(json);
//        } catch (IOException e) {
//            System.out.println("Writing failed " + e.getMessage());
//        }
//
////        PrintWriter writer = new PrintWriter(new FileWriter("kayttaja.txt", true));
////        writer.println(this.list);
////        writer.close();
//        return false;
//    }
        
        //locaali log file clientille... ei toimi pitää tehä sama ku serverille
        
    }
}
