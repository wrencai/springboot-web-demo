package net.atesu.atesvcdataapi.dao;

import java.util.List;
import net.atesu.atesvcdataapi.model.DO.DemoTest;
import org.springframework.stereotype.Repository;

@Repository
public interface DemoTestMapper {
    int insert(DemoTest record);

    List<DemoTest> selectPage();
}