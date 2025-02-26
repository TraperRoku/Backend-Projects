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
 *
 * @returns {string|null} 
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
 * 
 * @param {string|null} token 
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


const initializeAxios = () => {
    const token = getAuthToken();
    if (token) {
        axiosInstance.defaults.headers.common['Authorization'] = `Bearer ${token}`;
    }
};


axiosInstance.interceptors.response.use(
    response => response,
    error => {
        if (error.response) {
          
            switch (error.response.status) {
                case 401:
                    setAuthHeader(null); 
                
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
 * 
 * @param {string} method 
 * @param {string} url 
 * @param {Object} [data] 
 * @param {Object} [customConfig] 
 * @returns {Promise} 
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


initializeAxios();

export default axiosInstance;