package com.zhuxueup.common.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.zip.DeflaterOutputStream;
import java.util.zip.InflaterOutputStream;

import org.apache.commons.codec.binary.Base64;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class ImgConvertUtil {
	
	/**
	 * 将图片转换成base64
	 * @return
	 * @throws IOException 
	 */
	public static String convertImgStr(String imgBase64) throws IOException{
		InputStream in=null;
		byte [] data=null;
		BASE64Encoder encoder=null;
		try {
			in=new FileInputStream(imgBase64);
			
			data=new byte[in.available()];
			
			in.read(data);
			
			encoder=new BASE64Encoder();
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}finally{
			if(null!=in){
				in.close();
			}
		}
		return encoder.encode(data);
	}
	
	/**
	 * 将base64生产图片保存
	 * @param baseStr
	 * @throws IOException 
	 */
	public static boolean createImgByBASE64(String baseStr,String filePath) throws IOException{
		boolean flag=false;
		if(null==baseStr || "".equals(baseStr)){
			return flag;
		}
		
		System.out.println("将base64生产图片保存 filePath :"  +filePath);
		
		BASE64Decoder decoder = new BASE64Decoder();
		OutputStream out=null;
		byte[] b=null;
		try {
			b = decoder.decodeBuffer(baseStr);
			for(int i=0;i<b.length;++i)  
	         {  
	             if(b[i]<0)  
	             {  
	                 b[i]+=256;  
	             }  
	         }  
			//String filePath="http://192.168.10.87:8080/pic/img/xp.jpg";
			out = new FileOutputStream(filePath);      
            out.write(b);  
            out.flush();  
            out.close();
			
			//FtpUtil.uploadFile(192.168.10.87, 22, name, pwd, "", "wdd.jpg",);
			
            flag=true;
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			out.close();
		}
		return flag;
	}

	public static boolean createImgByBASE64(String baseStr,File file,String filePath) throws IOException{
		boolean flag=false;
		if(null==baseStr || "".equals(baseStr)){
			return flag;
		}
		
		System.out.println("将base64生产图片保存 filePath :"  +filePath);
		
		if(!file .exists() && !file.isDirectory()){
			file.mkdirs();
		}
		
		BASE64Decoder decoder = new BASE64Decoder();
		OutputStream out=null;
		byte[] b=null;
		try {
			b = decoder.decodeBuffer(baseStr);
			for(int i=0;i<b.length;++i)  
	         {  
	             if(b[i]<0)  
	             {  
	                 b[i]+=256;  
	             }  
	         }  
			//String filePath="http://192.168.10.87:8080/pic/img/xp.jpg";
			out = new FileOutputStream(filePath);      
            out.write(b);  
            out.flush();  
            out.close();
			
			//FtpUtil.uploadFile(192.168.10.87, 22, name, pwd, "", "wdd.jpg",);
			
            flag=true;
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			out.close();
		}
		/**
		 * 将上传的文件copy到   /home/img目录下
		 */
		
		/*File oldfile=new File(filePath);
		if(oldfile.isFile()){
			int bytesum = 0,byteread=0; 
			byte[] buffer = new byte[1444]; 
			InputStream inStream = new FileInputStream(filePath);
			FileOutputStream fs = new FileOutputStream("/home/img/");
			while ( (byteread = inStream.read(buffer)) != -1) { 
				bytesum += byteread;
				fs.write(buffer, 0, byteread); 
			} 
			inStream.close(); 
		}*/
		
		
		return flag;
	}
	
	
	
	
	public static String compressData(String data) {  
        try {  
            ByteArrayOutputStream bos = new ByteArrayOutputStream();  
            DeflaterOutputStream zos = new DeflaterOutputStream(bos);  
            zos.write(data.getBytes());  
            zos.close();  
            return new String(getenBASE64inCodec(bos.toByteArray()));  
        } catch (Exception ex) {  
            ex.printStackTrace();  
            return "ZIP_ERR";  
        }  
    }  
      
//  使用apche codec对数组进行encode  
    public static String getenBASE64inCodec(byte [] b) {  
        if (b == null)  
            return null;  
        return new String((new Base64()).encode(b));  
    }  
      
      
//  base64转码为string  
    public static byte[] getdeBASE64inCodec(String s) {  
        if (s == null)  
            return null;  
        return new Base64().decode(s.getBytes());  
    }  
      
//  解码字符串  
    public static  String decompressData(String encdata) {  
        try {  
            ByteArrayOutputStream bos = new ByteArrayOutputStream();  
            InflaterOutputStream zos = new InflaterOutputStream(bos);  
            zos.write(getdeBASE64inCodec(encdata));   
            zos.close();  
            return new String(bos.toByteArray());  
        } catch (Exception ex) {  
            ex.printStackTrace();  
            return "UNZIP_ERR";  
        }  
    }  
    
    
    
    public static void main(String[] args) {
    	/*String str="iVBORw0KGgoAAAANSUhEUgAAACwAAAAcCAYAAAAa/l2w0KGgoAAAANSUhEUgAAACwAAAAcCAYAAAAa/l2sAAAAGXRFWHRTb2Z0d2FyZQBBZG9iZSBJbWFnZVJlYWR5ccllPAAAAyhpVFh0WE1MOmNvbS5hZG9iZS54bXAAAAAAADw/eHBhY2tldCBiZWdpbj0i77u/IiBpZD0iVzVNME1wQ2VoaUh6cmVTek5UY3prYzlkIj8+IDx4OnhtcG1ldGEgeG1sbnM6eD0iYWRvYmU6bnM6bWV0YS8iIHg6eG1wdGs9IkFkb2JlIFhNUCBDb3JlIDUuNi1jMDY3IDc5LjE1Nzc0NywgMjAxNS8wMy8zMC0yMzo0MDo0MiAgICAgICAgIj4gPHJkZjpSREYgeG1sbnM6cmRmPSJodHRwOi8vd3d3LnczLm9yZy8xOTk5LzAyLzIyLXJkZi1zeW50YXgtbnMjIj4gPHJkZjpEZXNjcmlwdGlvbiByZGY6YWJvdXQ9IiIgeG1sbnM6eG1wPSJodHRwOi8vbnMuYWRvYmUuY29tL3hhcC8xLjAvIiB4bWxuczp4bXBNTT0iaHR0cDovL25zLmFkb2JlLmNvbS94YXAvMS4wL21tLyIgeG1sbnM6c3RSZWY9Imh0dHA6Ly9ucy5hZG9iZS5jb20veGFwLzEuMC9zVHlwZS9SZXNvdXJjZVJlZiMiIHhtcDpDcmVhdG9yVG9vbD0iQWRvYmUgUGhvdG9zaG9wIENDIDIwMTUgKE1hY2ludG9zaCkiIHhtcE1NOkluc3RhbmNlSUQ9InhtcC5paWQ6MUUzQjg4QTRCMEE5MTFFNTlFRTZFQjY5OTVCMzUwREQiIHhtcE1NOkRvY3VtZW50SUQ9InhtcC5kaWQ6MUUzQjg4QTVCMEE5MTFFNTlFRTZFQjY5OTVCMzUwREQiPiA8eG1wTU06RGVyaXZlZEZyb20gc3RSZWY6aW5zdGFuY2VJRD0ieG1wLmlpZDoxRTNCODhBMkIwQTkxMUU1OUVFNkVCNjk5NUIzNTBERCIgc3RSZWY6ZG9jdW1lbnRJRD0ieG1wLmRpZDoxRTNCODhBM0IwQTkxMUU1OUVFNkVCNjk5NUIzNTBERCIvPiA8L3JkZjpEZXNjcmlwdGlvbj4gPC9yZGY6UkRGPiA8sAAAAGXRFWHRTb2Z0d2FyZQBBZG9iZSBJbWFnZVJlYWR5ccllPAAAAyhpVFh0WE1MOmNvbS5hZG9iZS54bXAAAAAAADw/eHBhY2tldCBiZWdpbj0i77u/IiBpZD0iVzVNME1wQ2VoaUh6cmVTek5UY3prYzlkIj8+IDx4OnhtcG1ldGEgeG1sbnM6eD0iYWRvYmU6bnM6bWV0YS8iIHg6eG1wdGs9IkFkb2JlIFhNUCBDb3JlIDUuNi1jMDY3IDc5LjE1Nzc0NywgMjAxNS8wMy8zMC0yMzo0MDo0MiAgICAgICAgIj4gPHJkZjpSREYgeG1sbnM6cmRmPSJodHRwOi8vd3d3LnczLm9yZy8xOTk5LzAyLzIyLXJkZi1zeW50YXgtbnMjIj4gPHJkZjpEZXNjcmlwdGlvbiByZGY6YWJvdXQ9IiIgeG1sbnM6eG1wPSJodHRwOi8vbnMuYWRvYmUuY29tL3hhcC8xLjAvIiB4bWxuczp4bXBNTT0iaHR0cDovL25zLmFkb2JlLmNvbS94YXAvMS4wL21tLyIgeG1sbnM6c3RSZWY9Imh0dHA6Ly9ucy5hZG9iZS5jb20veGFwLzEuMC9zVHlwZS9SZXNvdXJjZVJlZiMiIHhtcDpDcmVhdG9yVG9vbD0iQWRvYmUgUGhvdG9zaG9wIENDIDIwMTUgKE1hY2ludG9zaCkiIHhtcE1NOkluc3RhbmNlSUQ9InhtcC5paWQ6MUUzQjg4QTRCMEE5MTFFNTlFRTZFQjY5OTVCMzUwREQiIHhtcE1NOkRvY3VtZW50SUQ9InhtcC5kaWQ6MUUzQjg4QTVCMEE5MTFFNTlFRTZFQjY5OTVCMzUwREQiPiA8eG1wTU06RGVyaXZlZEZyb20gc3RSZWY6aW5zdGFuY2VJRD0ieG1wLmlpZDoxRTNCODhBMkIwQTkxMUU1OUVFNkVCNjk5NUIzNTBERCIgc3RSZWY6ZG9jdW1lbnRJRD0ieG1wLmRpZDoxRTNCODhBM0IwQTkxMUU1OUVFNkVCNjk5NUIzNTBERCIvPiA8L3JkZjpEZXNjcmlwdGlvbj4gPC9yZGY6UkRGPiA8";
    	String s2=compressData(str);
    	System.out.println(s2);
    	System.out.println(decompressData(s2));*/
    	String str="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";
    	try {
			createImgByBASE64(str, new File("d:/tstImg/"), "d:/tstImg/tt.png");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
	
}
