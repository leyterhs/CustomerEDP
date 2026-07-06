import React from 'react';
import { Link, useNavigate } from 'react-router-dom';
import { Navbar, Nav, Button, Container } from 'react-bootstrap';

function AppNavbar() {
    const navigate = useNavigate();
    const token = localStorage.getItem('token');

    const handleLogout = () => {
        localStorage.clear();
        navigate('/login');
    };

    if (!token) return null;

    return (
        <Navbar bg="primary" variant="dark" expand="lg">
            <Container>
                <Navbar.Brand href="/deliveries">
                    <img
                        src="/logo.png"
                        width="30"
                        height="30"
                        className="d-inline-block align-top me-2"
                        alt="CustomerEDP Logo"
                    />
                    CustomerEDP
                </Navbar.Brand>
                <Navbar.Toggle aria-controls="basic-navbar-nav" />
                <Navbar.Collapse id="basic-navbar-nav">
                    <Nav className="me-auto">
                        <Nav.Link as={Link} to="/clients">Clients</Nav.Link>
                        <Nav.Link as={Link} to="/engagements">Engagements</Nav.Link>
                        <Nav.Link as={Link} to="/deliveries">Deliveries</Nav.Link>
                        <Nav.Link as={Link} to="/admin">Admin Panel</Nav.Link>
                    </Nav>
                    <Button variant="outline-light" onClick={handleLogout}>Logout</Button>
                </Navbar.Collapse>
            </Container>
        </Navbar>
    );
}

export default AppNavbar;