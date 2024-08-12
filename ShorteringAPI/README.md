# URL Shortening API
This project is a URL shortening application built using Spring Boot. It provides a RESTful API for generating short URLs from long URLs and retrieving the original URLs using the short version. The app handles URL expiration and returns error messages for invalid or expired URLs.

## Features
- Short URL Generation: Users can generate short URLs by providing the original URL and an optional expiration date. The application encodes the URL and stores it in a database with a unique short version.

- Original URL Retrieval: The app allows users to retrieve the original URL using the short version. If the URL has expired or does not exist, the application returns an appropriate error message.

- Expiration Handling: Each URL can have a specified expiration date. The application checks the expiration date before redirecting to the original URL and handles expired URLs gracefully.

## What I Learned
- URL Encoding: Learned how to implement URL encoding using Google's Guava library to create unique short URLs.

# Setup
- Clone the repository:

git clone https://github.com/TraperRoku/URL-Shortening-API.git



## Generate Short URL: Use a tool like Postman to send a POST request to the following endpoint with a JSON body containing the original URL and optional expiration date:

### POST http://localhost:8080/generateShortUrl
- Request Body Example:
{
  "url": "https://example.com",
  "expirationDate": "2024-12-31T23:59:59"
}
## Retrieve Original URL: Send a GET request to the following endpoint with the short URL path:
- GET http://localhost:8080/{shortUrl}
