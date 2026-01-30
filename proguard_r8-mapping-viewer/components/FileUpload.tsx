import React, { useRef, useState } from 'react';
import { Upload, FileText, AlertCircle } from 'lucide-react';

interface FileUploadProps {
  onFileSelected: (file: File) => void;
}

export const FileUpload: React.FC<FileUploadProps> = ({ onFileSelected }) => {
  const [isDragging, setIsDragging] = useState(false);
  const [error, setError] = useState<string | null>(null);
  const inputRef = useRef<HTMLInputElement>(null);

  const handleFile = (file: File) => {
    setError(null);
    if (!file) return;
    
    // We process the file directly now, no size limit check needed here
    // as we stream it in the parser.
    onFileSelected(file);
  };

  const handleDragOver = (e: React.DragEvent) => {
    e.preventDefault();
    setIsDragging(true);
  };

  const handleDragLeave = (e: React.DragEvent) => {
    e.preventDefault();
    setIsDragging(false);
  };

  const handleDrop = (e: React.DragEvent) => {
    e.preventDefault();
    setIsDragging(false);
    if (e.dataTransfer.files && e.dataTransfer.files.length > 0) {
      handleFile(e.dataTransfer.files[0]);
    }
  };

  return (
    <div className="w-full max-w-2xl mx-auto mt-20 p-6">
      <div
        className={`relative border-2 border-dashed rounded-xl p-12 text-center transition-all duration-200 ease-in-out cursor-pointer group
          ${isDragging 
            ? 'border-blue-500 bg-blue-50' 
            : 'border-slate-300 hover:border-blue-400 hover:bg-slate-50'
          }`}
        onDragOver={handleDragOver}
        onDragLeave={handleDragLeave}
        onDrop={handleDrop}
        onClick={() => inputRef.current?.click()}
      >
        <input
          type="file"
          ref={inputRef}
          className="hidden"
          accept=".txt,.map,.mapping"
          onChange={(e) => {
            if (e.target.files && e.target.files.length > 0) {
              handleFile(e.target.files[0]);
            }
          }}
        />

        <div className="flex flex-col items-center gap-4">
          <div className={`p-4 rounded-full bg-slate-100 group-hover:bg-blue-100 transition-colors`}>
            <Upload className="w-8 h-8 text-slate-400 group-hover:text-blue-500" />
          </div>
          <div>
            <h3 className="text-lg font-semibold text-slate-700">
              Upload Mapping File
            </h3>
            <p className="text-sm text-slate-500 mt-1">
              Drag and drop your <code className="bg-slate-200 px-1 rounded">mapping.txt</code> here, or click to browse
            </p>
          </div>
        </div>
      </div>

      {error && (
        <div className="mt-4 p-4 bg-red-50 text-red-700 rounded-lg flex items-center gap-2">
          <AlertCircle className="w-5 h-5" />
          <span>{error}</span>
        </div>
      )}

      <div className="mt-8 text-center">
        <div className="inline-flex items-center gap-2 px-4 py-2 bg-slate-100 text-slate-600 rounded-full text-xs font-medium">
          <FileText className="w-4 h-4" />
          Supports ProGuard & R8 formats (100MB+)
        </div>
      </div>
    </div>
  );
};