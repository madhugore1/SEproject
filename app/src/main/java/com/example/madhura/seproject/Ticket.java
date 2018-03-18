package com.example.madhura.seproject;

/**
 * Created by Madhura on 18-Mar-18.
 */

public class Ticket {

    public String ticket_id;
    public String user_email;
    public String source;
    public String destination;

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String timestamp;
    private int amount, tickets, fare;

    public String getUser_email() {
        return user_email;
    }

    public void setUser_email(String user_email) {
        this.user_email = user_email;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getTickets() {
        return tickets;
    }

    public void setTickets(int tickets) {
        this.tickets = tickets;
    }

    public int getFare() {
        return fare;
    }

    public void setFare(int fare) {
        this.fare = fare;
    }

    public String getTicket_id() {
        return ticket_id;
    }

    public void setTicket_id(String ticket_id) {
        this.ticket_id = ticket_id;
    }

    public Ticket(String ticket_id, String user_email, String source, String destination, int amount, int tickets, int fare, String timestamp) {
        this.ticket_id = ticket_id;
        this.user_email = user_email;
        this.source = source;
        this.destination = destination;
        this.amount = amount;
        this.tickets = tickets;
        this.fare = fare;
        this.timestamp = timestamp;
    }
}
