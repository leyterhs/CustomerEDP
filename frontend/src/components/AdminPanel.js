import React, { useState, useEffect } from 'react';
import { getUsers, deleteUser, createUser } from '../services/api';
import { Link } from 'react-router-dom';
import ErrorMessage from './ErrorMessage';

function AdminPanel() {
    const [users, setUsers] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);
    const [form, setForm] = useState({ username: '', email: '', password: '', role: 'MEMBER' });

    useEffect(() => {
        loadUsers();
    }, []);

    const loadUsers = async () => {
        try {
            const response = await getUsers();
            setUsers(response.data);
        } catch (error) {
            console.error('Error loading users:', error);
            setError('Failed to load users. Please try again.');
        } finally {
            setLoading(false);
        }
    };

    const handleDelete = async (id) => {
        if (!window.confirm(`Delete user with ID ${id}?`)) return;
        try {
            await deleteUser(id);
            setUsers(users.filter(u => u.id !== id));
            setError(null);
        } catch (err) {
            console.error('Delete user failed:', err);
            if (err.response && err.response.status === 403) {
                setError('You do not have permission to delete users. Only ADMIN users can delete.');
            } else {
                setError('Failed to delete user. Please try again.');
            }
        }
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        try {
            const response = await createUser(form);
            setUsers([...users, response.data]);
            setForm({ username: '', email: '', password: '', role: 'MEMBER' });
            setError(null);
        } catch (err) {
            console.error('Create user failed:', err);
            if (err.response && err.response.status === 403) {
                setError('You do not have permission to create users. Only ADMIN users can create.');
            } else {
                setError('Failed to create user. Please try again.');
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
                <h1 className="mb-0">Admin Panel - User Management</h1>
                <button className="btn btn-outline-secondary" onClick={() => window.location.href = '/login'}>
                    <i className="bi bi-box-arrow-right me-2"></i>Logout
                </button>
            </div>

            {/* Create User Card */}
            <div className="card mb-4">
                <div className="card-header">
                    <h5 className="mb-0">Create New User</h5>
                </div>
                <div className="card-body">
                    <form onSubmit={handleSubmit}>
                        <div className="row g-3">
                            <div className="col-md-3">
                                <label className="form-label">Username</label>
                                <input
                                    type="text"
                                    className="form-control"
                                    value={form.username}
                                    onChange={(e) => setForm({ ...form, username: e.target.value })}
                                    required
                                />
                            </div>
                            <div className="col-md-3">
                                <label className="form-label">Email</label>
                                <input
                                    type="email"
                                    className="form-control"
                                    value={form.email}
                                    onChange={(e) => setForm({ ...form, email: e.target.value })}
                                    required
                                />
                            </div>
                            <div className="col-md-2">
                                <label className="form-label">Password</label>
                                <input
                                    type="password"
                                    className="form-control"
                                    value={form.password}
                                    onChange={(e) => setForm({ ...form, password: e.target.value })}
                                    required
                                />
                            </div>
                            <div className="col-md-2">
                                <label className="form-label">Role</label>
                                <select
                                    className="form-select"
                                    value={form.role}
                                    onChange={(e) => setForm({ ...form, role: e.target.value })}
                                >
                                    <option value="MEMBER">MEMBER</option>
                                    <option value="ADMIN">ADMIN</option>
                                </select>
                            </div>
                            <div className="col-md-2 d-flex align-items-end">
                                <button type="submit" className="btn btn-primary w-100">
                                    <i className="bi bi-person-plus me-2"></i>Create User
                                </button>
                            </div>
                        </div>
                    </form>
                </div>
            </div>

            {/* Users Table Card */}
            <div className="card">
                <div className="card-header">
                    <h5 className="mb-0">All Users</h5>
                </div>
                <div className="card-body p-0">
                    <div className="table-responsive">
                        <table className="table table-hover mb-0">
                            <thead>
                                <tr>
                                    <th>ID</th>
                                    <th>Username</th>
                                    <th>Email</th>
                                    <th>Role</th>
                                    <th className="text-end">Actions</th>
                                </tr>
                            </thead>
                            <tbody>
                                {users.length === 0 ? (
                                    <tr>
                                        <td colSpan="5" className="text-center text-muted py-4">
                                            No users found.
                                        </td>
                                    </tr>
                                ) : (
                                    users.map(user => (
                                        <tr key={user.id}>
                                            <td>{user.id}</td>
                                            <td className="fw-semibold">{user.username}</td>
                                            <td>{user.email}</td>
                                            <td>
                                                <span className={`badge bg-${user.role === 'ADMIN' ? 'danger' : 'secondary'}`}>
                                                    {user.role}
                                                </span>
                                            </td>
                                            <td className="text-end">
                                                <button
                                                    onClick={() => handleDelete(user.id)}
                                                    className="btn btn-sm btn-danger"
                                                    disabled={user.username === 'admin'}
                                                >
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

export default AdminPanel;