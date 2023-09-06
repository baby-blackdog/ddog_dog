package com.babyblackdog.ddogdog.review;

import org.springframework.data.domain.Page;

public interface ReviewService {

  /**
   * 특정 roomId 에 따른 모든 리뷰 반환
   *
   * @param roomId
   * @return Page<Review>
   */
  Page<Review> findReviewsByRoomId(long roomId);

}