package com.sparta.team14project.review.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ReviewRequestDto {

    private String title;
    private int star;
    private String detail;

}
