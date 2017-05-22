package chat.room;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.*;
import java.net.*;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Server {


    private static int uniqueId;

    private ArrayList<ClientThread> al;

    private SimpleDateFormat sdf;

    private int port;

    private boolean keepGoing;

    private ArrayList<keskustelu_id> ki = new ArrayList<keskustelu_id>();

    private ArrayList<Log> message = new ArrayList<Log>();
    private int s;

    public Server(int port) {
        this.port = port;
        sdf = new SimpleDateFormat("HH:mm");
        al = new ArrayList<ClientThread>();
    }

    public void start() {
        keepGoing = true;

        try {
            ServerSocket serverSocket = new ServerSocket(port);

            while (keepGoing) {
                display("Server waiting for clients on port " + port + ".");

                Socket socket = serverSocket.accept();
                if (!keepGoing) {
                    break;
                }
                ClientThread t = new ClientThread(socket);
                al.add(t);
                t.start();
            }
            try {
                serverSocket.close();
                for (int i = 0; i < al.size(); ++i) {
                    ClientThread tc = al.get(i);
                    try {
                        tc.sInput.close();
                        tc.sOutput.close();
                        tc.socket.close();
                    } catch (IOException ioE) {

                    }
                }
            } catch (Exception e) {
                display("Exception closing the server and clients: " + e);
            }
        } catch (IOException e) {
            String msg = sdf.format(new Date()) + "Exception on new ServerSocket: " + e + "\n";
            display(msg);
        }
    }

    private void display(String msg) {
        String time = sdf.format(new Date()) + " " + msg;
        System.out.println(time);

    }


      private void addToLog(String msg)  throws IOException {
            Log asd = new Log(msg);
            message.add(asd);
            WriteToFile();
        }

    private synchronized void broadcast(String message, int friend, int user) throws IOException {
        String time = sdf.format(new Date());
        String messageLf = time + " " + message + "\n";
        System.out.print(messageLf);
        addToLog(messageLf);

        //Käy läpi kaikki käyttäjät ja lähettää niille viestit
        for (int i = al.size(); --i >= 0;) {

            ClientThread ct = al.get(i);
            if (ct.id == friend || ct.id == user) {
                if (!ct.writeMsg(messageLf)) {
                    al.remove(i);
                    display("Disconnected Client " + ct.username + " removed from list.");
                }
            }
        }
    }

    synchronized void remove(int id) {
        for (int i = 0; i < al.size(); ++i) {
            ClientThread ct = al.get(i);

            if (ct.id == id) {
                al.remove(i);
                return;
            }
        }
    }

    public static void main(String[] args) {
        int portNumber = 1500;

        switch (args.length) {
            case 1:
                try {
                    portNumber = Integer.parseInt(args[0]);
                } catch (Exception e) {
                    System.out.println("Invalid port number.");
                    System.out.println("Usage is: > java Server [portNumber]");
                }
            case 0:
                break;
            default:
                System.out.println("Usage is: > java Server [portNumber]");
        }

        Server server = new Server(portNumber);
        server.start();
    }

    class ClientThread extends Thread {
        Socket socket;
        ObjectInputStream sInput;
        ObjectOutputStream sOutput;
        int id;
        String username;
        String fUser;
        ChatMessage cm;
        String date;
        keskustelu_id keskId = new keskustelu_id();
        int friend_id;

        ClientThread(Socket socket) {
            id = ++uniqueId;
            this.socket = socket;

            System.out.println("Thread trying to create Object Input/Output Streams");
            try {
                sOutput = new ObjectOutputStream(socket.getOutputStream());
                sInput = new ObjectInputStream(socket.getInputStream());
                username = (String) sInput.readObject();
                fUser = (String) sInput.readObject();
                display(username + " just connected.");

                if (ki.size() == 0){
                    keskId.setKenelle(fUser);
                    keskId.setKuka(username);
                    keskId.setKuka_id(id);
                    ki.add(keskId);
                }else {
                    for (int i = 0; i <= ki.size(); i++) {
                        if(i != ki.size()) {
                            keskustelu_id kis = ki.get(i);
                            if (username.contains(kis.getKuka()) || fUser.contains(kis.getKuka())) {
                                if (username.contains(kis.getKenelle()) || fUser.contains(kis.getKenelle())){
                                    if (username.contains(kis.getKenelle())){
                                        if (kis.getKenen_id() == 0){
                                            kis.setKenen_id(id);
                                        }
                                    }else if(username.contains(kis.getKuka())){
                                        if (kis.getKuka_id() == 0){
                                            kis.setKuka_id(id);
                                        }
                                    }
                                    break;
                                }
                            }
                        }
                        if (i == ki.size()) {
                            keskId.setKenelle(fUser);
                            keskId.setKuka(username);
                            keskId.setKuka_id(id);
                            ki.add(keskId);
                            break;
                        }

                    }
                }
            } catch (IOException e) {
                display("Exception creating new input/output Streams: " + e);
            } catch (ClassNotFoundException e) {
            }
            date = new Date().toString() + "\n";
        }

        public void run() {

            boolean keepGoing = true;
            while (keepGoing) {
                try {
                    for (int i = 0; i < ki.size(); i++) {
                        keskustelu_id kis = ki.get(i);
                        if (username.contains(kis.getKuka()) || fUser.contains(kis.getKuka())) {
                            if (username.contains(kis.getKenelle()) || fUser.contains(kis.getKenelle())) {
                                if (fUser.contains(kis.getKuka())){
                                    friend_id = kis.getKuka_id();
                                }else if(fUser.contains(kis.getKenelle())){
                                    friend_id = kis.getKenen_id();
                                }
                            }
                        }
                    }
                    cm = (ChatMessage) sInput.readObject();
                } catch (IOException e) {
                    display(username + " Exception reading Streams: " + e);
                    break;
                } catch (ClassNotFoundException e2) {
                    break;
                }

                String message = cm.getMessage();

                switch (cm.getType()) {

                    case ChatMessage.MESSAGE:
                {
                    try {
                        broadcast(username + ": " + message, friend_id, id);
                    } catch (IOException ex) {
                        Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }

                        break;
                    case ChatMessage.LOGOUT:
                        display(username + " disconnected with a LOGOUT message.");
                        keepGoing = false;
                        break;
                    case ChatMessage.WHOISIN:
                        writeMsg("List of the users connected at " + sdf.format(new Date()) + "\n");

                        for (int i = 0; i < al.size(); ++i) {
                            ClientThread ct = al.get(i);

                            writeMsg((i + 1) + ")" + ct.username + " since " + ct.date);
                        }
                        break;
                }
            }

            remove(id);
            close();
        }

        private void close() {
            try {
                if (sOutput != null) {
                    sOutput.close();
                }
            } catch (Exception e) {
            }
            try {
                if (sInput != null) {
                    sInput.close();
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



        private boolean writeMsg(String msg) {
            if (!socket.isConnected()) {
                close();
                return false;
            }

            try {
                sOutput.writeObject(msg);


            } catch (IOException e) {
                display("error sending message to " + username);
                display(e.toString());
            }

            return true;
        }

    }

    public boolean WriteToFile() throws IOException {

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        LocalDate localDate = LocalDate.now();

        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        String json = gson.toJson(message);

        try (FileWriter writer = new FileWriter("Log\\"+dtf.format(localDate)+".json")) {

            writer.write(json);
        } catch (IOException e) {
            System.out.println("Writing failed " + e.getMessage());
        }

        return false;
    }
    public class keskustelu_id{
        private String Kuka = null;
        private String kenelle = null;
        private int id = 0;
        private int kuka_id = 0;
        private int kenen_id = 0;

        public int getId() {
            id++;
            return id;
        }

        public String getKenelle() {
            return kenelle;
        }

        public String getKuka() {
            return Kuka;
        }

        public int getKuka_id() {
            return kuka_id;
        }

        public int getKenen_id() {
            return kenen_id;
        }

        public void setKenen_id(int kenen_id) {
            this.kenen_id = kenen_id;
        }

        public void setKuka_id(int kuka_id) {
            this.kuka_id = kuka_id;
        }

        public void setKuka(String kuka) {
            Kuka = kuka;
        }

        public void setKenelle(String kenelle) {
            this.kenelle = kenelle;
        }

    }
    public void käy_läpi_ihmiset(){

    }

}
