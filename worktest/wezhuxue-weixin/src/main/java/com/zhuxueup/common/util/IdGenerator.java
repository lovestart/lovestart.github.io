package com.zhuxueup.common.util;

import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;

public class IdGenerator {

	private static AtomicInteger uniqueId=new AtomicInteger(0);
	
	public static String nextId(){
		int id=0;
		if((id=uniqueId.getAndIncrement())>=99){
			uniqueId.set(0);
		}
		return (System.nanoTime() / (10000 * 60)) % 1000000000 +"0" + String.format("%02d", id);
	}
	public static String orderId(){
		int id=0;
		if((id=uniqueId.getAndIncrement())>=99){
			uniqueId.set(0);
		}
		return "O"+DateUtil.DateToString(new Date(), "yyyyMMdd")+(System.nanoTime() / (10000 * 60)) % 1000000000 +"0" + String.format("%02d", id);
	}
	public static String wxCouponOrder(){
		int id=0;
		if((id=uniqueId.getAndIncrement())>=99){
			uniqueId.set(0);
		}
		return DateUtil.DateToString(new Date(), "yyyyMMdd")+(System.currentTimeMillis()+"").substring(3);
	}
	/**
	 * @param args
	 */
	public static void main(String[] args){
		String str = wxCouponOrder();
		System.out.println(str);
		System.out.println(str.length());
		if(str.length() > 18){
			str = str.substring(0, 18);
		}else if(str.length() < 18){
			for(int i=0;i<(18-str.length());i++){
				str += "0";
			}
		}
		System.out.println(str);
		System.out.println(str.length());
		
		System.out.println("1433585402201701201848871003".length());
		System.out.println((System.currentTimeMillis()+"").substring(3));
	}
}
