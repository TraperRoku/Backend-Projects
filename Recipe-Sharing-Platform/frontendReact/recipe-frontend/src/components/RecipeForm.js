import React, { useState } from "react";
import { Plus, ArrowLeft, ArrowRight, Trash2 } from "lucide-react";
import { request } from '../axios_helper';
import { useNavigate } from 'react-router-dom';

const RecipeForm = () => {
  const navigate = useNavigate();
  const [currentStep, setCurrentStep] = useState(1);
  const [error, setError] = useState("");
  const [formData, setFormData] = useState({
    title: "",
    description: "",
    tags: [],
    difficulty: "medium",
    servings: 1,
    time: 45,
    ingredients: [],
    steps: [],
    images: [],
  });

  const [newTag, setNewTag] = useState("");
  const [newIngredient, setNewIngredient] = useState({
    name: "",
    amount: "",
    unit: "gram",
  });
  const [newStep, setNewStep] = useState({ description: "", imageUrl: null });

  const convertDifficultyToEnum = (difficulty) => {
    const mapping = {
      'easy': 'EASY',
      'medium': 'MEDIUM',
      'hard': 'HARD'
    };
    return mapping[difficulty] || 'MEDIUM';
  };

  const handleNextStep = () => {
    if (currentStep === 1 && !formData.title.trim()) {
      setError("Recipe name cannot be empty!");
      return;
    }
    setError("");
    setCurrentStep((prev) => prev + 1);
  };

  const handleAddIngredient = () => {
    if (newIngredient.name.trim() && newIngredient.amount) {
      setFormData((prev) => ({
        ...prev,
        ingredients: [...prev.ingredients, { ...newIngredient }],
      }));
      setNewIngredient({ name: "", amount: "", unit: "gram" });
    }
  };

  const handleAddStep = () => {
    if (newStep.description.trim()) {
      setFormData((prev) => ({
        ...prev,
        steps: [
          ...prev.steps,
          { ...newStep, stepNumber: prev.steps.length + 1 },
        ],
      }));
      setNewStep({ description: "", imageUrl: null });
    }
  };

  const handleAddTag = () => {
    if (newTag.trim()) {
      setFormData((prev) => ({
        ...prev,
        tags: [...prev.tags, newTag.trim()],
      }));
      setNewTag("");
    }
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    if (!formData.title.trim()) {
      setError("Recipe name cannot be empty!");
      return;
    }

    // Create the DTO object matching backend structure
    const recipeDto = {
      title: formData.title,
      description: formData.description,
      difficultyRecipe: formData.difficulty, // Change this to match the backend's @JsonProperty
      time: formData.time,
      tags: formData.tags,
      steps: formData.steps,
      images: formData.imagePaths,
    };
    

    // Create FormData for multipart submission
    const data = new FormData();
    data.append("recipe", new Blob([JSON.stringify(recipeDto)], {
      type: "application/json"
    }));

    // Append each image to FormData
    formData.images.forEach((image, index) => {
      data.append("images", image);
    });

    try {
      await request("POST", "/api/recipes", data, {
        headers: {
          'Content-Type': 'multipart/form-data',
        },
      });
      
      navigate("/");
    } catch (error) {
      console.error("Error adding recipe:", error);
      setError("Failed to save recipe. Please try again.");
    }
  };

  // Rest of your component JSX remains the same...
  return (
    <div className="max-w-4xl mx-auto p-8 bg-white rounded-lg shadow-lg">
      <form onSubmit={handleSubmit} className="space-y-8">
        {/* Steps indicator */}
        <div className="flex justify-between mb-12">
          {["General Info", "Ingredients", "Instructions", "Photos"].map(
            (step, index) => (
              <div
                key={index}
                className={`flex flex-col items-center ${
                  currentStep >= index + 1 ? "text-blue-600" : "text-gray-400"
                }`}
              >
                <div
                  className={`w-10 h-10 rounded-full flex items-center justify-center mb-2 
                ${currentStep >= index + 1 ? "bg-blue-100" : "bg-gray-100"}`}
                >
                  {index + 1}
                </div>
                <span className="text-lg">{step}</span>
              </div>
            )
          )}
        </div>

        {error && (
          <div className="p-4 bg-red-100 text-red-700 rounded-lg text-lg">
            {error}
          </div>
        )}

        {/* Step 1: General Info */}
        {currentStep === 1 && (
          <div className="space-y-6">
            <div>
              <label className="block text-xl mb-2">Recipe Name*</label>
              <input
                type="text"
                placeholder="Enter recipe name"
                className="w-full p-4 text-xl border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-500"
                value={formData.title}
                onChange={(e) => {
                  setFormData({ ...formData, title: e.target.value });
                  if (e.target.value.trim()) setError("");
                }}
              />
            </div>

            <div className="space-y-6">
              <label className="block text-xl mb-2">Recipe Description*</label>
              <input
                type="text"
                name="description"
                className="w-full p-4 text-xl border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-500"
                placeholder="Enter recipe description"
                value={formData.description}
                onChange={(e) => setFormData({ ...formData, description: e.target.value })}
                required
              />
            </div>

            {/* Tags section */}
            <div>
              <label className="block text-xl mb-2">Tags</label>
              <div className="flex gap-4">
                <input
                  type="text"
                  placeholder="Add tag"
                  className="flex-1 p-4 text-xl border border-gray-300 rounded-lg"
                  value={newTag}
                  onChange={(e) => setNewTag(e.target.value)}
                  onKeyPress={(e) => {
                    if (e.key === "Enter") {
                      e.preventDefault();
                      handleAddTag();
                    }
                  }}
                />
                <button
                  type="button"
                  onClick={handleAddTag}
                  className="px-6 bg-blue-500 text-white rounded-lg hover:bg-blue-600"
                >
                  <Plus size={24} />
                </button>
              </div>
              <div className="flex flex-wrap gap-2 mt-4">
                {formData.tags.map((tag, index) => (
                  <span
                    key={index}
                    className="px-4 py-2 bg-gray-100 rounded-full text-lg"
                  >
                    {tag}
                  </span>
                ))}
              </div>
            </div>

            {/* Difficulty section */}
            <div>
              <label className="block text-xl mb-2">Difficulty Level</label>
              <div className="flex gap-4">
                {["Easy", "Medium", "Hard"].map((level) => (
                  <button
                    key={level}
                    type="button"
                    className={`flex-1 py-4 text-xl rounded-lg border ${
                      formData.difficulty === level.toLowerCase()
                        ? "border-blue-500 bg-blue-50 text-blue-700"
                        : "border-gray-300 hover:bg-gray-50"
                    }`}
                    onClick={() =>
                      setFormData({
                        ...formData,
                        difficulty: level.toLowerCase(),
                      })
                    }
                  >
                    {level}
                  </button>
                ))}
              </div>
            </div>

            {/* Time section */}
            <div>
              <label className="block text-xl mb-2">
                Preparation Time: {formData.time} minutes
              </label>
              <input
                type="range"
                min="15"
                max="90"
                step="15"
                value={formData.time}
                onChange={(e) =>
                  setFormData({ ...formData, time: Number(e.target.value) })
                }
                className="w-full h-3 bg-gray-200 rounded-lg appearance-none cursor-pointer"
              />
              <div className="flex justify-between text-lg mt-2">
                <span>15 min</span>
                <span>90 min</span>
              </div>
            </div>
          </div>
        )}

        {/* Step 2: Ingredients */}
        {currentStep === 2 && (
          <div className="space-y-4">
            <div className="flex gap-2">
              <input
                type="text"
                placeholder="Ingredient name"
                className="flex-1 p-2 border rounded"
                value={newIngredient.name}
                onChange={(e) =>
                  setNewIngredient({ ...newIngredient, name: e.target.value })
                }
              />
              <input
                type="number"
                placeholder="Amount"
                className="w-24 p-2 border rounded"
                value={newIngredient.amount}
                onChange={(e) =>
                  setNewIngredient({ ...newIngredient, amount: e.target.value })
                }
              />
              <select
                className="p-2 border rounded"
                value={newIngredient.unit}
                onChange={(e) =>
                  setNewIngredient({ ...newIngredient, unit: e.target.value })
                }
              >
                <option value="gram">g</option>
                <option value="kilogram">kg</option>
                <option value="milliliter">ml</option>
                <option value="piece">pcs</option>
              </select>
              <button
                type="button"
                onClick={handleAddIngredient}
                className="p-2 bg-green-600 text-white rounded"
              >
                <Plus size={24} />
              </button>
            </div>

            <div className="space-y-2">
              {formData.ingredients.map((ingredient, index) => (
                <div
                  key={index}
                  className="flex justify-between items-center p-2 bg-gray-50 rounded"
                >
                  <span>
                    {ingredient.name} - {ingredient.amount} {ingredient.unit}
                  </span>
                  <button
                    type="button"
                    onClick={() =>
                      setFormData({
                        ...formData,
                        ingredients: formData.ingredients.filter(
                          (_, i) => i !== index
                        ),
                      })
                    }
                    className="text-red-500"
                  >
                    <Trash2 size={24} />
                  </button>
                </div>
              ))}
            </div>
          </div>
        )}

        {/* Step 3: Instructions */}
        {currentStep === 3 && (
          <div className="space-y-4">
            <div className="flex gap-2">
              <textarea
                placeholder="Describe the step..."
                className="flex-1 p-2 border rounded"
                value={newStep.description}
                onChange={(e) =>
                  setNewStep({ ...newStep, description: e.target.value })
                }
              />
              <button
                type="button"
                onClick={handleAddStep}
                className="p-2 bg-green-600 text-white rounded"
              >
                <Plus size={24} />
              </button>
            </div>

            <div className="space-y-2">
              {formData.steps.map((step, index) => (
                <div
                  key={index}
                  className="flex justify-between items-center p-2 bg-gray-50 rounded"
                >
                  <span>
                    Step {step.stepNumber}: {step.description}
                  </span>
                  <button
                    type="button"
                    onClick={() =>
                      setFormData({
                        ...formData,
                        steps: formData.steps.filter((_, i) => i !== index),
                      })
                    }
                    className="text-red-500"
                  >
                    <Trash2 size={20} />
                  </button>
                </div>
              ))}
            </div>
          </div>
        )}

        {/* Step 4: Photos */}
        {currentStep === 4 && (
          <div className="space-y-4">
            <input
              type="file"
              multiple
              accept="image/*"
              onChange={(e) =>
                setFormData({
                  ...formData,
                  images: Array.from(e.target.files),
                })
              }
              className="w-full p-2 border rounded"
            />

            <div className="grid grid-cols-2 gap-4">
              {formData.images.map((image, index) => (
                <div key={index} className="relative">
                  <img
                    src="/api/placeholder/400/300"
                    alt={`Recipe preview ${index + 1}`}
                    className="w-full h-48 object-cover rounded"
                  />
                  <button
                    type="button"
                    onClick={() =>
                      setFormData({
                        ...formData,
                        images: formData.images.filter((_, i) => i !== index),
                      })
                    }
                    className="absolute top-2 right-2 p-1 bg-red-500 text-white rounded-full"
                  >
                    <Trash2 size={24} />
                  </button>
                </div>
              ))}
            </div>
          </div>
        )}

        {/* Navigation buttons */}
        <div className="flex justify-between pt-6">
          {currentStep > 1 && (
            <button
              type="button"
              onClick={() => setCurrentStep((prev) => prev - 1)}
              className="flex items-center gap-2 px-6 py-3 text-lg bg-gray-200 rounded-lg hover:bg-gray-300"
            >
              <ArrowLeft size={24} />
              Previous
            </button>
          )}

          {currentStep < 5 ? (
            <button
              type="button"
              onClick={handleNextStep}
              className="flex items-center gap-2 px-6 py-3 text-lg bg-blue-500 text-white rounded-lg hover:bg-blue-600 ml-auto"
            >
              Next
              <ArrowRight size={24} />
            </button>
          ) : (
            <button
              type="submit"
              className="px-6 py-3 text-lg bg-green-600 text-white rounded-lg hover:bg-green-700 ml-auto"
            >
              Save Recipe
            </button>
          )}
        </div>
      </form>
    </div>
  );
};

export default RecipeForm;