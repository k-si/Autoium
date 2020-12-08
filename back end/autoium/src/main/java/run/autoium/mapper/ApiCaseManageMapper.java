package run.autoium.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import run.autoium.entity.po.ApiCase;
import run.autoium.entity.vo.ApiCaseManageVo;
import run.autoium.entity.vo.ApiCaseVo;

import java.util.List;

@Mapper
public interface ApiCaseManageMapper extends BaseMapper<ApiCaseVo> {

    public List<ApiCaseManageVo> getApiCasePageCondition(Page page, @Param("apiCaseManageVo") ApiCaseManageVo apiCaseManageVo);

    public ApiCaseManageVo getById(@Param("apiId") String id);

    public boolean removeById(@Param("apiId") String id);

    public boolean save(@Param("apiCase") ApiCase apiCase);

    public int updateByID(@Param("apiCase") ApiCase apiCase);
}
