package com.service.pdfservice;

import org.springframework.web.multipart.MultipartFile;

public class FileUtils {
  public static ValidationResponse validatePDF(MultipartFile file){

    if (file.isEmpty()){
      return ValidationResponse.EMPTY_FILE;
    }

    return ValidationResponse.SUCCESS;
  }
}
