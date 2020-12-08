package com.kbytech.init.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;

@Entity
public class User extends AbstractEntity {
    /*@Id  // pk
    @JsonProperty
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 자동증가 autoIncrement
    private Long id;*/

    @JsonProperty
    @Column(nullable = false) // Not Null
    private String username;
    @JsonProperty
    private String email;


    // @ 무시..
    @JsonIgnore
    private String pw2;

    @JsonIgnore
    private String pw;

    public boolean idMatch(Long newId)
        {
            if(newId==null)
                return false;
            return newId.equals(getId());
        }
//   Win: Alt + Insert (get/set)
    public boolean pwMatch(String newPw)
    {
        if(newPw==null)
            return false;
        return newPw.equals(pw);
    }

    @Override
    public String toString() {
        return
                "User{" +
                "username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", pw='" + pw + '\'' +
                ", pw2='" + pw2 + '\'' +
                "super String is "+super.toString()+
                '}';
    }



    public void setUsername(String username) {
        this.username = username;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPw(String pw) {
        this.pw = pw;
    }

    public void setPw2(String pw2) {
        this.pw2 = pw2;
    }


    public void update(User updateuser) {
        this.username=updateuser.username;
        this.email=updateuser.email;
    }

}