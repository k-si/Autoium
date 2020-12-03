package run.autoium.controller;

import org.springframework.web.bind.annotation.*;
import run.autoium.common.DataCode.response.R;

@RestController
@RequestMapping("/user")
@CrossOrigin // 解决跨域
public class LoginController {

    @PostMapping("/login")
    public R login() {
        return R.ok()
                .data("token", "admin");
    }

    @GetMapping("/info")
    public R getInfo() {
        return R.ok()
                .data("roles", "[admin]")
                .data("name", "admin")
                .data("avatar", "https://ksir-oss.oss-cn-beijing.aliyuncs.com/guli/edu/teacher/%E7%BA%AF%E9%9F%B3%E7%9A%84%E4%B8%96%E7%95%8C%E6%B2%89%E9%9D%99%E5%A6%82%E6%B5%B7.jpg");
    }
}
