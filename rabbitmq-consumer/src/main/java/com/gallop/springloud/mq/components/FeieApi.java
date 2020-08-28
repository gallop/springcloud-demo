package com.gallop.springloud.mq.components;

import com.gallop.common.utils.FeieResult;
import com.gallop.springloud.mq.dto.TakeoutPrinter;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

/**
 * author gallop
 * date 2020-06-03 22:22
 * Description:
 * Modified By:
 */
@Component
@Order(-50)
public class FeieApi {
    public static final String URL = "http://localhost:9700/feieapi/print";//不需要修改

    public static final String USER = "feie_user";//*必填*：账号名
    public static final String UKEY = "key_test";//*必填*: 飞鹅云后台注册账号后生成的UKEY 【备注：这不是填打印机的KEY】
    //public static final String SN = "122222229";
    @Autowired
    private RestTemplate feieRestTemplate;

    public String printerAdd(TakeoutPrinter printer){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        MultiValueMap<String, Object> map= new LinkedMultiValueMap<>();
        map.add("user",USER);
        String STIME = String.valueOf(System.currentTimeMillis()/1000);
        map.add("stime",STIME);
        map.add("sig",signature(USER,UKEY,STIME));
        map.add("apiname","Open_printerAddlist");//固定值,不需要修改
        StringBuffer strBuffer = new StringBuffer();
        strBuffer.append(printer.getSn());
        strBuffer.append("#");
        strBuffer.append(printer.getKey());
        strBuffer.append("#");
        strBuffer.append(printer.getRemark());
        strBuffer.append("#");
        if(printer.getCarnum() != null){
            strBuffer.append(printer.getCarnum());
        }
        String printerContent = strBuffer.toString();
        if(printerContent.endsWith("#"))
            printerContent = printerContent.substring(0,printerContent.lastIndexOf("#"));
        map.add("printerContent",printerContent);

        System.err.println("printerContent:"+printerContent);

        /*HttpEntity<MultiValueMap<String, Object>> request = new HttpEntity<>(map, headers);
        ResponseEntity<String> response = feieRestTemplate.postForEntity(URL, request , String.class );
        System.err.println(response.getBody());
        return response.getBody();*/
        return "";

    }

    public FeieResult printMsg(String sn, String content, Integer times){
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        MultiValueMap<String, Object> map= new LinkedMultiValueMap<>();
        map.add("user",USER);
        String STIME = String.valueOf(System.currentTimeMillis()/1000);
        map.add("stime",STIME);
        map.add("sig",signature(USER,UKEY,STIME));
        map.add("apiname","Open_printMsg");//固定值,不需要修改
        map.add("sn",sn);
        map.add("content",content);
        map.add("times",String.valueOf(times));

        HttpEntity<MultiValueMap<String, Object>> request = new HttpEntity<>(map, headers);
        FeieResult result = restTemplate.postForObject(URL, request , FeieResult.class );
        System.err.println(result.getMsg());
        return result;
    }

    //生成签名字符串
    private static String signature(String USER,String UKEY,String STIME){
        String s = DigestUtils.sha1Hex(USER+UKEY+STIME);
        return s;
    }

    public static void main(String[] args) {

        FeieApi api = new FeieApi();
        TakeoutPrinter printer = new TakeoutPrinter();
        printer.setSn("hty001");
        printer.setKey("79800kh");
        printer.setRemark("大耳朵甜品铺");

        api.printerAdd(printer);
    }
}
