import React, { useState, useEffect } from 'react';
import { useNavigate, Link } from 'react-router-dom';
import api from '../lib/apiClient';
import toast from 'react-hot-toast';
import type { Location } from '../types';

const Register: React.FC = () => {
  const [name, setName] = useState('');
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [showPassword, setShowPassword] = useState(false);
  const [phoneNumber, setPhoneNumber] = useState('');
  const [address, setAddress] = useState('');
  const [locationId, setLocationId] = useState('');
  const [locations, setLocations] = useState<Location[]>([]);
  const [loading, setLoading] = useState(false);
  const navigate = useNavigate();

  useEffect(() => {
    api.get('/locations').then(r => setLocations(r.data)).catch(() => {});
  }, []);

  const handleRegister = async (e: React.FormEvent) => {
    e.preventDefault();
    if (!name || !email || !password || !phoneNumber || !address || !locationId) {
      toast.error('Please fill all fields!');
      return;
    }
    if (!/^[0-9]{10}$/.test(phoneNumber)) {
      toast.error('Please enter a valid 10-digit phone number.');
      return;
    }
    setLoading(true);
    try {
      const res = await api.post('/auth/register', {
        name,
        email,
        password,
        phoneNo: phoneNumber,
        address,
        location: { locationId: Number(locationId) },
      });
      if (res.status === 201) {
        toast.success('Registration Successful');
        navigate('/login');
      }
    } catch (err: any) {
      toast.error(err.response?.data?.message || 'Registration failed');
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="flex justify-center items-center min-h-screen bg-gray-100 py-8">
      <div className="w-full max-w-md bg-white shadow-lg rounded-xl p-8">
        <div className="flex justify-center mb-4">
          <img src="/logo.png" alt="Baskit" className="h-14" />
        </div>
        <h2 className="text-center text-2xl font-bold text-gray-700 mb-4">User Registration</h2>
        <hr className="mb-6" />

        <form onSubmit={handleRegister} className="space-y-4">
          {[
            { label: 'Name', value: name, setter: setName, type: 'text', placeholder: 'Enter Name' },
            { label: 'Email', value: email, setter: setEmail, type: 'email', placeholder: 'Enter Email' },
            { label: 'Phone Number', value: phoneNumber, setter: setPhoneNumber, type: 'text', placeholder: '10-digit phone' },
            { label: 'Address', value: address, setter: setAddress, type: 'text', placeholder: 'Enter Address' },
          ].map(({ label, value, setter, type, placeholder }) => (
            <div key={label}>
              <label className="block text-gray-600 text-sm font-semibold mb-1">{label}</label>
              <input
                type={type}
                className="w-full p-2.5 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-green-400"
                placeholder={placeholder}
                value={value}
                onChange={e => setter(e.target.value)}
                required
              />
            </div>
          ))}

          {/* Password */}
          <div>
            <label className="block text-gray-600 text-sm font-semibold mb-1">Password</label>
            <div className="relative">
              <input
                type={showPassword ? 'text' : 'password'}
                className="w-full p-2.5 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-green-400 pr-10"
                placeholder="Enter Password"
                value={password}
                onChange={e => setPassword(e.target.value)}
                required
              />
              <button type="button" className="absolute right-3 top-3 text-gray-400" onClick={() => setShowPassword(p => !p)}>
                {showPassword ? '🙈' : '👁️'}
              </button>
            </div>
          </div>

          {/* Location select */}
          <div>
            <label className="block text-gray-600 text-sm font-semibold mb-1">Location</label>
            <select
              className="w-full p-2.5 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-green-400"
              value={locationId}
              onChange={e => setLocationId(e.target.value)}
              required
            >
              <option value="">Select Location</option>
              {locations.map(l => (
                <option key={l.locationId} value={l.locationId}>{l.locationName}</option>
              ))}
            </select>
          </div>

          <button
            type="submit"
            disabled={loading}
            className={`w-full py-2.5 text-white rounded-lg font-semibold transition ${loading ? 'bg-gray-400' : 'bg-green-600 hover:bg-green-700'}`}
          >
            {loading ? 'Registering...' : 'Register'}
          </button>

          <p className="text-center text-sm text-gray-600">
            Already have an account?{' '}
            <Link to="/login" className="text-green-600 hover:underline font-medium">Login here</Link>
          </p>
        </form>
      </div>
    </div>
  );
};

export default Register;
