package com.mycompany.mavenproject3;
//sementara gini dulu
public class User {
    private int id;
    private String username;
    private String email;
    private String address;
    private int phoneNumber;

    public User(int id, String username, String email, String address, int phoneNumber) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.address = address;
        this.phoneNumber = phoneNumber;
    }

    public int getId() { 
        return id; 
    }

    public void setId(int id) { 
        this.id = id; 
    }

    public String getUsername() { 
        return username; 
    }

    public void setUsername(String username) { 
        this.username = username; 
    }

    public String getAddress() { 
        return address; 
    }

    public void setAddress(String address) { 
        this.address = address; 
    }

    public String getEmail() { 
        return email; 
    }

    public void setEmail(String email) { 
        this.email = email; 
    }

    public int getphoneNumber() { 
        return phoneNumber; 
    }

    public void setphoneNumber(int phoneNumber) { 
        this.phoneNumber = phoneNumber; 
    }
}