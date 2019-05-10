package com.cecs453.sudoku;

public class userProfile {
    public String displayname;
    public String uid;
    public int highscore;
    public userProfile(){

    }
    public userProfile(String displayname, String uid, int highscore){
        this.displayname = displayname;
        this.uid = uid;
        this.highscore = highscore;
    }

    public String getDisplayname() {
        return displayname;
    }

    public int getHighscore() {
        return highscore;
    }

    public String getUid() {
        return uid;
    }
}
