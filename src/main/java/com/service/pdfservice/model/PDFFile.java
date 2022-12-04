package com.service.pdfservice.model;

public class PDFFile {

  public final String name;
  public final byte[] filecontent;

  public PDFFile(String name,
                 byte[] filecontent) {
    this.name = name;
    this.filecontent = filecontent;
  }
}
