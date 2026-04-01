import React from 'react';
import { useNavigate } from 'react-router-dom';
import { CheckCircle } from 'lucide-react';

const OrderSuccess: React.FC = () => {
  const navigate = useNavigate();

  return (
    <div className="flex flex-col items-center justify-center min-h-[70vh] px-4">
      <CheckCircle className="w-24 h-24 text-green-500 mb-6" />
      <h1 className="text-3xl font-bold text-gray-800 mb-2">Order Placed Successfully!</h1>
      <p className="text-gray-500 text-center max-w-md mb-8">
        Thank you for shopping with Baskit. Your order is being processed and will be delivered shortly.
      </p>
      <div className="flex gap-4">
        <button
          onClick={() => navigate('/')}
          className="px-6 py-2.5 bg-green-600 text-white rounded-lg font-semibold hover:bg-green-700 transition"
        >
          Continue Shopping
        </button>
        <button
          onClick={() => navigate('/profile')}
          className="px-6 py-2.5 border border-green-600 text-green-600 rounded-lg font-semibold hover:bg-green-50 transition"
        >
          My Orders
        </button>
      </div>
    </div>
  );
};

export default OrderSuccess;
