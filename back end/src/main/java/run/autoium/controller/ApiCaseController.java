package run.autoium.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import run.autoium.common.DataCode.response.R;
import run.autoium.entity.po.ApiCase;
import run.autoium.entity.po.ApiCaseSuite;
import run.autoium.entity.vo.ApiCaseResultVo;
import run.autoium.entity.vo.ApiCaseVo;
import run.autoium.entity.vo.SimpleApiSuiteVo;
import run.autoium.service.impl.ApiCaseServiceImpl;
import run.autoium.service.impl.ApiCaseSuiteServiceImpl;

import java.util.List;

/**
 * <p>
 * 接口用例 前端控制器
 * </p>
 *
 * @author Ks1r
 * @since 2020-11-28
 */
@RestController
@RequestMapping("/api")
@CrossOrigin
public class ApiCaseController {

    @Autowired
    private ApiCaseServiceImpl apiCaseService;

    @Autowired
    private ApiCaseSuiteServiceImpl apiCaseSuiteService;

    /**
     * 获取所有的目录及接口
     *
     * @return
     */
    @GetMapping("/getAll")
    public R getAll() {
        List<SimpleApiSuiteVo> list =  apiCaseService.getAll();
        return R.ok().data("all", list);
    }

    /**
     * 通过id查询接口
     *
     * @param id
     * @return
     */
    @GetMapping("/getApi/{id}")
    public R getApiInfo(@PathVariable Long id) {
        ApiCaseVo apiCaseVo = apiCaseService.getApiCaseById(id);
        return R.ok().data("item", apiCaseVo);
    }

    /**
     * 创建api文件夹
     *
     * @return
     */
    @PostMapping("/createSuite")
    public R createApiModule(@RequestBody ApiCaseSuite suite) {
        apiCaseSuiteService.save(suite);
        return R.ok();
    }

    /**
     * 保存api
     *
     * @param apiCase
     * @return
     */
    @PostMapping("/save")
    public R saveApi(@RequestBody ApiCaseVo apiCase) {
        return R.ok();
    }

    /**
     * 模拟api请求
     *
     * @param apiCase
     * @return
     */
    @PostMapping("/execute")
    public R executeApi(@RequestBody ApiCaseVo apiCase) {
        ApiCaseResultVo apiCaseResult = apiCaseService.executeApi(apiCase);
        return R.ok().data("res", apiCaseResult);
    }
}

