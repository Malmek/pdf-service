package com.service.pdfservice;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.springframework.lang.Nullable;

import com.service.pdfservice.model.PDFFile;

public class InMemoryDB {
  private static Set<PDFFile> persistedPDFs = new HashSet<>();

  public static Set<PDFFile> getAllPdfs(){
    return persistedPDFs;
  }

  @Nullable
  public static PDFFile getPdf(String name){
    Optional<PDFFile> first = persistedPDFs.stream().filter(pdf -> pdf.name.equals(name)).findFirst();

    // unwrap that filthy Optional
    return first.orElse(null);
  }

  public static boolean storePdf(PDFFile pdf){
    persistedPDFs.add(pdf);
    return true;
  }
}
