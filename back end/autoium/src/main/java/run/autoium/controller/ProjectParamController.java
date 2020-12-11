package run.autoium.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import run.autoium.common.DataCode.response.R;
import run.autoium.entity.po.ProjectParam;
import run.autoium.service.impl.ProjectParamServiceImpl;

import java.util.List;

/**
 * <p>
 * 参数化 前端控制器
 * </p>
 *
 * @author Ks1r
 * @since 2020-11-28
 */
@RestController
@RequestMapping("/param")
@CrossOrigin
public class ProjectParamController {

    @Autowired
    private ProjectParamServiceImpl projectParamService;

    /**
     * 分页查询数据库中的参数
     *
     * @return
     */
    @GetMapping("/pageParam/{current}/{limit}")
    public R pageParamList(@PathVariable Long current, @PathVariable Long limit) {

        // 获取page
        Page<ProjectParam> page = new Page<>(current, limit);

        // 执行分页查询
        projectParamService.page(page, null); // 没有特殊规则

        // 获取分页结果
        long total = page.getTotal(); // 总记录数
        List<ProjectParam> records = page.getRecords(); // 当前页的数据

        return R.ok().data("total", total).data("records", records);
    }

    /**
     * 新增一个项目的参数
     *
     * @param projectParam
     * @return
     */
    @PostMapping("/addParam")
    public R addParam(@RequestBody ProjectParam projectParam) {
        boolean flag = projectParamService.save(projectParam);
        if (flag) {
            return R.ok();
        } else {
            return R.error();
        }
    }
}

