package org.example.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import java.util.List;

@JsonNaming (PropertyNamingStrategies.SnakeCaseStrategy.class)
@JsonIgnoreProperties(ignoreUnknown = true)
public class User {
    private String name;
    private String password;
    private String hashedPassword;
    private List<Ticket> bookedTickets;
    private String userId;

    public User() {}
    public User(String name, String password, String hashedPassword, List<Ticket> bookedTickets, String userId) {
        this.name=name;
        this.password=password;
        this.hashedPassword=hashedPassword;
        this.bookedTickets=bookedTickets;
        this.userId=userId;
    }

    //Getters and Setters

    public String getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }

    public String getHashedPassword() {
        return hashedPassword;
    }

    public List<Ticket> getBookedTickets() {
        return bookedTickets;
    }

    public String getPassword() {
        return password;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setHashedPassword(String hashedPassword) {
        this.hashedPassword = hashedPassword;
    }

    public void setBookedTickets(List<Ticket> bookedTickets) {
        this.bookedTickets = bookedTickets;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
