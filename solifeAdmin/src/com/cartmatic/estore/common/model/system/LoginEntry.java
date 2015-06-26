package com.cartmatic.estore.common.model.system;

import java.io.Serializable;
import java.util.Date;

/**
 * for the record the information for user login 
 * @author csx
 *
 */
public class LoginEntry implements Serializable{
    //user ip
    private String ip="";
    //times fail to login 
    private int visitedTimes=0;
    //current user visited. 
    private Date visitedDateTime=new Date();
    
    public LoginEntry(){
        
    }
   
    public String getIp() {
        return ip;
    }
    public void setIp(String ip) {
        this.ip = ip;
    }
    public Date getVisitedDateTime() {
        return visitedDateTime;
    }
    public void setVisitedDateTime(Date visitedDateTime) {
        this.visitedDateTime = visitedDateTime;
    }
    public int getVisitedTimes() {
        return visitedTimes;
    }
    public void setVisitedTimes(int visitedTimes) {
        this.visitedTimes = visitedTimes;
    }
}
