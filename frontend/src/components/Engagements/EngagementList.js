import React, { useState, useEffect } from 'react';
import { getEngagements, deleteEngagement } from '../../services/api';

function EngagementList() {
    const [engagements, setEngagements] = useState([]);
    const [loading, setLoading] = useState(true);

    useEffect(() => {
        loadEngagements();
    }, []);

    const loadEngagements = async () => {
    try {
        const response = await getEngagements();
        console.log('Engagements response:', response);
        console.log('Engagements data:', response.data);
        console.log('Is array?', Array.isArray(response.data));
        setEngagements(response.data);
    } catch (error) {
        console.error('Error loading engagements:', error);
    } finally {
        setLoading(false);
    }
};

    const handleDelete = async (id) => {
        if (!window.confirm('Delete this engagement?')) return;
        try {
            await deleteEngagement(id);
            setEngagements(engagements.filter(e => e.id !== id));
        } catch (error) {
            console.error('Delete failed:', error);
        }
    };

    if (loading) return <div>Loading...</div>;

    return (
        <div>
            <h2>Engagements</h2>
            <button onClick={() => window.location.href = '/engagements/new'}>Add Engagement</button>
            <table>
                <thead>
                    <tr>
                        <th>Title</th>
                        <th>Client</th>
                        <th>Status</th>
                        <th>Budget</th>
                        <th>Actions</th>
                    </tr>
                </thead>
                <tbody>
                    {engagements.map(engagement => (
                        <tr key={engagement.id}>
                            <td>{engagement.title}</td>
                            <td>{engagement.client?.name || 'N/A'}</td>
                            <td>{engagement.status}</td>
                            <td>{engagement.budget}</td>
                            <td>
                                <button onClick={() => window.location.href = `/engagements/${engagement.id}`}>Edit</button>
                                <button onClick={() => handleDelete(engagement.id)}>Delete</button>
                            </td>
                        </tr>
                    ))}
                </tbody>
            </table>
        </div>
    );
}

export default EngagementList;