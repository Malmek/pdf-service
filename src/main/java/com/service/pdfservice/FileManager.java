package com.service.pdfservice;

import org.springframework.web.multipart.MultipartFile;

public class FileManager {
  private static MultipartFile temporaryFile;

  public static void save(MultipartFile file) throws RuntimeException{

    ValidationResponse validationResponse = FileUtils.validatePDF(file);

    if (ValidationResponse.SUCCESS == validationResponse) {
      temporaryFile = file;
    } else {
      throw new IllegalArgumentException("Not a valid PDF");
    }
  }

  public static MultipartFile getFile() {
    return temporaryFile;
  }
}
