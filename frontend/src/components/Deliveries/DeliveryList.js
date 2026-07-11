import React, { useState, useEffect } from 'react';
import { getDeliveries, deleteDelivery } from '../../services/api';
import { Link } from 'react-router-dom';
import ErrorMessage from '../ErrorMessage';

function DeliveryList() {
    const [deliveries, setDeliveries] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);

    useEffect(() => {
        loadDeliveries();
    }, []);

    const loadDeliveries = async () => {
        try {
            const response = await getDeliveries();
            setDeliveries(response.data);
        } catch (error) {
            console.error('Error loading deliveries:', error);
            setError('Failed to load deliveries. Please try again.');
        } finally {
            setLoading(false);
        }
    };

    const handleDelete = async (id) => {
        if (!window.confirm('Delete this delivery?')) return;
        try {
            await deleteDelivery(id);
            setDeliveries(deliveries.filter(d => d.id !== id));
            setError(null);
        } catch (err) {
            console.error('Delete failed:', err);
            if (err.response && err.response.status === 403) {
                setError('You do not have permission to delete this delivery. Only ADMIN users can delete.');
            } else {
                setError('Failed to delete delivery. Please try again.');
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
                <h1 className="mb-0">Deliveries</h1>
                <Link to="/deliveries/new" className="btn btn-primary">
                    <i className="bi bi-plus-circle me-2"></i>Add Delivery
                </Link>
            </div>

            <div className="card">
                <div className="card-body p-0">
                    <div className="table-responsive">
                        <table className="table table-hover mb-0">
                            <thead>
                                <tr>
                                    <th>Title</th>
                                    <th>Engagement</th>
                                    <th>Priority</th>
                                    <th>Status</th>
                                    <th>Due Date</th>
                                    <th className="text-end">Actions</th>
                                </tr>
                            </thead>
                            <tbody>
                                {deliveries.length === 0 ? (
                                    <tr>
                                        <td colSpan="6" className="text-center text-muted py-4">
                                            No deliveries found. Create your first delivery!
                                        </td>
                                    </tr>
                                ) : (
                                    deliveries.map(delivery => (
                                        <tr key={delivery.id}>
                                            <td className="fw-semibold">{delivery.title}</td>
                                            <td>{delivery.engagement?.title || 'N/A'}</td>
                                            <td>
                                                <span className={`badge bg-${delivery.priority === 'HIGH' ? 'danger' : delivery.priority === 'MEDIUM' ? 'warning' : 'secondary'}`}>
                                                    {delivery.priority || 'N/A'}
                                                </span>
                                            </td>
                                            <td>
                                                <span className={`badge bg-${delivery.status === 'COMPLETED' ? 'success' : delivery.status === 'IN_PROGRESS' ? 'info' : 'secondary'}`}>
                                                    {delivery.status || 'N/A'}
                                                </span>
                                            </td>
                                            <td>{delivery.dueDate ? new Date(delivery.dueDate).toLocaleDateString() : 'N/A'}</td>
                                            <td className="text-end">
                                                <Link to={`/deliveries/${delivery.id}`} className="btn btn-sm btn-outline-secondary me-2">
                                                    <i className="bi bi-pencil"></i> Edit
                                                </Link>
                                                <button onClick={() => handleDelete(delivery.id)} className="btn btn-sm btn-danger">
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

export default DeliveryList;