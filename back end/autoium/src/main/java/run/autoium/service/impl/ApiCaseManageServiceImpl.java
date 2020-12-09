package run.autoium.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import run.autoium.entity.po.ApiCase;
import run.autoium.entity.vo.ApiCaseManageVo;
import run.autoium.entity.vo.ApiCaseVo;
import run.autoium.mapper.ApiCaseManageMapper;
import run.autoium.service.ApiCaseManageService;
import run.autoium.utils.StringUtils;

import java.util.Date;
import java.util.List;

@Service
public class ApiCaseManageServiceImpl extends ServiceImpl<ApiCaseManageMapper, ApiCaseVo> implements ApiCaseManageService {

    @Autowired
    private ApiCaseManageMapper apiCaseManageMapper;

    @Override
    public List<ApiCaseManageVo> getApiCasePageCondition(Page<ApiCaseManageVo> page, ApiCaseManageVo apiCaseManageVo) {

        if(apiCaseManageVo != null && apiCaseManageVo.getName() != null && !StringUtils.isEmpty(apiCaseManageVo.getName())){
            String name = apiCaseManageVo.getName();
            apiCaseManageVo.setName("%" + name + "%");
        }
        if(apiCaseManageVo != null && apiCaseManageVo.getDescription() != null && !StringUtils.isEmpty(apiCaseManageVo.getDescription())){
            String name = apiCaseManageVo.getDescription();
            apiCaseManageVo.setDescription("%" + name + "%");
        }
        List<ApiCaseManageVo> list = apiCaseManageMapper.getApiCasePageCondition(page, apiCaseManageVo);

        page.setRecords(list);
        return list;
    }

    @Override
    public ApiCaseManageVo getById(String id) {
        return apiCaseManageMapper.getById(id);
    }

    @Override
    public boolean removeById(String id) {
        return apiCaseManageMapper.removeById(id);
    }

    @Override
    public boolean save(ApiCase apiCase) {
        return apiCaseManageMapper.save(apiCase);
    }

    @Override
    public int updateByID(ApiCase apiCase) {
        apiCase.setGmtModified(new Date());
        return apiCaseManageMapper.updateByID(apiCase);
    }

    @Override
    public List<ApiCaseManageVo> getAll() {
        return apiCaseManageMapper.getAll();
    }
}
