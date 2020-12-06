package run.autoium.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import run.autoium.common.DataCode.response.R;
import run.autoium.entity.po.ApiCase;
import run.autoium.entity.vo.ApiCaseVo;
import run.autoium.service.ApiCaseService;
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
    private ApiCaseService apiCaseService;

    /**
     * 条件分页查询数据
     * @param current 当前页
     * @param limit 每页大小
     * @param apiCaseVo 可选条件
     * @return
     */
    @PostMapping("/PageCaseCondition/{current}/{limit}")
    public R getCaseListPage(@PathVariable("current") Long current,
                             @PathVariable("limit") Long limit,
                             @RequestBody(required = false) ApiCaseVo apiCaseVo){
        //创建分页查询
        //创建页面
        Page<ApiCase> page = new Page<>(current , limit);
        //创建查询对象的对象
        QueryWrapper<ApiCase> wrapper = new QueryWrapper<>();

        //判断条件，如果非空再去加条件，动态查询
        if(apiCaseVo != null){

            String name = apiCaseVo.getName();

            String description = apiCaseVo.getDescription();

            String gmtCreateStart = apiCaseVo.getGmtCreateStart();

            String gmtCreateEnd = apiCaseVo.getGmtCreateEnd();


            if(!Strings.isEmpty(name)){
                wrapper.like("name" , name);
            }

            if(!Strings.isEmpty(description)){
                wrapper.like("description" , description);
            }

            if(!Strings.isEmpty(gmtCreateStart)){
                wrapper.ge("gmt_create" , gmtCreateStart);
            }

            if(!Strings.isEmpty(gmtCreateEnd)){
                wrapper.le("gmt_create" , gmtCreateEnd);
            }
        }
        //排序
        wrapper.orderByDesc("gmt_create");

        //查询
        apiCaseService.page(page , wrapper);

        //获取数据
        long total = page.getTotal();
        List<ApiCase> list = page.getRecords();

        //返回
        return R.ok().data("rows" , list).data("total" , total);
    }


    /**
     * 根据id值获取用例
     * @param id
     * @return
     */
    @GetMapping("/getCase/{id}")
    public R getCaseById(@PathVariable("id") String id) {
        ApiCase apiCase = apiCaseService.getById(id);

        if (apiCase != null) {
            return R.ok().data("case", apiCase);
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

        boolean b = apiCaseService.removeById(id);

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

        boolean b = apiCaseService.save(apiCase);

        if(b == true){
            return R.ok().message("添加成功");
        }else{
            return R.error().message("添加失败");
        }
    }

    @PostMapping("/updateCase")
    public R updateCase(@RequestBody ApiCase apiCase){
        boolean b = apiCaseService.updateById(apiCase);


        if(b){
            return R.ok().message("更新成功");
        }else{
            return R.error().message("更新失败");
        }
    }
}
