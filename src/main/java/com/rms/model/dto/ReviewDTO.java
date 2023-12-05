package com.rms.model.dto;

import jakarta.validation.constraints.*;

public class ReviewDTO {

    private Long id;

    @Size(min = 5, max = 200, message = "Съдържанието трябва да е между 5 и 200 символа")
    @NotNull
    private String content;

    @Min(value = 1, message = "Оценката трябва да е минимум 1")
    @Max(value = 5, message = "Оценката трябва да е максимум 5")
    @NotBlank
    private String score;

    private String username;

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

    public ReviewDTO() {
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }
}
