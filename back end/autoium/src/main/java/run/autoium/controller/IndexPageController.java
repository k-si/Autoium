package run.autoium.controller;

import lombok.Data;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import run.autoium.common.DataCode.response.R;
import run.autoium.entity.vo.ApiCaseVo;
import run.autoium.entity.vo.SevenDaysDataVo;
import run.autoium.service.impl.ApiCaseManageServiceImpl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@RestController
@RequestMapping
@CrossOrigin
public class IndexPageController {

    @Autowired
    private ApiCaseManageServiceImpl apiCaseService;

    @GetMapping("/index")
    public R getData() {
        int successCount = 0;
        int failCount = 0;
        int notPerformed = 0;
        List<ApiCaseVo> list = apiCaseService.getAll();
        List<SevenDaysDataVo> dateList = new ArrayList<>(7);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        for (int i = 0; i < 7; i++) {
            Date date = DateUtils.addDays(new Date(), -i);
            String formatDate = sdf.format(date);
            SevenDaysDataVo sevenDaysData = new SevenDaysDataVo();
            sevenDaysData.setDate(formatDate);
            dateList.add(sevenDaysData);
        }
        for (ApiCaseVo acm : list) {
            if (acm.getStatus() == 0) {
                notPerformed++;
            } else if (acm.getStatus() == 1) {
                successCount++;
            } else {
                failCount++;
            }
            String formatAcm = sdf.format(acm.getGmtCreate());
            for (SevenDaysDataVo sdd : dateList) {
                if (formatAcm.equals(sdd.getDate())) {
                    sdd.addTotal();
                } else {
                    continue;
                }
                if (acm.getStatus() == 0) {
                    sdd.addNotPerformed();
                } else if (acm.getStatus() == 1) {
                    sdd.addSuccessCount();
                } else {
                    sdd.addFailCount();
                }
            }
        }
        return R.ok().data("total", list.size())
                .data("notPerformed", notPerformed)
                .data("successCount", successCount)
                .data("failCount", failCount)
                .data("sevenDays", dateList);
    }

}