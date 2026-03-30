import React, { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { LayoutGrid, Search, ShoppingBag, MapPin, CircleUserRound, Plus, Minus, Trash2 } from 'lucide-react';
import { useLocation } from '../context/LocationContext';
import { useCategory } from '../context/CategoryContext';
import { useCart } from '../context/CartContext';
import api from '../lib/apiClient';
import toast from 'react-hot-toast';
import type { Category, Location, User } from '../types';

const Header: React.FC = () => {
  const navigate = useNavigate();
  const { locationId, setLocation } = useLocation();
  const { categoryId, setCategory } = useCategory();
  const { cartItems, cartCount, totalPrice, updateItemQuantity, removeItem, fetchCartDetails } = useCart();

  const [categories, setCategories] = useState<Category[]>([]);
  const [locations, setLocations] = useState<Location[]>([]);
  const [user, setUser] = useState<User | null>(null);
  const [cartOpen, setCartOpen] = useState(false);
  const [catOpen, setCatOpen] = useState(false);
  const [locOpen, setLocOpen] = useState(false);
  const [userOpen, setUserOpen] = useState(false);

  useEffect(() => {
    const stored = sessionStorage.getItem('user');
    if (stored) {
      const u: User = JSON.parse(stored);
      if (u.name && u.email) setUser(u);
      if (u.locationId) setLocation(u.locationId);
    }
  }, [setLocation]);

  useEffect(() => {
    api.get('/categories').then(r => setCategories(r.data)).catch(() => {});
    api.get('/locations').then(r => setLocations(r.data)).catch(() => {});
  }, []);

  useEffect(() => {
    fetchCartDetails();
  }, []);

  function handleLogout() {
    sessionStorage.removeItem('user');
    sessionStorage.removeItem('cartId');
    setUser(null);
    navigate('/login');
  }

  function handleLocationChange(id: number) {
    setLocation(id);
    const stored = sessionStorage.getItem('user');
    if (stored) {
      const u = JSON.parse(stored);
      u.locationId = id;
      sessionStorage.setItem('user', JSON.stringify(u));
    }
    setLocOpen(false);
  }

  return (
    <header className="p-2 shadow-sm flex justify-between items-center bg-white sticky top-0 z-40">
      {/* Left: logo + category + search */}
      <div className="flex items-center gap-6">
        <a href="/" className="flex items-center">
          <img src="/logo.png" alt="Baskit" className="h-12 w-auto" />
        </a>

        {/* Category Dropdown */}
        <div className="relative hidden md:block">
          <button
            onClick={() => setCatOpen(!catOpen)}
            className="flex gap-2 items-center border rounded-full py-2 px-6 bg-slate-100 cursor-pointer text-sm font-medium hover:bg-slate-200 transition"
          >
            <LayoutGrid className="h-4 w-4" />
            {categories.find(c => c.categoryId === categoryId)?.categoryName || 'Category'}
          </button>
          {catOpen && (
            <div className="absolute left-0 top-11 bg-white shadow-lg rounded-lg z-50 w-52 max-h-64 overflow-y-auto border">
              <div className="p-2 text-xs text-gray-500 font-semibold uppercase tracking-wide px-3">Browse Category</div>
              <button
                className="w-full text-left px-3 py-2 hover:bg-gray-50 text-sm font-semibold"
                onClick={() => { setCategory(null); setCatOpen(false); }}
              >All Categories</button>
              {categories.map(cat => (
                <button
                  key={cat.categoryId}
                  className="w-full text-left px-3 py-2 hover:bg-gray-50 text-sm"
                  onClick={() => { setCategory(cat.categoryId); setCatOpen(false); }}
                >
                  {cat.categoryName}
                </button>
              ))}
            </div>
          )}
        </div>

        {/* Search */}
        <div className="hidden md:flex gap-2 items-center border rounded-full py-2 px-4">
          <Search className="h-4 w-4 text-gray-400" />
          <input type="text" placeholder="Search" className="outline-none text-sm w-36" />
        </div>
      </div>

      {/* Right: location + cart + user */}
      <div className="flex gap-5 items-center">
        {/* Location Dropdown */}
        <div className="relative hidden md:block">
          <button
            onClick={() => setLocOpen(!locOpen)}
            className="flex gap-2 items-center border rounded-full py-2 px-6 bg-slate-100 cursor-pointer text-sm font-medium hover:bg-slate-200 transition"
          >
            <MapPin className="h-4 w-4" />
            {locations.find(l => l.locationId === locationId)?.locationName || 'Location'}
          </button>
          {locOpen && (
            <div className="absolute right-0 top-11 bg-white shadow-lg rounded-lg z-50 w-52 max-h-64 overflow-y-auto border">
              <div className="p-2 text-xs text-gray-500 font-semibold uppercase tracking-wide px-3">Select Location</div>
              {locations.map(loc => (
                <button
                  key={loc.locationId}
                  className="w-full text-left px-3 py-2 hover:bg-gray-50 text-sm"
                  onClick={() => handleLocationChange(loc.locationId)}
                >
                  {loc.locationName}
                </button>
              ))}
            </div>
          )}
        </div>

        {/* Cart */}
        <button onClick={() => setCartOpen(true)} className="relative flex items-center text-lg">
          <ShoppingBag className="h-6 w-6" />
          <span className="absolute -top-2 -right-3 bg-red-500 text-white text-xs font-bold px-1.5 py-0.5 rounded-full leading-none">
            {cartCount}
          </span>
        </button>

        {/* Cart Slide-out */}
        {cartOpen && (
          <div className="fixed inset-0 z-50 flex justify-end">
            <div className="absolute inset-0 bg-black/30" onClick={() => setCartOpen(false)} />
            <div className="relative bg-white w-full max-w-md h-full flex flex-col shadow-2xl">
              <div className="p-4 border-b">
                <h2 className="text-lg font-semibold">My Cart ({cartCount} items)</h2>
              </div>
              <div className="flex-1 overflow-y-auto p-4 flex flex-col gap-4">
                {cartItems.length === 0 ? (
                  <div className="text-center py-10 text-gray-400">Your cart is empty</div>
                ) : (
                  cartItems.map(ci => (
                    <div key={ci.id} className="flex gap-3 border-b pb-4">
                      <img
                        src={`/images/product${ci.item.itemId}.jpeg`}
                        alt={ci.item.itemName}
                        className="w-20 h-20 object-contain rounded-md border"
                        onError={(e) => { (e.target as HTMLImageElement).src = '/images/default-product.gif'; }}
                      />
                      <div className="flex-1">
                        <h3 className="font-medium text-sm">{ci.item.itemName}</h3>
                        <p className="text-xs text-gray-500">₹{ci.item.price} × {ci.quantity} = ₹{(ci.item.price * ci.quantity).toFixed(2)}</p>
                        <div className="flex items-center gap-2 mt-2">
                          <button onClick={() => updateItemQuantity(ci.id, ci.quantity - 1, ci.item.itemId)} className="border rounded p-1 hover:bg-gray-100"><Minus className="h-3 w-3" /></button>
                          <span className="w-5 text-center text-sm">{ci.quantity}</span>
                          <button onClick={() => updateItemQuantity(ci.id, ci.quantity + 1, ci.item.itemId)} className="border rounded p-1 hover:bg-gray-100"><Plus className="h-3 w-3" /></button>
                          <button onClick={() => removeItem(ci.id)} className="ml-auto text-red-500 hover:text-red-700"><Trash2 className="h-4 w-4" /></button>
                        </div>
                      </div>
                    </div>
                  ))
                )}
              </div>
              <div className="p-4 border-t">
                <div className="flex justify-between mb-3 font-medium">
                  <span>Total:</span>
                  <span className="font-bold">₹{totalPrice.toFixed(2)}</span>
                </div>
                <button
                  className="w-full bg-green-600 text-white py-2 rounded-lg hover:bg-green-700 transition disabled:opacity-50"
                  disabled={cartItems.length === 0}
                  onClick={() => {
                    if (!user) {
                      toast.error('Please log in to proceed to checkout');
                      setCartOpen(false);
                      navigate('/login');
                      return;
                    }
                    setCartOpen(false);
                    navigate('/checkout');
                  }}
                >
                  Proceed to Buy
                </button>
              </div>
            </div>
          </div>
        )}

        {/* User Menu */}
        {user ? (
          <div className="relative">
            <button onClick={() => setUserOpen(!userOpen)}>
              <CircleUserRound className="bg-green-100 p-1 rounded-full h-9 w-9 text-green-600" />
            </button>
            {userOpen && (
              <div className="absolute right-0 top-11 bg-white shadow-lg rounded-lg z-50 w-44 border">
                <div className="px-3 py-2 border-b text-sm font-semibold text-green-600">Welcome, {user.name}!</div>
                <button className="w-full text-left px-3 py-2 text-sm hover:bg-gray-50" onClick={() => { navigate('/profile'); setUserOpen(false); }}>Profile</button>
                <button className="w-full text-left px-3 py-2 text-sm hover:bg-gray-50 text-red-500" onClick={handleLogout}>Logout</button>
              </div>
            )}
          </div>
        ) : (
          <a href="/login">
            <button className="bg-green-600 text-white px-4 py-2 rounded-lg text-sm hover:bg-green-700 transition">Login</button>
          </a>
        )}
      </div>

      {/* Close dropdowns on outside click */}
      {(catOpen || locOpen || userOpen) && (
        <div className="fixed inset-0 z-30" onClick={() => { setCatOpen(false); setLocOpen(false); setUserOpen(false); }} />
      )}
    </header>
  );
};

export default Header;
