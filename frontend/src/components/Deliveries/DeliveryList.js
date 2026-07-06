import React, { useState, useEffect } from 'react';
import { getDeliveries, deleteDelivery } from '../../services/api';
import { Container, Table, Button, Spinner, Alert } from 'react-bootstrap';
import { Link } from 'react-router-dom';

function DeliveryList() {
    const [deliveries, setDeliveries] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState('');

    useEffect(() => {
        loadDeliveries();
    }, []);

    const loadDeliveries = async () => {
        setLoading(true);
        try {
            const response = await getDeliveries();
            setDeliveries(response.data);
        } catch (err) {
            console.error('Error loading deliveries:', err);
            setError('Failed to load deliveries.');
        } finally {
            setLoading(false);
        }
    };

    const handleDelete = async (id) => {
        if (!window.confirm('Delete this delivery?')) return;
        try {
            await deleteDelivery(id);
            setDeliveries(deliveries.filter(d => d.id !== id));
        } catch (error) {
            console.error('Delete failed:', error);
        }
    };

    if (loading) {
        return (
            <Container className="text-center mt-5">
                <Spinner animation="border" variant="primary" />
            </Container>
        );
    }

    if (error) {
        return (
            <Container className="mt-5">
                <Alert variant="danger">{error}</Alert>
            </Container>
        );
    }

    return (
        <Container className="mt-4">
            <h2>Deliveries</h2>
            <Link to="/deliveries/new" className="btn btn-primary mb-3">Add Delivery</Link>
            <Table striped bordered hover responsive>
                <thead>
                    <tr>
                        <th>Title</th>
                        <th>Description</th>
                        <th>Engagement</th>
                        <th>Priority</th>
                        <th>Status</th>
                        <th>Due Date</th>
                        <th>Actions</th>
                    </tr>
                </thead>
                <tbody>
                    {deliveries.map(delivery => (
                        <tr key={delivery.id}>
                            <td>{delivery.title}</td>
                            <td>{delivery.description || ''}</td>
                            <td>{delivery.engagement?.title || 'N/A'}</td>
                            <td>{delivery.priority}</td>
                            <td>{delivery.status}</td>
                            <td>{delivery.dueDate}</td>
                            <td>
                                <Link to={`/deliveries/${delivery.id}`} className="btn btn-sm btn-warning me-2">Edit</Link>
                                <Button variant="danger" size="sm" onClick={() => handleDelete(delivery.id)}>Delete</Button>
                            </td>
                        </tr>
                    ))}
                </tbody>
            </Table>
        </Container>
    );
}

export default DeliveryList;