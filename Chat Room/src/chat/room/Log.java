package chat.room;

public class Log {
    public String message;
    public String user;
      Log(String message) {
        this.user = user;
        this.message = message;
    }
      
      
      //might use in the future but for now they are useless
//      public String getuser(String user)
//{
//    this.user = user;
//    
//    return this.user;
//}
//      
//      public String getuser(){
//    return this.user;
//}
 
public String getmessage(){
     
       return this.message;      
}            
public String getmessage(String message)
{
     this.message = message;

    return this.message;
}
public String toString() {
        return this.message;
    }
    
    
}
