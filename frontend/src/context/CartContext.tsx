import React, { createContext, useContext, useState, useEffect, useCallback } from 'react';
import api from '../lib/apiClient';
import toast from 'react-hot-toast';
import type { CartItem } from '../types';

interface CartContextType {
  cartItems: CartItem[];
  cartCount: number;
  totalPrice: number;
  cartId: number | null;
  fetchCartDetails: () => Promise<void>;
  addItemToCart: (product: { itemId: number; price: number }, quantity: number) => Promise<void>;
  updateItemQuantity: (listItemId: number, newQuantity: number, itemId: number) => Promise<void>;
  removeItem: (listItemId: number) => Promise<void>;
}

const CartContext = createContext<CartContextType | undefined>(undefined);

export const useCart = () => {
  const ctx = useContext(CartContext);
  if (!ctx) throw new Error('useCart must be used within a CartProvider');
  return ctx;
};

export const CartProvider: React.FC<{ children: React.ReactNode }> = ({ children }) => {
  const [cartItems, setCartItems] = useState<CartItem[]>([]);
  const [cartCount, setCartCount] = useState(0);
  const [totalPrice, setTotalPrice] = useState(0);
  const [cartId, setCartId] = useState<number | null>(null);

  const getUser = () => {
    try {
      return JSON.parse(sessionStorage.getItem('user') || 'null');
    } catch {
      return null;
    }
  };

  const fetchCartDetails = useCallback(async () => {
    const user = getUser();
    if (!user) return;
    try {
      const res = await api.get(`/carts/user/${user.userId}`);
      if (res.status === 200 && res.data.cartId) {
        const cartData = res.data;
        setCartId(cartData.cartId);
        setCartItems([...cartData.listOfItems]);
        setCartCount(cartData.listOfItems.length);
        setTotalPrice(
          cartData.listOfItems.reduce(
            (sum: number, item: CartItem) => sum + item.item.price * item.quantity,
            0
          )
        );
        sessionStorage.setItem('cartId', cartData.cartId.toString());
      }
    } catch (error: any) {
      if (error.response?.status === 404) {
        await createCart(user.userId);
      }
    }
  }, []);

  const createCart = async (userId: number) => {
    try {
      const res = await api.post('/carts', { userId, address: 'Not Provided' });
      if (res.status === 200 || res.status === 201) {
        setCartId(res.data.cartId);
        sessionStorage.setItem('cartId', res.data.cartId.toString());
        setCartItems([]);
        toast.success('New cart created! 🎉');
        await fetchCartDetails();
      }
    } catch {
      toast.error('Failed to create cart.');
    }
  };

  const addItemToCart = async (product: { itemId: number; price: number }, quantity: number) => {
    const user = getUser();
    if (!user) {
      toast.error('Login to add items to cart');
      return;
    }
    try {
      const payload = { cartId, itemId: product.itemId, quantity };
      const res = await api.post('/list-of-items', payload);
      if (res.status === 200 || res.status === 201) {
        toast.success('Item added to cart!');
        await fetchCartDetails();
      }
    } catch {
      toast.error('Error adding item to cart.');
    }
  };

  const updateItemQuantity = async (listItemId: number, newQuantity: number, itemId: number) => {
    try {
      if (newQuantity < 1) {
        await removeItem(listItemId);
        return;
      }
      const res = await api.put(`/list-of-items/${listItemId}`, {
        cartId,
        itemId,
        quantity: newQuantity,
      });
      if (res.status === 200) {
        setCartItems((prev) =>
          prev.map((item) => (item.id === listItemId ? { ...item, quantity: newQuantity } : item))
        );
        await fetchCartDetails();
        toast.success('Cart updated!');
      }
    } catch {
      toast.error('Failed to update quantity');
    }
  };

  const removeItem = async (listItemId: number) => {
    try {
      await api.delete(`/list-of-items/${listItemId}`);
      setCartItems((prev) => prev.filter((item) => item.id !== listItemId));
      await fetchCartDetails();
      toast.success('Item removed from cart');
    } catch {
      toast.error('Failed to remove item');
    }
  };

  useEffect(() => {
    fetchCartDetails();
  }, [fetchCartDetails]);

  return (
    <CartContext.Provider
      value={{ cartItems, cartCount, totalPrice, cartId, fetchCartDetails, addItemToCart, updateItemQuantity, removeItem }}
    >
      {children}
    </CartContext.Provider>
  );
};
