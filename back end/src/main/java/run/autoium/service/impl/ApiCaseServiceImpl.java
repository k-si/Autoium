package run.autoium.service.impl;

import com.alibaba.fastjson.JSON;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import run.autoium.common.DataCode.request.BodyType;
import run.autoium.common.DataCode.MethodType;
import run.autoium.entity.MyHeader;
import run.autoium.entity.MyParams;
import run.autoium.entity.po.ApiCase;
import run.autoium.entity.po.ApiCaseSuite;
import run.autoium.entity.vo.ApiCaseResultVo;
import run.autoium.entity.vo.ApiCaseVo;
import run.autoium.entity.vo.SimpleApiCaseVo;
import run.autoium.entity.vo.SimpleApiSuiteVo;
import run.autoium.mapper.ApiCaseMapper;
import run.autoium.service.ApiCaseService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import run.autoium.utils.HttpClientDriver;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 接口用例 服务实现类
 * </p>
 *
 * @author Ks1r
 * @since 2020-11-28
 */
@Service
public class ApiCaseServiceImpl extends ServiceImpl<ApiCaseMapper, ApiCase> implements ApiCaseService {

    @Autowired
    private HttpClientDriver driver;

    @Autowired
    private ApiCaseService apiCaseService;

    @Autowired
    private ApiCaseSuiteServiceImpl apiCaseSuiteService;

    /**
     * 获取所有的apiSuite以及内部的api
     *
     * @return
     */
    public List<SimpleApiSuiteVo> getAll() {
        List<SimpleApiSuiteVo> list = new ArrayList<>();

        // 查询出所有的文件夹及用例
        List<ApiCaseSuite> apiCaseSuiteList = apiCaseSuiteService.list(null);
        List<ApiCase> apiCaseList = apiCaseService.list(null);

        // 遍历文件夹
        for (int i = 0; i < apiCaseSuiteList.size(); i++) {
            ApiCaseSuite suite = apiCaseSuiteList.get(i);
            SimpleApiSuiteVo apiSuiteVo = new SimpleApiSuiteVo();
//            BeanUtils.copyProperties(suite, apiSuiteVo);
            apiSuiteVo.setId(suite.getId());
            apiSuiteVo.setLabel(suite.getName());
            apiSuiteVo.setValue(suite.getName());
            apiSuiteVo.setButtonable(true);

            List<SimpleApiCaseVo> apiCaseVoList = new ArrayList<>();

            // 遍历所有用例，抽取属于同一个文件夹的用例
            for (int j = 0; j < apiCaseList.size(); j++) {
                ApiCase apiCase = apiCaseList.get(j);
                if (apiCase.getApiCaseSuiteId().equals(suite.getId())) {
                    SimpleApiCaseVo apiCaseVo = new SimpleApiCaseVo();
//                    BeanUtils.copyProperties(apiCase, apiCaseVo);
                    apiCaseVo.setId(apiCase.getId());
                    apiCaseVo.setLabel(apiCase.getName());
                    apiCaseVo.setButtonable(false);
                    apiCaseVoList.add(apiCaseVo);
                }
            }
            apiSuiteVo.setChildren(apiCaseVoList);
            list.add(apiSuiteVo);
        }

        return list;
    }

    /**
     * 通过id获取api信息
     *
     * @return
     */
    public ApiCaseVo getApiCaseById(Long id) {
        ApiCase apiCase = apiCaseService.getById(id);
        // todo...
        ApiCaseVo apiCaseVo = new ApiCaseVo();
        apiCaseVo.setHost(apiCase.getHost());
        apiCaseVo.setPath(apiCase.getPath());
        apiCaseVo.setReqMethod(apiCase.getReqMethod());
        apiCaseVo.setDescription(apiCase.getDescription());
//        BeanUtils.copyProperties(apiCase, apiCaseVo);
        return apiCaseVo;
    }

    /**
     * 将header转为map
     *
     * @param headers
     * @return
     */
    private Map<String, String> headerToMap(List<MyHeader> headers) {
        Map<String, String> map = new HashMap<>();
        for (MyHeader header : headers) {
            map.put(header.getKey(), header.getValue());
        }
        return map;
    }

    private Map<String, String> formToMap(List<MyParams> params) {
        Map<String, String> map = new HashMap<>();
        for (MyParams param : params) {
            map.put(param.getKey(), param.getValue());
        }
        return map;
    }

    /**
     * 执行接口测试
     *
     * @param apiCase
     * @return
     */
    public ApiCaseResultVo executeApi(ApiCaseVo apiCase) {
        Integer method = apiCase.getReqMethod();
        String url = apiCase.getHost() + apiCase.getPath();
        Map<String, String> headers = headerToMap(apiCase.getReqHeader());
        ApiCaseResultVo apiCaseResult = null;

        if (method.equals(MethodType.GET)) { // get请求
            apiCaseResult = driver.doCommonGet(url, headers);
        } else if ((method.equals(MethodType.POST))) { // post请求
            if ((apiCase.getReqBodyType().equals(BodyType.JSON))) {

                // body为json格式
                driver.doCommonPostByJson(url, headers, JSON.parseObject(apiCase.getReqBodyJson()));
            } else if ((method.equals(BodyType.FORM))) {

                // body为form格式
                driver.doCommonPostByForm(url, headers, formToMap(apiCase.getReqBodyForm()));
            } else if ((method.equals(BodyType.FILE))) {
                // body为file格式
                // todo...
            }
        } else if ((apiCase.getReqBodyType().equals(MethodType.PUT))) { // PUT请求
            // todo...
        } else { // DELETE请求
            // todo...
        }
        return apiCaseResult;
    }


}
