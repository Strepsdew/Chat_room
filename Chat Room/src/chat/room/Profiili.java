package chat.room;
public class Profiili {
    private int ID;
    private String Username;
    private String etunimi;
    private String sukunimi;
    private String nickname;
    private int ika;
    private String bio;
    private String location;
    
    public Profiili(int id,String Username,String etunimi,String sukunimi, String nickname,int ika, String bio,String location) {
        this.ID = id;
        this.Username = Username;
        this.etunimi = etunimi;
        this.sukunimi = sukunimi;
        this.nickname = nickname;
        this.ika = ika;
        this.bio = bio;
        this.location = location;
    }
    public int getId() {
        return this.ID;
    }
    public String getUsername() {
        return this.Username;
    }
    public String getEtunimi() {
        return this.etunimi;
    }
    public String getSukunimi() {
        return this.sukunimi;
    }
    public String getNickname() {
        return this.nickname;
    }
    public int getIka() {
        return this.ika;
    }
    public String getBio() {
        return this.bio;
    }
    public String getLocation() {
        return this.location;
    }
    public String toString() {
        return "" + this.ID + this.Username + this.etunimi + this.sukunimi + this.nickname + this.ika + this.bio + this.location;
    }
}

