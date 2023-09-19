package com.sparta.team14project.review.entity;

import com.sparta.team14project.order.entity.Delivery;
import com.sparta.team14project.review.dto.ReviewRequestDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class OrderReview {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "order_id")
    private Delivery delivery;

    private String title;
    private int star;
    private String details;
    private String image;

    public OrderReview(ReviewRequestDto requestDto) {
        this.title = requestDto.getTitle();
        this.star = requestDto.getStar();
        this.details = requestDto.getDetail();
        this.image= requestDto.getImage();
    }

    public void update(ReviewRequestDto requestDto) {
        this.title = requestDto.getTitle();
        this.star = requestDto.getStar();
        this.details = requestDto.getDetail();
        this.image= requestDto.getImage();
    }
}
