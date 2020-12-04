package run.autoium.controller;


<<<<<<< HEAD
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.ApiParam;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
=======
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
>>>>>>> 5af518775487b58c1a83f1ee9cbda3dae2313d41
import org.springframework.web.bind.annotation.*;

import run.autoium.common.DataCode.response.R;
import run.autoium.entity.po.ApiCaseSuite;
<<<<<<< HEAD
import run.autoium.entity.vo.ApiCaseSuiteVo;
import run.autoium.service.ApiCaseSuiteService;

import java.util.List;
=======
import run.autoium.service.impl.ApiCaseSuiteServiceImpl;
>>>>>>> 5af518775487b58c1a83f1ee9cbda3dae2313d41

/**
 * <p>
 * 用例模块 前端控制器
 * </p>
 *
 * @author Ks1r
 * @since 2020-11-28
 */
@RestController
<<<<<<< HEAD
@RequestMapping("/autoium/api-case-suite")
=======
@RequestMapping("/apiSuite")
>>>>>>> 5af518775487b58c1a83f1ee9cbda3dae2313d41
@CrossOrigin
public class ApiCaseSuiteController {

    @Autowired
<<<<<<< HEAD
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

    /**
     * 根据id值获取用例
     * @param id
     * @return
     */
    @GetMapping("/getCase/{id}")
    public R getCaseById(@PathVariable("id") String id){
        ApiCaseSuite apiCaseSuite = apiCaseSuiteService.getById(id);

        if(apiCaseSuite != null){
            return R.ok().data("case" , apiCaseSuite);
        }else{
            return R.error().message("查询失败");
=======
    private ApiCaseSuiteServiceImpl apiCaseSuiteService;

    /**
     * 保存用例文件夹、修改文件夹
     *
     * @param apiCaseSuite 用例文件夹po对象
     * @return 如果文件夹名称为空，返回失败数据；反之返回正常数据
     */
    @PostMapping("/save")
    public R saveSuite(@RequestBody ApiCaseSuite apiCaseSuite) {
        if (StringUtils.isEmpty(apiCaseSuite.getName())) {
            return R.error().message("文件夹名称不能为空");
        }
        boolean flag = apiCaseSuiteService.save(apiCaseSuite);
        if (flag) {
            return R.ok().data("id", apiCaseSuite.getId());
        } else {
            return R.error();
>>>>>>> 5af518775487b58c1a83f1ee9cbda3dae2313d41
        }
    }

    /**
<<<<<<< HEAD
     * 根据id删除用例
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    public R deleteApiCaseSuiteById(@PathVariable("id") String id){

        boolean b = apiCaseSuiteService.removeById(id);

        if(b == true){
            return R.ok().message("删除成功");
        }else{
            return R.error().message("删除失败");
=======
     * 删除文件夹
     *
     * @param id suite id
     * @return 成功 / 失败
     */
    @GetMapping("/delete/{id}")
    public R deleteSuite(@PathVariable String id) {
        boolean flag = apiCaseSuiteService.removeById(id);
        if (flag) {
            return R.ok();
        } else {
            return R.error();
>>>>>>> 5af518775487b58c1a83f1ee9cbda3dae2313d41
        }
    }

    /**
<<<<<<< HEAD
     * 添加一条用例
     * @param apiCaseSuite
     * @return
     */
    @PostMapping("/addCase")
    public R addCase(@RequestBody ApiCaseSuite apiCaseSuite){

        System.out.println(apiCaseSuite.getProjectId());
        boolean b = apiCaseSuiteService.save(apiCaseSuite);

        if(b == true){
            return R.ok().message("添加成功");
        }else{
            return R.error().message("添加失败");
        }
    }

    @PostMapping("/updateCase")
    public R updateCase(@RequestBody ApiCaseSuite testcase){
        boolean b = apiCaseSuiteService.updateById(testcase);


        if(b){
            return R.ok().message("更新成功");
        }else{
            return R.error().message("更新失败");
        }
    }

=======
     * 修改文件夹
     *
     * @param apiCaseSuite
     * @return
     */
    @PostMapping("/update")
    public R updateSuite(@RequestBody ApiCaseSuite apiCaseSuite) {
        if (StringUtils.isEmpty(apiCaseSuite.getName())) {
            return R.error().message("文件夹名称不能为空");
        }
        boolean flag = apiCaseSuiteService.updateById(apiCaseSuite);
        if (flag) {
            return R.ok().data("id", apiCaseSuite.getId());
        } else {
            return R.error();
        }
    }
>>>>>>> 5af518775487b58c1a83f1ee9cbda3dae2313d41
}

