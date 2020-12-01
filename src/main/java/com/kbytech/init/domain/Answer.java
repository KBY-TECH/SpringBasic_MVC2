package com.kbytech.init.domain;

import org.springframework.lang.NonNull;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

@Entity
public class Answer {

    @Id  // pk
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 자동증가 autoIncrement
    private Long id;

    //foreignKey
    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name="fk_answer_username"))
    private User username;

    // board one -> answer many
    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name="fk_answer_board"))
    private Board board;


    @Lob // 해당 255글자 이상 쓰겟다.
    @NonNull
    private String contents;
    private LocalDateTime createDate;

    public Answer(User username, Board board, @NonNull String contents) {
        this.username = username;
        this.board = board;
        this.contents = contents;
        this.createDate = LocalDateTime.now();
    }

    public String getFormattiedCreateDate(){
        if(createDate == null){
            return "";
        }
        return createDate.format(DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm:ss"));
    }

    // 해당 equals 생성 -> equals
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Answer answer = (Answer) o;
        return id.equals(answer.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public Answer() {

    }

    @Override
    public String toString() {
        return "Answer{" +
                "id=" + id +
                ", username=" + username +
                ", contents='" + contents + '\'' +
                ", createDate=" + createDate +
                '}';
    }

}
