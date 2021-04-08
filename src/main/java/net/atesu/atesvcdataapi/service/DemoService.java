package net.atesu.atesvcdataapi.service;

import net.atesu.atesvcdataapi.model.DTO.DemoDTO;

import java.util.List;

public interface DemoService {

    List<DemoDTO> getDemoList(Integer pageNum, Integer pageSize);
}
