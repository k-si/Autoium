package run.autoium.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import run.autoium.common.DataCode.response.R;
import run.autoium.entity.po.ApiCaseSuite;
import run.autoium.service.impl.ApiCaseSuiteServiceImpl;

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
     * 保存用例文件夹
     *
     * @param apiCaseSuite 用例文件夹po对象
     * @return 如果文件夹名称为空，返回失败数据；反之返回正常数据
     */
    @PostMapping("/save")
    public R saveSuite(@RequestBody ApiCaseSuite apiCaseSuite) {
        if (StringUtils.isEmpty(apiCaseSuite)) {
            return R.error().message("添加失败");
        }
        apiCaseSuiteService.save(apiCaseSuite);
        return R.ok().message("添加成功");
    }

}


