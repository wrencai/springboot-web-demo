package net.atesu.atesvcdataapi.controller;

import net.atesu.atesvcdataapi.model.VO.ResultVO;
import net.atesu.atesvcdataapi.model.VO.DemoVO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DemoController {

    @GetMapping("/demoJson")
    public ResultVO<DemoVO> demoJson(@RequestParam(value = "name", defaultValue = "World") String name) {
        return ResultVO.success(new DemoVO("小明",10));
    }

    @GetMapping("/demo")
    public String demo(@RequestParam(value = "name", defaultValue = "World") String name) {
        return "hello world";
    }
}
