package com.service.pdfservice;

import java.io.IOException;

import org.springframework.lang.Nullable;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.service.pdfservice.model.PDFFile;
import com.service.pdfservice.utils.FileUtils;
import com.service.pdfservice.utils.ValidationResult;

public class FileManager {
  private static PDFFile pdfFile;

  public static void save(MultipartFile file) throws RuntimeException{
    ValidationResult validationResult = FileUtils.validatePDF(file);

    switch (validationResult) {
      case SUCCESS -> storePDF(file);
      case EMPTY_FILE -> throw new IllegalArgumentException("File is empty");
      case INVALID_CONTENT_TYPE -> throw new IllegalArgumentException("Not a valid PDF");
      default -> throw new RuntimeException("Unknown error occurred");
    }
  }

  @Nullable
  public static PDFFile getPDFFile(String filename) {
    if (filename.equals(pdfFile.name)){
      return pdfFile;
    }
    return null;
  }
  @Nullable
  public static PDFFile getPDFFiles() {
    return pdfFile;
  }

  public static void storePDF(MultipartFile file) {
    String fileName = StringUtils.cleanPath(file.getOriginalFilename());

    try {
      pdfFile = new PDFFile(fileName, file.getBytes());
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
}
