import React, { useState, useEffect } from 'react';
import SearchBar from '../SearchBar/SearchBar.jsx';

export default function ProgramList({ onSelectProgram, onCreateNewProgram }) {
  const [allPrograms, setAllPrograms] = useState([]);
  const [filteredPrograms, setFilteredPrograms] = useState([]);
  const [filters, setFilters] = useState({
    MV: true,
    SH: true,
    EP: true,
  });
  const [searchTerm, setSearchTerm] = useState('');
  const [isDropdownOpen, setIsDropdownOpen] = useState(false);

  useEffect(() => {
    async function fetchPrograms() {
      try {
        const response = await fetch('http://localhost:8091/programs');
        const data = await response.json();
        const programs = Array.isArray(data) ? data : [data];
        setAllPrograms(programs);
        setFilteredPrograms(programs);
      } catch (error) {
        console.error('Error fetching programs:', error);
      }
    }

    fetchPrograms();
  }, []);

  const handleSearch = (term) => {
    setSearchTerm(term);
    filterPrograms(term, filters);
  };

  const handleFilterChange = (newFilters) => {
    setFilters(newFilters);
    filterPrograms(searchTerm, newFilters);
  };

  const filterPrograms = (term, filters) => {
    let results = allPrograms;

    if (term) {
      const lowercasedTerm = term.toLowerCase();
      results = results.filter(program => {
        const titleMatch = program.titles.some(title => title.value.toLowerCase().includes(lowercasedTerm));
        const idMatch = program.programId.toLowerCase().includes(lowercasedTerm);
        return titleMatch || idMatch;
      });
    }

    results = results.filter(program => {
      const prefix = program.programId.substring(0, 2);
      return filters[prefix];
    });

    setFilteredPrograms(results);
  };

  const renderProgramDetails = (program) => {
    const title = program.titles && program.titles.length > 0 ? program.titles[0].value : "No Title";
    const description = program.descriptions && program.descriptions.length > 0 ? program.descriptions[0].value : "No Description";
    const programIdPrefix = program.programId ? program.programId.substring(0, 2) : "Unknown Type";

    return (
      <div key={program.programId} onClick={() => onSelectProgram(program)}
        style={{ marginBottom: '20px', cursor: 'pointer' }}
        className="mb-4 p-4 bg-white border border-gray-200 rounded-lg shadow hover:bg-gray-100 cursor-pointer"
      >
        <div className="flex flex-row justify-between">
          <div className="font-bold">{title}</div>
          <div className="font-bold text-white bg-blue-400 text-sm p-1 rounded-md">{programIdPrefix}</div>
        </div>
        <div>{description}</div>
      </div>
    );
  };

  const toggleDropdown = () => {
    setIsDropdownOpen(!isDropdownOpen);
  };

  const handleOptionClick = (option) => {
    const programType = option === 'SPORTS SERIES' ? 'SERIES' : option.toUpperCase();
    onCreateNewProgram(programType);
    setIsDropdownOpen(false);
  };
  
  return (
    <div className="left-0 h-full w-full flex flex-col">
      <div className="flex-shrink-0">
        <SearchBar onSearch={handleSearch} onFilterChange={handleFilterChange} />
      </div>
      <div className="overflow-auto h-full flex-grow w-full p-2">
        {filteredPrograms.map((program) => renderProgramDetails(program))}
      </div>
      <div className="flex-shrink-0 bg-gray-100 h-12 border-t border-gray-300 flex justify-end items-center p-2">
        <div className="relative inline-block">
          <button
            onClick={toggleDropdown}
            className="h-8 px-3 text-sm bg-gradient-to-b from-btnLightBlue to-btnDeepBlue text-white rounded shadow hover:bg-gradient-to-b hover:from-blue-600 hover:to-blue-800 focus:outline-none"
            style={{ height: '33px' }}
          >
            Create Program <span className="ml-0.5 text-lg">▾</span>
          </button>
          {isDropdownOpen && (
            <div
              className="absolute w-full mt-1 bg-white border border-gray-300 rounded shadow-lg z-10"
              style={{ bottom: '100%', left: 0 }}
            >
              <div
                onClick={() => handleOptionClick('MOVIE')}
                className="cursor-pointer px-4 py-2 hover:bg-gray-100"
              >
                Movie
              </div>
              <div
                onClick={() => handleOptionClick('SHOW')}
                className="cursor-pointer px-4 py-2 hover:bg-gray-100"
              >
                Show
              </div>
              <div
                onClick={() => handleOptionClick('SPORTS SERIES')}
                className="cursor-pointer px-4 py-2 hover:bg-gray-100"
              >
                Sports Series
              </div>
            </div>
          )}
        </div>
      </div>
    </div>
  );
}
