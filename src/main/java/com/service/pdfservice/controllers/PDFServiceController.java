package com.service.pdfservice.controllers;

import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

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

  @GetMapping("/uploads")
  public ResponseEntity<Map<Long, String>> listFiles() {
    Map<Long, PDFFile> files = FileManager.getPDFFiles();
    Map<Long, String> collect = files.entrySet().stream().collect(Collectors.toMap(Entry::getKey, k -> k.getValue().name));
    return new ResponseEntity<>(collect, HttpStatus.OK);
  }

  @GetMapping("/download/{fileId}")
  @ResponseBody
  public ResponseEntity<Resource> downloadFile(@PathVariable Long fileId) {
    PDFFile pDFFile = FileManager.getPDFFile(fileId);
    if (pDFFile == null) {
      throw new ResponseStatusException(
        HttpStatus.NOT_FOUND, "No file with id: " + fileId);
    }
    return ResponseEntity.ok()
      .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + pDFFile.name + "\"")
      .body(new ByteArrayResource(pDFFile.filecontent));
  }

  @PostMapping("/upload/")
  public ResponseEntity<Map<Long, String>> handleFileUpload(@RequestParam("file") MultipartFile file) {
    try {
      long id = FileManager.save(file);
      PDFFile pDFFile = FileManager.getPDFFile(id);
      return new ResponseEntity<>(Map.of(id, pDFFile.name), HttpStatus.OK);
    } catch (Exception e) {
      throw new ResponseStatusException(
        HttpStatus.BAD_REQUEST, e.getMessage());
    }
  }
}
