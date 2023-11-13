package com.rms.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import java.time.LocalDateTime;

@Entity
@Table(name = "reviews")
public class ReviewEntity extends BaseEntity {

    @ManyToOne
    private UserEntity writtenBy;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    @Column(nullable = false)
    private LocalDateTime postedOn;

    @Column(nullable = false)
    private int score;

    public ReviewEntity() {
    }

    public UserEntity getWrittenBy() {
        return writtenBy;
    }

    public void setWrittenBy(UserEntity writtenBy) {
        this.writtenBy = writtenBy;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime getPostedOn() {
        return postedOn;
    }

    public void setPostedOn(LocalDateTime postedOn) {
        this.postedOn = postedOn;
    }
}
