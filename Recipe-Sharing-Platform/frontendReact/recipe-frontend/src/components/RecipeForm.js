import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import { request } from "../axios_helper";

const RecipeForm = () => {
  const [formData, setFormData] = useState({ title: "", description: "" });
  const [images, setImages] = useState([]);
  const navigate = useNavigate();

  const handleSubmit = async (e) => {
    e.preventDefault();
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
        <input
          type="file"
          className="form-control"
          onChange={(e) => setImages(Array.from(e.target.files))}
          multiple
          accept="image/*"
        />
      </div>
      <button type="submit" className="btn btn-success">Submit</button>
    </form>
  );
};

export default RecipeForm;
