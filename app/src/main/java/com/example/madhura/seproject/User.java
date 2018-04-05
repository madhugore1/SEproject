package com.example.madhura.seproject;

import java.util.ArrayList;

/**
 * Created by Madhura on 18-Mar-18.
 */

public class User {

    // model user class
    public String email, name;
    public ArrayList booked_tickets;

    public User(String email) {
        this.email = email;
    }

}
