# Recipe Sharing Platfrom
## 💡 Features
### Backend Functionalities
<h3> User Authentication & Authorization</h3>

- Secure login and registration system for chefs using JWT Authentication.
- Password hashing with Spring Security and BCrypt.
<h3>  Recipe Management</h3>

- Create, read, update, and delete recipes.
- Associate each recipe with a registered chef.
<h3>Image Processing</h3>

- Upload and store multiple images per recipe.
- Resize images to standard dimensions (800x600 px) using Thumbnailator.
- Serve images via REST endpoints.
<h3>Database Schema</h3>

- chef: Stores user information for chefs, including login credentials.
- recipe: Contains recipe details such as title, description, and associated images.
- recipe_image : Holds metadata for uploaded recipe images.
- recipe_step: Stores step-by-step instructions for preparing a recipe, with each step ordered by a sequence number and holds metadata for uploaded steps images.

- recipe_tags: Represents categories or tags for recipes (e.g., "vegan", "quick", "spicy") to help users filter and discover recipes.

- recipe_ingredient: Stores details about ingredients required for a recipe, including ingredient names, quantities, and units (e.g., "2 cups of flour").
  
<h3>Filtering & Pagination</h3>

- Search recipes by title, chef, tags, or creation time.

<h3>Security</h3>

- JWT-protected endpoints for creating and managing recipes.
- Public access for viewing recipes.



![image](https://github.com/user-attachments/assets/9dc48913-c2cc-4be9-bb60-3c2e569b94ba)
![image](https://github.com/user-attachments/assets/be17d851-8b55-45f0-a2b2-070608e332a5)



![image](https://github.com/user-attachments/assets/e515088f-245f-43c3-b9e4-68fc1927d78f)
![image](https://github.com/user-attachments/assets/d3768cc8-8b6a-4982-9b69-878b32214d23)

![image](https://github.com/user-attachments/assets/8a7bde2c-1147-495d-a42e-7733b95fca18)
![image](https://github.com/user-attachments/assets/0d314183-3d48-420d-80e1-9556c65d6f31)
![image](https://github.com/user-attachments/assets/e6a3806d-00d9-420b-8699-b49ded7e6635)
![image](https://github.com/user-attachments/assets/105938ba-e0e6-4010-94d5-b28cc6caf269)
![image](https://github.com/user-attachments/assets/a5bb1e3f-b94a-44d4-b389-ae79438a4c4c)
![image](https://github.com/user-attachments/assets/791e960b-cc6c-4156-bf03-3f93ff554d5b)
![image](https://github.com/user-attachments/assets/f1e56874-6628-47e2-ab29-29488a63a1c6)
![image](https://github.com/user-attachments/assets/6b3fe55c-230d-4ae1-8219-14db16a31452)
![image](https://github.com/user-attachments/assets/f309e662-b1c6-453c-8abc-c40b8670f789)

# Entity 
## chef  
![image](https://github.com/user-attachments/assets/44b44872-5f91-4df9-b950-7982e2d2e154)

## recipe
![image](https://github.com/user-attachments/assets/ec8d8535-8898-4b62-8000-96dd6d7f34ae)

## recipe_step
![image](https://github.com/user-attachments/assets/9ed2987d-bed5-486e-bc66-939dc2178b1e)
## recipe_tags
![image](https://github.com/user-attachments/assets/beb1686d-c66a-46d3-92ba-40626b4c6976)
## recipe_ingredient
![image](https://github.com/user-attachments/assets/07ba48f5-8888-4482-9c0f-8339e66f33ec)
## recipe_image
![image](https://github.com/user-attachments/assets/e2c01150-512d-487b-a6bd-26c6269cb946)





















