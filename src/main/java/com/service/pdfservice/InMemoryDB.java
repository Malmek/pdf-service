package com.service.pdfservice;

import java.util.HashMap;
import java.util.Map;

import org.springframework.lang.Nullable;

import com.service.pdfservice.model.PDFFile;

public class InMemoryDB {

  private static Map<Long, PDFFile> persistedPDFs = new HashMap<>();
  private static long index = 0;

  public static Map<Long, PDFFile> getAllPdfs() {
    return persistedPDFs;
  }

  @Nullable
  public static PDFFile getPdf(long id) {
    return persistedPDFs.get(id);
  }

  public static long storePdf(PDFFile pdf) {
    persistedPDFs.put(index, pdf);
    index++;
    //ugly fake db hack
    return index - 1;
  }

  public static void clear() {
    persistedPDFs = new HashMap<>();
    index = 0;
  }
}
