package run.autoium.controller;


import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import run.autoium.common.DataCode.response.R;
import run.autoium.entity.po.ApiCase;
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
     * 显示用例详细信息，通过id查询接口
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
     * 保存简略的api信息
     *
     * @param apiCase
     * @return
     */
    @PostMapping("/simpleSave")
    public R saveSimpleApi(@RequestBody ApiCase apiCase) {
        String name = apiCase.getName();
        String suiteId = apiCase.getApiCaseSuiteId();
        if (StringUtils.isEmpty(name) || StringUtils.isEmpty(suiteId)) {
            return R.error().message("信息不完整");
        }
        boolean flag = apiCaseService.simpleSave(apiCase);
        if (flag) {
            return R.ok().data("id", apiCase.getId());
        } else {
            return R.error();
        }
    }

    /**
     * 保存api详细信息
     *
     * @param apiCaseVo
     * @return
     */
    @ApiOperation(value = "保存接口详细信息")
    @PostMapping("/detailSave")
    public R saveDetailApi(@RequestBody ApiCaseVo apiCaseVo) {
        Boolean flag = apiCaseService.detailSave(apiCaseVo);

        if (flag) {
            return R.ok();
        } else {
            return R.error();
        }
    }

    /**
     * 通过id删除用例
     *
     * @param id 用例id
     * @return 成功 / 失败
     */
    @GetMapping("/delete/{id}")
    public R deleteApi(@PathVariable String id) {
        boolean flag = apiCaseService.removeById(id);
        if (flag) {
            return R.ok();
        } else {
            return R.error();
        }
    }

    /**
     * 修改api
     *
     * @param apiCase
     * @return
     */
    @PostMapping("/update")
    public R updateApi(@RequestBody ApiCase apiCase) {
        String name = apiCase.getName();
        String suiteId = apiCase.getApiCaseSuiteId();
        if (StringUtils.isEmpty(name) || StringUtils.isEmpty(suiteId)) {
            return R.error().message("信息不完整");
        }
        boolean flag = apiCaseService.updateById(apiCase);
        if (flag) {
            return R.ok().data("id", apiCase.getId());
        } else {
            return R.error();
        }
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

