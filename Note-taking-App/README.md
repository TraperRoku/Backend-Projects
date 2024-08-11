# Note-Taking App
This project is a note-taking application that supports file uploads, grammar checking, and Markdown processing. It uses various services to increase functionality, such as grammar checking and file storage, and it is built using Spring Boot. The app provides RESTful endpoints to handle notes efficiently.
# Features
### File Upload: 
- The app allows users to upload files using RESTful endpoints. It handles file storage through a dedicated service, demonstrating how to manage file data within an application.
### Grammar Checking:
- It integrates a grammar-checking service using LanguageTool, allowing users to check the grammar of their notes. This feature increase note accuracy and correctness.
### Markdown Processing:
- The app includes functionality to convert Markdown content into HTML, using a dedicated service for Markdown processing. This allows users to store and render text notes.
# What I Learned
### File Management:
- I learned how to handle file uploads and storage within a web application, providing users with the ability to manage files effectively.
### Service Integration:
- I gained experience in integrating external services, such as LanguageTool for grammar checking, to increase application functionality.
### Markdown Handling:
- I learned how to process and convert Markdown to HTML, allowing for richer text formatting within the application.
# Setup
### Clone :
- git clone https://github.com/TraperRoku/Backend-Projects.git
### Configure environment 
#### Use a tool like Postman or a web browser to access the app at:
- For file uploads: http://localhost:8080/api/files/upload
- For grammar checking: http://localhost:8080/api/notes/check-grammar
- For Markdown conversion: http://localhost:8080/api/notes/convert-html
- For Markdown save: http://localhost:8080/api/notes/save-markdown

