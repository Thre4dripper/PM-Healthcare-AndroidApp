package com.example.pmhealthcare.database;

import android.net.Uri;

public class RecordDetails {
    String name;
    String imageID;
    int type;

    public RecordDetails(String name, String imageID, int type) {
        this.name = name;
        this.imageID = imageID;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageID() {
        return imageID;
    }

    public void setImageID(String imageID) {
        this.imageID = imageID;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
