package com.babyblackdog.ddogdog.global.exception;

import static com.babyblackdog.ddogdog.global.exception.ErrorCode.INTERNAL_SERVER_ERROR;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

  private final Logger logger = LoggerFactory.getLogger(getClass());

  @ExceptionHandler(HotelException.class)
  public ResponseEntity<ErrorResponse> placeExceptionHandler(HotelException exception) {
    ErrorResponse errorResponse = ErrorResponse.of(exception.getErrorCode());
    logger.info("HotelException: {}", errorResponse);
    return ResponseEntity
        .status(errorResponse.getStatusCode())
        .body(errorResponse);
  }

  @ExceptionHandler(ReviewException.class)
  public ResponseEntity<ErrorResponse> reviewExceptionHandler(ReviewException exception) {
    ErrorResponse errorResponse = ErrorResponse.of(exception.getErrorCode());
    logger.info("ReviewException: {}", errorResponse);
    return ResponseEntity
        .status(errorResponse.getStatusCode())
        .body(errorResponse);
  }

  @ExceptionHandler(RoomException.class)
  public ResponseEntity<ErrorResponse> roomExceptionHandler(RoomException exception) {
    ErrorResponse errorResponse = ErrorResponse.of(exception.getErrorCode());
    logger.info("ReviewException: {}", errorResponse);
    return ResponseEntity
        .status(errorResponse.getStatusCode())
        .body(errorResponse);
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<ErrorResponse> internalServerErrorExceptionHandler(Exception exception) {
    ErrorResponse errorResponse = ErrorResponse.of(INTERNAL_SERVER_ERROR);
    logger.info("Exception: {}", errorResponse);
    return ResponseEntity
        .status(errorResponse.getStatusCode())
        .body(errorResponse);
  }

}