package run.autoium.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import run.autoium.entity.po.ApiCase;
import run.autoium.entity.po.ApiCaseManage;
import run.autoium.entity.vo.ApiCaseManageVo;
import run.autoium.entity.vo.ApiCaseVo;
import run.autoium.mapper.ApiCaseManageMapper;
import run.autoium.service.ApiCaseManageService;
import run.autoium.utils.StringUtils;

import java.util.Date;
import java.util.List;

@Service
public class ApiCaseManageServiceImpl extends ServiceImpl<ApiCaseManageMapper, ApiCaseManage> implements ApiCaseManageService {

    @Autowired
    private ApiCaseManageMapper mapper;

    @Override
    public List<ApiCaseManage> getApiCasePageCondition(Page<ApiCaseManage> page, ApiCaseManage apiCaseManage) {

        QueryWrapper<ApiCaseManage> wrapper = new QueryWrapper<>();
        
        if(apiCaseManage != null){
            String name = apiCaseManage.getName();

            String description = apiCaseManage.getDescription();

            String gmtCreateStart = apiCaseManage.getGmtCreateStart();

            String gmtCreateEnd = apiCaseManage.getGmtCreateEnd();

            if(name != null && !StringUtils.isEmpty(name)){
                wrapper.like("a.name" , name);
            }
            
            if(description != null && !StringUtils.isEmpty(description)){
                wrapper.like("a.description" , description);
            }
            
            if(gmtCreateStart != null && !StringUtils.isEmpty(gmtCreateStart)){
                wrapper.ge("a.gmt_create" , gmtCreateStart);
            }

            if(gmtCreateEnd != null && !StringUtils.isEmpty(gmtCreateEnd)){
                wrapper.le("a.gmt_create" , gmtCreateEnd);
            }
        }

        List<ApiCaseManage> list = mapper.getApiCasePageCondition(page, wrapper);

        page.setRecords(list);
        return list;
    }


    @Override
    public ApiCaseManage getById(String id) {
        return mapper.getById(id);
    }

    @Override
    public List<ApiCaseManage> getAll() {
        return mapper.getAll();
    }


}
