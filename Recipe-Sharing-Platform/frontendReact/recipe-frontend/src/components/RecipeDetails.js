import React, { useState, useEffect } from 'react';
import { useParams } from 'react-router-dom';
import { request } from '../axios_helper';

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

  if (!recipe) return <div>Loading...</div>;

  return (
    <div className="container mt-4">
      <h2>{recipe.title}</h2>
      <p>{recipe.description}</p>
      
      <div className="row mt-4">
        <div className="col-md-8">
          <h3>Steps</h3>ya
          {recipe.steps?.map((step, index) => (
            <div key={step.id} className="card mb-3">
              <div className="card-body">
                <h5 className="card-title">Step {step.stepNumber}</h5>
                <p className="card-text">{step.description}</p>
              </div>
            </div>
          ))}
        </div>
        
        <div className="col-md-4">
          <div className="card">
            <div className="card-body">
              <h5>Recipe Details</h5>
              <p>Difficulty: {recipe.difficulty}</p>
              <p>Time: {recipe.time} minutes</p>
              <p>Chef: {recipe.chef?.login}</p>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};

export default RecipeDetails;