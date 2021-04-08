package net.atesu.atesvcdataapi.controller;

import com.alibaba.fastjson.JSON;
import net.atesu.atesvcdataapi.model.DTO.DemoDTO;
import net.atesu.atesvcdataapi.model.VO.DemoForm;
import net.atesu.atesvcdataapi.model.VO.ResultVO;
import net.atesu.atesvcdataapi.model.VO.DemoVO;
import net.atesu.atesvcdataapi.service.DemoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class DemoController extends AbstractController{
    @Autowired
    private DemoService demoService;

    @GetMapping("/demoJson")
    public ResultVO<DemoVO> demoJson(@RequestParam(value = "name", defaultValue = "World") String name) {
        return ResultVO.success(new DemoVO("小明",10));
    }

    @GetMapping("/demo")
    public String demo(@RequestParam(value = "name", defaultValue = "World") String name) {
        return "hello world";
    }


    @PostMapping("/demoJsonParam")
    public ResultVO<DemoVO> DemoForm(@RequestBody DemoForm name) {
        logger.info(JSON.toJSONString(name));
        return ResultVO.success(name.getDemoVo());
    }

    @GetMapping("/demoDatabase")
    public ResultVO<List<DemoDTO>> demoDatabase(@RequestParam(defaultValue = "1") Integer pageNum, @RequestParam(defaultValue = "1") Integer pageSize) {
        return ResultVO.success(demoService.getDemoList(pageNum,pageSize));
    }
}
