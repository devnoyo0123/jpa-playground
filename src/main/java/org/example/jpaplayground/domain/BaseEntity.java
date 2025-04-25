package org.example.jpaplayground.domain;

import lombok.Getter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@MappedSuperclass
@Getter
public abstract class BaseEntity {

    @CreationTimestamp
    @Column(name = "cre_dtm", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "upd_dtm")
    private LocalDateTime updatedAt;

    @Column(name = "delete_yn", length = 1)
    private String deleteYn = "N";

    public void delete() {
        this.deleteYn = "Y";
    }
}