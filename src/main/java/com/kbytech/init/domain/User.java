package com.kbytech.init.domain;

import javax.persistence.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;

@Entity
public class User {
    @Id  // pk
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 자동증가 autoIncrement
    private Long id;

    @Column(nullable = false) // Not Null
    private String username;
    private String email;
    private String pw;
    private String pw2;

    public Long getId() {
        return id;
    }
    public boolean idMatch(Long newId)
        {
            if(newId==null)
                return false;
            return newId.equals(id);
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
        return "User{" +
                "username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", pw='" + pw + '\'' +
                ", pw2='" + pw2 + '\'' +

                '}';
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getPw() {
        return pw;
    }

    public String getPw2() {
        return pw2;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id.equals(user.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}