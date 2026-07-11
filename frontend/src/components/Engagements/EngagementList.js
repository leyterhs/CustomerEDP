import React, { useState, useEffect } from 'react';
import { getEngagements, deleteEngagement } from '../../services/api';
import { Link } from 'react-router-dom';
import ErrorMessage from '../ErrorMessage';

function EngagementList() {
    const [engagements, setEngagements] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);

    useEffect(() => {
        loadEngagements();
    }, []);

    const loadEngagements = async () => {
        try {
            console.log('🔍 Fetching engagements...');
            const response = await getEngagements();
            console.log('✅ Raw response:', response);
            console.log('📦 Response data:', response.data);
            console.log('📊 Is array?', Array.isArray(response.data));
            
            if (response.data && Array.isArray(response.data)) {
                setEngagements(response.data);
                if (response.data.length === 0) {
                    setError('No engagements found. Create your first engagement!');
                }
            } else {
                console.error('❌ Unexpected response format:', response);
                setError('Invalid response format from server');
            }
        } catch (error) {
            console.error('❌ Error loading engagements:', error);
            if (error.response && error.response.status === 403) {
                setError('You do not have permission to view engagements. Only ADMIN users can view.');
            } else {
                setError('Failed to load engagements. Please try again.');
            }
        } finally {
            setLoading(false);
        }
    };

    const handleDelete = async (id) => {
        if (!window.confirm('Delete this engagement?')) return;
        try {
            await deleteEngagement(id);
            setEngagements(engagements.filter(e => e.id !== id));
            setError(null);
        } catch (err) {
            console.error('❌ Delete failed:', err);
            if (err.response && err.response.status === 403) {
                setError('You do not have permission to delete this engagement. Only ADMIN users can delete.');
            } else {
                setError('Failed to delete engagement. Please try again.');
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
                <h1 className="mb-0">Engagements</h1>
                <Link to="/engagements/new" className="btn btn-primary">
                    <i className="bi bi-plus-circle me-2"></i>Add Engagement
                </Link>
            </div>

            <div className="card">
                <div className="card-body p-0">
                    <div className="table-responsive">
                        <table className="table table-hover mb-0">
                            <thead>
                                <tr>
                                    <th>Title</th>
                                    <th>Client</th>
                                    <th>Status</th>
                                    <th>Budget</th>
                                    <th className="text-end">Actions</th>
                                </tr>
                            </thead>
                            <tbody>
                                {engagements.length === 0 ? (
                                    <tr>
                                        <td colSpan="5" className="text-center text-muted py-4">
                                            No engagements found. Create your first engagement!
                                        </td>
                                    </tr>
                                ) : (
                                    engagements.map(engagement => (
                                        <tr key={engagement.id}>
                                            <td className="fw-semibold">{engagement.title}</td>
                                            <td>{engagement.client?.name || 'N/A'}</td>
                                            <td>
                                                <span className={`badge bg-${engagement.status === 'ACTIVE' ? 'success' : engagement.status === 'PENDING' ? 'warning' : 'secondary'}`}>
                                                    {engagement.status || 'N/A'}
                                                </span>
                                            </td>
                                            <td>{engagement.budget ? `€${engagement.budget}` : 'N/A'}</td>
                                            <td className="text-end">
                                                <Link to={`/engagements/${engagement.id}`} className="btn btn-sm btn-outline-secondary me-2">
                                                    <i className="bi bi-pencil"></i> Edit
                                                </Link>
                                                <button onClick={() => handleDelete(engagement.id)} className="btn btn-sm btn-danger">
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

export default EngagementList;