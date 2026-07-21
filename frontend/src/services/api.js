import axios from 'axios';

const API_BASE_URL = import.meta.env.VITE_API_BASE_URL || 'http://localhost:8080';

const api = axios.create({
  baseURL: API_BASE_URL,
  headers: {
    'Content-Type': 'application/json',
  },
});

// Add JWT token to requests
api.interceptors.request.use((config) => {
  const token = localStorage.getItem('token');
  if (token) {
    config.headers.Authorization = `Bearer ${token}`;
  }
  return config;
});

// Handle responses
api.interceptors.response.use(
  (response) => response,
  (error) => {
    if (error.response?.status === 401) {
      localStorage.removeItem('token');
      window.location.href = '/login';
    }
    return Promise.reject(error);
  }
);

// Auth Services
export const authService = {
  register: (username, password) => api.post('/auth/register', { username, password }),
  login: (username, password) => api.post('/auth/login', { username, password }),
};

// Blood Bank Services
export const bloodBankService = {
  getAll: (page = 0, size = 10) => api.get(`/api/bloodbank/all?page=${page}&size=${size}`),
  getById: (id) => api.get(`/api/bloodbank/${id}`),
  getByCity: (city) => api.get(`/api/bloodbank/filter?city=${city}`),
  getActiveByCity: (city) => api.get(`/api/bloodbank/active?city=${city}`),
  search: (keyword) => api.get(`/api/bloodbank/search?keyword=${keyword}`),
  count: (city) => api.get(`/api/bloodbank/count?city=${city}`),
  add: (data) => api.post('/api/bloodbank/add', data),
  update: (id, data) => api.put(`/api/bloodbank/${id}`, data),
  delete: (id) => api.delete(`/api/bloodbank/${id}`),
};

// Donor Services
export const donorService = {
  getAll: (page = 0, size = 10) => api.get(`/api/donors/all?page=${page}&size=${size}`),
  getById: (id) => api.get(`/api/donors/${id}`),
  getByBloodGroup: (bloodGroup) => api.get(`/api/donors/bloodgroup?type=${bloodGroup}`),
  getByBank: (bankId) => api.get(`/api/donors/bank/${bankId}`),
  getByGender: (gender) => api.get(`/api/donors/gender?gender=${gender}`),
  getAgeAbove: (age) => api.get(`/api/donors/age-above?age=${age}`),
  getEligible: () => api.get('/api/donors/eligible'),
  count: (bloodGroup) => api.get(`/api/donors/count?bloodGroup=${bloodGroup}`),
  add: (data) => api.post('/api/donors/add', data),
  update: (id, data) => api.put(`/api/donors/${id}`, data),
  delete: (id) => api.delete(`/api/donors/${id}`),
};

// Blood Stock Services
export const stockService = {
  getAll: (page = 0, size = 10) => api.get(`/api/stock/all?page=${page}&size=${size}`),
  getByBank: (bankId) => api.get(`/api/stock/bank/${bankId}`),
  getByBloodGroup: (bloodGroup) => api.get(`/api/stock/bloodgroup?type=${bloodGroup}`),
  getCritical: () => api.get('/api/stock/critical'),
  getCriticalUnder: (units) => api.get(`/api/stock/critical-under?units=${units}`),
  getRange: (min, max) => api.get(`/api/stock/range?min=${min}&max=${max}`),
  getTotal: (bloodGroup) => api.get(`/api/stock/total?bloodGroup=${bloodGroup}`),
  search: (bloodGroup, city) => api.get(`/api/stock/search?bloodGroup=${bloodGroup}&city=${city}`),
  add: (data) => api.post('/api/stock/add', data),
  update: (id, units) => api.patch(`/api/stock/${id}/update?units=${units}`),
  delete: (id) => api.delete(`/api/stock/${id}`),
};

export default api;
