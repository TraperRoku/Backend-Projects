import React, { useEffect, useState } from "react";
import { request } from "../axios_helper";

const RecipesList = () => {
  const [recipes, setRecipes] = useState([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    const fetchRecipes = async () => {
      try {
        const response = await request("GET", "/api/recipes");
        setRecipes(response.data);
      } catch (error) {
        console.error("Error fetching recipes:", error);
      } finally {
        setLoading(false);
      }
    };

    fetchRecipes();
  }, []);

  if (loading) return <p>Loading recipes...</p>;

  return (
    <div>
      <h1>Recipes</h1>
      <div className="row">
        {recipes.map((recipe) => (
          <div key={recipe.id} className="col-md-4 mb-3">
            <div className="card">
              {recipe.images && recipe.images.length > 0 ? (
                <img
                  src={`/uploads/images/${recipe.images[0].fileName}`}
                  className="card-img-top"
                  alt={recipe.title}
                />
              ) : (
                <div className="card-img-top bg-secondary text-white text-center">
                  No Image
                </div>
              )}
              <div className="card-body">
                <h5 className="card-title">{recipe.title}</h5>
                <p className="card-text">{recipe.description}</p>
              </div>
            </div>
          </div>
        ))}
      </div>
    </div>
  );
};

export default RecipesList;
