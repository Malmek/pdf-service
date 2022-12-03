package com.service.pdfservice.utils;

import org.springframework.web.multipart.MultipartFile;

public class FileUtils {
  private static final String PDF_CONTENT_TYPE = "application/pdf";
  public static ValidationResult validatePDF(MultipartFile file){
    if (file.isEmpty()){
      return ValidationResult.EMPTY_FILE;
    }

    if (!PDF_CONTENT_TYPE.equals(file.getContentType())){
      return ValidationResult.INVALID_CONTENT_TYPE;
    }

    return ValidationResult.SUCCESS;
  }
}
