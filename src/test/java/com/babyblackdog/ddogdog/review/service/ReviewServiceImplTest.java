package com.babyblackdog.ddogdog.review.service;

import com.babyblackdog.ddogdog.review.service.dto.ReviewResult;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
class ReviewServiceImplTest {

    @Autowired
    private ReviewService service;

    @Test
    @DisplayName("[registerReview] 유효한 리뷰 정보로 생성할 수 있다.")
    void createReview_SuccessOnValid() {
        // Given & When
        ReviewResult savedReviewResult = service.registerReview(1L, 2L, "객실에 모기가 많았습니다.", 2.5);

        // Then
        assertThat(savedReviewResult).isNotNull();
        assertThat(savedReviewResult.content()).isEqualTo("객실에 모기가 많았습니다.");
        assertThat(savedReviewResult.rating()).isEqualTo(2.5, within(0.0001));
    }
}