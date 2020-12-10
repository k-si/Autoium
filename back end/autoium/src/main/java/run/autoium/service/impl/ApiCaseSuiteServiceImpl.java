package run.autoium.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import run.autoium.entity.po.ApiCaseSuite;
import run.autoium.entity.vo.SimpleApiSuiteVo;
import run.autoium.mapper.ApiCaseMapper;
import run.autoium.mapper.ApiCaseSuiteMapper;
import run.autoium.service.ApiCaseSuiteService;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 用例模块 服务实现类
 * </p>
 *
 * @author Ks1r
 * @since 2020-11-28
 */
@Service
public class ApiCaseSuiteServiceImpl extends ServiceImpl<ApiCaseSuiteMapper, ApiCaseSuite> implements ApiCaseSuiteService {

    @Autowired
    private ApiCaseSuiteMapper apiCaseSuiteMapper;

    @Autowired
    private ApiCaseMapper apiCaseMapper;

    @Override
    public List<SimpleApiSuiteVo> getAllName() {
        List<ApiCaseSuite> apiCaseSuites = apiCaseSuiteMapper.selectList(null);

        List<SimpleApiSuiteVo> list = new ArrayList<>();

        for (ApiCaseSuite apiCaseSuite : apiCaseSuites) {
            SimpleApiSuiteVo simpleApiSuiteVo = new SimpleApiSuiteVo();

            simpleApiSuiteVo.setId(apiCaseSuite.getId());
            simpleApiSuiteVo.setName(apiCaseSuite.getName());

            list.add(simpleApiSuiteVo);
        }
        return list;
    }

    /**
     * 删除文件夹和文件夹下所有内容
     * 
     * @param id
     * @return
     */
    public boolean deleteSuiteById(String id) {
        int i = apiCaseSuiteMapper.deleteById(id);
        int i1 = apiCaseMapper.deleteBySuiteId(id);
        return i > 0 && i1 > 0;
    }
}
