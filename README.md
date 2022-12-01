# my pdf-service!

Criterias

- Provide an API to which PDF files can be uploaded.
- Validate that an uploaded file is a PDF file.
- Validate that the uploaded file is max 2MB in size.
- Persist uploaded PDF-files in a simple manner. In-memory storage is OK.
- Validate that a file has not already been uploaded. Files are considered as duplicates if they have the same name and same checksum.
- Allow uploaded PDF-files to be downloaded through another endpoint. 
