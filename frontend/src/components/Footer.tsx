import React from 'react';

const Footer: React.FC = () => (
  <footer className="bg-gray-900 text-white mt-16 py-10 px-6">
    <div className="max-w-6xl mx-auto grid grid-cols-1 md:grid-cols-3 gap-8">
      <div>
        <img src="/logo.png" alt="Baskit" className="h-12 mb-3 brightness-0 invert" />
        <p className="text-gray-400 text-sm">Fresh groceries delivered to your door.</p>
      </div>
      <div>
        <h3 className="font-semibold text-lg mb-3">Quick Links</h3>
        <ul className="space-y-2 text-gray-400 text-sm">
          <li><a href="/" className="hover:text-green-400 transition">Home</a></li>
          <li><a href="/login" className="hover:text-green-400 transition">Login</a></li>
          <li><a href="/register" className="hover:text-green-400 transition">Register</a></li>
          <li><a href="/profile" className="hover:text-green-400 transition">My Profile</a></li>
        </ul>
      </div>
      <div>
        <h3 className="font-semibold text-lg mb-3">Contact</h3>
        <ul className="space-y-2 text-gray-400 text-sm">
          <li>📧 support@baskit.com</li>
          <li>📞 +91 98765 43210</li>
          <li>🕐 Mon–Sat, 9am–6pm</li>
        </ul>
      </div>
    </div>
    <div className="mt-8 border-t border-gray-700 pt-6 text-center text-gray-500 text-sm">
      © {new Date().getFullYear()} Baskit. All rights reserved.
    </div>
  </footer>
);

export default Footer;
