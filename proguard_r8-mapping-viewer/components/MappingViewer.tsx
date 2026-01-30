import React, { useMemo, useState, useEffect } from 'react';
import { MappingFile, ClassMapping } from '../types';
import { Search, FileText, ArrowRight, Code2, Box, Layers, X, Info } from 'lucide-react';

interface MappingViewerProps {
  data: MappingFile;
  onClose: () => void;
}

export const MappingViewer: React.FC<MappingViewerProps> = ({ data, onClose }) => {
  const [searchTerm, setSearchTerm] = useState('');
  const [selectedClassId, setSelectedClassId] = useState<string | null>(null);
  
  // Render limit to prevent DOM explosion with large files
  const RENDER_LIMIT = 200;
  const [renderLimit, setRenderLimit] = useState(RENDER_LIMIT);

  // Reset limit when search changes
  useEffect(() => {
    setRenderLimit(RENDER_LIMIT);
  }, [searchTerm]);

  // Filter logic
  const filteredClasses = useMemo(() => {
    if (!searchTerm) return data.classes;
    
    const lowerTerm = searchTerm.toLowerCase();
    return data.classes.filter(cls => 
      cls.originalName.toLowerCase().includes(lowerTerm) || 
      cls.obfuscatedName.toLowerCase().includes(lowerTerm)
    );
  }, [data.classes, searchTerm]);

  // Visible subset
  const visibleClasses = useMemo(() => {
    return filteredClasses.slice(0, renderLimit);
  }, [filteredClasses, renderLimit]);

  // Find selected class object
  const selectedClass = useMemo(() => {
    return data.classes.find(c => c.id === selectedClassId) || null;
  }, [data.classes, selectedClassId]);

  const handleScroll = (e: React.UIEvent<HTMLDivElement>) => {
    const { scrollTop, scrollHeight, clientHeight } = e.currentTarget;
    if (scrollHeight - scrollTop - clientHeight < 100) {
       if (renderLimit < filteredClasses.length) {
         setRenderLimit(prev => Math.min(prev + 100, filteredClasses.length));
       }
    }
  };

  return (
    <div className="flex flex-col h-screen bg-slate-50 overflow-hidden">
      {/* Header */}
      <header className="flex-none bg-white border-b border-slate-200 px-6 py-4 flex items-center justify-between shadow-sm z-10">
        <div className="flex items-center gap-3">
          <div className="p-2 bg-blue-100 text-blue-600 rounded-lg">
            <FileText className="w-5 h-5" />
          </div>
          <div>
            <h1 className="font-bold text-slate-800 leading-tight">{data.fileName}</h1>
            <p className="text-xs text-slate-500 flex gap-2">
              <span>{data.classes.length.toLocaleString()} classes found</span>
              <span>•</span>
              <span>R8 / ProGuard</span>
            </p>
          </div>
        </div>
        <div className="flex items-center gap-3">
             <button 
                onClick={onClose}
                className="p-2 hover:bg-slate-100 text-slate-500 rounded-lg transition-colors"
                title="Close file"
            >
                <X className="w-5 h-5" />
            </button>
        </div>
      </header>

      {/* Main Content Area */}
      <div className="flex-1 flex overflow-hidden">
        
        {/* Left Sidebar: Class List */}
        <div className="w-full md:w-1/3 lg:w-1/4 bg-white border-r border-slate-200 flex flex-col z-0">
          <div className="p-4 border-b border-slate-100">
            <div className="relative">
              <Search className="absolute left-3 top-1/2 -translate-y-1/2 w-4 h-4 text-slate-400" />
              <input 
                type="text" 
                placeholder="Search classes..." 
                className="w-full pl-9 pr-4 py-2 bg-slate-50 border border-slate-200 rounded-lg text-sm focus:outline-none focus:ring-2 focus:ring-blue-500 focus:border-transparent"
                value={searchTerm}
                onChange={(e) => setSearchTerm(e.target.value)}
              />
            </div>
            <div className="mt-2 text-xs text-slate-400 flex justify-between">
                <span>Showing {visibleClasses.length.toLocaleString()} of {filteredClasses.length.toLocaleString()}</span>
            </div>
          </div>
          
          <div 
            className="flex-1 overflow-y-auto"
            onScroll={handleScroll}
          >
            {visibleClasses.length === 0 ? (
                <div className="p-8 text-center text-slate-400 text-sm">
                    No classes match your search.
                </div>
            ) : (
                <div className="divide-y divide-slate-50">
                {visibleClasses.map((cls) => (
                    <button
                    key={cls.id}
                    onClick={() => setSelectedClassId(cls.id)}
                    className={`w-full text-left p-4 hover:bg-slate-50 transition-colors border-l-4 ${
                        selectedClassId === cls.id 
                        ? 'bg-blue-50 border-blue-500' 
                        : 'border-transparent'
                    }`}
                    >
                    <div className="flex items-center justify-between mb-1">
                        <span className="font-mono text-xs font-bold text-slate-500 bg-slate-100 px-2 py-0.5 rounded">
                           {cls.obfuscatedName}
                        </span>
                        <span className="text-[10px] text-slate-400 uppercase tracking-wider">Class</span>
                    </div>
                    <div className="text-sm font-medium text-slate-800 truncate" title={cls.originalName}>
                        {cls.originalName}
                    </div>
                    </button>
                ))}
                {renderLimit < filteredClasses.length && (
                    <div className="p-4 text-center text-xs text-slate-400">
                        Scroll for more...
                    </div>
                )}
                </div>
            )}
          </div>
        </div>

        {/* Right Content: Class Details */}
        <div className="flex-1 bg-slate-50 overflow-y-auto p-4 md:p-8">
          {selectedClass ? (
            <div className="max-w-4xl mx-auto space-y-6">
              
              {/* Class Card */}
              <div className="bg-white rounded-xl shadow-sm border border-slate-200 p-6">
                <div className="flex items-start gap-4">
                  <div className="p-3 bg-indigo-100 text-indigo-600 rounded-xl">
                    <Box className="w-6 h-6" />
                  </div>
                  <div className="flex-1">
                    <h2 className="text-lg font-semibold text-slate-800 mb-1">Class Mapping</h2>
                    <div className="flex flex-col md:flex-row md:items-center gap-2 md:gap-4 mt-4 bg-slate-50 p-4 rounded-lg border border-slate-100">
                      <div className="flex-1">
                        <div className="text-xs text-slate-500 uppercase tracking-wider font-semibold mb-1">Original</div>
                        <code className="text-sm text-slate-700 break-all">{selectedClass.originalName}</code>
                      </div>
                      <ArrowRight className="hidden md:block w-5 h-5 text-slate-300" />
                      <div className="flex-1">
                        <div className="text-xs text-slate-500 uppercase tracking-wider font-semibold mb-1">Obfuscated</div>
                        <code className="text-sm text-indigo-600 font-bold break-all">{selectedClass.obfuscatedName}</code>
                      </div>
                    </div>
                  </div>
                </div>
              </div>

              {/* Members List */}
              <div className="bg-white rounded-xl shadow-sm border border-slate-200 overflow-hidden">
                <div className="px-6 py-4 border-b border-slate-100 flex items-center justify-between">
                  <h3 className="font-semibold text-slate-800 flex items-center gap-2">
                    <Layers className="w-5 h-5 text-slate-400" />
                    Members
                    <span className="text-xs font-normal text-slate-400 bg-slate-100 px-2 py-0.5 rounded-full">
                        {selectedClass.members.length}
                    </span>
                  </h3>
                </div>

                {selectedClass.members.length === 0 ? (
                   <div className="p-12 text-center text-slate-400">
                       No obfuscated members found for this class.
                   </div>
                ) : (
                    <div className="divide-y divide-slate-100">
                    {selectedClass.members.map((member) => (
                        <div key={member.id} className="p-4 hover:bg-slate-50 transition-colors group">
                        <div className="flex flex-col md:flex-row gap-4">
                            
                            {/* Obfuscated Side (Left for emphasis in debugging usually, but here strict left-right mapping) */}
                            <div className="md:w-1/4 flex-none">
                                <span className="text-xs text-slate-400 uppercase tracking-wider font-semibold block mb-1">Obfuscated</span>
                                <div className="flex items-center gap-2">
                                    <span className={`w-2 h-2 rounded-full ${member.type === 'method' ? 'bg-green-400' : 'bg-orange-400'}`}></span>
                                    <code className="font-mono text-sm font-bold text-indigo-600">
                                        {member.obfuscated}
                                    </code>
                                </div>
                            </div>

                            {/* Original Side */}
                            <div className="flex-1 min-w-0">
                                <span className="text-xs text-slate-400 uppercase tracking-wider font-semibold block mb-1">Original Signature</span>
                                <code className="font-mono text-sm text-slate-700 break-all block whitespace-pre-wrap">
                                    {member.rawOriginal}
                                </code>
                            </div>

                        </div>
                        </div>
                    ))}
                    </div>
                )}
              </div>

            </div>
          ) : (
            <div className="h-full flex flex-col items-center justify-center text-center opacity-50 p-8">
              <Code2 className="w-16 h-16 text-slate-300 mb-4" />
              <h3 className="text-xl font-semibold text-slate-700">Select a Class</h3>
              <p className="text-slate-500 max-w-sm mt-2">
                Click on a class from the list on the left to view its member mappings and details.
              </p>
            </div>
          )}
        </div>
      </div>
      
      {/* Footer / Metadata overlay hint */}
      {data.metadata.length > 0 && (
          <div className="fixed bottom-4 right-4 group">
            <div className="bg-white border border-slate-200 shadow-lg p-4 rounded-lg text-xs text-slate-500 max-w-xs hidden group-hover:block absolute bottom-10 right-0 mb-2 z-50">
                <h4 className="font-bold text-slate-700 mb-2">Metadata</h4>
                <ul className="list-disc pl-4 space-y-1">
                    {data.metadata.map((m, i) => (
                        <li key={i}>{m.replace(/^#\s?/, '')}</li>
                    ))}
                </ul>
            </div>
            <button className="bg-slate-800 text-white p-2 rounded-full shadow-lg hover:bg-slate-700 transition-colors">
                <Info className="w-5 h-5" />
            </button>
          </div>
      )}
    </div>
  );
};