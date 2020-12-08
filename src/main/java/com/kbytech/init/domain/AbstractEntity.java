package com.kbytech.init.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

// Answer Board User 객체들의 상위 클래스(부모 클래스)
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class AbstractEntity {
    @Id  // pk
    @JsonProperty
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 자동증가 autoIncrement
    private Long id;

    // 자동으로 업데이트.
    @CreatedDate
    private LocalDateTime createDate;

    @LastModifiedBy
    private LocalDateTime modifyDate;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AbstractEntity that = (AbstractEntity) o;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "AbstractEntity{" +
                "id=" + id +
                ", createDate=" + createDate +
                ", ModifyDate=" + modifyDate +
                '}';
    }

    public String getFormattiedCreateDate(){
        if(createDate == null){
            return "";
        }
//        return createDate.format(DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm:ss"));
        return getFormattiedDate(createDate,"yyyy.MM.dd HH:mm:ss");

    }

    public String getFormattiedModifyDate(){
        if(modifyDate == null){
            return "";
        }
//        return modifyDate.format(DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm:ss"));
        return getFormattiedDate(modifyDate,"yyyy.MM.dd HH:mm:ss");
    }
    public String getFormattiedDate(LocalDateTime dataTime,String format){
        if(dataTime == null){
            return "";
        }
        return dataTime.format(DateTimeFormatter.ofPattern(format));
    }

}
