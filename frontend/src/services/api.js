import axios from 'axios';

const API_BASE = 'http://localhost:8080/api';

const getToken = () => localStorage.getItem('token');

// Clients
export const getClients = () => axios.get(`${API_BASE}/clients`, {
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
export const createDelivery = (data) => axios.post(`${API_BASE}/deliveries`, data, {
    headers: { 'Authorization': `Bearer ${getToken()}` }
});
export const updateDelivery = (id, data) => axios.put(`${API_BASE}/deliveries/${id}`, data, {
    headers: { 'Authorization': `Bearer ${getToken()}` }
});
export const deleteDelivery = (id) => axios.delete(`${API_BASE}/deliveries/${id}`, {
    headers: { 'Authorization': `Bearer ${getToken()}` }
});

// Default export για τις υπάρχουσες κλάσεις που χρησιμοποιούν το api
export default {
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