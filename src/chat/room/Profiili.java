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
    private String email;
    
    public Profiili(int id,String Username,String etunimi,String sukunimi, String nickname,int ika, String bio,String location,String email) {
        this.ID = id;
        this.Username = Username;
        this.etunimi = etunimi;
        this.sukunimi = sukunimi;
        this.nickname = nickname;
        this.ika = ika;
        this.bio = bio;
        this.location = location;
        this.email = email;
    }
    public int getId() { // kertoo ideen
        return this.ID;
    }
    public String getUsername() { // kertoo usernamen
        return this.Username;
    }
    public String getEtunimi() { // kertoo etunimen
        return this.etunimi;
    }
    public String getSukunimi() { // kertoo sukunimen
        return this.sukunimi;
    }
    public String getNickname() { // kertoo nicknamen
        return this.nickname;
    }
    public int getIka() { // kertoo iän
        return this.ika;
    }
    public String getBio() { // kertoo bion
      
        return this.bio;
    }
    public String getLocation() { // kertoo sijainnin
        return this.location;
    }
    public String getEmail() { // kertoo sähköposti osoitteen
        return this.email;
    }
    public String toString() {
        return this.ID+ "," + this.Username +","+ this.etunimi + ","+ this.sukunimi + ","+ this.nickname + ","+ this.ika + ","+ this.bio + "," + this.location + "," + this.email;
    }
}

