package run.autoium.service;

import com.baomidou.mybatisplus.extension.service.IService;
import run.autoium.entity.po.ApiCaseSuite;
import run.autoium.entity.vo.SimpleApiSuiteVo;

import java.util.List;

/**
 * <p>
 * 用例模块 服务类
 * </p>
 *
 * @author Ks1r
 * @since 2020-11-28
 */
public interface ApiCaseSuiteService extends IService<ApiCaseSuite> {

    public List<SimpleApiSuiteVo> getAllName();
}
