import { jwtDecode } from "jwt-decode";

export const getUserRole = () => {
    const token = localStorage.getItem("auth_token");
    if (!token) return null;

    try {
        const decoded = jwtDecode(token);
        return decoded.role || null;  
    } catch (error) {
        console.error("Error decoding token:", error);
        return null;
    }
};
