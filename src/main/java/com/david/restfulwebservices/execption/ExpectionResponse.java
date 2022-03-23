package com.david.restfulwebservices.execption;

import java.util.Date;

public class ExpectionResponse {
    private Date timeStamps;
    private String messages;
    private String details;

    public ExpectionResponse(Date timeStamps, String messages, String details) {
        super();
        this.timeStamps = timeStamps;
        this.messages = messages;
        this.details = details;
    }

    public Date getTimeStamps() {
        return timeStamps;
    }

    public String getMessages() {
        return messages;
    }

    public String getDetails() {
        return details;
    }
}
