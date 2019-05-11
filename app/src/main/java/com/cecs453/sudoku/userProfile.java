package com.cecs453.sudoku;

public class userProfile {
    public String displayname;
    public String uid;
    public int highscore;
    public String profile_photo;
    public userProfile(){

    }

    public userProfile(String displayname, String uid, int highscore, String profile_photo){
        this.displayname = displayname;
        this.uid = uid;
        this.highscore = highscore;
        this.profile_photo = profile_photo;
    }

    public String getPhoto() {
        return profile_photo;
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
