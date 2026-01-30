import { ClassMapping, MappingFile } from "../types";

export const parseMappingFile = async (
  file: File,
  onProgress: (percent: number) => void
): Promise<MappingFile> => {
  const classes: ClassMapping[] = [];
  const metadata: string[] = [];
  
  let currentClass: ClassMapping | null = null;
  let memberIdCounter = 0;

  // Configuration
  const CHUNK_SIZE = 1024 * 512; // 512KB chunks
  const totalSize = file.size;
  let offset = 0;
  
  const decoder = new TextDecoder("utf-8");
  let leftover = "";

  // Helper to process a single line
  const processLine = (line: string) => {
    if (!line.trim()) return;

    // Handle Comments/Metadata
    if (line.startsWith('#')) {
      metadata.push(line);
      return;
    }

    // ProGuard format: Original -> Obfuscated:
    // Identify Class Line (ends with colon)
    if (!line.startsWith(' ') && line.trim().endsWith(':')) {
      const parts = line.split(' -> ');
      if (parts.length === 2) {
        const originalName = parts[0].trim();
        const obfuscatedName = parts[1].replace(':', '').trim();

        currentClass = {
          id: `class-${classes.length}`,
          originalName,
          obfuscatedName,
          members: []
        };
        classes.push(currentClass);
      }
      return;
    }

    // Identify Member Line (starts with whitespace)
    if (line.startsWith(' ') && currentClass) {
      const arrowIndex = line.lastIndexOf(' -> ');
      if (arrowIndex !== -1) {
        const leftSide = line.substring(0, arrowIndex).trim();
        const rightSide = line.substring(arrowIndex + 4).trim();

        let lineNumbers = "";
        const lineNumMatch = leftSide.match(/^(\d+:\d+:)/);
        if (lineNumMatch) {
            lineNumbers = lineNumMatch[1];
        }

        const isMethod = leftSide.includes('(');

        currentClass.members.push({
          id: `member-${classes.length}-${memberIdCounter++}`,
          rawOriginal: leftSide,
          obfuscated: rightSide,
          type: isMethod ? 'method' : 'field',
          lineNumbers
        });
      }
    }
  };

  while (offset < totalSize) {
    const slice = file.slice(offset, offset + CHUNK_SIZE);
    // Convert blob to array buffer
    const buffer = await new Promise<ArrayBuffer>((resolve, reject) => {
        const reader = new FileReader();
        reader.onload = (e) => resolve(e.target?.result as ArrayBuffer);
        reader.onerror = reject;
        reader.readAsArrayBuffer(slice);
    });

    // Decode with stream: true to handle multi-byte characters across chunks
    const textChunk = decoder.decode(buffer, { stream: true });
    
    // Combine with leftover from previous chunk
    const content = leftover + textChunk;
    
    // Split into lines
    const lines = content.split(/\r?\n/);
    
    // The last line is likely incomplete (unless file ends exactly at chunk boundary, which is rare)
    // Save it for the next iteration
    leftover = lines.pop() || "";

    // Process lines in this chunk
    for (const line of lines) {
      processLine(line);
    }

    offset += CHUNK_SIZE;
    
    // Update progress
    const progress = Math.min(100, Math.round((offset / totalSize) * 100));
    onProgress(progress);

    // Yield to main thread to allow UI rendering
    await new Promise(resolve => setTimeout(resolve, 0));
  }

  // Process the very last segment
  if (leftover.trim()) {
    processLine(leftover);
  }

  return {
    fileName: file.name,
    metadata,
    classes
  };
};