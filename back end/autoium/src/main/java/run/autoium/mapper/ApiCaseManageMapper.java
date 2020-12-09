package run.autoium.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.*;
import run.autoium.entity.po.ApiCase;
import run.autoium.entity.po.ApiCaseManage;
import run.autoium.entity.vo.ApiCaseManageVo;
import run.autoium.entity.vo.ApiCaseVo;

import java.util.List;

@Mapper
public interface ApiCaseManageMapper extends BaseMapper<ApiCaseManage> {

    public List<ApiCaseManage> getApiCasePageCondition(Page page, @Param(Constants.WRAPPER) Wrapper<ApiCaseManage> wrapper);

    public ApiCaseManage getById(@Param("apiId") String id);

    public List<ApiCaseManage> getAll();

}
