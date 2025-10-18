import React from 'react';

const LoadingSpinner = ({ size = 'medium', color = 'white' }) => {
  const sizeClasses = {
    small: 'spinner-sm',
    medium: 'spinner-md',
    large: 'spinner-lg'
  };

  return (
    <div className={`loading-spinner ${sizeClasses[size]}`}>
      <div className="spinner-ring">
        <div></div>
        <div></div>
        <div></div>
        <div></div>
      </div>
    </div>
  );
};

export default LoadingSpinner;

