package run.autoium.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.*;
import run.autoium.entity.po.ApiCase;
import run.autoium.entity.vo.ApiCaseVo;

import java.util.List;

@Mapper
public interface ApiCaseManageMapper extends BaseMapper<ApiCase> {

    List<ApiCaseVo> getApiCasePageCondition(Page page, @Param("condition") ApiCaseVo apiCaseVo);

    ApiCaseVo getById(@Param("id") String id);

    List<ApiCaseVo> getAll();

}
