package run.autoium.controller;


import com.baomidou.mybatisplus.extension.api.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * <p>
 * 用例模块 前端控制器
 * </p>
 *
 * @author Ks1r
 * @since 2020-11-28
 */

public class ApiCaseSuiteController {
    @Autowired
    private ApiCaseSuiteService apiCaseSuiteService;

    /**
     * 条件分页查询数据
     * @param current 当前页
     * @param limit 每页大小
     * @param apiCaseSuiteVo 可选条件
     * @return
     */
    @PostMapping("/PageCaseCondition/{current}/{limit}")
    public R getCaseListPage(@PathVariable("current") Long current,
                             @PathVariable("limit") Long limit,
                             @RequestBody(required = false) ApiCaseSuiteVo apiCaseSuiteVo){
        //创建分页查询
        //创建页面
        Page<ApiCaseSuite> page = new Page<>(current , limit);
        //创建查询对象的对象
        QueryWrapper<ApiCaseSuite> wrapper = new QueryWrapper<>();

        //判断条件，如果非空再去加条件，动态查询
        if(apiCaseSuiteVo != null){

            String name = apiCaseSuiteVo.getName();

            String description = apiCaseSuiteVo.getDescription();

            String gmtCreateStart = apiCaseSuiteVo.getGmtCreateStart();

            String gmtCreateEnd = apiCaseSuiteVo.getGmtCreateEnd();


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
                wrapper.le("gmt_modified" , gmtCreateEnd);
            }
        }
        //排序
        wrapper.orderByAsc("id");

        //查询
        apiCaseSuiteService.page(page , wrapper);

        //获取数据
        long total = page.getTotal();
        List<ApiCaseSuite> list = page.getRecords();

        //返回
        return R.ok().data("list" , list).data("total" , total);
    }
}

