import React, { useState, useEffect } from 'react';

function ErrorMessage({ message, onClose, duration = 5000 }) {
    const [visible, setVisible] = useState(true);

    useEffect(() => {
        const timer = setTimeout(() => {
            setVisible(false);
            if (onClose) onClose();
        }, duration);
        return () => clearTimeout(timer);
    }, [duration, onClose]);

    if (!visible || !message) return null;

    return (
        <div className="alert alert-danger alert-dismissible fade show position-fixed top-0 start-50 translate-middle-x mt-3" 
             style={{ zIndex: 9999, maxWidth: '500px', width: '90%' }} 
             role="alert">
            <i className="bi bi-exclamation-triangle-fill me-2"></i>
            {message}
            <button type="button" className="btn-close" onClick={() => setVisible(false)}></button>
        </div>
    );
}

export default ErrorMessage;