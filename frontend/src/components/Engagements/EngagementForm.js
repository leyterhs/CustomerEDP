import React, { useState, useEffect } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import { Container, Form, Button, Card, Spinner } from 'react-bootstrap';
import { getClients } from '../../services/api';

function EngagementForm() {
    const { id } = useParams();
    const navigate = useNavigate();
    const [formData, setFormData] = useState({
        title: '',
        description: '',
        client: { id: '' },
        status: 'ACTIVE',
        budget: '',
        deadline: ''
    });
    const [clients, setClients] = useState([]);
    const [loading, setLoading] = useState(false);
    const [isLoading, setIsLoading] = useState(true);

    useEffect(() => {
        const token = localStorage.getItem('token');
        if (!token) {
            navigate('/login');
            return;
        }
        const fetchData = async () => {
            await loadClients();
            if (id) {
                await loadEngagement();
            }
            setIsLoading(false);
        };
        fetchData();
    }, [id]);

    const loadClients = async () => {
        try {
            const response = await getClients();
            setClients(response.data);
        } catch (error) {
            console.error('Error loading clients:', error);
        }
    };

    const loadEngagement = async () => {
        try {
            const token = localStorage.getItem('token');
            const response = await fetch(`http://localhost:8080/api/engagements/${id}`, {
                headers: { 'Authorization': `Bearer ${token}` }
            });
            const data = await response.json();
            if (data) {
                setFormData({
                    title: data.title || '',
                    description: data.description || '',
                    client: data.client || { id: '' },
                    status: data.status || 'ACTIVE',
                    budget: data.budget || '',
                    deadline: data.deadline || ''
                });
            }
        } catch (error) {
            console.error('Error loading engagement:', error);
        }
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        setLoading(true);
        const token = localStorage.getItem('token');
        if (!token) {
            alert('You are not logged in!');
            setLoading(false);
            return;
        }

        const data = {
            title: formData.title,
            description: formData.description || '',
            client: { id: formData.client.id },
            status: formData.status,
            budget: formData.budget ? parseFloat(formData.budget) : null,
            deadline: formData.deadline || null
        };

        try {
            const url = id ? `http://localhost:8080/api/engagements/${id}` : 'http://localhost:8080/api/engagements';
            const method = id ? 'PUT' : 'POST';
            const response = await fetch(url, {
                method: method,
                headers: {
                    'Content-Type': 'application/json',
                    'Authorization': `Bearer ${token}`
                },
                body: JSON.stringify(data)
            });
            if (response.ok) {
                navigate('/engagements');
            } else {
                const text = await response.text();
                alert('Error: ' + text);
            }
        } catch (error) {
            console.error('Fetch error:', error);
            alert('Network error: ' + error.message);
        } finally {
            setLoading(false);
        }
    };

    if (isLoading) {
        return (
            <Container className="text-center mt-5">
                <Spinner animation="border" variant="primary" />
            </Container>
        );
    }

    return (
        <Container className="mt-4">
            <Card>
                <Card.Header as="h3">{id ? 'Edit' : 'Create'} Engagement</Card.Header>
                <Card.Body>
                    <Form onSubmit={handleSubmit}>
                        <Form.Group className="mb-3">
                            <Form.Label>Title</Form.Label>
                            <Form.Control
                                type="text"
                                value={formData.title}
                                onChange={(e) => setFormData({ ...formData, title: e.target.value })}
                                required
                            />
                        </Form.Group>
                        <Form.Group className="mb-3">
                            <Form.Label>Description</Form.Label>
                            <Form.Control
                                as="textarea"
                                rows={3}
                                value={formData.description}
                                onChange={(e) => setFormData({ ...formData, description: e.target.value })}
                            />
                        </Form.Group>
                        <Form.Group className="mb-3">
                            <Form.Label>Client</Form.Label>
                            <Form.Select
                                value={formData.client?.id || ''}
                                onChange={(e) => {
                                    const id = parseInt(e.target.value);
                                    setFormData({ ...formData, client: { id } });
                                }}
                                required
                            >
                                <option value="">Select Client</option>
                                {clients.map(c => (
                                    <option key={c.id} value={c.id}>{c.name}</option>
                                ))}
                            </Form.Select>
                        </Form.Group>
                        <Form.Group className="mb-3">
                            <Form.Label>Status</Form.Label>
                            <Form.Select
                                value={formData.status}
                                onChange={(e) => setFormData({ ...formData, status: e.target.value })}
                            >
                                <option value="ACTIVE">Active</option>
                                <option value="COMPLETED">Completed</option>
                                <option value="ARCHIVED">Archived</option>
                            </Form.Select>
                        </Form.Group>
                        <Form.Group className="mb-3">
                            <Form.Label>Budget</Form.Label>
                            <Form.Control
                                type="number"
                                value={formData.budget}
                                onChange={(e) => setFormData({ ...formData, budget: e.target.value })}
                            />
                        </Form.Group>
                        <Form.Group className="mb-3">
                            <Form.Label>Deadline</Form.Label>
                            <Form.Control
                                type="date"
                                value={formData.deadline}
                                onChange={(e) => setFormData({ ...formData, deadline: e.target.value })}
                            />
                        </Form.Group>
                        <Button variant="primary" type="submit" disabled={loading}>
                            {loading ? <Spinner animation="border" size="sm" /> : 'Save'}
                        </Button>
                        <Button variant="secondary" className="ms-2" onClick={() => navigate('/engagements')}>
                            Cancel
                        </Button>
                    </Form>
                </Card.Body>
            </Card>
        </Container>
    );
}

export default EngagementForm;