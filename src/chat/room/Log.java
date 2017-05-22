package chat.room;

public class Log {

    public String message;
  
      Log(String message) {
          String[] splitted_msg = message.split("\n");
        this.message = splitted_msg[0];
    }


public String toString() {

        return this.message;

    }
    
    
}
