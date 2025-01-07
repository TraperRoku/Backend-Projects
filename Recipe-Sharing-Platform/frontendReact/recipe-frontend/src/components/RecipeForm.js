import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import { request } from "../axios_helper";

const RecipeForm = () => {
  const [currentStep, setCurrentStep] = useState(1);
  const [formData, setFormData] = useState({
    title: "",
    description: "",
    time: "",
    difficulty: "",
    steps: []
  });
  const [images, setImages] = useState([]);
  const [stepDescription, setStepDescription] = useState("");
  const navigate = useNavigate();

  const handleAddStep = () => {
    if (stepDescription.trim()) {
      setFormData(prev => ({
        ...prev,
        steps: [...prev.steps, { stepNumber: prev.steps.length + 1, description: stepDescription }]
      }));
      setStepDescription("");
    }
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    if (stepDescription.trim()) {
      handleAddStep();
    }
    
    const data = new FormData();
    data.append("recipe", JSON.stringify(formData));
    images.forEach((img) => data.append("images", img));

    try {
      await request("POST", "/api/recipes", data, {
        headers: { "Content-Type": "multipart/form-data" },
      });
      navigate("/");
    } catch (error) {
      console.error("Error adding recipe:", error);
    }
  };

  return (
    <form onSubmit={handleSubmit}>
      <h2>Add Recipe</h2>
      {currentStep === 1 ? (
        <>
          {/* Basic recipe fields */}
          <div className="mb-3">
            <input
              type="text"
              name="title"
              className="form-control"
              placeholder="Recipe Title"
              value={formData.title}
              onChange={(e) => setFormData({ ...formData, title: e.target.value })}
              required
            />
          </div>
          <div className="mb-3">
            <textarea
              name="description"
              className="form-control"
              placeholder="Recipe Description"
              value={formData.description}
              onChange={(e) => setFormData({ ...formData, description: e.target.value })}
              required
            />
          </div>
          <div className="mb-3">
            <select
              name="difficulty"
              className="form-control"
              value={formData.difficulty}
              onChange={(e) => setFormData({ ...formData, difficulty: e.target.value })}
              required
            >
              <option value="" disabled>Select difficulty</option>
              <option value="easy">Easy</option>
              <option value="medium">Medium</option>
              <option value="hard">Hard</option>
            </select>
          </div>
          <div className="mb-3">
            <input
              type="number"
              name="time"
              className="form-control"
              placeholder="Recipe Time (minutes)"
              value={formData.time}
              onChange={(e) => setFormData({ ...formData, time: e.target.value })}
              required
            />
          </div>
          <div className="mb-3">
            <input
              type="file"
              className="form-control"
              onChange={(e) => setImages(Array.from(e.target.files))}
              multiple
              accept="image/*"
            />
          </div>
          <button type="button" className="btn btn-primary" onClick={() => setCurrentStep(2)}>
            Next: Add Steps
          </button>
        </>
      ) : (
        <>
          <div className="mb-3">
            <h4>Steps</h4>
            {formData.steps.map((step, index) => (
              <div key={index} className="mb-2">
                <p>Step {step.stepNumber}: {step.description}</p>
              </div>
            ))}
            <textarea
              className="form-control"
              placeholder="Describe the next step"
              value={stepDescription}
              onChange={(e) => setStepDescription(e.target.value)}
            />
          </div>
          <div className="d-flex gap-2">
            <button type="button" className="btn btn-secondary" onClick={handleAddStep}>
              Add Step
            </button>
            <button type="submit" className="btn btn-success">
              Finish Recipe
            </button>
          </div>
        </>
      )}
    </form>
  );
};

export default RecipeForm;