package run.autoium.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import run.autoium.entity.po.ApiCase;
import run.autoium.entity.vo.ApiCaseManageVo;

import java.util.List;

public interface ApiCaseManageService {
    public List<ApiCaseManageVo> getApiCasePageCondition(Page<ApiCaseManageVo> page,
                                                  @Param("apiCaseManageVo") ApiCaseManageVo apiCaseManageVo);

    public ApiCaseManageVo getById(String id);

    public boolean removeById(String id);

    public boolean save(ApiCase apiCase);

    public int updateByID(ApiCase apiCase);
}
