package com.service.pdfservice.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.service.pdfservice.FileManager;

@RestController
public class PDFServiceController {


  @GetMapping("/")
  public String index() {

    if (FileManager.getFile() == null || FileManager.getFile().isEmpty()) {
      return "no files yet";
    }
    return "This is the file: " + FileManager.getFile().getOriginalFilename();
  }

  @PostMapping("/")
  public String handleFileUpload(@RequestParam("file") MultipartFile file,
                                 RedirectAttributes redirectAttributes) {

  try {
    FileManager.save(file);
    redirectAttributes.addFlashAttribute("message",
                                         "Upload successful: " + file.getOriginalFilename());
  } catch (Exception e) {
    throw new ResponseStatusException(
      HttpStatus.BAD_REQUEST, e.getMessage());
  }

  return HttpStatus.OK.toString();

  }


}
