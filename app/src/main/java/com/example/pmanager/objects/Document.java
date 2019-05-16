package com.example.pmanager.objects;


import android.arch.persistence.room.Entity;

import java.io.Serializable;

@Entity
public class Document implements Serializable {

    private static final long serialVersionUID = -7367289796391092618L;

    private String name;
    private Integer number;
    private String password;
    private String login;

    public Document(String name,String password, String login) {
        super();
        this.name = name;
        this.password = password;
        this.login = login;
    }

    public Document() {

    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    @Override
    public String toString() {
        return name;
    }

}