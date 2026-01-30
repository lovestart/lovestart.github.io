import React, { useState, useCallback } from 'react';
import { FileUpload } from './components/FileUpload';
import { MappingViewer } from './components/MappingViewer';
import { MappingFile } from './types';
import { parseMappingFile } from './services/mappingParser';
import { Github, Bug } from 'lucide-react';

const App: React.FC = () => {
  const [mappingData, setMappingData] = useState<MappingFile | null>(null);
  const [isLoading, setIsLoading] = useState(false);
  const [progress, setProgress] = useState(0);

  const handleFileSelected = useCallback(async (file: File) => {
    setIsLoading(true);
    setProgress(0);
    
    try {
        const parsedData = await parseMappingFile(file, (percent) => {
            setProgress(percent);
        });
        setMappingData(parsedData);
    } catch (e) {
        console.error("Parsing failed", e);
        alert("Failed to parse the mapping file. Ensure it is a valid R8/ProGuard format.");
    } finally {
        setIsLoading(false);
    }
  }, []);

  const handleClose = () => {
    setMappingData(null);
    setProgress(0);
  };

  if (mappingData) {
    return <MappingViewer data={mappingData} onClose={handleClose} />;
  }

  return (
    <div className="min-h-screen bg-slate-50 flex flex-col">
      {/* Navigation */}
      <nav className="bg-white border-b border-slate-200 px-6 py-4">
        <div className="max-w-7xl mx-auto flex items-center justify-between">
          <div className="flex items-center gap-2">
            <div className="p-2 bg-indigo-600 rounded-lg text-white">
                <Bug className="w-5 h-5" />
            </div>
            <span className="font-bold text-xl text-slate-800 tracking-tight">De-Obfuscator</span>
          </div>
          <a href="#" className="text-slate-500 hover:text-slate-800 transition-colors">
            <Github className="w-6 h-6" />
          </a>
        </div>
      </nav>

      {/* Main Content */}
      <main className="flex-1 flex flex-col items-center justify-center p-6">
        <div className="text-center max-w-2xl mx-auto mb-8">
            <h1 className="text-4xl font-extrabold text-slate-900 mb-4">
                Analyze your <span className="text-indigo-600">mapping.txt</span>
            </h1>
            <p className="text-lg text-slate-600">
                Easily browse, search, and de-obfuscate your Android R8 or ProGuard mapping files. 
                Debug production crashes faster by translating obfuscated stack traces back to the source.
            </p>
        </div>

        {isLoading ? (
          <div className="flex flex-col items-center justify-center p-12 bg-white rounded-xl shadow-sm border border-slate-200 w-full max-w-2xl h-64">
            <div className="w-full max-w-xs space-y-4">
                 <div className="flex justify-between text-sm font-medium text-slate-700">
                    <span>Parsing file...</span>
                    <span>{progress}%</span>
                 </div>
                 <div className="w-full bg-slate-100 rounded-full h-2.5 overflow-hidden">
                    <div 
                        className="bg-indigo-600 h-2.5 rounded-full transition-all duration-300 ease-out" 
                        style={{ width: `${progress}%` }}
                    ></div>
                 </div>
                 <p className="text-xs text-slate-400 text-center">Processing large files may take a moment</p>
            </div>
          </div>
        ) : (
          <FileUpload onFileSelected={handleFileSelected} />
        )}
        
        {/* Sample / Demo Instruction */}
         <div className="mt-12 grid grid-cols-1 md:grid-cols-3 gap-6 max-w-4xl w-full text-left">
            <div className="bg-white p-6 rounded-xl border border-slate-100 shadow-sm">
                <div className="w-10 h-10 bg-blue-50 text-blue-600 rounded-lg flex items-center justify-center mb-4 font-bold">1</div>
                <h3 className="font-semibold text-slate-800 mb-2">Upload File</h3>
                <p className="text-sm text-slate-500">Drag and drop your <code>mapping.txt</code> generated during your Android release build.</p>
            </div>
            <div className="bg-white p-6 rounded-xl border border-slate-100 shadow-sm">
                <div className="w-10 h-10 bg-indigo-50 text-indigo-600 rounded-lg flex items-center justify-center mb-4 font-bold">2</div>
                <h3 className="font-semibold text-slate-800 mb-2">Search & Filter</h3>
                <p className="text-sm text-slate-500">Instantly find classes by their obfuscated names (e.g., <code>a.b.c</code>) or original names.</p>
            </div>
             <div className="bg-white p-6 rounded-xl border border-slate-100 shadow-sm">
                <div className="w-10 h-10 bg-purple-50 text-purple-600 rounded-lg flex items-center justify-center mb-4 font-bold">3</div>
                <h3 className="font-semibold text-slate-800 mb-2">Inspect Details</h3>
                <p className="text-sm text-slate-500">Drill down into specific classes to see method and field mappings with line numbers.</p>
            </div>
         </div>
      </main>

      <footer className="bg-white border-t border-slate-200 py-6 text-center text-slate-500 text-sm">
        <p>&copy; {new Date().getFullYear()} Mapping Viewer Tool. Runs entirely in your browser.</p>
      </footer>
    </div>
  );
};

export default App;