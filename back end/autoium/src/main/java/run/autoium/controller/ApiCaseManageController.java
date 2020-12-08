package run.autoium.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import run.autoium.common.DataCode.response.R;
import run.autoium.entity.po.ApiCase;
import run.autoium.entity.vo.ApiCaseManageVo;
import run.autoium.service.ApiCaseManageService;

import java.util.List;

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
    /**
     * 条件分页查询数据
     * @param current 当前页
     * @param limit 每页大小
     * @param apiCaseManageVo 可选条件
     * @return
     */
    @PostMapping("/PageCaseCondition/{current}/{limit}")
    public R getCaseListPage(@PathVariable("current") Long current,
                             @PathVariable("limit") Long limit,
                             @RequestBody(required = false) ApiCaseManageVo apiCaseManageVo){

        Page<ApiCaseManageVo> page = new Page<>(current , limit);

        apiCaseManageService.getApiCasePageCondition(page, apiCaseManageVo);

        return R.ok().data("list" , page.getRecords()).data("total",page.getTotal());
    }


    /**
     * 根据id值获取用例
     * @param id
     * @return
     */
    @GetMapping("/getCase/{id}")
    public R getCaseById(@PathVariable("id") String id) {

        ApiCaseManageVo apiCaseManageVo = apiCaseManageService.getById(id);

        if (apiCaseManageVo != null) {
            return R.ok().message("查询成功").data("case", apiCaseManageVo);
        } else {
            return R.error().message("查询失败");
        }
    }

    /**
     * 根据id删除用例
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
     * 添加一条用例
     * @param apiCase
     * @return
     */
    @PostMapping("/addCase")
    public R addCase(@RequestBody ApiCase apiCase){

        boolean b = apiCaseManageService.save(apiCase);

        if(b == true){
            return R.ok().message("添加成功");
        }else{
            return R.error().message("添加失败");
        }
    }

    @PostMapping("/updateCase")
    public R updateCase(@RequestBody ApiCase apiCase){
        int i = apiCaseManageService.updateByID(apiCase);


        if(i > 0){
            return R.ok().message("更新成功");
        }else{
            return R.error().message("更新失败");
        }
    }
}
