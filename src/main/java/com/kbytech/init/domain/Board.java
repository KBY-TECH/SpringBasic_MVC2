package com.kbytech.init.domain;

import org.springframework.core.annotation.Order;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;

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
    @Lob  // 255자가 넘어가면 대입이 안들어가는데 255이상을 받기위해.
    private String contents;
    private LocalDateTime createDate;
    // h2 db에는 binary 타입으로 되어있으므로 변경할거임.-> timestamp 매핑
    // 컨버팅 필요, 해당 클래스 필요.

    @OneToMany(mappedBy = "board") // 매핑 대상의 필드 이름. Answer.java 의 private Board board;
    @OrderBy("id ASC") // 단변 id 의 오름차순.
    private List<Answer> comment;


    public Board(User username, String title, String contents) {
        super();
        this.username = username;
        this.title = title;
        this.contents = contents;
        this.createDate=LocalDateTime.now();
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


    public String getFormattiedCreateDate(){
        if(createDate == null){
            return "";
        }
        return createDate.format(DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm:ss"));
    }

    public void update(String title, String contents) {
        this.title=title;
        this.contents=contents;
    }


    public boolean isSameWriter(User loginUser) {
            //user의 getUser메소드도 필요없음.
        return this.username.equals(loginUser);

        // 같은 사용자 임에도 불구하고 false가 날거임... 왜?
        // equalse 인스턴스는 다르지만 값이 같을 경우에 같은 놈으로 인식하는게 이퀄즈.
        //equals Override 필요.
    }


}
