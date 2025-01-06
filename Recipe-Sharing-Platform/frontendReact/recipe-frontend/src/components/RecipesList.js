import React, { useEffect, useState } from "react";
import { request } from "../axios_helper";

const RecipesList = () => {
  const [recipes, setRecipes] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  useEffect(() => {
    const fetchRecipes = async () => {
      try {
        const response = await request("GET", "/api/recipes");
        console.log("Fetched recipes:", response.data);
        setRecipes(Array.isArray(response.data) ? response.data : [response.data]);
      } catch (error) {
        console.error("Error fetching recipes:", error);
        setError("Failed to load recipes");
      } finally {
        setLoading(false);
      }
    };

    fetchRecipes();
  }, []);

  if (loading) return <div>Loading...</div>;
  if (error) return <div>Error: {error}</div>;

  return (
    <div className="container">
      <h1>Recipes</h1>
      <div className="row">
        {recipes.map((recipe) => (
          <div key={recipe.id} className="col-md-4 mb-4">
            <div className="card h-100">
              {recipe.images && recipe.images.length > 0 ? (
                 <>
                 {recipe.images[1] && console.log(recipe.images[1].fileName)}
                <img
                
                  src={`http://localhost:8080/api/recipes/uploads/images/${recipe.images[1].fileName}`}

                  className="card-img-top"
                  alt={recipe.title}
                  style={{ height: '200px', objectFit: 'cover' }}
                  onError={(e) => {
                    console.error("Image load error:", e);
                    e.target.onerror = null;
                    e.target.src = 'https://via.placeholder.com/400x300?text=No+Image';
                  }}
                />
                </>
              ) : (
                <div 
                  className="card-img-top bg-secondary text-white d-flex align-items-center justify-content-center"
                  style={{ height: '200px' }}
                >
                  No Image Available
                </div>
              )}
              <div className="card-body">
                <h5 className="card-title">{recipe.title}</h5>
                <p className="card-text">{recipe.description}</p>
                <p className="card-text">
                  <small className="text-muted">
                    By {recipe.chef?.chefLogin} 
                  </small>
                </p>
              </div>
            </div>
          </div>
        ))}
      </div>
    </div>
  );
};

export default RecipesList;