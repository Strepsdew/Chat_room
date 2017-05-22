package chat.room;

import java.util.ArrayList;

public class Kaveri {
    private ArrayList<String> friendnames = new ArrayList();
    private ArrayList<Integer> IDs = new ArrayList();
    
    public Kaveri() {
    }
    
    public void addFriend(String nimi,int id) {
        // lisää kaverit arraylistoihin
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
        }
        k += l;
        return k;
    }
    public ArrayList<String> getFriendnames() { // hakee kavereitten nimet
        return this.friendnames;
    }
    public ArrayList<Integer> getIds(){ // hakee kavereitten ideet
        return this.IDs;
    }
}
