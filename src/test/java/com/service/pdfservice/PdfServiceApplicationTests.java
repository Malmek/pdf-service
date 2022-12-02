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

  @Test
  public void testBasicUploadAndFetch() throws Exception {
    MockMultipartFile multipartFile = new MockMultipartFile("file", "testfile.pdf",
                                                            "text/plain", "Spring Framework".getBytes());
    this.mvc.perform(multipart("/").file(multipartFile))
      .andExpect(status().isOk());

    this.mvc.perform(get("/")).andExpect(content().string("This is the file: testfile.pdf"));

  }

  @Test
  public void testUploadEmptyFile() throws Exception {
    MockMultipartFile multipartFile = new MockMultipartFile("file", (byte[]) null);
    this.mvc.perform(multipart("/").file(multipartFile))
      .andExpect(status().is(HttpStatus.BAD_REQUEST.value()));
  }

}
