import React from 'react';
import { BrowserRouter, Routes, Route, useLocation } from 'react-router-dom';
import { Toaster } from 'react-hot-toast';

import { LocationProvider } from './context/LocationContext';
import { CategoryProvider } from './context/CategoryContext';
import { CartProvider } from './context/CartContext';

import Header from './components/Header';
import Home from './pages/Home';
import Login from './pages/Login';
import Register from './pages/Register';
import Checkout from './pages/Checkout';
import OrderSuccess from './pages/OrderSuccess';
import Profile from './pages/Profile';

const noHeaderRoutes = ['/login', '/register'];

const Layout: React.FC<{ children: React.ReactNode }> = ({ children }) => {
  const location = useLocation();
  const showHeader = !noHeaderRoutes.includes(location.pathname);
  return (
    <>
      {showHeader && <Header />}
      {children}
    </>
  );
};

const App: React.FC = () => (
  <BrowserRouter>
    <LocationProvider>
      <CategoryProvider>
        <CartProvider>
          <Toaster position="bottom-right" />
          <Layout>
            <Routes>
              <Route path="/" element={<Home />} />
              <Route path="/login" element={<Login />} />
              <Route path="/register" element={<Register />} />
              <Route path="/checkout" element={<Checkout />} />
              <Route path="/order-success" element={<OrderSuccess />} />
              <Route path="/profile" element={<Profile />} />
            </Routes>
          </Layout>
        </CartProvider>
      </CategoryProvider>
    </LocationProvider>
  </BrowserRouter>
);

export default App;
