package com.service.pdfservice.utils;

public enum ValidationResult {
  SUCCESS,
  EMPTY_FILE,
  DUPLICATE,
  INVALID_CONTENT_TYPE,
  INVALID_FILE_CONTENT,
  FILE_TOO_LARGE,
}
