package com.service.pdfservice.controllers;

import java.util.Set;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import com.service.pdfservice.FileManager;
import com.service.pdfservice.model.PDFFile;

@RestController
public class PDFServiceController {


  @GetMapping("/files")
  public String listFiles() {
    Set<PDFFile> files = FileManager.getPDFFiles();
    if (files == null) {
      return "There are no files yet";
    }
    return "This is the file: " + files.stream().findFirst().map(pdf -> pdf.name).get();
  }

  @GetMapping("/files/{filename}")
  @ResponseBody
  public ResponseEntity<Resource> downloadFile(@PathVariable String filename) {
    PDFFile pDFFile = FileManager.getPDFFile(filename);
    if (pDFFile == null) {
      throw new ResponseStatusException(
        HttpStatus.BAD_REQUEST, "No file with name: " + filename);
    }
    return ResponseEntity.ok()
      .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + pDFFile.name + "\"")
      .body(new ByteArrayResource(pDFFile.filecontent));
  }

  @PostMapping("/")
  public String handleFileUpload(@RequestParam("file") MultipartFile file) {
  try {
    FileManager.save(file);
  } catch (Exception e) {
    throw new ResponseStatusException(
      HttpStatus.BAD_REQUEST, e.getMessage());
  }

  return HttpStatus.OK.toString();
  }
}
