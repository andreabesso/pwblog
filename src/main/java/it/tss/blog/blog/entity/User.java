/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.tss.blog.blog.entity;

import java.io.Serializable;
import java.time.format.DateTimeFormatter;
import javax.json.Json;
import javax.json.JsonObject;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

/**
 *
 * @author andre
 */
@Entity
@Table(name = "user")
public class User extends AbstractEntity implements Serializable {

    public enum Role {
        ADMIN, USER
    }
    @Column(nullable = false)
    private String fname;
    @Column(nullable = false)
    private String lname;
    @Column(nullable = false, unique = true)
    private String email;
    @Column(nullable = false)
    private String pwd;
    @Enumerated(EnumType.STRING)
    private Role role = Role.USER;

    
    private boolean ban = false;

    public User() {
    }

    public User(String fname, String lname, String email, String pwd, Role role) {
        this.fname = fname;
        this.lname = lname;
        this.email = email;
        this.pwd = pwd;
        this.role = role;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public boolean isBan() {
        return ban;
    }

    public void setBan(boolean ban) {
        this.ban = ban;
    }

    public JsonObject toJson() {
        return Json.createObjectBuilder()
                .add("fname", this.fname)
                .add("lname", this.lname)
                .add("email", this.email)
                .add("role", this.role.toString())
                .add("ban", this.isBan())
                .build();
    }

}