package run.autoium.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import run.autoium.entity.po.ApiCase;

/**
 * <p>
 * 接口用例 Mapper 接口
 * </p>
 *
 * @author Ks1r
 * @since 2020-11-28
 */
@Mapper
public interface ApiCaseMapper extends BaseMapper<ApiCase> {
    
    int deleteBySuiteId(@Param("suiteId") String id);

    int updateStatusById(@Param("status") String status, @Param("id") String id);
}
