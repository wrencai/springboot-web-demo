package net.atesu.atesvcdataapi.service.impl;

import com.github.pagehelper.PageHelper;
import net.atesu.atesvcdataapi.dao.DemoTestMapper;
import net.atesu.atesvcdataapi.model.DO.DemoTest;
import net.atesu.atesvcdataapi.model.DTO.DemoDTO;
import net.atesu.atesvcdataapi.service.DemoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DemoServiceImpl implements DemoService {

    @Autowired
    private DemoTestMapper demoTestMapper;

    @Override
    public List<DemoDTO> getDemoList(Integer pageNum, Integer pageSize) {
        //使用分页插件,核心代码就这一行
        PageHelper.startPage(pageNum, pageSize);
        List<DemoTest> list = demoTestMapper.selectPage();
        List<DemoDTO> listRes = new ArrayList<>();
        list.forEach(ele -> {
            listRes.add(new DemoDTO().build(ele));
        });
        return listRes;
    }
}
