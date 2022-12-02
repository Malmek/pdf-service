package com.service.pdfservice.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import com.service.pdfservice.FileManager;

@RestController
public class PDFServiceController {


  @GetMapping("/list")
  public String listFiles() {

    if (FileManager.getFile() == null || FileManager.getFile().isEmpty()) {
      return "There are no files yet";
    }
    return "This is the file: " + FileManager.getFile().getOriginalFilename();
  }

  @PostMapping("/")
  public String handleFileUpload(@RequestParam("file") MultipartFile file) {
  try {
    FileManager.save(file);
  } catch (Exception e) {
    System.out.println(e.getMessage());
    throw new ResponseStatusException(
      HttpStatus.BAD_REQUEST, e.getMessage());
  }

  return HttpStatus.OK.toString();
  }
}
