package com.zhuxueup.common.pay;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;

import org.apache.log4j.Logger;

public class SignUtil {

	private static Logger logger = Logger.getLogger(SignUtil.class);
    /**
     * @Description：sign签名
     * @param characterEncoding 编码格式
     * @param parameters 请求参数
     * @return
     */
    public static String createSign(String characterEncoding, SortedMap<String,String> parameters){
        StringBuffer sb = new StringBuffer();
        Set es = parameters.entrySet();
        Iterator it = es.iterator();
        while(it.hasNext()) {
            Map.Entry entry = (Map.Entry)it.next();
            String k = (String)entry.getKey();
            Object v = entry.getValue();
            if(null != v && !"".equals(v)
                    && !"sign".equals(k) && !"key".equals(k)) {
                sb.append(k + "=" + v + "&");
            }
        }
        sb.append("key=" + WXConstants.WX_KEY);
        logger.info("请求参数-----------"+sb.toString());
        String sign = MD5Util.MD5Encode(sb.toString(), characterEncoding).toUpperCase();
        return sign;
    }
}
