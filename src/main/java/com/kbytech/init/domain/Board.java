package com.kbytech.init.domain;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
public class Board {
    @Id  // pk
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 자동증가 autoIncrement
    private Long id;

//    @Column(nullable = false) // Not Null
    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name="fk_board_username"))
    private User username;
    private String title;
    private String contents;
    private String createDate;



    public Board(User username, String title, String contents) {
        super();
        this.username = username;
        this.title = title;
        this.contents = contents;
        this.createDate=LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
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

    public void update(String title, String contents) {
        this.title=title;
        this.contents=contents;
    }
}
