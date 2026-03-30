import React, { useState } from 'react';
import { ShoppingBasket } from 'lucide-react';
import { useNavigate } from 'react-router-dom';
import { useCart } from '../context/CartContext';
import toast from 'react-hot-toast';
import type { Item } from '../types';

interface Props {
  product: Item;
  onClose: () => void;
}

const ProductItemDetails: React.FC<Props> = ({ product, onClose }) => {
  const [quantity, setQuantity] = useState(1);
  const navigate = useNavigate();
  const { addItemToCart, fetchCartDetails } = useCart();

  const user = (() => {
    try { return JSON.parse(sessionStorage.getItem('user') || 'null'); } catch { return null; }
  })();

  const handleAddToCart = async () => {
    if (!user?.userId) {
      toast.error('Login to add items to cart');
      navigate('/login');
      return;
    }
    try {
      await addItemToCart(product, quantity);
      setTimeout(() => fetchCartDetails(), 500);
      onClose();
    } catch {
      toast.error('Error adding item to cart.');
    }
  };

  return (
    <div className="grid grid-cols-1 md:grid-cols-2 gap-4 mt-2">
      <div className="w-full h-52 flex items-center justify-center border rounded-md overflow-hidden">
        <img
          src={`/images/product${product.itemId}.jpeg`}
          alt={product.itemName}
          className="max-h-full object-contain"
          onError={(e) => { (e.target as HTMLImageElement).src = '/images/default-product.gif'; }}
        />
      </div>

      <div>
        <div className="text-gray-700 font-bold text-xl">₹{product.price}</div>
        <div className="text-gray-500 text-sm mt-1">Category: {product.categoryName || 'N/A'}</div>
        <div className="text-sm text-gray-600 mt-1">Stock: {product.stockAvailability}</div>

        <div className="flex gap-3 mt-4 items-center">
          <button
            className="border rounded px-3 py-1 hover:bg-gray-100 disabled:opacity-50"
            onClick={() => setQuantity(q => q - 1)}
            disabled={quantity <= 1}
          >−</button>
          <span className="text-lg w-6 text-center">{quantity}</span>
          <button
            className="border rounded px-3 py-1 hover:bg-gray-100 disabled:opacity-50"
            onClick={() => setQuantity(q => q + 1)}
            disabled={quantity >= product.stockAvailability}
          >+</button>
        </div>

        <div className="font-bold text-lg mt-2">Total: ₹{(product.price * quantity).toFixed(2)}</div>

        <button
          className="mt-4 flex items-center gap-2 bg-green-600 text-white px-4 py-2 rounded-lg hover:bg-green-700 transition"
          onClick={handleAddToCart}
        >
          <ShoppingBasket className="h-4 w-4" />
          Add {quantity} to Cart
        </button>
      </div>
    </div>
  );
};

export default ProductItemDetails;
