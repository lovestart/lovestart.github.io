export interface MethodMapping {
  id: string; // unique identifier for React keys
  rawOriginal: string;
  obfuscated: string;
  type: 'method' | 'field';
  lineNumbers?: string;
}

export interface ClassMapping {
  id: string;
  originalName: string;
  obfuscatedName: string;
  members: MethodMapping[];
}

export interface MappingFile {
  fileName: string;
  metadata: string[]; // Header comments like compiler version
  classes: ClassMapping[];
}