export interface User {
  userId: number;
  name: string;
  email: string;
  locationId?: number;
  address?: string;
}

export interface Category {
  categoryId: number;
  categoryName: string;
}

export interface Location {
  locationId: number;
  locationName: string;
}

export interface Offer {
  offerId: number;
  offerName: string;
  discountPercent: number;
}

export interface Item {
  itemId: number;
  itemName: string;
  price: number;
  stockAvailability: number;
  categoryName?: string;
  category?: Category;
  offer?: Offer;
}

export interface CartItem {
  id: number;
  item: Item;
  quantity: number;
}

export interface Cart {
  cartId: number;
  totalPrice: number;
  listOfItems: CartItem[];
}

export interface Order {
  orderId?: number;
  userId: number;
  cartId: number;
  paymentMethod: string;
  totalAmount: number;
  name: string;
  phoneNumber: string;
  address: string;
}
