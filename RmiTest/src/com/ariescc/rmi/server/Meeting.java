package com.ariescc.rmi.server;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Meeting {

    public String userOne;
    public String userTwo;
    public String startTime;
    public String endTime;
    public String title;

    public Meeting(String userOne, String userTwo, String startTime,
                   String endTime, String title) {
        this.userOne = userOne;
        this.userTwo = userTwo;
        this.startTime = startTime;
        this.endTime = endTime;
        this.title = title;
    }

}
