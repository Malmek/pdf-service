package com.service.pdfservice;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
class PdfServiceApplicationTests {

  @Autowired
  private MockMvc mvc;

  // Happy flow
  @Test
  public void testBasicUploadAndFetch() throws Exception {
    MockMultipartFile multipartFile = new MockMultipartFile("file", "testfile.pdf",
                                                            "application/pdf", "Some PDF File".getBytes());
    this.mvc.perform(multipart("/").file(multipartFile))
      .andExpect(status().isOk());

    this.mvc.perform(get("/files")).andExpect(content().string("This is the file: testfile.pdf"));

    this.mvc.perform(get("/files/testfile.pdf")).andExpect(status().isOk());

  }

  // Upload
  @Test
  public void testUploadNoFile() throws Exception {
    this.mvc.perform(multipart("/"))
      .andExpect(status().is(HttpStatus.BAD_REQUEST.value()));
  }
  @Test
  public void testUploadEmptyFile() throws Exception {
    MockMultipartFile multipartFile = new MockMultipartFile("file", (byte[]) null);
    this.mvc.perform(multipart("/").file(multipartFile))
      .andExpect(status().is(HttpStatus.BAD_REQUEST.value()));
  }
  @Test
  public void testContentType() throws Exception {
    MockMultipartFile multipartFile = new MockMultipartFile("file", "testfile.txt",
                                                            "text/plain", "Some Text File".getBytes());
    this.mvc.perform(multipart("/").file(multipartFile))
      .andExpect(status().is(HttpStatus.BAD_REQUEST.value()));
  }

  // Download

  @Test
  public void testDownloadNonExistingFile() throws Exception {
    this.mvc.perform(get("/files/testfile.pdf")).andExpect(status().is(HttpStatus.BAD_REQUEST.value()));
  }

}
