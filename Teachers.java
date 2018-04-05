package com.example.ronanlina.attendancechecker;

/**
 * Created by Ronan Lina on 12/03/2018.
 */

public class Teachers {

    //member vars
    private String teacherid;
    private String email;
    private String password;

    //constructor
    public Teachers(String teacherid, String email, String password) {
        this.teacherid = teacherid;
        this.email = email;
        this.password = password;
    }

    //getters

    public String getTeacherid() {
        return teacherid;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword(){
        return password;
    }
}
