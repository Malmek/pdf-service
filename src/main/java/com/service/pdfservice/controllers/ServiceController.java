package com.service.pdfservice.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@RestController
public class ServiceController {

  private MultipartFile temporaryFile;

  @GetMapping("/")
  public String index() {
    return "This is the file: " + temporaryFile.getOriginalFilename();
  }

  @PostMapping("/")
  public String handleFileUpload(@RequestParam("file") MultipartFile file,
                                 RedirectAttributes redirectAttributes) {

    temporaryFile = file;
    redirectAttributes.addFlashAttribute("message",
                                         "Upload successful: " + file.getOriginalFilename());

    return "redirect:/";
  }

}
