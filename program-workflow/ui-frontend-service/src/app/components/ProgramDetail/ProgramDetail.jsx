import React, { useState, useEffect } from 'react';
import Genres from '../Genres/Genres.jsx';

export default function ProgramDetail({ programId, programType }) {
  const [programData, setProgramData] = useState({
    programId: '',
    vRioId: '',
    rootId: '',
    programType: programType || '',
    countries: '',
    season: '',
    episode: '',
    ratings: '',
    ratingContentDescriptor: '',
    originalTitle: '',
    originalAudiolang: '',
    originalAirDate: '',
    genres: [],
    englishTitle: '',
    englishShortTitle: '',
    portugueseTitle: '',
    portugueseShortTitle: '',
    spanishTitle: '',
    spanishShortTitle: '',
    englishDescription: '',
    portugueseDescription: '',
    spanishDescription: '',
    englishShortDescription: '',
    portugueseShortDescription: '',
    spanishShortDescription: '',
    published: false, 
    createdDate: '',
  });

  const [selectedGenres, setSelectedGenres] = useState([]);
  const [allGenres, setAllGenres] = useState([]);
  const [externalInfo, setExternalInfo] = useState([]);
  const [showModal, setShowModal] = useState(false);
  const [editIndex, setEditIndex] = useState(null);

  const [newExternalInfo, setNewExternalInfo] = useState({ system: '', name: '', value: '' });

  useEffect(() => {
    async function fetchGenres() {
      try {
        const response = await fetch('/api/genres');
        const genreData = await response.json();
        const genres = genreData.map(genre => ({
          id: genre._id,
          name: genre.names.find(name => name.language === 'en')?.value || 'Unknown Genre',
        }));
        setAllGenres(genres);
      } catch (error) {
        console.error('Error fetching genres:', error);
      }
    }

    fetchGenres();
  }, []);

  useEffect(() => {
    if (programId) {
      async function fetchProgramDetails() {
        try {
          const response = await fetch(`http://localhost:8091/programs/${programId}`);
          const data = await response.json();

          const titles = data.titles || [];
          const descriptions = data.descriptions || [];

          const findTitle = (lang, length) => {
            return titles.find(
              (title) => (!lang || title.lang === lang) && (!length || title.length === length)
            )?.value || '';
          };

          const findDescription = (lang, length) => {
            return descriptions.find(
              (description) => (!lang || description.lang === lang) && (!length || description.length === length)
            )?.value || '';
          };

          const genres = data.genres.map(genreId => {
            const genre = allGenres.find(g => g.id === genreId);
            return genre ? { value: genre.id, label: genre.name } : { value: genreId, label: 'Unknown Genre' };
          });

          const rootId = data.externalRefs?.find(ref => ref.refName === 'rootId')?.id || '';

          setProgramData({
            programId: data.programId || '',
            vRioId: data.vRioId || '',
            rootId: rootId,
            programType: data.programType || '',
            countries: data.countries?.join(', ') || '',
            season: data.season || '',
            episode: data.episode || '',
            ratings: data.ratings || '',
            ratingContentDescriptor: data.ratingContentDescriptor || '',
            originalTitle: findTitle(null, null, 'original') || '',
            originalAudiolang: data.originalAudiolang || '', 
            originalAirDate: data.originalAirDate || '',
            genres: genres,
            englishTitle: findTitle('en', '120') || '',
            englishShortTitle: findTitle('en', '70') || '',
            portugueseTitle: findTitle('pt', '120') || '',
            portugueseShortTitle: findTitle('pt', '70') || '',
            spanishTitle: findTitle('es', '120') || '',
            spanishShortTitle: findTitle('es', '70') || '',
            englishDescription: findDescription('en', '500') || '',
            portugueseDescription: findDescription('pt', '500') || '',
            spanishDescription: findDescription('es', '500') || '',
            englishShortDescription: findDescription('en', '250') || '',
            portugueseShortDescription: findDescription('pt', '250') || '',
            spanishShortDescription: findDescription('es', '250') || '',
            published: data.published || false,
            createdDate: data.createdDate || '',
          });

          setSelectedGenres(genres);
          setExternalInfo(data.externalRefs.map(ref => ({
            system: ref.system,
            name: ref.refName,
            value: ref.id,
          })));
        } catch (error) {
          console.error('Error fetching program details:', error);
        }
      }

      fetchProgramDetails();
    } else {
      // Reset form for new program
      setProgramData({
        programId: '',
        vRioId: '',
        rootId: '',
        programType: programType || '',
        countries: '',
        season: '',
        episode: '',
        ratings: '',
        ratingContentDescriptor: '',
        originalTitle: '',
        originalAudiolang: '',
        originalAirDate: '',
        genres: [],
        englishTitle: '',
        englishShortTitle: '',
        portugueseTitle: '',
        portugueseShortTitle: '',
        spanishTitle: '',
        spanishShortTitle: '',
        englishDescription: '',
        portugueseDescription: '',
        spanishDescription: '',
        englishShortDescription: '',
        portugueseShortDescription: '',
        spanishShortDescription: '',
        published: false, 
        createdDate: '',
      });
      setSelectedGenres([]);
      setExternalInfo([]);
    }
  }, [programId, programType, allGenres]);

  const handleChange = (e) => {
    const { name, value } = e.target;
    setProgramData({
      ...programData,
      [name]: value,
    });
  };

  const handleGenresChange = (selectedOptions) => {
    setSelectedGenres(selectedOptions);
    setProgramData({
      ...programData,
      genres: selectedOptions.map((option) => option.value),
    });
  };

  const handleExternalInfoChange = (e) => {
    const { name, value } = e.target;
    setNewExternalInfo({
      ...newExternalInfo,
      [name]: value,
    });
  };

  const addOrUpdateExternalInfo = () => {
    if (editIndex !== null) {
      const updatedExternalInfo = [...externalInfo];
      updatedExternalInfo[editIndex] = newExternalInfo;
      setExternalInfo(updatedExternalInfo);
    } else {
      setExternalInfo([...externalInfo, newExternalInfo]);
    }
    setNewExternalInfo({ system: '', name: '', value: '' });
    setShowModal(false);
    setEditIndex(null);
  };

  const deleteExternalInfo = (index) => {
    setExternalInfo(externalInfo.filter((_, i) => i !== index));
  };

  const openModal = (index = null) => {
    if (index !== null) {
      setNewExternalInfo(externalInfo[index]);
      setEditIndex(index);
    } else {
      setNewExternalInfo({ system: '', name: '', value: '' });
      setEditIndex(null);
    }
    setShowModal(true);
  };

  const closeModal = () => {
    setShowModal(false);
    setNewExternalInfo({ system: '', name: '', value: '' });
    setEditIndex(null);
  };

  const handleSave = async (publishStatus) => {
    if (!programData.originalAudiolang) {
      alert('Original Audio is required.');
      return;
    }
    
    if (!programData.originalAirDate) {
      alert('Original Air Date is required.');
      return;
    }
    
    if (!programData.englishTitle && !programData.spanishTitle && !programData.portugueseTitle) {
      alert('Title is required.');
      return;
    }
  
    const titles = [
      {
        lang: "en",
        value: programData.originalTitle,
        length: "120",
        type: "original",
      },
      {
        lang: "en",
        value: programData.englishTitle,
        length: "120",
        type: "red",
      },
      {
        lang: "en",
        value: programData.englishShortTitle,
        length: "70",
        type: "red",
      },
      {
        lang: "pt",
        value: programData.portugueseTitle,
        length: "120",
        type: "red",
      },
      {
        lang: "pt",
        value: programData.portugueseShortTitle,
        length: "70",
        type: "red",
      },
      {
        lang: "es",
        value: programData.spanishTitle,
        length: "120",
        type: "red",
      },
      {
        lang: "es",
        value: programData.spanishShortTitle,
        length: "70",
        type: "red",
      }
    ].filter(title => title.value); 
  
    const genres = programData.genres.map(genre => genre.value); 
  
    const payload = {
      programId: programData.programId,
      programType: programData.programType,
      countries: programData.countries.split(',').map(country => country.trim()), 
      originalAirDate: programData.originalAirDate,
      genres: genres,
      titles: titles,
      descriptions: [
        {
          lang: "en",
          value: programData.englishDescription,
          length: "500"
        },
        {
          lang: "pt",
          value: programData.portugueseDescription,
          length: "500"
        },
        {
          lang: "es",
          value: programData.spanishDescription,
          length: "500"
        },
        {
          lang: "en",
          value: programData.englishShortDescription,
          length: "250"
        },
        {
          lang: "pt",
          value: programData.portugueseShortDescription,
          length: "250"
        },
        {
          lang: "es",
          value: programData.spanishShortDescription,
          length: "250"
        },
      ].filter(description => description.value), 
      published: publishStatus,
      externalRefs: externalInfo.map(ref => ({
        system: ref.system,
        refName: ref.name,
        id: ref.value,
      })),
      createdDate: programData.createdDate,
    };
    try {
      let response; 
    
      if (programId) {
        response = await fetch(`http://localhost:8091/programs/${programId}`, {
          method: 'PUT',
          headers: {
            'Content-Type': 'application/json',
          },
          body: JSON.stringify(payload),
        });
      } else {
        const url = new URL('http://localhost:8091/programs');
        url.searchParams.append('programType', programData.programType); 
    
        response = await fetch(url, {
          method: 'POST',
          headers: {
            'Content-Type': 'application/json',
          },
          body: JSON.stringify(payload),
        });
      }
    
      if (!response.ok) {
        throw new Error('Failed to save the program');
      }
    
      const savedProgram = await response.json();
      alert(`Program ${programId ? 'updated' : 'created'} successfully!`);
      setProgramData(savedProgram); 
    } catch (error) {
      console.error('Error saving program:', error);
      alert('Error saving program.');
    }
  }

  const inputClassNames = "px-3 py-4 h-8 mt-1 block w-full rounded-md border border-gray-300 shadow-sm focus:border-indigo-500 focus:ring-indigo-500";

  return (
    <div className="w-full flex flex-col h-full bg-white overflow-hidden">
      <div className="p-2 px-1 flex-shrink-0 bg-gradient-to-b from-lighterBlue to-deeperBlue">
        <h2 className="text-white text-sm font-medium">{programData.originalTitle || programData.englishTitle || programData.portugueseTitle || programData.spanishTitle || 'New Program'}</h2>
      </div>
      <div className="overflow-auto h-full flex-grow w-full p-2">
        <form className="font-medium">
          <div className="grid grid-cols-4 gap-6">
            {/* Row 1 */}
            <div className="col-span-1">
              <label className="block text-gray-700 font-bold">Program ID</label>
              <input
                type="text"
                name="programId"
                value={programData.programId}
                readOnly
                className={`${inputClassNames} bg-gray-100 text-gray-500 cursor-not-allowed`}
              />
            </div>
            <div className="col-span-1">
              <label className="block text-gray-700 font-bold">VRIO ID</label>
              <input
                type="text"
                name="vRioId"
                value={programData.vRioId}
                readOnly
                className={`${inputClassNames} bg-gray-100 text-gray-500 cursor-not-allowed`}
              />
            </div>
            <div className="col-span-1">
              <label className="block text-gray-700 font-bold">Root ID</label>
              <input
                type="text"
                name="rootId"
                value={programData.rootId}
                readOnly
                className={`${inputClassNames} bg-gray-100 text-gray-500 cursor-not-allowed`}
              />
            </div>
            <div className="col-span-1">
              <label className="block text-gray-700 font-bold">Program Type</label>
              <input
                type="text"
                name="programType"
                value={programData.programType}
                readOnly
                className={`${inputClassNames} bg-gray-100 text-gray-500 cursor-not-allowed`}
              />
            </div>

            {/* Row 2 */}
            <div className="col-span-2">
              <label className="block text-gray-700 font-bold">Genres</label>
              <Genres selectedGenres={selectedGenres} onGenresChange={handleGenresChange} />
            </div>
            <div className="col-span-1">
              <label className="block text-gray-700 font-bold">Countries</label>
              <input
                type="text"
                name="countries"
                value={programData.countries}
                onChange={handleChange}
                className={inputClassNames}
              />
            </div>
            <div className="col-span-1">
              <label className="block text-gray-700 font-bold">Series Program ID</label>
              <input
                type="text"
                name="seriesProgramId"
                value={programData.seriesProgramId}
                onChange={handleChange}
                className={`${inputClassNames} bg-gray-100 text-gray-500 cursor-not-allowed`}
              />
            </div>

            {/* Row 3 */}
            <div className="col-span-2">
              <label className="block text-gray-700 font-bold">Original Title</label>
              <input
                type="text"
                name="originalTitle"
                value={programData.originalTitle}
                onChange={handleChange}
                className={inputClassNames}
              />
            </div>
            <div className="col-span-1">
              <label className="block text-gray-700 font-bold">Original Air Date</label>
              <input
                type="date"
                name="originalAirDate"
                value={programData.originalAirDate}
                onChange={handleChange}
                className={inputClassNames}
              />
            </div>
            <div className="col-span-1">
              <label className="block text-gray-700 font-bold">Original Audio</label>
              <select
                name="originalAudiolang"  
                value={programData.originalAudiolang}  
                onChange={handleChange}
                className="px-3 py-[5px] h-[34px] mt-1 block w-full rounded-md border border-gray-300 shadow-sm focus:border-indigo-500 focus:ring-indigo-500"
              >
                <option value="pt">Portuguese</option>
                <option value="es">Spanish</option>
                <option value="en">English</option>
              </select>
            </div>
          </div>

          <div className="mt-6"></div>

          <div className="grid grid-cols-3 gap-6">
            {/* Row 4: Titles */}
            <div className="col-span-1">
              <label className="block text-gray-700 font-bold">English Title (Max 120)</label>
              <input
                type="text"
                name="englishTitle"
                value={programData.englishTitle}
                onChange={handleChange}
                className={inputClassNames}
              />
            </div>
            <div className="col-span-1">
              <label className="block text-gray-700 font-bold">Portuguese Title (Max 120)</label>
              <input
                type="text"
                name="portugueseTitle"
                value={programData.portugueseTitle}
                onChange={handleChange}
                className={inputClassNames}
              />
            </div>
            <div className="col-span-1">
              <label className="block text-gray-700 font-bold">Spanish Title (Max 120)</label>
              <input
                type="text"
                name="spanishTitle"
                value={programData.spanishTitle}
                onChange={handleChange}
                className={inputClassNames}
              />
            </div>

            {/* Row 5: Short Titles */}
            <div className="col-span-1">
              <label className="block text-gray-700 font-bold">English Short Title (Max 70)</label>
              <input
                type="text"
                name="englishShortTitle"
                value={programData.englishShortTitle}
                onChange={handleChange}
                className={inputClassNames}
              />
            </div>
            <div className="col-span-1">
              <label className="block text-gray-700 font-bold">Portuguese Short Title (Max 70)</label>
              <input
                type="text"
                name="portugueseShortTitle"
                value={programData.portugueseShortTitle}
                onChange={handleChange}
                className={inputClassNames}
              />
            </div>
            <div className="col-span-1">
              <label className="block text-gray-700 font-bold">Spanish Short Title (Max 70)</label>
              <input
                type="text"
                name="spanishShortTitle"
                value={programData.spanishShortTitle}
                onChange={handleChange}
                className={inputClassNames}
              />
            </div>

            {/* Row 6: Descriptions */}
            <div className="col-span-1">
              <label className="block text-gray-700 font-bold">English Description (Max 500)</label>
              <textarea
                name="englishDescription"
                value={programData.englishDescription}
                onChange={handleChange}
                className={`${inputClassNames} h-auto`}
                rows="3"
              ></textarea>
            </div>
            <div className="col-span-1">
              <label className="block text-gray-700 font-bold">Portuguese Description (Max 500)</label>
              <textarea
                name="portugueseDescription"
                value={programData.portugueseDescription}
                onChange={handleChange}
                className={`${inputClassNames} h-auto`}
                rows="3"
              ></textarea>
            </div>
            <div className="col-span-1">
              <label className="block text-gray-700 font-bold">Spanish Description (Max 500)</label>
              <textarea
                name="spanishDescription"
                value={programData.spanishDescription}
                onChange={handleChange}
                className={`${inputClassNames} h-auto`}
                rows="3"
              ></textarea>
            </div>

            {/* Row 7: Short Descriptions */}
            <div className="col-span-1">
              <label className="block text-gray-700 font-bold">English Short Description (Max 250)</label>
              <textarea
                name="englishShortDescription"
                value={programData.englishShortDescription}
                onChange={handleChange}
                className={`${inputClassNames} h-auto`}
                rows="2"
              ></textarea>
            </div>
            <div className="col-span-1">
              <label className="block text-gray-700 font-bold">Portuguese Short Description (Max 250)</label>
              <textarea
                name="portugueseShortDescription"
                value={programData.portugueseShortDescription}
                onChange={handleChange}
                className={`${inputClassNames} h-auto`}
                rows="2"
              ></textarea>
            </div>
            <div className="col-span-1">
              <label className="block text-gray-700 font-bold">Spanish Short Description (Max 250)</label>
              <textarea
                name="spanishShortDescription"
                value={programData.spanishShortDescription}
                onChange={handleChange}
                className={`${inputClassNames} h-auto`}
                rows="2"
              ></textarea>
            </div>
          </div>

          <div className="mt-6"></div>

          {/* External Info Section */}
          <div className="mt-8">
            <h3 className="text-xl font-bold mb-4">External Info</h3>
            <div className="w-full flex justify-end">
              <div
                onClick={() => openModal()}
                className="inline-flex justify-center items-center h-[33px] px-3 text-sm bg-gradient-to-b from-btnLightBlue to-btnDeepBlue text-white rounded shadow cursor-pointer hover:bg-gradient-to-b hover:from-blue-600 hover:to-blue-800 focus:outline-none"
              >
                Add External Info
              </div>
            </div>

            <table className="mt-1 w-full border text-sm">
              <thead className="bg-gradient-to-b from-btnLightBlue to-btnDeepBlue text-white">
                <tr>
                  <th className="p-2 text-left">System</th>
                  <th className="p-2 text-left">Name</th>
                  <th className="p-2 text-left">Value</th>
                  <th className="p-2 text-right">Actions</th>
                </tr>
              </thead>
              <tbody>
                {externalInfo.map((info, index) => (
                  <tr key={index}>
                    <td className="p-2">{info.system}</td>
                    <td className="p-2">{info.name}</td>
                    <td className="p-2">{info.value}</td>
                    <td className="p-2 text-right">
                      <div 
                        onClick={() => openModal(index)} 
                        className="inline-block cursor-pointer text-blue-600 mr-2"
                      >
                        Edit
                      </div>
                      <div 
                        onClick={() => deleteExternalInfo(index)} 
                        className="inline-block cursor-pointer text-red-600"
                      >
                        Delete
                      </div>
                    </td>
                  </tr>
                ))}
              </tbody>
            </table>
          </div>
        </form>

        {/* Modal for External Info */}
        {showModal && (
          <div className="fixed inset-0 bg-gray-600 bg-opacity-50 flex items-center justify-center">
            <div className="bg-white p-6 rounded-lg shadow-lg w-1/3">
              <h3 className="text-xl font-bold mb-4">
                {editIndex !== null ? 'Edit External Info' : 'Add External Info'}
              </h3>
              <div className="grid grid-cols-1 gap-4 mb-4">
                <div>
                  <label className="block text-gray-700 font-bold mb-2">System</label>
                  <select
                    name="system"
                    value={newExternalInfo.system}
                    onChange={handleExternalInfoChange}
                    className="block w-full px-3 h-10 rounded-md border border-gray-300 shadow-sm focus:border-indigo-500 focus:ring-indigo-500"
                  >
                    <option value="">Select System</option>
                    <option value="gracenote">Gracenote</option>
                    <option value="fifa">FIFA</option>
                    <option value="opta">Opta</option>
                  </select>
                </div>
                <div>
                  <label className="block text-gray-700 font-bold mb-2">Name</label>
                  <select
                    name="name"
                    value={newExternalInfo.name}
                    onChange={handleExternalInfoChange}
                    disabled={!newExternalInfo.system}
                    className="block w-full px-3 h-10 rounded-md border border-gray-300 shadow-sm focus:border-indigo-500 focus:ring-indigo-500"
                  >
                    <option value="">Select Reference Name</option>
                    {newExternalInfo.system === 'gracenote' && (
                      <>
                        <option value="tmsId">TMSId</option>
                        <option value="rootId">RootId</option>
                        <option value="seriesId">SeriesId</option>
                        <option value="connectorId">ConnectorId</option>
                      </>
                    )}
                    {newExternalInfo.system === 'fifa' && (
                      <option value="fifaMatchId">FifaMatchId</option>
                    )}
                    {newExternalInfo.system === 'opta' && (
                      <>
                        <option value="optaMatchId">OptaMatchId</option>
                        <option value="competitionId">CompetitionId</option>
                        <option value="tournamentCalendarId">TournamentCalendarId</option>
                      </>
                    )}
                  </select>
                </div>
                <div>
                  <label className="block text-gray-700 font-bold mb-2">Value</label>
                  <input
                    type="text"
                    name="value"
                    value={newExternalInfo.value}
                    onChange={handleExternalInfoChange}
                    className="block w-full px-3 h-10 rounded-md border border-gray-300 shadow-sm focus:border-indigo-500 focus:ring-indigo-500"
                  />
                </div>
              </div>
              <div className="flex justify-end">
                <button
                  onClick={addOrUpdateExternalInfo}
                  className="px-4 h-10 bg-blue-600 text-white rounded-md shadow-md hover:bg-blue-700 focus:outline-none focus:ring-2 focus:ring-blue-500 mr-2"
                >
                  {editIndex !== null ? 'Update' : 'Add'}
                </button>
                <button
                  onClick={closeModal}
                  className="px-4 h-10 bg-gray-300 text-black rounded-md shadow-md hover:bg-gray-400 focus:outline-none focus:ring-2 focus:ring-gray-400"
                >
                  Cancel
                </button>
              </div>
            </div>
          </div>
        )}
      </div>
    
      <div className="flex-shrink-0 bg-gray-100 h-12 border-t border-gray-300 flex justify-between items-center w-full p-2">
        <button 
          onClick={() => handleSave(false)} 
          className="h-8 px-3 text-sm bg-gradient-to-b from-btnLightBlue to-btnDeepBlue text-white rounded shadow hover:bg-gradient-to-b hover:from-blue-600 hover:to-blue-800 focus:outline-none"
          style={{ height: '33px' }}
        >
          Quick Save
        </button>
        <button 
          onClick={() => handleSave(true)} 
          className="h-8 px-3 text-sm bg-gradient-to-b from-btnLightBlue to-btnDeepBlue text-white rounded shadow hover:bg-gradient-to-b hover:from-blue-600 hover:to-blue-800 focus:outline-none"
          style={{ height: '33px' }}
        >
          Publish
        </button>
      </div>
    </div>
  );
}
