import React, { useState, useEffect } from 'react';
import { getClients, deleteClient } from '../../services/api';

function ClientList() {
    const [clients, setClients] = useState([]);
    const [loading, setLoading] = useState(true);

    useEffect(() => {
        loadClients();
    }, []);

    const loadClients = async () => {
        try {
            const response = await getClients();
            setClients(response.data);
        } catch (error) {
            console.error('Error loading clients:', error);
        } finally {
            setLoading(false);
        }
    };

    const handleDelete = async (id) => {
        if (!window.confirm('Delete this client?')) return;
        try {
            await deleteClient(id);
            setClients(clients.filter(c => c.id !== id));
        } catch (error) {
            console.error('Delete failed:', error);
        }
    };

    if (loading) return <div>Loading...</div>;

    return (
        <div>
            <h2>Clients</h2>
            <button onClick={() => window.location.href = '/clients/new'}>Add Client</button>
            <table>
                <thead>
                    <tr>
                        <th>Name</th>
                        <th>Email</th>
                        <th>Company</th>
                        <th>Actions</th>
                    </tr>
                </thead>
                <tbody>
                    {clients.map(client => (
                        <tr key={client.id}>
                            <td>{client.name}</td>
                            <td>{client.email}</td>
                            <td>{client.company}</td>
                            <td>
                                <button onClick={() => window.location.href = `/clients/${client.id}`}>Edit</button>
                                <button onClick={() => handleDelete(client.id)}>Delete</button>
                            </td>
                        </tr>
                    ))}
                </tbody>
            </table>
        </div>
    );
}

export default ClientList;