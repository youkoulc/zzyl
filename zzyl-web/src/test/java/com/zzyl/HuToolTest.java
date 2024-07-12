package com.zzyl;

import cn.hutool.core.date.LocalDateTimeUtil;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import cn.hutool.http.Method;
import cn.hutool.json.JSONUtil;
import com.alibaba.druid.sql.visitor.SQLASTOutputVisitor;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description HuToolTest
 * @Author HeFeng
 * @Date 2024-07-11
 */
public class HuToolTest {
    // 远程调用百度获取响应体
    @Test
    public void testGet1() {
        String responseBody = HttpUtil.get("https://www.baidu.com");
        System.out.println(responseBody);
    }

    @Test
    public void testGet2() {
        String url = "http://localhost:9995/nursing_project";
        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("pageNum", 1);
        paramMap.put("pageSize", 10);
        // 获取响应对象（包含响应行、头、体）
        HttpResponse response = HttpUtil.createRequest(Method.GET, url).form(paramMap).execute();
        if (response.getStatus() == 200) {
            System.out.println("response.body() = " + response.body());
        }
    }

    @Test
    public void testGet3() {
        String url = "http://localhost:9995/nursing_project";
        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("pageNum", 1);
        paramMap.put("pageSize", 10);

        String response = HttpUtil.get(url, paramMap);
        System.out.println("response = " + response);
    }

    @Test
    public void testPost() {
        String url = "http://localhost:9995/nursing_project";
        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("name", "护理项目测试");
        paramMap.put("orderNo", 1);
        paramMap.put("unit", "次");
        paramMap.put("price", 10.00);
        paramMap.put("image", "https://yjy-slwl-oss.oss-cn-hangzhou.aliyuncs.com/ae7cf766-fb7b-49ff-a73c-c86c25f280e1.png");
        paramMap.put("nursingRequirement", "无特殊要求");
        paramMap.put("status", 1);
        String result = HttpUtil.post(url, JSONUtil.toJsonStr(paramMap));
        System.out.println(result);
    }

    @Test
    public void testPost2() {
        String url = "http://localhost:9995/nursing_project";
        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("name", "护理项目测试2");
        paramMap.put("orderNo", 1);
        paramMap.put("unit", "次");
        paramMap.put("price", 10.00);
        paramMap.put("image", "https://yjy-slwl-oss.oss-cn-hangzhou.aliyuncs.com/ae7cf766-fb7b-49ff-a73c-c86c25f280e1.png");
        paramMap.put("nursingRequirement", "无特殊要求");
        paramMap.put("status", 1);
        HttpResponse response = HttpUtil.createRequest(Method.POST, url)
                .body(JSONUtil.toJsonStr(paramMap))
                .execute();
        if (response.getStatus() == 200) {
            System.out.println(response.body());
        }
    }

    @Test
    public void testmj() {
        String host = "https://aliv18.data.moji.com";
        String path = "/whapi/json/alicityweather/forecast24hours";
        String method = "POST";
        String appcode = "9e425b4330a74ff593ee9afea9c01dfa";
        Map<String, String> headers = new HashMap<String, String>();
        //最后在header中的格式(中间是英文空格)为Authorization:APPCODE 83359fd73fe94948385f570e3c139105
        headers.put("Authorization", "APPCODE " + appcode);
        //根据API的要求，定义相对应的Content-Type
        headers.put("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");

        Map<String, Object> bodys = new HashMap<String, Object>();
        bodys.put("cityId", "2");

        HttpResponse response = HttpUtil.createRequest(Method.POST, host + path).headerMap(headers, true).form(bodys).execute();
        System.out.println("response = " + response.body());
    }

    @Test
    public void testkd() {
        String host = "https://lhkdcx.market.alicloudapi.com";
        String path = "/express/query";
        String method = "POST";
        String appcode = "9e425b4330a74ff593ee9afea9c01dfa";
        Map<String, String> headers = new HashMap<String, String>();
        //最后在header中的格式(中间是英文空格)为Authorization:APPCODE 83359fd73fe94948385f570e3c139105
        headers.put("Authorization", "APPCODE " + appcode);
        //根据API的要求，定义相对应的Content-Type
        headers.put("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");

        Map<String, String> bodys = new HashMap<String, String>();
        // bodys.put("expressNo", "expressNo");
        bodys.put("waybillNo", "75616046538073");
        // bodys.put("mobile", "mobile");

        HttpResponse response = HttpUtil.createRequest(Method.POST, host + path).headerMap(headers, true).formStr(bodys).execute();
        System.out.println("response.body() = " + response.body());
    }

    @Test

    public void testTime() {
        Long time = 1720800000000L;
        LocalDateTime localDateTime = LocalDateTimeUtil.of(time);
        System.out.println("localDateTime = " + localDateTime);
        List<LocalDateTime> list=new ArrayList<>();
        for (int i = 0; i <= 48; i++) {
            LocalDateTime newTime = localDateTime.plusMinutes(30 * i);
            list.add(newTime);
        }
        System.out.println("list = " + list);
    }
}
