package model;

public class Credential {
    public String site;
    public String username;
    public String password;

    public Credential(String site, String username, String password) {
        this.site = site;
        this.username = username;
        this.password = password;
    }
}