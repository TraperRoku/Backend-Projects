import React, { useState, useEffect } from 'react';
import { useParams } from 'react-router-dom';
import { request } from '../axios_helper';
import { Clock, ChefHat, Star , ChevronLeft, ChevronRight} from 'lucide-react';

const RecipeDetails = () => {
  const [recipe, setRecipe] = useState(null);
  const [currentImageIndex, setCurrentImageIndex] = useState(0);
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

  useEffect(() => {
    if (!recipe?.images?.length) return;
    
    const interval = setInterval(() => {
      setCurrentImageIndex((prevIndex) => 
        prevIndex === recipe.images.length - 1 ? 0 : prevIndex + 1
      );
    }, 5000); // Change slide every 5 seconds

    return () => clearInterval(interval);
  }, [recipe?.images?.length]);

  if (!recipe) {
    return (
      <div className="flex items-center justify-center min-h-screen">
        <div className="text-xl">Loading...</div>
      </div>
    );
  }

  const nextImage = () => {
    setCurrentImageIndex((prevIndex) => 
      prevIndex === recipe.images.length - 1 ? 0 : prevIndex + 1
    );
  };

  const previousImage = () => {
    setCurrentImageIndex((prevIndex) => 
      prevIndex === 0 ? recipe.images.length - 1 : prevIndex - 1
    );
  };

  const goToImage = (index) => {
    setCurrentImageIndex(index);
  };


  const getStepImageUrl = (fileName) => {
    return fileName
      ? `http://localhost:8080/api/recipes/steps${fileName}`
      : "/api/placeholder/400/300";
  };

  const ImageCarousel = () => (
    <div className="relative mb-8 group">
      {/* Main image */}
      <div className="relative w-full h-96">
        <img
          src={`http://localhost:8080/api/recipes/uploads/images/${recipe.images[currentImageIndex].fileName}`}
          className="w-full h-full object-cover rounded-lg shadow-lg transition-opacity duration-500"
          alt={recipe.title}
          onError={(e) => {
            e.target.onerror = null;
            e.target.src = "/api/placeholder/400/300";
          }}
        />
      </div>

      {/* Navigation arrows - only show if there are multiple images */}
      {recipe.images.length > 1 && (
        <>
          {/* Previous button */}
          <button
            onClick={previousImage}
            className="absolute left-4 top-1/2 -translate-y-1/2 bg-black/50 text-white p-2 rounded-full opacity-0 group-hover:opacity-100 transition-opacity duration-300 hover:bg-black/70"
            aria-label="Previous image"
          >
            <ChevronLeft size={24} />
          </button>

          {/* Next button */}
          <button
            onClick={nextImage}
            className="absolute right-4 top-1/2 -translate-y-1/2 bg-black/50 text-white p-2 rounded-full opacity-0 group-hover:opacity-100 transition-opacity duration-300 hover:bg-black/70"
            aria-label="Next image"
          >
            <ChevronRight size={24} />
          </button>

          {/* Dots navigation */}
          <div className="absolute bottom-4 left-1/2 -translate-x-1/2 flex gap-2">
            {recipe.images.map((_, index) => (
              <button
                key={index}
                onClick={() => goToImage(index)}
                className={`w-3 h-3 rounded-full transition-all duration-300 ${
                  currentImageIndex === index 
                    ? 'bg-white scale-110' 
                    : 'bg-white/50 hover:bg-white/75'
                }`}
                aria-label={`Go to image ${index + 1}`}
              />
            ))}
          </div>
        </>
      )}
    </div>
  );

  return (
    <div className="max-w-6xl mx-auto px-4 py-8">
      {/* Recipe Title */}
      <h1 className="text-4xl font-bold mb-4">{recipe.title}</h1>

      {/* Main Recipe Image */}
      <ImageCarousel />

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
        <div className="md:col-span-2">
          <h2 className="text-2xl font-bold">Food description</h2>
          <div className="h-1 bg-blue-300 my-3"></div>
          
          {/* Recipe Description */}
          <div className="bg-white p-6 rounded-lg shadow-sm mb-8">
            <p className="text-lg text-gray-700">{recipe.description}</p>
          </div>

          {/* Steps with Images */}
          <div className="space-y-6">
            <h2 className="text-2xl font-bold">Preparation step by step</h2>
            <div className="h-1 bg-blue-300 my-4"></div>
            {recipe.steps?.map((step, index) => (
              <div key={step.id} className="bg-white p-6 rounded-lg shadow-sm">
                <div className="flex flex-col gap-4">
                  <div className="flex gap-4">
                    <div className="flex-shrink-0 w-8 h-8 bg-blue-500 text-white rounded-full flex items-center justify-center font-bold">
                      {step.stepNumber}
                    </div>
                    <div className="flex-1">
                      <p className="text-lg">{step.description}</p>
                    </div>
                  </div>
                  {step.imageUrl && (
                    <div className="mt-4">
                      <img
                        src={getStepImageUrl(step.imageUrl)}
                        alt={`Step ${step.stepNumber}`}
                        className="w-full max-w-md h-64 object-cover rounded-lg shadow-sm"
                        onError={(e) => {
                          e.target.onerror = null;
                          e.target.src = "/api/placeholder/400/300";
                        }}
                      />
                    </div>
                  )}
                </div>
              </div>
            ))}
          </div>
        </div>
      </div>
    </div>
  );
};

export default RecipeDetails;