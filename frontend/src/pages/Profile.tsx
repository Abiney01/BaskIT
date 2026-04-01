import React, { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import api from '../lib/apiClient';
import toast from 'react-hot-toast';
import type { User } from '../types';

const Profile: React.FC = () => {
  const navigate = useNavigate();
  const [user, setUser] = useState<User | null>(null);
  const [orders, setOrders] = useState<any[]>([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    const stored = sessionStorage.getItem('user');
    if (!stored) {
      toast.error('Please log in');
      navigate('/login');
      return;
    }
    const u: User = JSON.parse(stored);
    setUser(u);

    api.get(`/orders/user/${u.userId}`)
      .then(r => setOrders(r.data || []))
      .catch(() => setOrders([]))
      .finally(() => setLoading(false));
  }, [navigate]);

  const handleLogout = () => {
    sessionStorage.removeItem('user');
    sessionStorage.removeItem('cartId');
    navigate('/login');
  };

  if (!user) return null;

  return (
    <div className="max-w-3xl mx-auto px-4 py-10">
      <div className="bg-white rounded-xl shadow-md p-6 mb-8">
        <div className="flex items-center justify-between mb-4">
          <h1 className="text-2xl font-bold text-gray-800">My Profile</h1>
          <button
            onClick={handleLogout}
            className="px-4 py-2 border border-red-400 text-red-500 rounded-lg text-sm hover:bg-red-50 transition"
          >
            Logout
          </button>
        </div>

        <div className="grid grid-cols-1 md:grid-cols-2 gap-4 text-sm">
          {[
            { label: 'Name', value: user.name },
            { label: 'Email', value: user.email },
            { label: 'Address', value: user.address || 'N/A' },
          ].map(({ label, value }) => (
            <div key={label} className="bg-gray-50 rounded-lg p-4">
              <span className="text-gray-500 text-xs uppercase tracking-wide">{label}</span>
              <p className="font-semibold text-gray-800 mt-1">{value}</p>
            </div>
          ))}
        </div>
      </div>

      <div className="bg-white rounded-xl shadow-md p-6">
        <h2 className="text-xl font-semibold text-gray-800 mb-4">Order History</h2>
        {loading ? (
          <p className="text-gray-400">Loading orders...</p>
        ) : orders.length === 0 ? (
          <p className="text-gray-400">No orders found.</p>
        ) : (
          <div className="space-y-4">
            {orders.map((order: any) => (
              <div key={order.orderId} className="border rounded-lg p-4">
                <div className="flex justify-between items-center">
                  <div>
                    <span className="text-sm text-gray-500">Order #{order.orderId}</span>
                    <p className="font-semibold text-gray-800">₹{order.totalAmount?.toFixed(2)}</p>
                    <p className="text-xs text-gray-500">{order.address}</p>
                  </div>
                  <span className={`text-xs font-semibold px-3 py-1 rounded-full
                    ${order.orderStatus === 'Delivered' ? 'bg-green-100 text-green-700'
                      : order.orderStatus === 'Pending' ? 'bg-yellow-100 text-yellow-700'
                      : 'bg-blue-100 text-blue-700'}`}>
                    {order.orderStatus || 'Pending'}
                  </span>
                </div>
              </div>
            ))}
          </div>
        )}
      </div>
    </div>
  );
};

export default Profile;
