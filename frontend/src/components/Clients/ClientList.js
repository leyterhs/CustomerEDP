import React, { useState, useEffect } from 'react';
import { getClients, deleteClient } from '../../services/api';
import { Link } from 'react-router-dom';
import ErrorMessage from '../ErrorMessage';

function ClientList() {
    const [clients, setClients] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);

    useEffect(() => {
        loadClients();
    }, []);

    const loadClients = async () => {
        try {
            const response = await getClients();
            setClients(response.data);
        } catch (error) {
            console.error('Error loading clients:', error);
            setError('Failed to load clients. Please try again.');
        } finally {
            setLoading(false);
        }
    };

    const handleDelete = async (id) => {
        if (!window.confirm('Delete this client?')) return;
        try {
            await deleteClient(id);
            setClients(clients.filter(c => c.id !== id));
            setError(null);
        } catch (err) {
            console.error('Delete failed:', err);
            if (err.response && err.response.status === 403) {
                setError('You do not have permission to delete this client. Only ADMIN users can delete.');
            } else {
                setError('Failed to delete client. Please try again.');
            }
        }
    };

    if (loading) return (
        <div className="d-flex justify-content-center align-items-center" style={{ minHeight: '200px' }}>
            <div className="spinner-border text-primary" role="status">
                <span className="visually-hidden">Loading...</span>
            </div>
        </div>
    );

    return (
        <div className="container my-4">
            {error && <ErrorMessage message={error} onClose={() => setError(null)} />}

            <div className="d-flex justify-content-between align-items-center mb-4">
                <h1 className="mb-0">Clients</h1>
                <Link to="/clients/new" className="btn btn-primary">
                    <i className="bi bi-plus-circle me-2"></i>Add Client
                </Link>
            </div>

            <div className="card">
                <div className="card-body p-0">
                    <div className="table-responsive">
                        <table className="table table-hover mb-0">
                            <thead>
                                <tr>
                                    <th>Name</th>
                                    <th>Email</th>
                                    <th>Phone</th>
                                    <th>Company</th>
                                    <th className="text-end">Actions</th>
                                </tr>
                            </thead>
                            <tbody>
                                {clients.length === 0 ? (
                                    <tr>
                                        <td colSpan="5" className="text-center text-muted py-4">
                                            No clients found. Create your first client!
                                        </td>
                                    </tr>
                                ) : (
                                    clients.map(client => (
                                        <tr key={client.id}>
                                            <td className="fw-semibold">{client.name}</td>
                                            <td>{client.email || 'N/A'}</td>
                                            <td>{client.phone || 'N/A'}</td>
                                            <td>{client.company || 'N/A'}</td>
                                            <td className="text-end">
                                                <Link to={`/clients/${client.id}`} className="btn btn-sm btn-outline-secondary me-2">
                                                    <i className="bi bi-pencil"></i> Edit
                                                </Link>
                                                <button onClick={() => handleDelete(client.id)} className="btn btn-sm btn-danger">
                                                    <i className="bi bi-trash"></i> Delete
                                                </button>
                                            </td>
                                        </tr>
                                    ))
                                )}
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
        </div>
    );
}

export default ClientList;