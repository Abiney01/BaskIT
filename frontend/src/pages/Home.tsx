import React from 'react';
import Slider from '../components/Slider';
import CategoryList from '../components/CategoryList';
import ProductList from '../components/ProductList';
import Footer from '../components/Footer';

const Home: React.FC = () => (
  <div>
    <Slider/>
    <CategoryList />
    <ProductList />
    <Footer />
  </div>
);

export default Home;
