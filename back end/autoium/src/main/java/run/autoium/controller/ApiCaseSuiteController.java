package run.autoium.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import run.autoium.common.DataCode.response.R;
import run.autoium.entity.po.ApiCaseSuite;
import run.autoium.service.impl.ApiCaseSuiteServiceImpl;
import run.autoium.utils.StringUtils;

/**
 * <p>
 * 用例模块 前端控制器
 * </p>
 *
 * @author Ks1r
 * @since 2020-11-28
 */
@RestController
@RequestMapping("/apiSuite")
@CrossOrigin
public class ApiCaseSuiteController {

    @Autowired
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
        }
    }

    /**
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
        }
    }

    /**
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
}

