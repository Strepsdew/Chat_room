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
    
    

Map<Integer, ClientThread> clients = new HashMap<Integer, ClientThread> ();

    private static int uniqueId;

    private ArrayList<ClientThread> al;

    private SimpleDateFormat sdf;

    private int port;

    private boolean keepGoing;


    private ArrayList<Log> message = new ArrayList<Log>();
    
    private int myId;
    private Database k = new Database();
    
    private int friendsId;

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

      private void add(String msg)  throws IOException {
            Log asd = new Log(msg);
            message.add(asd);
            WriteToFile();
        }

    private synchronized void broadcast(String message, String friend, String user) throws IOException {
        String time = sdf.format(new Date());
        String messageLf = time + " " + message + "\n";
        System.out.print(messageLf);
        add(messageLf);


        for (int i = al.size(); --i >= 0;) {
            
            ClientThread ct = al.get(i);
            if (friend.equals(ct.username) || ct.username.equals(user)) {
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

        ClientThread(Socket socket) {
            id = ++uniqueId;
            this.socket = socket;
            System.out.println("Thread trying to create Object Input/Output Streams");
            try {
                sOutput = new ObjectOutputStream(socket.getOutputStream());
                sInput = new ObjectInputStream(socket.getInputStream());

                username = (String) sInput.readObject();
                fUser = (String) sInput.readObject();
                display(username + " just connected and is trying to talk to: "+fUser);
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
                        broadcast(username + ": " + message, fUser, username);
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
        
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        LocalDate localDate = LocalDate.now();
        
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        String json = gson.toJson(message);

        try (FileWriter writer = new FileWriter(localDate+".json")) {

            writer.write(json);
        } catch (IOException e) {
            System.out.println("Writing failed " + e.getMessage());
        }

//        PrintWriter writer = new PrintWriter(new FileWriter("kayttaja.txt", true));
//        writer.println(this.list);
//        writer.close();
        return false;
    }

}
