import axios from 'axios';

const API_BASE = process.env.REACT_APP_API_URL 
    ? `${process.env.REACT_APP_API_URL}/api` 
    : 'http://localhost:8080/api';

const getToken = () => localStorage.getItem('token');

console.log('API_BASE is:', API_BASE);
console.log('Token exists?', !!localStorage.getItem('token'));

// Clients
export const getClients = () => axios.get(`${API_BASE}/clients`, {
    headers: { 'Authorization': `Bearer ${getToken()}` }
});
export const getClient = (id) => axios.get(`${API_BASE}/clients/${id}`, {
    headers: { 'Authorization': `Bearer ${getToken()}` }
});
export const createClient = (data) => axios.post(`${API_BASE}/clients`, data, {
    headers: { 'Authorization': `Bearer ${getToken()}` }
});
export const updateClient = (id, data) => axios.put(`${API_BASE}/clients/${id}`, data, {
    headers: { 'Authorization': `Bearer ${getToken()}` }
});
export const deleteClient = (id) => axios.delete(`${API_BASE}/clients/${id}`, {
    headers: { 'Authorization': `Bearer ${getToken()}` }
});

// Engagements
export const getEngagements = () => axios.get(`${API_BASE}/engagements`, {
    headers: { 'Authorization': `Bearer ${getToken()}` }
});
export const getEngagement = (id) => axios.get(`${API_BASE}/engagements/${id}`, {
    headers: { 'Authorization': `Bearer ${getToken()}` }
});
export const createEngagement = (data) => axios.post(`${API_BASE}/engagements`, data, {
    headers: { 'Authorization': `Bearer ${getToken()}` }
});
export const updateEngagement = (id, data) => axios.put(`${API_BASE}/engagements/${id}`, data, {
    headers: { 'Authorization': `Bearer ${getToken()}` }
});
export const deleteEngagement = (id) => axios.delete(`${API_BASE}/engagements/${id}`, {
    headers: { 'Authorization': `Bearer ${getToken()}` }
});

// Deliveries
export const getDeliveries = () => axios.get(`${API_BASE}/deliveries`, {
    headers: { 'Authorization': `Bearer ${getToken()}` }
});
export const getDelivery = (id) => axios.get(`${API_BASE}/deliveries/${id}`, {
    headers: { 'Authorization': `Bearer ${getToken()}` }
});
export const createDelivery = (data) => axios.post(`${API_BASE}/deliveries`, data, {
    headers: { 'Authorization': `Bearer ${getToken()}` }
});
export const updateDelivery = (id, data) => axios.put(`${API_BASE}/deliveries/${id}`, data, {
    headers: { 'Authorization': `Bearer ${getToken()}` }
});
export const deleteDelivery = (id) => axios.delete(`${API_BASE}/deliveries/${id}`, {
    headers: { 'Authorization': `Bearer ${getToken()}` }
});

// ============ USERS (Admin) ============

export const getUsers = () => axios.get(`${API_BASE}/admin/users`, {
    headers: { 'Authorization': `Bearer ${getToken()}` }
});

export const deleteUser = (id) => axios.delete(`${API_BASE}/admin/users/${id}`, {
    headers: { 'Authorization': `Bearer ${getToken()}` }
});

export const createUser = (data) => axios.post(`${API_BASE}/admin/users`, data, {
    headers: { 'Authorization': `Bearer ${getToken()}` }
});

// Default export (για τα components που το χρησιμοποιούν)
const apiService = {
    get: (url) => axios.get(`${API_BASE}${url}`, {
        headers: { 'Authorization': `Bearer ${getToken()}` }
    }),
    post: (url, data) => axios.post(`${API_BASE}${url}`, data, {
        headers: { 'Authorization': `Bearer ${getToken()}` }
    }),
    put: (url, data) => axios.put(`${API_BASE}${url}`, data, {
        headers: { 'Authorization': `Bearer ${getToken()}` }
    }),
    delete: (url) => axios.delete(`${API_BASE}${url}`, {
        headers: { 'Authorization': `Bearer ${getToken()}` }
    })
};

export default apiService;