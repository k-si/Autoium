package run.autoium.service;

import com.baomidou.mybatisplus.extension.service.IService;
import run.autoium.entity.po.ApiCase;
import run.autoium.entity.vo.ApiCaseResultVo;
import run.autoium.entity.vo.ApiCaseVo;
import run.autoium.entity.vo.SimpleApiSuiteVo;

import java.util.List;

/**
 * <p>
 * 接口用例 服务类
 * </p>
 *
 * @author Ks1r
 * @since 2020-11-28
 */
public interface ApiCaseService extends IService<ApiCase> {

    Boolean simpleSave(ApiCase apiCase);

    Boolean detailSave(ApiCaseVo apiCaseVo);

    List<SimpleApiSuiteVo> getAll();

    ApiCaseVo getApiCaseById(Long id);

    ApiCaseResultVo executeApi(ApiCaseVo apiCase);
}
