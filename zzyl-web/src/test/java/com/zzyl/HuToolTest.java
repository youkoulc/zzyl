package com.zzyl;

import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import cn.hutool.http.Method;
import cn.hutool.json.JSONUtil;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

/**
 * @Description HuToolTest
 * @Author HeFeng
 * @Date 2024-07-11
 */
public class HuToolTest {
    // 远程调用百度获取响应体
    @Test
    public void testGet1(){
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
    public void testPost(){
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
    public void testPost2(){
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
        if(response.getStatus() == 200){
            System.out.println(response.body());
        }
    }
}
