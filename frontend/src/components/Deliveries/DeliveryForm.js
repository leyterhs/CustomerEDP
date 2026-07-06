import React, { useState, useEffect } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import { Container, Form, Button, Card, Spinner } from 'react-bootstrap';
import { getEngagements } from '../../services/api';

function DeliveryForm() {
    const { id } = useParams();
    const navigate = useNavigate();
    const [formData, setFormData] = useState({
        title: '',
        description: '',
        engagement: { id: '' },
        priority: 'MEDIUM',
        status: 'PENDING',
        dueDate: ''
    });
    const [engagements, setEngagements] = useState([]);
    const [loading, setLoading] = useState(false);
    const [isLoading, setIsLoading] = useState(true);

    useEffect(() => {
        const token = localStorage.getItem('token');
        if (!token) {
            navigate('/login');
            return;
        }
        const fetchData = async () => {
            await loadEngagements();
            if (id) {
                await loadDelivery();
            }
            setIsLoading(false);
        };
        fetchData();
    }, [id]);

    const loadEngagements = async () => {
        try {
            const response = await getEngagements();
            setEngagements(response.data);
        } catch (error) {
            console.error('Error loading engagements:', error);
        }
    };

    const loadDelivery = async () => {
        try {
            const token = localStorage.getItem('token');
            const response = await fetch(`http://localhost:8080/api/deliveries/${id}`, {
                headers: { 'Authorization': `Bearer ${token}` }
            });
            const data = await response.json();
            if (data) {
                setFormData({
                    title: data.title || '',
                    description: data.description || '',
                    engagement: data.engagement || { id: '' },
                    priority: data.priority || 'MEDIUM',
                    status: data.status || 'PENDING',
                    dueDate: data.dueDate || ''
                });
            }
        } catch (error) {
            console.error('Error loading delivery:', error);
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
            engagement: { id: formData.engagement.id },
            priority: formData.priority,
            status: formData.status,
            dueDate: formData.dueDate || null
        };

        try {
            const url = id ? `http://localhost:8080/api/deliveries/${id}` : 'http://localhost:8080/api/deliveries';
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
                navigate('/deliveries');
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
                <Card.Header as="h3">{id ? 'Edit' : 'Create'} Delivery</Card.Header>
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
                            <Form.Label>Engagement</Form.Label>
                            <Form.Select
                                value={formData.engagement?.id || ''}
                                onChange={(e) => {
                                    const id = parseInt(e.target.value);
                                    setFormData({ ...formData, engagement: { id } });
                                }}
                                required
                            >
                                <option value="">Select Engagement</option>
                                {engagements.map(e => (
                                    <option key={e.id} value={e.id}>{e.title}</option>
                                ))}
                            </Form.Select>
                        </Form.Group>
                        <Form.Group className="mb-3">
                            <Form.Label>Priority</Form.Label>
                            <Form.Select
                                value={formData.priority}
                                onChange={(e) => setFormData({ ...formData, priority: e.target.value })}
                            >
                                <option value="LOW">Low</option>
                                <option value="MEDIUM">Medium</option>
                                <option value="HIGH">High</option>
                            </Form.Select>
                        </Form.Group>
                        <Form.Group className="mb-3">
                            <Form.Label>Status</Form.Label>
                            <Form.Select
                                value={formData.status}
                                onChange={(e) => setFormData({ ...formData, status: e.target.value })}
                            >
                                <option value="PENDING">Pending</option>
                                <option value="IN_PROGRESS">In Progress</option>
                                <option value="DONE">Done</option>
                            </Form.Select>
                        </Form.Group>
                        <Form.Group className="mb-3">
                            <Form.Label>Due Date</Form.Label>
                            <Form.Control
                                type="date"
                                value={formData.dueDate}
                                onChange={(e) => setFormData({ ...formData, dueDate: e.target.value })}
                            />
                        </Form.Group>
                        <Button variant="primary" type="submit" disabled={loading}>
                            {loading ? <Spinner animation="border" size="sm" /> : 'Save'}
                        </Button>
                        <Button variant="secondary" className="ms-2" onClick={() => navigate('/deliveries')}>
                            Cancel
                        </Button>
                    </Form>
                </Card.Body>
            </Card>
        </Container>
    );
}

export default DeliveryForm;