package run.autoium.utils;

import com.alibaba.fastjson.JSONObject;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import run.autoium.common.DataCode.response.BodyType;
import run.autoium.entity.MyHeader;
import run.autoium.entity.vo.ApiCaseResultVo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 封装httpClient的各种请求操作
 */
@Component
public class HttpClientDriver {

    @Autowired
    private CloseableHttpClient client;

    @Autowired
    private RequestConfig config;

    /**
     * 普通的get请求，不设置任何超时限制。参数在前端
     * 中填入url
     *
     * @param url 请求地址
     * @return 响应的内容
     */
    public ApiCaseResultVo doCommonGet(String url, Map<String, String> header) {
        CloseableHttpResponse response = null;
        HttpEntity entity = null;

        // 向前端传输的调试结果对象
        ApiCaseResultVo resultVo = new ApiCaseResultVo();
        try {
            HttpGet get = new HttpGet(url);
            if (header != null) {
                for (Map.Entry<String, String> entry : header.entrySet()) {
                    get.setHeader(entry.getKey(), entry.getValue());
                }
            }
            response = client.execute(get);
            entity = response.getEntity();
            byte[] bytes = EntityUtils.toByteArray(entity);

            // 设置vo对象的响应码，响应头，响应体，响应体长度，响应体格式
            resultVo.setStatusCode(response.getStatusLine().getStatusCode());
            List<MyHeader> headerList = new ArrayList<>();
            for (Header h : response.getAllHeaders()) {
                MyHeader myHeader = new MyHeader();
                myHeader.setKey(h.getName());
                myHeader.setValue(h.getValue());
                headerList.add(myHeader);
            }
            resultVo.setHeaders(headerList);
            resultVo.setBody(new String(bytes));
            resultVo.setRespBodySize(bytes.length);
            resultVo.setBodyType(StringUtils.checkStringFormat(entity.getContentType().getValue()));
        } catch (IOException e) {
            e.printStackTrace();
            resultVo.setBodyType(BodyType.NOBODY);
            resultVo.setException(e.getMessage());
            return resultVo;
        } finally {
            closeResources(response, entity);
        }
        return resultVo;
    }

    /**
     * 发送请求体为json格式的post请求，不带任何限时
     *
     * @param url    请求地址
     * @param header 请求头
     * @param json   请求体
     * @return 响应内容
     */
    public String doCommonPostByJson(String url, Map<String, String> header, JSONObject json) {
        CloseableHttpResponse response = null;
        HttpEntity entity = null;
        try {
            HttpPost post = new HttpPost(url);
            if (header != null) {
                for (Map.Entry<String, String> entry : header.entrySet()) {
                    post.setHeader(entry.getKey(), entry.getValue());
                }
            }
            if (json != null) {
                HttpEntity body = new StringEntity(json.toJSONString());
                post.setEntity(body);
            }
            response = client.execute(post);
            entity = response.getEntity();
            return EntityUtils.toString(entity, "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            closeResources(response, entity);
        }
        return null;
    }

    /**
     * 发送请求体为form格式的请求，不限制超时
     *
     * @param url    请求地址
     * @param header 请求头
     * @param form   请求体
     * @return 响应的数据
     */
    public String doCommonPostByForm(String url, Map<String, String> header, Map<String, String> form) {
        CloseableHttpResponse response = null;
        HttpEntity entity = null;
        try {
            HttpPost post = new HttpPost(url);
            if (header != null) {
                for (Map.Entry<String, String> entry : header.entrySet()) {
                    post.setHeader(entry.getKey(), entry.getValue());
                }
            }
            if (form != null) {
                List<NameValuePair> nvps = new ArrayList<>();
                for (Map.Entry<String, String> entry : form.entrySet()) {
                    nvps.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
                }
                post.setEntity(new UrlEncodedFormEntity(nvps));
            }
            response = client.execute(post);
            entity = response.getEntity();
            return EntityUtils.toString(entity, "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            closeResources(response, entity);
        }
        return null;
    }

    /**
     * 关闭资源
     *
     * @param response CloseableHttpResponse
     * @param entity   HttpEntity
     */
    private void closeResources(CloseableHttpResponse response, HttpEntity entity) {
        try {
            if (entity != null)
                EntityUtils.consume(entity);
            if (response != null)
                response.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
