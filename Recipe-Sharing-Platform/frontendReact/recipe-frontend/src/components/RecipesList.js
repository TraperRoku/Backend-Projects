import React, { useEffect, useState } from "react";
import { request } from "../axios_helper";
import "./RecipesList.css";
import clockIcon from "../icons/clock.png";
import difficultyIcon from "../icons/difficulty-icon.png";
import { Link } from "react-router-dom"; // Ensure you have this import for Link

const RecipesList = () => {
  const [recipes, setRecipes] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  useEffect(() => {
    const fetchRecipes = async () => {
      try {
        const response = await request("GET", "/api/recipes");
        setRecipes(
          Array.isArray(response.data) ? response.data : [response.data]
        );
      } catch (error) {
        setError("Failed to load recipes");
      } finally {
        setLoading(false);
      }
    };

    fetchRecipes();
  }, []);

  const getImageUrl = (recipe) => {
    return recipe.imagePaths?.[0]
      ? `http://localhost:8080/api/recipes/${recipe.imagePaths[0]}`
      : null;
  };

  if (loading) return <div>Loading...</div>;
  if (error) return <div>Error: {error}</div>;

  return (
    <div className="container">
      <h1>Recipes</h1>
      <div className="row">
        {recipes.map((recipe) => (
          <div key={recipe.id} className="col-md-4 mb-4">
            <div className="card h-100">
              {recipe.imagePaths?.length ? (
                <Link to={`/recipes/${recipe.id}`} className="image-link">
                  <img
                    src={getImageUrl(recipe)}
                    className="card-img-top hover-effect"
                    alt={recipe.title}
                    style={{ height: "200px", objectFit: "cover" }}
                    onError={(e) => {
                      e.target.onerror = null;
                      e.target.src =
                        "https://via.placeholder.com/400x300?text=No+Image";
                    }}
                  />
                </Link>
              ) : (
                <div
                  className="card-img-top bg-secondary text-white d-flex align-items-center justify-content-center"
                  style={{ height: "200px" }}
                >
                  No Image Available
                </div>
              )}
              <div className="card-body">
                <Link
                  to={`/recipes/${recipe.id}`}
                  className="hover-link"
                  style={{ textDecoration: "none", color: "inherit" }}
                >
                  <h5 className="card-title">{recipe.title}</h5>
                </Link>

                <p className="card-text">{recipe.description}</p>

                <div className="card-text custom-flex-right">
                  <div className="icons">
                    <div className="icon-item">
                      <img
                        src={clockIcon}
                        alt="Time"
                        className="icon"
                        width={24}
                      />
                      <span>{recipe.time} minutes</span>
                    </div>
                    <div className="icon-item">
                      <img
                        src={difficultyIcon}
                        alt="Difficulty"
                        className="icon"
                        width={24}
                      />
                      <span>{recipe.difficultyRecipe}</span>
                    </div>
                  </div>
                </div>
                <p className="card-text">
                  <small className="text-muted">
                    By {recipe.chefLogin || "Unknown Chef"}
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
