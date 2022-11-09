package com.xuexi.settings.service.impl;

import com.xuexi.settings.domain.DicValue;
import com.xuexi.settings.mapper.DicValueMapper;
import com.xuexi.settings.service.DicValueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class DicValueServiceImpl implements DicValueService {

    @Autowired
    private DicValueMapper dicValueMapper;
    @Override
    public List<DicValue> queryDicValueByTypeCode(String typecode) {
        return dicValueMapper.selectDicValueByTypeCode(typecode);
    }
}
