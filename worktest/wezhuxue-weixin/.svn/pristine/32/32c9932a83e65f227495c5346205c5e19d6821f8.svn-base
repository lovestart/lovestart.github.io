package com.zhuxueup.common.oss;

import java.io.File;
import java.net.URL;
import java.util.Date;

import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.model.CannedAccessControlList;
import com.aliyun.oss.model.CreateBucketRequest;
import com.aliyun.oss.model.OSSObject;
import com.zhuxueup.common.util.CTUtils;

public class AliyunOSSUtil {
	
	private static String endpoint = "http://oss-cn-beijing.aliyuncs.com";
	private static String accessKeyId = "WHGLpYWuBgkLDCiC";
	private static String accessKeySecret = "yFI7cdeZWncNgKpkiWE3JbN8q9zrLQ";
	private static String bucketName = "wezhuxue-wx";

	public static boolean uploadFile(String bucketName, String key, File file) {
		boolean flag = false;
		OSSClient ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);
		try {
			if(CTUtils.isEmpty(bucketName)){
				bucketName = AliyunOSSUtil.bucketName;
			}
			if (ossClient.doesBucketExist(bucketName)) {
				System.out.println("您已经创建Bucket：" + bucketName + "。");
			} else {
				CreateBucketRequest createBucketRequest = new CreateBucketRequest(bucketName);
				// 设置bucket权限
				createBucketRequest.setCannedACL(CannedAccessControlList.PublicRead);
				ossClient.createBucket(createBucketRequest);
			}
			ossClient.putObject(bucketName, key, file);
			System.out.println("file:" + file.getPath() + "Object：" + key + "存入OSS成功。");
			flag = true;
		} catch (OSSException oe) {
			oe.printStackTrace();
		} catch (ClientException ce) {
			ce.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			ossClient.shutdown();
		}
		return flag;
	}
	public static String getFile(String key) {
		OSSClient ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);
		String str = "";
		try {
			if (ossClient.doesBucketExist(bucketName)) {
				System.out.println("您已经创建Bucket：" + bucketName + "。");
			} else {
				CreateBucketRequest createBucketRequest = new CreateBucketRequest(bucketName);
				// 设置bucket权限
				createBucketRequest.setCannedACL(CannedAccessControlList.PublicRead);
				ossClient.createBucket(createBucketRequest);
			}
			OSSObject ossObject = ossClient.getObject(bucketName, key);
			ossObject.getObjectContent();

			// 设置URL过期时间为1小时
			Date expiration = new Date(new Date().getTime() + 3600 * 1000);

			// 生成URL
			URL url = ossClient.generatePresignedUrl(bucketName, key, expiration);
			url.getPath();
			str = url.toString();
			System.out.println("---------"+str);
		} catch (OSSException oe) {
			oe.printStackTrace();
		} catch (ClientException ce) {
			ce.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			ossClient.shutdown();
		}
		return str;
	}
	public static void main(String[] args){
		getFile("packet/u1485057874527BVEODT");
		//uploadFile("wezhuxue", "test/test", new File("d:/tt.jpg"));
		//getFile("video/cfd96d3d134d1c9fb73257aa8b36fbe6/cut_adffa8a65c9af341151eec6b9596809c");
	}
}
