package com.sparta.team14project.review;

import com.sparta.team14project.review.dto.ReviewRequestDto;
import com.sparta.team14project.review.dto.ReviewResponseDto;
import com.sparta.team14project.user.login.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping({"/api"})
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    @PostMapping("/review/{orderId}")
    public ReviewResponseDto createReview(@PathVariable Long orderId, @RequestBody ReviewRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails){
        return reviewService.createReview(orderId,requestDto, userDetails.getUser());
    }

    @PutMapping("/review/{reviewId}")
    public String updateReview(@PathVariable Long reviewId, @RequestBody ReviewRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails){
        return reviewService.updateReview(reviewId,requestDto,userDetails.getUser());
    }
}
