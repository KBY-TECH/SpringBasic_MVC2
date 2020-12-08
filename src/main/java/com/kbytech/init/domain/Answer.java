package com.kbytech.init.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.engine.internal.Cascade;
import org.springframework.lang.NonNull;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

@Entity
public class Answer extends AbstractEntity{

 /*   @Id  // pk
    @JsonProperty
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 자동증가 autoIncrement
    private Long id;*/

    //foreignKey
    @ManyToOne
    @JsonProperty
    @JoinColumn(foreignKey = @ForeignKey(name="fk_answer_username"))
    private User username;

    // board one -> answer many
    @ManyToOne
    @JsonProperty
    @JoinColumn(foreignKey = @ForeignKey(name="fk_answer_board"))
    private Board board;


    @Lob // 해당 255글자 이상 쓰겟다.
    @NonNull
    @JsonProperty
    private String contents;


//    private LocalDateTime createDate;

    public Answer(User username, Board board, @NonNull String contents) {
        this.username = username;
        this.board = board;
        this.contents = contents;
//        this.createDate = LocalDateTime.now();
    }

  /*  public String getFormattiedCreateDate(){
        if(createDate == null){
            return "";
        }
        return createDate.format(DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm:ss"));
    }*/

   /* @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Answer answer = (Answer) o;
        return id.equals(answer.id);
    }*/

    public boolean isSameWriter(User loginUser) {

        return loginUser.equals(this.username);
    }


    // 해당 equals 생성 -> equals
   /* @Override
    public int hashCode() {
        return Objects.hash(id);
    }*/

    public Answer() {

    }

    @Override
    public String toString() {
        return "Answer{" +
                "id=" +super.toString() +
                ", username=" + username +
                ", contents='" + contents + '\'' +
                +'}';
    }
}
