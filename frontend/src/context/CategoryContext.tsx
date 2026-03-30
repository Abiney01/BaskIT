import React, { createContext, useContext, useState } from 'react';

interface CategoryContextType {
  categoryId: number | null;
  setCategory: (id: number | null) => void;
}

const CategoryContext = createContext<CategoryContextType | undefined>(undefined);

export const useCategory = () => {
  const ctx = useContext(CategoryContext);
  if (!ctx) throw new Error('useCategory must be used within a CategoryProvider');
  return ctx;
};

export const CategoryProvider: React.FC<{ children: React.ReactNode }> = ({ children }) => {
  const [categoryId, setCategoryId] = useState<number | null>(null);

  return (
    <CategoryContext.Provider value={{ categoryId, setCategory: setCategoryId }}>
      {children}
    </CategoryContext.Provider>
  );
};
