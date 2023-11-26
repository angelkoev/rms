package com.rms.model.dto;

import jakarta.validation.constraints.*;

public class ReviewDTO {

    //    private String author;
    @Size(min = 5, max = 100, message = "Съдържанието трябва да е между 5 и 100 символа")
    @NotNull
    private String content;
//    private String postedOn;

    @Min(1)
    @Max(5)
    @NotBlank
    private String score;

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
