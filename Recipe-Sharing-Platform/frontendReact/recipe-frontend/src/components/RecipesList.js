import React, { useEffect, useState } from "react";
import { request } from "../axios_helper";
import "./RecipesList.css";
import clockIcon from "../icons/clock.png";
import difficultyIcon from "../icons/difficulty-icon.png";
import { Link } from "react-router-dom"; // Ensure you have this import for Link
import DatePicker from "react-datepicker";
import "react-datepicker/dist/react-datepicker.css";

const RecipesList = () => {
  const [recipes, setRecipes] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  const [filters, setFilters] = useState({
    keyword: "",
    chef: "",
    tag: "",
    difficulty: "",
    startDate: null,
    endDate: null
  });

  const difficultyLevels = ["easy", "medium", "hard"];

  const fetchRecipes = async (params = "") => {
    try {
      setLoading(true);
      const response = await request("GET", `/api/recipes/find${params}`);
      setRecipes(Array.isArray(response.data) ? response.data : [response.data]);
    } catch (error) {
      console.error("Error fetching recipes:", error);
      setError("Failed to load recipes");
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    const buildQueryString = () => {
      const params = new URLSearchParams();
      
      if (filters.keyword) params.append("keyword", filters.keyword);
      if (filters.chef) params.append("chef", filters.chef);
      if (filters.tag) params.append("tag", filters.tag);
      if (filters.difficulty) params.append("difficulty", filters.difficulty);
      if (filters.startDate) params.append("startDate", filters.startDate.toISOString().split('T')[0]);
      if (filters.endDate) params.append("endDate", filters.endDate.toISOString().split('T')[0]);
      
      return params.toString() ? `?${params.toString()}` : "";
    };

    const timeoutId = setTimeout(() => {
      fetchRecipes(buildQueryString());
    }, 500);

    return () => clearTimeout(timeoutId);
  }, [filters]);

  const handleFilterChange = (field, value) => {
    setFilters(prev => ({
      ...prev,
      [field]: value
    }));
  };
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
      
      <div className="search-filters mb-4">
        <div className="row g-3">
          <div className="col-md-3">
            <input
              type="text"
              className="form-control"
              placeholder="Search recipes..."
              value={filters.keyword}
              onChange={(e) => handleFilterChange("keyword", e.target.value)}
            />
          </div>
          
          <div className="col-md-2">
            <input
              type="text"
              className="form-control"
              placeholder="Chef name"
              value={filters.chef}
              onChange={(e) => handleFilterChange("chef", e.target.value)}
            />
          </div>
          
          <div className="col-md-2">
            <input
              type="text"
              className="form-control"
              placeholder="Tag"
              value={filters.tag}
              onChange={(e) => handleFilterChange("tag", e.target.value)}
            />
          </div>

          <div className="col-md-2">
            <select
              className="form-select"
              value={filters.difficulty}
              onChange={(e) => handleFilterChange("difficulty", e.target.value)}
            >
              <option value="">Select Difficulty</option>
              {difficultyLevels.map(level => (
                <option key={level} value={level}>{level}</option>
              ))}
            </select>
          </div>
          
          <div className="col-md-3">
            <div className="d-flex gap-2">
              <DatePicker
                selected={filters.startDate}
                onChange={(date) => handleFilterChange("startDate", date)}
                placeholderText="Start Date"
                className="form-control"
                dateFormat="yyyy-MM-dd"
              />
              <DatePicker
                selected={filters.endDate}
                onChange={(date) => handleFilterChange("endDate", date)}
                placeholderText="End Date"
                className="form-control"
                dateFormat="yyyy-MM-dd"
              />
            </div>
          </div>
        </div>
      </div>

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
                      <span>{recipe.difficulty}</span>
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
