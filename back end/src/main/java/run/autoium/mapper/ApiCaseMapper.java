package run.autoium.mapper;

import org.apache.ibatis.annotations.Mapper;
import run.autoium.entity.po.ApiCase;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

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

}
