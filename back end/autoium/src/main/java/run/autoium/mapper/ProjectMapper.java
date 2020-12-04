package run.autoium.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import run.autoium.entity.po.Project;

/**
 * <p>
 * 项目 Mapper 接口
 * </p>
 *
 * @author Ks1r
 * @since 2020-11-28
 */
@Mapper
public interface ProjectMapper extends BaseMapper<Project> {

}
