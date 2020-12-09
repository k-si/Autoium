package run.autoium.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.ibatis.annotations.Param;
import run.autoium.entity.po.ApiCase;
import run.autoium.entity.po.ApiCaseManage;
import run.autoium.entity.vo.ApiCaseManageVo;

import java.util.List;

public interface ApiCaseManageService extends IService<ApiCaseManage> {
    public List<ApiCaseManage> getApiCasePageCondition(Page<ApiCaseManage> page,
                                                  @Param("apiCaseManageVo") ApiCaseManage apiCaseManage);

    public ApiCaseManage getById(String id);

    public List<ApiCaseManage> getAll();

}
