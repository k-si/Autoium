package run.autoium.service.impl;

import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import run.autoium.entity.po.ApiCase;
import run.autoium.entity.vo.ApiCaseVo;
import run.autoium.listener.ExcelUploadListener;
import run.autoium.mapper.ApiCaseManageMapper;
import run.autoium.service.ApiCaseManageService;
import run.autoium.service.ApiCaseSuiteService;
import run.autoium.service.ProjectService;
import run.autoium.utils.StringUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Service
public class ApiCaseManageServiceImpl extends ServiceImpl<ApiCaseManageMapper, ApiCase> implements ApiCaseManageService {

    @Autowired
    private ApiCaseManageMapper mapper;

    @Override
    public List<ApiCaseVo> getApiCasePageCondition(Page<ApiCaseVo> page, ApiCaseVo apiCaseVo) {

        if (apiCaseVo != null) {

            String name = apiCaseVo.getName();

            String description = apiCaseVo.getDescription();

            if (name != null && !StringUtils.isEmpty(name)) {
                apiCaseVo.setName("%" + name + "%");
            }

            if (description != null && !StringUtils.isEmpty(description)) {
                apiCaseVo.setDescription("%" + description + "%");
            }
        }

        List<ApiCaseVo> list = mapper.getApiCasePageCondition(page, apiCaseVo);
        page.setRecords(list);
        return list;
    }


    @Override
    public ApiCaseVo getById(String id) {
        return mapper.getById(id);
    }

    @Override
    public List<ApiCaseVo> getAll() {
        return mapper.getAll();
    }

    @Override
    public void uploadCaseByExcel(MultipartFile file,
                                  ApiCaseManageService apiCaseManageService,
                                  ApiCaseSuiteService apiCaseSuiteService,
                                  ProjectService projectService) {
        //上传文件流
        try {
            InputStream inputStream = file.getInputStream();

            EasyExcel.read(inputStream, ApiCaseVo.class, new ExcelUploadListener(apiCaseManageService, apiCaseSuiteService, projectService)).sheet().doRead();

            System.out.println();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
