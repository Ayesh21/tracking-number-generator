package com.tracking.number.generator.exception;

import java.time.LocalDateTime;

/** The type Error response. */
public record ErrorResponse(String error, String message, String path, LocalDateTime timestamp) {

  public static ErrorResponse of(String error, String message, String path) {
    return new ErrorResponse(error, message, path, LocalDateTime.now());
  }
}
