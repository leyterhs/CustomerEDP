import React, { useState, useEffect } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import { Container, Form, Button, Card, Spinner } from 'react-bootstrap';

function ClientForm() {
    const { id } = useParams();
    const navigate = useNavigate();
    const [formData, setFormData] = useState({
        name: '',
        email: '',
        phone: '',
        company: ''
    });
    const [loading, setLoading] = useState(false);
    const [isLoading, setIsLoading] = useState(true);

    useEffect(() => {
        const token = localStorage.getItem('token');
        if (!token) {
            navigate('/login');
            return;
        }
        if (id) {
            loadClient();
        } else {
            setIsLoading(false);
        }
    }, [id]);

    const loadClient = async () => {
        try {
            const token = localStorage.getItem('token');
            const response = await fetch(`http://localhost:8080/api/clients/${id}`, {
                headers: { 'Authorization': `Bearer ${token}` }
            });
            const data = await response.json();
            if (data) {
                setFormData({
                    name: data.name || '',
                    email: data.email || '',
                    phone: data.phone || '',
                    company: data.company || ''
                });
            }
        } catch (error) {
            console.error('Error loading client:', error);
        } finally {
            setIsLoading(false);
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
            name: formData.name,
            email: formData.email || '',
            phone: formData.phone || '',
            company: formData.company || ''
        };

        try {
            const url = id ? `http://localhost:8080/api/clients/${id}` : 'http://localhost:8080/api/clients';
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
                navigate('/clients');
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
                <Card.Header as="h3">{id ? 'Edit' : 'Create'} Client</Card.Header>
                <Card.Body>
                    <Form onSubmit={handleSubmit}>
                        <Form.Group className="mb-3">
                            <Form.Label>Name</Form.Label>
                            <Form.Control
                                type="text"
                                value={formData.name}
                                onChange={(e) => setFormData({ ...formData, name: e.target.value })}
                                required
                            />
                        </Form.Group>
                        <Form.Group className="mb-3">
                            <Form.Label>Email</Form.Label>
                            <Form.Control
                                type="email"
                                value={formData.email}
                                onChange={(e) => setFormData({ ...formData, email: e.target.value })}
                            />
                        </Form.Group>
                        <Form.Group className="mb-3">
                            <Form.Label>Phone</Form.Label>
                            <Form.Control
                                type="text"
                                value={formData.phone}
                                onChange={(e) => setFormData({ ...formData, phone: e.target.value })}
                            />
                        </Form.Group>
                        <Form.Group className="mb-3">
                            <Form.Label>Company</Form.Label>
                            <Form.Control
                                type="text"
                                value={formData.company}
                                onChange={(e) => setFormData({ ...formData, company: e.target.value })}
                            />
                        </Form.Group>
                        <Button variant="primary" type="submit" disabled={loading}>
                            {loading ? <Spinner animation="border" size="sm" /> : 'Save'}
                        </Button>
                        <Button variant="secondary" className="ms-2" onClick={() => navigate('/clients')}>
                            Cancel
                        </Button>
                    </Form>
                </Card.Body>
            </Card>
        </Container>
    );
}

export default ClientForm;