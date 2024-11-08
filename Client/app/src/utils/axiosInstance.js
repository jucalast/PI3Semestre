import axios from 'axios';

const axiosInstance = axios.create({
  baseURL: import.meta.env.VITE_API_BASE_URL,
  timeout: 10000, 
  withCredentials: true, 
});

// axiosInstance.interceptors.request.use(
//   (config) => {
//     return config;
//   },
//   (error) => {
//     return Promise.reject(error);
//   }
// );

export default axiosInstance;