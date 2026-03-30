import React, { useEffect, useState } from 'react';
import { useCategory } from '../context/CategoryContext';
import api from '../lib/apiClient';
import type { Category } from '../types';

const categoryImages: Record<string, string> = {
  fruits: '/images/fruits.png',
  vegetables: '/images/vegetables.png',
  'dairy products': '/images/dairy.png',
  'bakery products': '/images/bakery.png',
  meat: '/images/meat.png',
  seafood: '/images/seafood.png',
};

const CategoryList: React.FC = () => {
  const { categoryId, setCategory } = useCategory();
  const [categories, setCategories] = useState<Category[]>([]);

  useEffect(() => {
    api.get('/categories').then(r => setCategories(r.data)).catch(() => {});
  }, []);

  const getImg = (name: string) =>
    categoryImages[name.toLowerCase()] || '/images/default.png';

  return (
    <div className="mt-10 px-4">
      <h2 className="text-green-600 font-bold text-2xl text-center mb-6">Shop by Category</h2>
      <div className="grid grid-cols-3 md:grid-cols-5 lg:grid-cols-6 gap-4">
        {categories.length > 0 ? (
          categories.map(cat => (
            <div
              key={cat.categoryId}
              className={`relative rounded-lg shadow-md overflow-hidden cursor-pointer transition-transform transform hover:scale-105 bg-green-50 p-2 hover:bg-green-100
                ${cat.categoryId === categoryId ? 'border-4 border-green-600' : 'border border-transparent'}`}
              onClick={() => setCategory(cat.categoryId === categoryId ? null : cat.categoryId)}
            >
              <div className="w-full h-[100px] flex items-center justify-center">
                <img
                  src={getImg(cat.categoryName)}
                  alt={cat.categoryName}
                  className="max-h-full object-contain rounded-md"
                  onError={(e) => { (e.target as HTMLImageElement).src = '/images/default.png'; }}
                />
              </div>
              <div className="text-center mt-2">
                <h3 className="text-sm font-semibold">{cat.categoryName}</h3>
              </div>
            </div>
          ))
        ) : (
          <p className="text-center text-gray-500 col-span-full">No categories available</p>
        )}
      </div>
    </div>
  );
};

export default CategoryList;
