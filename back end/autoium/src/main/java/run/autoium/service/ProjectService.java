package run.autoium.service;

import com.baomidou.mybatisplus.extension.service.IService;
import run.autoium.entity.po.Project;
import run.autoium.entity.vo.SimpleProjectVo;

import java.util.List;

/**
 * <p>
 * 项目 服务类
 * </p>
 *
 * @author Ks1r
 * @since 2020-11-28
 */
public interface ProjectService extends IService<Project> {

    public List<SimpleProjectVo> getAllName();
}
