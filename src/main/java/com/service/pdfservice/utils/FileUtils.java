package com.service.pdfservice.utils;

import org.springframework.web.multipart.MultipartFile;

public class FileUtils {
  private static final String PDF_CONTENT_TYPE = "application/pdf";
  public static ValidationResponse validatePDF(MultipartFile file){

    if (file.isEmpty()){
      return ValidationResponse.EMPTY_FILE;
    }

    if (!PDF_CONTENT_TYPE.equals(file.getContentType())){
      return ValidationResponse.INVALID_CONTENT_TYPE;
    }

    return ValidationResponse.SUCCESS;
  }
}
