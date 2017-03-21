package chat.room;

import java.util.ArrayList;

public class Kaveri {
    private ArrayList<String> friendnames = new ArrayList();
    private ArrayList<Integer> IDs = new ArrayList();
    
    public Kaveri() {
    }
    
    public void addFriend(String nimi,int id) {
        this.friendnames.add(nimi);
        this.IDs.add(id);
    }
    public String ToString() {
        return " " + this.friendnames + " " + this.IDs;
    }
    public ArrayList<String> getFriendnames() {
        System.out.println(this.friendnames);
        return this.friendnames;
    }
}
