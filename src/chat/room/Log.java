package chat.room;

public class Log {
    public String message;
    public String user;
      Log(String user,String message) {
        this.user = user;
        this.message = message;
    }
      public String getkayttaja(){
    return this.user;
}
public String getpisteet(){
     
       return this.message;      
}            
public String getkayttaja(String user)
{
    this.user = user;
    
    return this.user;
}
public String getpisteet(String message)
{
     this.message = message;

    return this.message;
}
public String toString() {
        return this.user +" "+ this.message;
    }
    
    
}
