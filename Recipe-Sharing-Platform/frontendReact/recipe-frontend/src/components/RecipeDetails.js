import React, { useState, useEffect } from 'react';
import { useParams } from 'react-router-dom';
import { request } from '../axios_helper';
import { Clock,  ChefHat, Star } from 'lucide-react';

const RecipeDetails = () => {
  
  const [recipe, setRecipe] = useState(null);
  const { id } = useParams();

  useEffect(() => {
    const fetchRecipe = async () => {
      try {
        const response = await request('GET', `/api/recipes/${id}`);
        setRecipe(response.data);
      } catch (error) {
        console.error('Error:', error);
      }
    };
    fetchRecipe();
  }, [id]);

  if (!recipe) {
    return (
      <div className="flex items-center justify-center min-h-screen">
        <div className="text-xl">Loading...</div>
      </div>
    );
  }

  const getImageUrl = (recipe) => {
    console.log("Recipe data:", recipe);
    return recipe.images?.[0]  // Changed from imagePaths to images
      ? `http://localhost:8080/api/recipes/uploads/images/${recipe.images[0].fileName}`
      : "/api/placeholder/400/300";
  };
  

  return (
    <div className="max-w-6xl mx-auto px-4 py-8">
      {/* Recipe Title */}
      <h1 className="text-4xl font-bold mb-4">{recipe.title}</h1>

      
          
                
                  <img
                    src={getImageUrl(recipe)}
                    className="card-img-top hover-effect"
                    alt={recipe.title}
                    style={{ height: "400px", objectFit: "cover" }}
                    onError={(e) => {
                      e.target.onerror = null;
                      e.target.src =
                        "https://via.placeholder.com/400x300?text=No+Image";
                    }}
                  />
             

      
      {/* Recipe Meta Info */}
      <div className="flex flex-wrap gap-6 mb-8 text-gray-600">
        <div className="flex items-center gap-2">
          <ChefHat className="w-5 h-5" />
         
          <span>Author: {recipe.chef.login}</span>
        </div>
        <div className="flex items-center gap-2">
          <Clock className="w-5 h-5" />
          <span>{recipe.time} min</span>
        </div>
        <div className="flex items-center gap-2">
          <Star className="w-5 h-5" />
          <span>Difficulty: {recipe.difficulty}</span>
        </div>
      </div>

      {/* Main Recipe Content */}
      <div className="grid md:grid-cols-3 gap-8">
        {/* Left Column - Ingredients */}
        <div className="md:col-span-1">
          <div className="bg-gray-50 p-6 rounded-lg">
            <h2 className="text-2xl font-bold mb-4">Ingredients</h2>
           
            <ul className="space-y-3">
              {recipe.ingredients?.map((ingredient, index) => (
                <li key={index} className="flex items-center gap-2">
                  <input type="checkbox" className="w-5 h-5 rounded" />
                  <span>
                    {ingredient.howMany} {ingredient.type} {ingredient.ingredient}
                  </span>
                </li>
              ))}
            </ul>
          </div>

          {/* Tags */}
          <div className="mt-6">
            <h3 className="text-xl font-semibold mb-3">Tags</h3>
            <div className="flex flex-wrap gap-2">
              {recipe.tags?.map((tag, index) => (
                <span
                  key={index}
                  className="px-3 py-1 bg-blue-100 text-blue-800 rounded-full text-sm"
                >
                  {tag}
                </span>
              ))}
            </div>
          </div>
        </div>

        {/* Right Column - Instructions and Images */}
        <div >
        <h2 className="text-2xl font-bold">Food description</h2>
        <div className="h-1 bg-blue-300 my-3"></div>
          {/* Recipe Description */}
          <div className="bg-white p-6 rounded-lg shadow-sm mb-8 md:col-span-2">
         
            <p className="text-lg text-gray-700">{recipe.description}</p>
          </div>

          {/* Steps */}
          <div className="space-y-6">
            <h2 className="text-2xl font-bold">Preparation step by step</h2>
            <div className="h-1 bg-blue-300 my-4"></div>
            {recipe.steps?.map((step, index) => (
              <div key={step.id} className="bg-white p-6 rounded-lg shadow-sm">
                <div className="flex gap-4">
                  <div className="flex-shrink-0 w-8 h-8 bg-blue-500 text-white rounded-full flex items-center justify-center font-bold">
                    {step.stepNumber}
                  </div>
                  <div>
                    <p className="text-lg">{step.description}</p>
                  </div>
                </div>
              </div>
            ))}
          </div>
        </div>
      </div>

      {/* Recipe Images */}
      {recipe.imagePaths && recipe.imagePaths.length > 0 && (
        <div className="mt-8">
          <h2 className="text-2xl font-bold mb-4">Recipe Photos</h2>
          <div className="grid grid-cols-2 md:grid-cols-3 gap-4">
            {recipe.imagePaths.map((imagePath, index) => (
              <div key={index} className="aspect-w-16 aspect-h-9">
                <img
                  src={imagePath}
                  alt={`Step ${index + 1}`}
                  className="object-cover rounded-lg w-full h-full"
                />
              </div>
            ))}
          </div>
        </div>
      )}
    </div>
  );
};

export default RecipeDetails;