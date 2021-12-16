package com.example.be_velog_team11.model;

import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@MappedSuperclass // 이 클래스를 상속해서 사용하면, 사용된걸 컬럼으로 인식해랏!!
@EntityListeners(AuditingEntityListener.class) // 계속 주시하고 있다가, 수정이 일어나면 자동으로 반영해!
public abstract class Timestamped {

    @CreatedDate // 생성일자임을 나타냄
    private String createdAt;

    @LastModifiedDate // 마지막 수정일자임을 나타냄
    private String modifiedAt;

    //insert 되기전에 date 날짜 형식 변환
    @PrePersist
    public void onPrePersist(){
        this.createdAt = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));
        this.modifiedAt = this.modifiedAt;
    }

    //update 되기 전에 date 날짜 형식 변환
    @PreUpdate
    public void onPreUpdate(){
        this.modifiedAt = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));
    }
}

