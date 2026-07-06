import React from 'react';
import { BrowserRouter, Routes, Route, Navigate } from 'react-router-dom';
import AppNavbar from './components/Navbar';
import Login from './components/Login';
import Register from './components/Register';
import AdminPanel from './components/AdminPanel';
import ClientList from './components/Clients/ClientList';
import ClientForm from './components/Clients/ClientForm';
import EngagementList from './components/Engagements/EngagementList';
import EngagementForm from './components/Engagements/EngagementForm';
import DeliveryList from './components/Deliveries/DeliveryList';
import DeliveryForm from './components/Deliveries/DeliveryForm';

function App() {
    return (
        <BrowserRouter>
            <AppNavbar />
            <div className="container mt-4">
                <Routes>
                    <Route path="/login" element={<Login />} />
                    <Route path="/register" element={<Register />} />
                    <Route path="/admin" element={<AdminPanel />} />
                    <Route path="/clients" element={<ClientList />} />
                    <Route path="/clients/new" element={<ClientForm />} />
                    <Route path="/clients/:id" element={<ClientForm />} />
                    <Route path="/engagements" element={<EngagementList />} />
                    <Route path="/engagements/new" element={<EngagementForm />} />
                    <Route path="/engagements/:id" element={<EngagementForm />} />
                    <Route path="/deliveries" element={<DeliveryList />} />
                    <Route path="/deliveries/new" element={<DeliveryForm />} />
                    <Route path="/deliveries/:id" element={<DeliveryForm />} />
                    <Route path="/" element={<Navigate to="/login" />} />
                </Routes>
            </div>
        </BrowserRouter>
    );
}

export default App;