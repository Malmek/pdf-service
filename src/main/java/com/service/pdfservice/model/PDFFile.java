package com.service.pdfservice.model;

public class PDFFile {

  public final String name;
  public final byte[] filecontent;

  public PDFFile(String name,
                 byte[] filecontent) {
    this.name = name;
    this.filecontent = filecontent;
  }

  private String getChecksum() {
    byte[] bytes = filecontent;

    StringBuilder sb = new StringBuilder();
    for (byte aByte : bytes) {
      sb.append(Integer.toString((aByte & 0xff) + 0x100, 16).substring(1));
    }
    return sb.toString();
  }
}
