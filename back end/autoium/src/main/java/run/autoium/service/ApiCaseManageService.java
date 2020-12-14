package run.autoium.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.multipart.MultipartFile;
import run.autoium.entity.po.ApiCase;
import run.autoium.entity.po.ApiCaseManage;
import run.autoium.entity.vo.ApiCaseVo;

import java.util.List;

public interface ApiCaseManageService extends IService<ApiCase> {

    List<ApiCaseVo> getApiCasePageCondition(Page<ApiCaseVo> page, @Param("apiCaseManageVo") ApiCaseVo apiCaseVo);

    ApiCaseVo getById(String id);

    List<ApiCaseVo> getAll();


    void uploadCaseByExcel(MultipartFile file,
                           ApiCaseManageService apiCaseManageService,
                           ApiCaseSuiteService apiCaseSuiteService,
                           ProjectService projectService);

}
