import React, { useState, useEffect } from 'react';
import Select from 'react-select';

export default function Genres({ selectedGenres, onGenresChange }) {
  const [genres, setGenres] = useState([]);

  useEffect(() => {
    async function fetchGenres() {
      try {
        const response = await fetch('/api/genres');
        const data = await response.json();
        const options = data.map(genre => ({
          value: genre._id,
          label: genre.names.find(name => name.language === 'en')?.value || 'Unknown Genre'
        }));
        setGenres(options);
      } catch (error) {
        console.error('Error fetching genres:', error);
      }
    }

    fetchGenres();
  }, []);

  const customStyles = {
    control: (provided, state) => ({
      ...provided,
      backgroundColor: state.isDisabled ? '#F3F4F6' : 'white', // Matching background color
      borderColor: state.isFocused ? '#2563EB' : '#D1D5DB', // Matching border color
      borderRadius: '0.375rem', // Matching rounded corners
      minHeight: '2rem', // Matching height
      boxShadow: state.isFocused ? '0 0 0 2px #2563EB' : provided.boxShadow, // Matching focus shadow
      '&:hover': {
        borderColor: state.isFocused ? '#2563EB' : '#D1D5DB', // Matching hover behavior
      },
    }),
    valueContainer: (provided) => ({
      ...provided,
      padding: '0 0.5rem', // Adjust padding to match other inputs
      height: '2rem', // Ensure height matches
      display: 'flex',
      alignItems: 'center',
    }),
    multiValue: (provided) => ({
      ...provided,
      backgroundColor: '#E5E7EB', // Matching selected item background color
    }),
    multiValueLabel: (provided) => ({
      ...provided,
      color: '#1F2937', // Matching selected item text color
    }),
    multiValueRemove: (provided) => ({
      ...provided,
      color: '#9CA3AF', // Matching remove button color
      '&:hover': {
        backgroundColor: '#F3F4F6',
        color: '#111827', // Matching hover color
      },
    }),
    dropdownIndicator: (provided) => ({
      ...provided,
      padding: '0.25rem', // Adjust to match the size of other input elements
    }),
    indicatorSeparator: () => ({
      display: 'none', // Remove the separator to match the simple look of other inputs
    }),
    menu: (provided) => ({
      ...provided,
      borderRadius: '0.375rem', // Matching dropdown menu rounded corners
      boxShadow: '0 1px 3px rgba(0, 0, 0, 0.1), 0 1px 2px rgba(0, 0, 0, 0.06)', // Matching shadow
    }),
    menuList: (provided) => ({
      ...provided,
      padding: '0', // Removing padding for a cleaner look
    }),
  };

  return (
    <>
      <Select
        isMulti
        value={selectedGenres}
        onChange={onGenresChange}
        options={genres}
        styles={customStyles}
        className="mt-1 block w-full rounded-md shadow-sm"
      />
    </>
  );
}
