package com.example.ipapp.object.institution;

public class Member {
    private String username;
    private int userID;
    private Role role;

    public Member(){
        this.role = null;
    }

    public Member setUsername(String username){
        this.username = username;
        return this;
    }

    public Member setRole(Role role) {
        this.role = role;
        return this;
    }

    public Member setUserID(int userID) {
        this.userID = userID;
        return this;
    }

    public int getUserID() {
        return userID;
    }

    public Role getRole() {
        return role;
    }

    public String getUsername() {
        return username;
    }
}
