import React, { useEffect, useState } from 'react';
import { useLocation } from '../context/LocationContext';
import { useCategory } from '../context/CategoryContext';
import api from '../lib/apiClient';
import type { Item } from '../types';
import ProductItemDetails from './ProductItemDetails';

const ProductList: React.FC = () => {
  const { locationId } = useLocation();
  const { categoryId } = useCategory();
  const [products, setProducts] = useState<Item[]>([]);
  const [loading, setLoading] = useState(true);
  const [selectedProduct, setSelectedProduct] = useState<Item | null>(null);

  useEffect(() => {
    if (!locationId) return;
    setLoading(true);
    let url = `/items?location_id=${locationId}`;
    if (categoryId) url += `&category_id=${categoryId}`;

    api.get(url)
      .then(r => { setProducts(r.data || []); setLoading(false); })
      .catch(() => setLoading(false));
  }, [locationId, categoryId]);

  return (
    <div className="mt-10 px-4 pb-10">
      <h2 className="text-green-600 font-bold text-2xl text-center mb-6">
        {categoryId ? 'Products in Selected Category' : 'All Products'}
      </h2>

      {loading ? (
        <p className="text-center text-gray-500">Loading products...</p>
      ) : products.length > 0 ? (
        <div className="grid grid-cols-2 md:grid-cols-3 lg:grid-cols-4 gap-6">
          {products.map(product => (
            <div
              key={product.itemId}
              className="border rounded-xl shadow-md p-4 hover:shadow-lg transition-transform transform hover:scale-105 bg-white"
            >
              <div className="w-full h-40 flex items-center justify-center overflow-hidden">
                <img
                  src={`/images/product${product.itemId}.jpeg`}
                  alt={product.itemName}
                  className="max-h-full object-contain rounded-md"
                  onError={(e) => { (e.target as HTMLImageElement).src = '/images/default-product.gif'; }}
                />
              </div>

              <div className="mt-3 text-center">
                <h3 className="text-base font-semibold">{product.itemName}</h3>
                <p className="text-gray-700 font-medium">₹{product.price}</p>
                <p className={`text-xs mt-1 ${product.stockAvailability > 0 ? 'text-green-600' : 'text-red-500'}`}>
                  {product.stockAvailability > 0 ? 'In Stock' : 'Out of Stock'}
                </p>
                <button
                  className={`mt-2 border rounded-lg px-4 py-1.5 text-sm font-medium transition
                    ${product.stockAvailability > 0
                      ? 'border-green-600 text-green-600 hover:bg-green-50'
                      : 'border-red-300 text-red-400 opacity-50 cursor-not-allowed'
                    }`}
                  disabled={product.stockAvailability <= 0}
                  onClick={() => setSelectedProduct(product)}
                >
                  Add to Cart
                </button>
              </div>
            </div>
          ))}
        </div>
      ) : (
        <p className="text-center text-gray-500">
          {locationId ? 'No products available' : 'Please select a location to view products'}
        </p>
      )}

      {/* Product detail modal */}
      {selectedProduct && (
        <div className="fixed inset-0 z-50 flex items-center justify-center p-4">
          <div className="absolute inset-0 bg-black/40" onClick={() => setSelectedProduct(null)} />
          <div className="relative bg-white rounded-xl shadow-2xl p-6 w-full max-w-lg z-10">
            <div className="flex justify-between items-center mb-4">
              <h2 className="text-xl font-bold">{selectedProduct.itemName}</h2>
              <button onClick={() => setSelectedProduct(null)} className="text-gray-400 hover:text-gray-600 text-2xl leading-none">&times;</button>
            </div>
            <ProductItemDetails product={selectedProduct} onClose={() => setSelectedProduct(null)} />
          </div>
        </div>
      )}
    </div>
  );
};

export default ProductList;
