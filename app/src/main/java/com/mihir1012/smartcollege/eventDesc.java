package com.mihir1012.smartcollege;

public class eventDesc {
    private String eventName,eventDate,eventDescription;

    public eventDesc(){
    }

    public eventDesc(String eventName,String eventDate, String eventDescription){
        this.eventName=eventName;
        this.eventDate=eventDate;
        this.eventDescription=eventDescription;
    }

    public String getEventName() {
        return eventName;
    }
    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getEventDate() {
        return eventDate;
    }
    public void setEventDate(String eventDate) {
        this.eventDate = eventDate;
    }

    public String getEventDescription() {
        return eventDescription;
    }
    public void setEventDescription(String eventDescription) {
        this.eventDescription = eventDescription;
    }
}
