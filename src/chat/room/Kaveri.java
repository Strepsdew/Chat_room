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
        String k = "";
        String l = "";
        int i = 0;
        for (String friendname : friendnames) {
            k += " " + friendname;
            l += " " + this.IDs.get(i);
            i++;
            System.out.println(friendname);
        }
        k += l;
        return k;
    }
    public ArrayList<String> getFriendnames() {
        System.out.println(this.friendnames);
        return this.friendnames;
    }
    public ArrayList<Integer> getIds(){
        return this.IDs;
    }
}
