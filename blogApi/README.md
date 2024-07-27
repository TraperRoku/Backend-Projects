# Project Description
This is a simple example blogging platform API built using Spring Boot and MySQL. It covers basic CRUD operations for managing articles.

## Features
- **Create a new article:** Allows users to create and publish a new article.
- **Retrieve a list of articles:** Fetches all articles from the database.
- **Retrieve a single article by ID:** Fetches details of a specific article using its ID.
- **Update an existing article:** Allows users to update the content of an existing article.
- **Delete an article:** Allows users to delete an article by its ID.

## Technologies Used
- **Java:** The programming language used to build the application.
- **Spring Boot:** A framework that simplifies the development of Java applications.
- **Spring Data JPA:** Used for data access and ORM.
- **MySQL:** The relational database used to store articles.
- **Postman:** Used for testing the API endpoints.

## Installation and Setup

1. **Clone the repository:**
   ```bash
   git clone https://github.com/TraperRoku/Backend-Projects.git
   cd Backend-Projects/blogApi

2. **Set up MySQL:**
- Ensure MySQL is installed and running.
- Create a database named blog:

3. **Configure application properties:**
- Update src/main/resources/application.properties with your MySQL credentials:
  ```bash
  spring.datasource.url = jdbc:mysql://localhost:3306/blog
  spring.datasource.username = YOURUSERNAME
  spring.datasource.password = YOURPASSWORD
  spring.jpa.hibernate.ddl-auto = update
  spring.jpa.show-sql = true
  
4. **Build and run the application:**
   ```bash
   ./mvnw spring-boot:run
   
5. **Test the API using Postman:**

- Import the Postman collection provided in the repository or create requests to the following endpoints:
```bash
GET /api/articles: Retrieve all articles.
GET /api/articles/{id}: Retrieve a single article by ID.
POST /api/articles/create: Create a new article.
PUT /api/articles/{id}: Update an article.
DELETE /api/articles/{id}: Delete an article.

{
  "title": "Article Title",
  "content": "Article content",
  "author": "Author name",
  "createdAt": "2024-07-27T00:00:00",
  "updatedAt": "2024-07-27T00:00:00"
}

  
  
