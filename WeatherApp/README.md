# Weather App
This project is a weather application that uses a third-party API and includes a smart caching system with Redis. From this project, I learned how to work with external APIs and make applications faster by using in-memory caching.

# Features
### Third-Party API: 
- The app gets current weather information from the Visual Crossing Weather API. This shows how to connect to and use data from another service.
### Caching with Redis: 
- The app uses Redis to store weather data temporarily. This helps to speed up the app by reducing the number of requests made to the weather API. Cached data is updated automatically after a set time.
### Spring Boot:
- The app is created using Spring Boot, which helps build Java applications quickly. It includes features like RESTful web services and cache management.
### Environment Variables:
- Sensitive information like API keys is handled through environment variables, which makes the app safer and easier to set up in different environments.
# What I Learned
### API Integration:
- I learned how to connect to and use APIs to get data for an application.
### Caching:
- I implemented caching with Redis to make the app run faster and more efficiently.
# Setup
### Clone :
- git clone https://github.com/TraperRoku/Backend-Projects.git
### Configure environment 
- Make sure Redis is installed  
### Use a tool like Postman or a web browser to access the app at:
- http://localhost:8080/weather/Yourlocation

