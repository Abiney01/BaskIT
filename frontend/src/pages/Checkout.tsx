import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { useCart } from '../context/CartContext';
import api from '../lib/apiClient';
import toast from 'react-hot-toast';

const Checkout: React.FC = () => {
  const { cartItems, totalPrice, cartId } = useCart();
  const navigate = useNavigate();
  const [name, setName] = useState('');
  const [phone, setPhone] = useState('');
  const [address, setAddress] = useState('');
  const [paymentMethod, setPaymentMethod] = useState<'cod' | 'online'>('cod');
  const [loading, setLoading] = useState(false);

  const user = (() => {
    try { return JSON.parse(sessionStorage.getItem('user') || 'null'); } catch { return null; }
  })();

  const handleOrder = async (e: React.FormEvent) => {
    e.preventDefault();
    if (!user) { toast.error('Please login'); navigate('/login'); return; }
    if (cartItems.length === 0) { toast.error('Your cart is empty'); return; }
    setLoading(true);
    try {
      const res = await api.post('/orders', {
        userId: user.userId,
        cartId,
        paymentMethod,
        totalAmount: totalPrice,
        name,
        phoneNumber: phone,
        address,
      });
      if (res.status === 200 || res.status === 201) {
        toast.success('Order placed successfully!');
        navigate('/order-success');
      }
    } catch {
      toast.error('Failed to place order. Please try again.');
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="max-w-4xl mx-auto px-4 py-10">
      <h1 className="text-3xl font-bold text-gray-800 mb-8">Checkout</h1>

      <div className="grid grid-cols-1 md:grid-cols-2 gap-8">
        {/* Order Summary */}
        <div className="bg-white rounded-xl shadow-md p-6">
          <h2 className="text-xl font-semibold mb-4">Order Summary</h2>
          {cartItems.length === 0 ? (
            <p className="text-gray-400">No items in cart</p>
          ) : (
            <>
              {cartItems.map(ci => (
                <div key={ci.id} className="flex justify-between items-center border-b py-2 text-sm">
                  <span>{ci.item.itemName} × {ci.quantity}</span>
                  <span className="font-medium">₹{(ci.item.price * ci.quantity).toFixed(2)}</span>
                </div>
              ))}
              <div className="flex justify-between mt-4 font-bold text-lg border-t pt-3">
                <span>Total</span>
                <span>₹{totalPrice.toFixed(2)}</span>
              </div>
            </>
          )}
        </div>

        {/* Delivery Details */}
        <form onSubmit={handleOrder} className="bg-white rounded-xl shadow-md p-6 space-y-4">
          <h2 className="text-xl font-semibold mb-4">Delivery Details</h2>

          <div>
            <label className="block text-sm font-semibold text-gray-600 mb-1">Full Name</label>
            <input
              type="text"
              className="w-full p-2.5 border rounded-lg focus:outline-none focus:ring-2 focus:ring-green-400"
              value={name}
              onChange={e => setName(e.target.value)}
              placeholder="Your Name"
              required
            />
          </div>
          <div>
            <label className="block text-sm font-semibold text-gray-600 mb-1">Phone</label>
            <input
              type="text"
              className="w-full p-2.5 border rounded-lg focus:outline-none focus:ring-2 focus:ring-green-400"
              value={phone}
              onChange={e => setPhone(e.target.value)}
              placeholder="10-digit number"
              required
            />
          </div>
          <div>
            <label className="block text-sm font-semibold text-gray-600 mb-1">Address</label>
            <textarea
              className="w-full p-2.5 border rounded-lg focus:outline-none focus:ring-2 focus:ring-green-400 resize-none"
              rows={3}
              value={address}
              onChange={e => setAddress(e.target.value)}
              placeholder="Delivery address"
              required
            />
          </div>
          <div>
            <label className="block text-sm font-semibold text-gray-600 mb-1">Payment Method</label>
            <div className="flex gap-4">
              {(['cod', 'online'] as const).map(m => (
                <label key={m} className="flex items-center gap-2 cursor-pointer text-sm">
                  <input
                    type="radio"
                    name="payment"
                    value={m}
                    checked={paymentMethod === m}
                    onChange={() => setPaymentMethod(m)}
                    className="accent-green-600"
                  />
                  {m === 'cod' ? 'Cash on Delivery' : 'Online Payment'}
                </label>
              ))}
            </div>
          </div>

          <button
            type="submit"
            disabled={loading || cartItems.length === 0}
            className="w-full py-2.5 bg-green-600 text-white rounded-lg font-semibold hover:bg-green-700 transition disabled:opacity-50"
          >
            {loading ? 'Placing Order...' : 'Place Order'}
          </button>
        </form>
      </div>
    </div>
  );
};

export default Checkout;
