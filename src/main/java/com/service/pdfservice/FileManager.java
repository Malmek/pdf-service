package com.service.pdfservice;

import java.io.IOException;
import java.util.Map;

import org.springframework.lang.Nullable;
import org.springframework.web.multipart.MultipartFile;

import com.service.pdfservice.model.PDFFile;
import com.service.pdfservice.utils.FileUtils;
import com.service.pdfservice.utils.ValidationResult;

public class FileManager {

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
  public static PDFFile getPDFFile(Long id) {
    if (id != null) {
      return InMemoryDB.getPdf(id);
    }
    return null;
  }

  @Nullable
  public static Map<Long, PDFFile> getPDFFiles() {
    return InMemoryDB.getAllPdfs();
  }

  public static void storePDF(MultipartFile file) {
    try {
      PDFFile pdfFile = new PDFFile(file.getOriginalFilename(), file.getBytes());
      InMemoryDB.storePdf(pdfFile);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
}
