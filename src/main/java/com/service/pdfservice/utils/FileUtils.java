package com.service.pdfservice.utils;

import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Collection;

import org.springframework.web.multipart.MultipartFile;

import com.service.pdfservice.InMemoryDB;
import com.service.pdfservice.model.PDFFile;

public class FileUtils {
  private static final String PDF_CONTENT_TYPE = "application/pdf";
  public static ValidationResult validatePDF(MultipartFile file) {
    if (file.isEmpty()) {
      return ValidationResult.EMPTY_FILE;
    }
    if (!PDF_CONTENT_TYPE.equals(file.getContentType())) {
      return ValidationResult.INVALID_CONTENT_TYPE;
    }

    String fileName = file.getOriginalFilename();
    byte[] fileContent;
    try {
      fileContent = file.getBytes();
    } catch (IOException e) {
      return ValidationResult.INVALID_FILE_CONTENT;
    }

    if (!isUnique(fileName, fileContent)) {
      return ValidationResult.DUPLICATE;
    }
    return ValidationResult.SUCCESS;
  }

  private static boolean isUnique(String newFileName,
                                  byte[] newFileContent) {

    Collection<PDFFile> currentPdfs = InMemoryDB.getAllPdfs().values();

    for (PDFFile currentPdf : currentPdfs) {
      if (currentPdf.name.equalsIgnoreCase(newFileName) &&
          generateChecksum(currentPdf.filecontent).equals(generateChecksum(newFileContent))) {
          return false;
      }
    }
    return true;
  }

  private static String generateChecksum(byte[] filecontent) {
    byte[] hash;
    try {
      hash = MessageDigest.getInstance("MD5").digest(filecontent);
    } catch (NoSuchAlgorithmException e) {
      throw new RuntimeException(e);
    }
    return new BigInteger(1, hash).toString(16);
  }
}
