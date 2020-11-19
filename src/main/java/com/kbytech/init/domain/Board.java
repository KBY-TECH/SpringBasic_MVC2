package com.kbytech.init.domain;

import javax.persistence.*;

@Entity
public class Board {
    @Id  // pk
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 자동증가 autoIncrement
    private Long id;

    @Column(nullable = false) // Not Null
    private String username;
    private String title;
    private String contents;

    public Board(String username, String title, String contents) {
        super();
        this.username = username;
        this.title = title;
        this.contents = contents;
    }

    public Board() {

    }

    @Override
    public String toString() {
        return "Board{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", title='" + title + '\'' +
                ", content='" + contents + '\'' +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }
}
