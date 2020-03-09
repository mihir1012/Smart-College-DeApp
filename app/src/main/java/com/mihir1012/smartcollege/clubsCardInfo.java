package com.mihir1012.smartcollege;

public class clubsCardInfo {
    String clubName;
    String clubActivityName;
    String clubActivityDesc;

    public clubsCardInfo(String clubName, String clubActivityName, String clubActivityDesc) {
        this.clubName = clubName;
        this.clubActivityName = clubActivityName;
        this.clubActivityDesc = clubActivityDesc;
    }

    public String getClubName() {
        return clubName;
    }

    public void setClubName(String clubName) {
        this.clubName = clubName;
    }

    public String getClubActivityName() {
        return clubActivityName;
    }

    public void setClubActivityName(String clubActivityName) {
        this.clubActivityName = clubActivityName;
    }

    public String getClubActivityDesc() {
        return clubActivityDesc;
    }

    public void setClubActivityDesc(String clubActivityDesc) {
        this.clubActivityDesc = clubActivityDesc;
    }
}
