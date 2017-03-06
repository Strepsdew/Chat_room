package chat.room;
import java.io.*;
import java.net.*;
import java.text.SimpleDateFormat;
import java.util.*;

public class Server {
    
    private static int uniqueId;
    
    private ArrayList<ClientThread> al;
    
    private SimpleDateFormat sdf;
    
    private int port;
    
    private boolean keepGoing;
    
    
    public Server(int port){
        this.port = port;
        sdf = new SimpleDateFormat("HH:mm:ss");
        al = new ArrayList<ClientThread>();
    }
    
   public void start(){
       keepGoing = true;
       
       try{
           ServerSocket serverSocket = new ServerSocket(port);
           
           while(keepGoing){
               display("Server waiting for clients on port "+port+".");
               
                       Socket socket = serverSocket.accept();
                       
                       if(!keepGoing)
                                           break;
                       ClientThread t = new ClientThread(socket);
                       al.add(t);
                       t.start();
           }
           try{
                serverSocket.close();
                for(int i = 0; i <al.size(); ++i){
                    ClientThread tc = al.get(i);
                    try{
                        tc.sInput.close();
                        tc.sOutput.close();
                        tc.socket.close();
                    }catch(IOException ioE){
                        
                    }
                }
           }catch(Exception e){
               display("Exception closing the server and clients: "+e);
           }
       }
       catch(IOException e){
           String msg = sdf.format(new Date())+ "Exception on new ServerSocket: "+e+"\n";
           display(msg);
       }
   }
    
            
    
}
