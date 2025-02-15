import axios from 'axios';

// Constants
const AUTH_TOKEN_KEY = 'auth_token';
const BASE_URL = process.env.REACT_APP_API_URL || 'http://localhost:8080';

// Axios instance configuration
const axiosInstance = axios.create({
    baseURL: BASE_URL,
    timeout: 10000, // 10 seconds timeout
    headers: {
        'Content-Type': 'application/json'
    }
});

/**
 * Get the authentication token from local storage
 * @returns {string|null} The authentication token or null if not found
 */
export const getAuthToken = () => {
    try {
        const token = window.localStorage.getItem(AUTH_TOKEN_KEY);
        return token !== "null" ? token : null;
    } catch (error) {
        console.error('Error accessing localStorage:', error);
        return null;
    }
};

/**
 * Set or remove the authentication token
 * @param {string|null} token - The token to set, or null to remove
 */
export const setAuthHeader = (token) => {
    try {
        if (token) {
            window.localStorage.setItem(AUTH_TOKEN_KEY, token);
            axiosInstance.defaults.headers.common['Authorization'] = `Bearer ${token}`;
        } else {
            window.localStorage.removeItem(AUTH_TOKEN_KEY);
            delete axiosInstance.defaults.headers.common['Authorization'];
        }
    } catch (error) {
        console.error('Error managing auth token:', error);
    }
};

/**
 * Initialize axios with stored auth token if it exists
 */
const initializeAxios = () => {
    const token = getAuthToken();
    if (token) {
        axiosInstance.defaults.headers.common['Authorization'] = `Bearer ${token}`;
    }
};

// Set up response interceptor for handling common errors
axiosInstance.interceptors.response.use(
    response => response,
    error => {
        if (error.response) {
            // Handle specific HTTP errors
            switch (error.response.status) {
                case 401:
                    setAuthHeader(null); // Clear invalid token
                    // You might want to redirect to login page here
                    break;
                case 403:
                    console.error('Access forbidden');
                    break;
                case 404:
                    console.error('Resource not found');
                    break;
                default:
                    console.error('Server error:', error.response.data);
            }
        } else if (error.request) {
            console.error('No response received:', error.request);
        } else {
            console.error('Request error:', error.message);
        }
        return Promise.reject(error);
    }
);

/**
 * Make an HTTP request
 * @param {string} method - The HTTP method to use
 * @param {string} url - The URL to send the request to
 * @param {Object} [data] - The data to send with the request
 * @param {Object} [customConfig] - Additional axios configuration
 * @returns {Promise} The axios response promise
 */
export const request = (method, url, data = null, customConfig = {}) => {
    const config = {
        method: method.toLowerCase(),
        url,
        ...customConfig
    };

    if (data) {
        if (method.toLowerCase() === 'get') {
            config.params = data;
        } else {
            config.data = data;
        }
    }

    return axiosInstance(config);
};

// Initialize axios with any stored auth token
initializeAxios();

// Export the axios instance for direct use if needed
export default axiosInstance;