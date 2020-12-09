package run.autoium.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import run.autoium.entity.po.Project;
import run.autoium.entity.vo.SimpleProjectVo;
import run.autoium.mapper.ProjectMapper;
import run.autoium.service.ProjectService;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 项目 服务实现类
 * </p>
 *
 * @author Ks1r
 * @since 2020-11-28
 */
@Service
public class ProjectServiceImpl extends ServiceImpl<ProjectMapper, Project> implements ProjectService {

    @Autowired
    private ProjectMapper projectMapper;

    @Override
    public List<SimpleProjectVo> getAllName() {
        List<Project> projects = projectMapper.selectList(null);

        List<SimpleProjectVo> list = new ArrayList<>();

        for (Project project : projects) {
            SimpleProjectVo simpleProjectVo = new SimpleProjectVo();

            simpleProjectVo.setId(project.getId());
            simpleProjectVo.setName(project.getName());

            list.add(simpleProjectVo);
        }

        return list;
    }
}
