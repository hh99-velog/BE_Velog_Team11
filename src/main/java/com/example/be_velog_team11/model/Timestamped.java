package com.example.be_velog_team11.model;

import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@Getter
@MappedSuperclass // 이 클래스를 상속해서 사용하면, 사용된걸 컬럼으로 인식해랏!!
@EntityListeners(AuditingEntityListener.class) // 계속 주시하고 있다가, 수정이 일어나면 자동으로 반영해!
public abstract class Timestamped {

    @CreatedDate // 생성일자임을 나타냄
    private LocalDateTime createdAt;

    @LastModifiedDate // 마지막 수정일자임을 나타냄
    private LocalDateTime modifiedAt;
}

