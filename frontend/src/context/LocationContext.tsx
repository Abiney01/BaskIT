import React, { createContext, useContext, useState } from 'react';

interface LocationContextType {
  locationId: number | undefined;
  setLocation: (id: number) => void;
}

const LocationContext = createContext<LocationContextType | undefined>(undefined);

export const useLocation = () => {
  const ctx = useContext(LocationContext);
  if (!ctx) throw new Error('useLocation must be used within a LocationProvider');
  return ctx;
};

export const LocationProvider: React.FC<{ children: React.ReactNode }> = ({ children }) => {
  const [locationId, setLocationId] = useState<number | undefined>(undefined);

  const setLocation = (newId: number) => setLocationId(newId);

  return (
    <LocationContext.Provider value={{ locationId, setLocation }}>
      {children}
    </LocationContext.Provider>
  );
};
