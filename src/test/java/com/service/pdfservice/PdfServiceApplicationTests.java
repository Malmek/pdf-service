package com.service.pdfservice;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.ContentResultMatchers;

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
  public void testUploadAndFetch() throws Exception {
    MockMultipartFile multipartFile = new MockMultipartFile("file", "testfile.txt",
                                                            "text/plain", "Spring Framework".getBytes());
    this.mvc.perform(multipart("/").file(multipartFile))
      .andExpect(status().isOk());

    this.mvc.perform(get("/")).andExpect(content().string("This is the file: testfile.txt"));


  }
}
