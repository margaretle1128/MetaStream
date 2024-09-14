"use client";

import React, { useState } from 'react';
import ProgramList from './components/ProgramList/ProgramList.jsx';
import ProgramDetail from './components/ProgramDetail/ProgramDetail.jsx';

export default function HomePage() {
  const [selectedProgramId, setSelectedProgramId] = useState(null);
  const [newProgramType, setNewProgramType] = useState(null);

  const handleSelectProgram = (program) => {
    setSelectedProgramId(program.programId);
    setNewProgramType(null);  
  };

  const handleCreateNewProgram = (programType) => {
    setSelectedProgramId(null); 
    setNewProgramType(programType); 
  };

  return (
      <div className="w-full h-screen bg-gradient-to-b from-black to-white">
        <div className="w-full bg-navBarGray text-white p-4 h-14">
          <h1>Program Management</h1>
        </div>

        <div className="flex flex-col relative items-center justify-center h-[calc(100vh-3.5rem)] py-14 px-10 w-full">
          <h1 className="text-white absolute font-bold text-xl top-4 left-10">Programs</h1>
          <div className="flex flex-row w-full h-full space-x-2 text-sm">
            <div className="w-3/12 bg-white overflow-hidden rounded h-full shadow-md">
              <ProgramList 
                onSelectProgram={handleSelectProgram} 
                onCreateNewProgram={handleCreateNewProgram} 
              />
            </div>

            <div className="w-9/12 bg-white overflow-hidden rounded relative h-full shadow-md">
              {newProgramType ? (
                <ProgramDetail key={`new-${newProgramType}`} programId={null} programType={newProgramType} />
              ) : selectedProgramId ? (
                <ProgramDetail key={`existing-${selectedProgramId}`} programId={selectedProgramId} />
              ) : (
                <div className="flex items-center justify-center h-full text-gray-400">
                  Select a program or create a new one.
                </div>
              )}
            </div>
          </div>
        </div>
      </div>
  );
}
