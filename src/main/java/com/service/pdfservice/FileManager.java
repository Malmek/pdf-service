package com.service.pdfservice;

import org.springframework.web.multipart.MultipartFile;

import com.service.pdfservice.utils.FileUtils;
import com.service.pdfservice.utils.ValidationResponse;

public class FileManager {
  private static MultipartFile temporaryFile;

  public static void save(MultipartFile file) throws RuntimeException{

    ValidationResponse validationResponse = FileUtils.validatePDF(file);

    switch(validationResponse){
      case SUCCESS: temporaryFile = file;
      case EMPTY_FILE: throw new IllegalArgumentException("File is empty");
      case INVALID_CONTENT_TYPE: throw new IllegalArgumentException("Not a valid PDF");
      default: throw new RuntimeException("Unknown error occurred");
    }

  }

  public static MultipartFile getFile() {
    return temporaryFile;
  }
}
