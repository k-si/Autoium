package run.autoium.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONPObject;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.util.StringUtils;
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
import run.autoium.utils.AToBUtils;
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
     * 保存详细的接口信息
     *
     * @param vo
     * @return
     */
    public Boolean detailSave(ApiCaseVo vo) {
        ApiCase api = new ApiCase();

        // 将vo信息传递给po
        api.setId(vo.getId());
        api.setHost(vo.getHost());
        api.setPath(vo.getPath());
        api.setReqMethod(vo.getReqMethod());

        // vo中的header是list类型，在po中以json格式存储
        List<MyHeader> voHeader = vo.getReqHeader();
        String poHeader = JSON.toJSONString(voHeader);
        api.setReqHeader(poHeader);

        // vo中的param是list类型，在po中以json格式存储
        List<MyParams> voParams = vo.getReqParams();
        String poParams = JSON.toJSONString(voParams);
        api.setReqParams(poParams);

        // 如果是GET请求则不设置请求体
        if (vo.getReqMethod().equals(MethodType.GET)) {

            // 请求body的类型 0 json、1 form、2 file
            api.setReqBodyType(vo.getReqBodyType());
            switch (vo.getReqBodyType()) {
                case 0:
                    api.setReqBodyJson(vo.getReqBodyJson());
                    break;
                case 1:

                    // vo中的form数据是list类型，在po中以json格式存储
                    List<MyParams> voForm = vo.getReqBodyForm();
                    String poForm = JSON.toJSONString(voForm);
                    api.setReqBodyForm(poForm);
            }
        }

        api.setDescription(vo.getDescription());

        return apiCaseService.updateById(api);
    }

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
        for (ApiCaseSuite suite : apiCaseSuiteList) {
            SimpleApiSuiteVo apiSuiteVo = new SimpleApiSuiteVo();
            apiSuiteVo.setId(suite.getId());
            apiSuiteVo.setLabel(suite.getName());
            apiSuiteVo.setValue(suite.getName());
            apiSuiteVo.setButtonable(true);

            List<SimpleApiCaseVo> apiCaseVoList = new ArrayList<>();

            // 遍历所有用例，抽取属于同一个文件夹的用例
            for (ApiCase apiCase : apiCaseList) {
                if (apiCase.getApiCaseSuiteId().equals(suite.getId())) {
                    SimpleApiCaseVo apiCaseVo = new SimpleApiCaseVo();
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
     * 显示用例详细信息，通过id获取
     *
     * @return
     */
    public ApiCaseVo getApiCaseById(Long id) {
        ApiCase apiCase = apiCaseService.getById(id);

        ApiCaseVo apiCaseVo = new ApiCaseVo();
        apiCaseVo.setHost(apiCase.getHost());
        apiCaseVo.setPath(apiCase.getPath());
        apiCaseVo.setReqMethod(apiCase.getReqMethod());

        // 数据库中header以json的格式存储，需要转回成对象
        String reqHeader = apiCase.getReqHeader();
        if (!StringUtils.isEmpty(reqHeader)) {
            List<MyHeader> myHeaders = AToBUtils.jsonToList(reqHeader, MyHeader.class);
            apiCaseVo.setReqHeader(myHeaders);
        }

        // 数据库中param以json的格式存储，需要转回成对象
        String reqParams = apiCase.getReqParams();
        if (!StringUtils.isEmpty(reqParams)) {
            List<MyParams> myParams = AToBUtils.jsonToList(reqParams, MyParams.class);
            apiCaseVo.setReqParams(myParams);
        }

        // 判断请求体类型
        if (apiCase.getReqBodyType() != null) {

            // 填充json
            String json = apiCase.getReqBodyJson();
            apiCaseVo.setReqBodyJson(json);

            // 填充form
            String form = apiCase.getReqBodyForm();
            List<MyParams> myParams = AToBUtils.jsonToList(form, MyParams.class);
            apiCaseVo.setReqBodyForm(myParams);
        }

        apiCaseVo.setDescription(apiCase.getDescription());

        return apiCaseVo;
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
        Map<String, String> headers = AToBUtils.listToMap(apiCase.getReqHeader(), MyHeader.class);
        ApiCaseResultVo apiCaseResult = null;

        // get请求
        if (method.equals(MethodType.GET)) {
            apiCaseResult = driver.doCommonGet(url, headers);
        }

        // post请求
        else if ((method.equals(MethodType.POST))) {
            if ((apiCase.getReqBodyType().equals(BodyType.JSON))) {

                // body为json格式
                driver.doCommonPostByJson(url, headers, JSON.parseObject(apiCase.getReqBodyJson()));
            } else if ((method.equals(BodyType.FORM))) {

                // body为form格式
                driver.doCommonPostByForm(url, headers, AToBUtils.listToMap(apiCase.getReqBodyForm(), MyParams.class));
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
