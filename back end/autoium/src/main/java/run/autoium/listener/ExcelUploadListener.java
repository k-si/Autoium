package run.autoium.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import run.autoium.entity.po.ApiCase;
import run.autoium.entity.po.ApiCaseSuite;
import run.autoium.entity.po.Project;
import run.autoium.entity.vo.ApiCaseVo;
import run.autoium.service.ApiCaseManageService;
import run.autoium.service.ApiCaseSuiteService;
import run.autoium.service.ProjectService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ExcelUploadListener extends AnalysisEventListener<ApiCaseVo> {

    private ApiCaseManageService apiCaseManageService = null;


    private ProjectService projectService = null;


    private ApiCaseSuiteService apiCaseSuiteService = null;

    private List<Project> projectList = null;
    Map<String , String> projectIdNames = new HashMap<>();

    private List<ApiCaseSuite> suiteList = null;
    Map<String , String> suiteIdNames = new HashMap<>();


    public ExcelUploadListener() { }

    public ExcelUploadListener(ApiCaseManageService apiCaseManageService, ApiCaseSuiteService apiCaseSuiteService , ProjectService projectService) {
        this.apiCaseManageService = apiCaseManageService;
        this.projectService = projectService;
        this.apiCaseSuiteService = apiCaseSuiteService;

        projectList = projectService.list(null);
        for (Project project : projectList) {
            projectIdNames.put(project.getName() , project.getId());
        }

        suiteList = apiCaseSuiteService.list(null);
        for (ApiCaseSuite apiCaseSuite : suiteList) {
            suiteIdNames.put(apiCaseSuite.getName() , apiCaseSuite.getId());
        }
    }

    //一行行读取
    @Override
    public void invoke(ApiCaseVo apiCaseVo, AnalysisContext analysisContext) {
        ApiCase apiCase = new ApiCase();

        String name = apiCaseVo.getName();
        String projectName = apiCaseVo.getProjectName();
        String apiCaseSuiteName = apiCaseVo.getApiCaseSuiteName();
        String host = apiCaseVo.getHost();
        String path = apiCaseVo.getPath();
        String stringReqMethod = apiCaseVo.getStringReqMethod();
        String stringReqHeader = apiCaseVo.getStringReqHeader();
        String stringReqParams = apiCaseVo.getStringReqParams();
        String stringReqBodyType = apiCaseVo.getStringReqBodyType();
        String reqBodyJson = apiCaseVo.getReqBodyJson();
        String stringReqBodyForm = apiCaseVo.getStringReqBodyForm();
        String stringReqBodyFile = apiCaseVo.getStringReqBodyFile();
        String stringExamine = apiCaseVo.getStringExamine();
        String preAction = apiCaseVo.getPreAction();
        String description = apiCaseVo.getDescription();
        Integer sort = apiCaseVo.getSort();

        if(name != null){
            apiCase.setName(name);
        }
        //设置项目id
        if(projectName != null){

            if(projectIdNames.containsKey(projectName)){
                apiCase.setProjectId(projectIdNames.get(projectName));
            } else {
                apiCase.setProjectId("-1");
            }
        }

        //设置文件夹id
        if(apiCaseSuiteName != null){
            apiCase.setApiCaseSuiteId(suiteIdNames.get(apiCaseSuiteName));
        }
        //设置主机地址和路径
        if(host != null){
            apiCase.setHost(host);
        }
        //设置请求路径
        if(path != null){
            apiCase.setPath(path);
        }
        //设置请求类型
        if(stringReqMethod != null){
            String s = stringReqMethod.toLowerCase();
            if(s.contains("get")){
                apiCase.setReqMethod(0);
            } else if(s.contains("post")){
                apiCase.setReqMethod(1);
            } else if ( s.contains("put")){
                apiCase.setReqMethod(2);
            } else if(s.contains("delete")){
                apiCase.setReqMethod(3);
            }
        }
        //设置请求头
        if(stringReqHeader != null){
            apiCase.setReqHeader(stringReqHeader);
        }
        //设置请求参数
        if(stringReqParams != null){
            apiCase.setReqParams(stringReqParams);
        }
        //设置请求body的类型
        if(stringReqBodyType != null){
            String s = stringReqBodyType.toLowerCase();
            if(s.contains("json")){
                apiCase.setReqMethod(0);
            } else if(s.contains("form")){
                apiCase.setReqMethod(1);
            } else if ( s.contains("file")){
                apiCase.setReqMethod(2);
            }
        }
        //设置json请求体
        if(reqBodyJson != null){
            apiCase.setReqBodyJson(reqBodyJson);
        }
        //设置form请求体
        if(stringReqBodyForm != null){
            apiCase.setReqBodyForm(stringReqBodyForm);
        }
        //设置断言
        if(stringExamine != null){
            apiCase.setExamine(stringExamine);
        }
        //设置前置条件
        if(preAction != null){
            apiCase.setExamine(stringExamine);
        }
        //设置描述
        if(description != null){
            apiCase.setDescription(description);
        }
        //设置排序等级
        if(sort != null){
            apiCase.setSort(sort);
        }

        apiCaseManageService.save(apiCase);
    }

    //读取表头
    @Override
    public void invokeHeadMap(Map<Integer, String> headMap, AnalysisContext context) {
        super.invokeHeadMap(headMap, context);
    }

    //执行方法之后
    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }
}
