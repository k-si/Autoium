package run.autoium.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import run.autoium.common.DataCode.MethodType;
import run.autoium.common.DataCode.request.BodyType;
import run.autoium.common.DataCode.response.ApiCaseStatus;
import run.autoium.entity.MyAssert;
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
import run.autoium.utils.AToBUtils;
import run.autoium.utils.AssertUtils;
import run.autoium.utils.HttpClientDriver;

import java.util.ArrayList;
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

    @Autowired
    private ApiCaseMapper apiCaseMapper;

    /**
     * 简单保存一条用例
     *
     * @param apiCase 用例对象
     * @return 插入的数据大于0条，就认为插入成功
     */
    public Boolean simpleSave(ApiCase apiCase) {
        apiCase.setReqMethod(0);
        int insert = apiCaseMapper.insert(apiCase);
        return insert > 0;
    }

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

        // 存储请求体类型，没有请求体时默认为1
        Integer reqBodyType = vo.getReqBodyType();
        api.setReqBodyType(reqBodyType);

        // 存储json请求体
        api.setReqBodyJson(vo.getReqBodyJson());

        // 存储form请求体
        List<MyParams> voForm = vo.getReqBodyForm();
        String poForm = JSON.toJSONString(voForm);
        api.setReqBodyForm(poForm);

        // 存储断言
        List<MyAssert> voExamine = vo.getExamine();
        if (voExamine != null) {
            String poExamine = JSON.toJSONString(voExamine);
            api.setExamine(poExamine);
        }

        // 存储用例状态，是否被执行
        api.setStatus(vo.getFinish());

        // 存储用例描述
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
                    apiCaseVo.setMethod(apiCase.getReqMethod());

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

        // 填充请求体类型
        Integer reqBodyType = apiCase.getReqBodyType();
        apiCaseVo.setReqBodyType(reqBodyType);

        // 填充json
        String json = apiCase.getReqBodyJson();
        if (!StringUtils.isEmpty(json)) {
            apiCaseVo.setReqBodyJson(json);
        }

        // 填充form
        String form = apiCase.getReqBodyForm();
        if (!StringUtils.isEmpty(form)) {
            List<MyParams> myParams = AToBUtils.jsonToList(form, MyParams.class);
            apiCaseVo.setReqBodyForm(myParams);
        }

        // 数据库中断言以json的格式存储，需要转回成对象
        String reqExamine = apiCase.getExamine();
        if (!StringUtils.isEmpty(reqExamine)) {
            List<MyAssert> myAsserts = AToBUtils.examineToList(reqExamine);
            apiCaseVo.setExamine(myAsserts);
        }

        // 填充是否执行
        apiCaseVo.setFinish(apiCase.getStatus());

        // 填充描述
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
        Integer bodyType = apiCase.getReqBodyType();
        String url = apiCase.getHost() + apiCase.getPath();
        Map<String, String> headers = AToBUtils.listToMap(apiCase.getReqHeader(), MyHeader.class);
        ApiCaseResultVo apiCaseResult = null;

        // get请求
        if (method.equals(MethodType.GET)) {
            apiCaseResult = driver.doCommonGet(url, headers);

            // 执行断言，并将断言结果填入数据库
            apiCaseResult.setAssertResult(apiCase.getExamine());
            boolean res = AssertUtils.executeAsserts(apiCaseResult);
            apiCaseMapper.updateStatusById(String.valueOf(res ? ApiCaseStatus.success : ApiCaseStatus.failed), apiCase.getId());
        }

        // post请求
        else if ((method.equals(MethodType.POST))) {
            if ((bodyType.equals(BodyType.JSON))) {

                // body为json格式
                apiCaseResult = driver.doCommonPostByJson(url, headers, JSON.parseObject(apiCase.getReqBodyJson()));
            } else if ((bodyType.equals(BodyType.FORM))) {

                // body为form格式
                apiCaseResult = driver.doCommonPostByForm(url, headers, AToBUtils.listToMap(apiCase.getReqBodyForm(), MyParams.class));
            } else if ((bodyType.equals(BodyType.FILE))) {

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
