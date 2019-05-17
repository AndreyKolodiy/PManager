package com.example.pmanager.model;

public class Account {

    private int id;
    private String name;
    private String password;
    private String login;

    public Account() {
    }

    public Account(String name, String password, String login)
    {
        super();
        this.name = name;
        this.password = password;
        this.login = login;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    @Override
    public String toString() {
        return "Account[id=" + id + ", name = " + name + ", password =" + password
                + "login" + login + "]";
    }
}
