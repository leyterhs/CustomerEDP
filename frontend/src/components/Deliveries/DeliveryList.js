import React, { useState, useEffect } from 'react';
import { getDeliveries, deleteDelivery } from '../../services/api';

function DeliveryList() {
    const [deliveries, setDeliveries] = useState([]);
    const [loading, setLoading] = useState(true);

    useEffect(() => {
        loadDeliveries();
    }, []);

    const loadDeliveries = async () => {
        try {
            const response = await getDeliveries();
            console.log('Deliveries response:', response);
            console.log('Deliveries data:', response.data);
            if (Array.isArray(response.data)) {
                setDeliveries(response.data);
            } else {
                console.error('Deliveries data is not an array:', response.data);
                setDeliveries([]);
            }
        } catch (error) {
            console.error('Error loading deliveries:', error);
            setDeliveries([]);
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

    if (loading) return <div>Loading...</div>;

    return (
        <div>
            <h2>Deliveries</h2>
            <button onClick={() => window.location.href = '/deliveries/new'}>Add Delivery</button>
            <table>
                <thead>
                    <tr>
                        <th>Title</th>
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
                            <td>{delivery.engagement?.title || 'N/A'}</td>
                            <td>{delivery.priority}</td>
                            <td>{delivery.status}</td>
                            <td>{delivery.dueDate}</td>
                            <td>
                                <button onClick={() => window.location.href = `/deliveries/${delivery.id}`}>Edit</button>
                                <button onClick={() => handleDelete(delivery.id)}>Delete</button>
                            </td>
                        </tr>
                    ))}
                </tbody>
            </table>
        </div>
    );
}

export default DeliveryList;