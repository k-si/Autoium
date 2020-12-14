package run.autoium.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import run.autoium.common.DataCode.response.R;
import run.autoium.entity.po.ApiCase;
import run.autoium.entity.vo.ApiCaseVo;
import run.autoium.entity.vo.SimpleApiSuiteVo;
import run.autoium.entity.vo.SimpleProjectVo;
import run.autoium.service.ApiCaseManageService;
import run.autoium.service.ApiCaseSuiteService;
import run.autoium.service.ProjectService;

import java.util.List;
import java.util.Map;

/**
 * @author lengtong
 * @since 2020-12-4
 */
@RestController
@RequestMapping("/api/manage")
@CrossOrigin
public class ApiCaseManageController {

    @Autowired
    private ApiCaseManageService apiCaseManageService;

    @Autowired
    private ProjectService projectService;

    @Autowired
    private ApiCaseSuiteService apiCaseSuiteService;


    /**
     * 条件分页查询数据
     *
     * @param current   当前页
     * @param limit     每页大小
     * @param apiCaseVo 可选条件
     * @return
     */
    @PostMapping("/PageCaseCondition/{current}/{limit}")
    public R getCaseListPage(@PathVariable("current") Long current,
                             @PathVariable("limit") Long limit,
                             @RequestBody(required = false) ApiCaseVo apiCaseVo) {

        Page<ApiCaseVo> page = new Page<>(current, limit);

        apiCaseManageService.getApiCasePageCondition(page, apiCaseVo);

        return R.ok().data("list", page.getRecords()).data("total", page.getTotal());
    }


    /**
     * 根据id值获取用例
     *
     * @param id
     * @return
     */
    @GetMapping("/getCase/{id}")
    public R getCaseById(@PathVariable("id") String id) {

        ApiCaseVo apiCaseVo = apiCaseManageService.getById(id);

        if (apiCaseVo != null) {
            return R.ok().message("查询成功").data("case", apiCaseVo);
        } else {
            return R.error().message("查询失败");
        }
    }

    @GetMapping("/getAll")
    public R getAll() {
        List<ApiCaseVo> list = apiCaseManageService.getAll();
        return R.ok().data("list", list).data("total", list.size());
    }

    /**
     * 根据id删除用例
     *
     * @param id
     * @return
     */
    @DeleteMapping("/delete/{id}")
    public R deleteApiCaseSuiteById(@PathVariable("id") String id) {

        boolean b = apiCaseManageService.removeById(id);

        if (b == true) {
            return R.ok().message("删除成功");
        } else {
            return R.error().message("删除失败");
        }
    }

    /**
     * 多选删除接口
     *
     * @param param
     * @return
     */
    @PostMapping("/delete")
    public R deleteApiCaseSuites(@RequestBody Map<Object, Object> param) {

        List<String> delArr = (List<String>) param.get("delArr");
        boolean b = apiCaseManageService.removeByIds(delArr);

        if (b == true) {
            return R.ok().message("删除成功");
        } else {
            return R.error().message("删除失败");
        }
    }

    /**
     * 添加一条用例
     *
     * @param apiCase
     * @return
     */
    @PostMapping("/addCase")
    public R addCase(@RequestBody ApiCase apiCase) {

        boolean b = apiCaseManageService.save(apiCase);

        if (b == true) {
            return R.ok().message("添加成功");
        } else {
            return R.error().message("添加失败");
        }
    }

    /**
     * 根据id更新
     *
     * @param apiCase
     * @return
     */
    @PostMapping("/updateCase")
    public R updateCase(@RequestBody ApiCase apiCase) {
        boolean b = apiCaseManageService.updateById(apiCase);


        if (b == true) {
            return R.ok().message("更新成功");
        } else {
            return R.error().message("更新失败");
        }
    }


    @GetMapping("/getAllProject")
    public R getAllProject() {

        List<SimpleProjectVo> list = projectService.getAllName();

        return R.ok().data("list", list);
    }

    @GetMapping("/getAllSuite")
    public R getAllSuite() {

        List<SimpleApiSuiteVo> list = apiCaseSuiteService.getAllName();

        return R.ok().data("list", list);
    }

    @PostMapping("/upload")
    public R uploadCasesByExcel(@RequestParam("file") MultipartFile file) {

        apiCaseManageService.uploadCaseByExcel(file, this.apiCaseManageService, this.apiCaseSuiteService, this.projectService);

        return R.ok();
    }

}
