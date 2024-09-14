import React, { useState } from 'react';
import { FiSearch } from 'react-icons/fi';

export default function SearchBar({ onSearch, onFilterChange }) {
  const [searchTerm, setSearchTerm] = useState('');
  const [filters, setFilters] = useState({
    MV: true,
    SH: true,
    EP: true,
  });

  const handleChange = (event) => {
    setSearchTerm(event.target.value);
    onSearch(event.target.value);
  };

  const handleSearch = () => {
    const trimmedSearchTerm = searchTerm.trim();
    onSearch(trimmedSearchTerm);
  };

  const handleFilterChange = (event) => {
    const { name, checked } = event.target;
    const newFilters = { ...filters, [name]: checked };
    setFilters(newFilters);
    onFilterChange(newFilters);
  };

  return (
    <div className="search-bar bg-gradient-to-b from-lighterBlue to-deeperBlue h-20">
      <div className="p-2 flex flex-col justify-center items-center w-full space-y-1 h-full">
        <div className="flex justify-between w-full px-1 h-full items-center">
          <h1 className="text-white text-sm font-medium">Programs</h1>
          <div className="flex space-x-2 text-xs">
            <label className="text-white">
              <input
                type="checkbox"
                name="MV"
                checked={filters.MV}
                onChange={handleFilterChange}
                className="mr-1"
              />
              MV
            </label>
            <label className="text-white">
              <input
                type="checkbox"
                name="SH"
                checked={filters.SH}
                onChange={handleFilterChange}
                className="mr-1"
              />
              SH
            </label>
            <label className="text-white">
              <input
                type="checkbox"
                name="EP"
                checked={filters.EP}
                onChange={handleFilterChange}
                className="mr-1"
              />
              EP
            </label>
          </div>
        </div>
        <div className="w-full flex h-8 pt-0.5">
          <input
            type="text"
            value={searchTerm}
            onChange={handleChange}
            placeholder="Search programs"
            className="flex-grow p-2 rounded-l-md focus:outline-none focus:ring-2 focus:ring-blue-500 h-full"
          />
          <button
            onClick={handleSearch}
            className="h-full p-2 bg-gray-300 text-black flex items-center justify-center rounded-r-md hover:bg-gray-400 focus:outline-none focus:ring-2 focus:ring-blue-500"
            style={{ width: '2.5rem' }}
          >
            <FiSearch />
          </button>
        </div>
      </div>
    </div>
  );
}
