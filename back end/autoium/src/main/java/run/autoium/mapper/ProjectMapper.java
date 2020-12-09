package run.autoium.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import run.autoium.entity.po.Project;
import run.autoium.entity.vo.SimpleProjectVo;

import java.util.List;

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
