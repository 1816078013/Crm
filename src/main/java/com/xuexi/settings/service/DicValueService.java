package com.xuexi.settings.service;

import com.xuexi.settings.domain.DicValue;
import com.xuexi.settings.mapper.DicValueMapper;

import java.util.List;

public interface DicValueService {

    List<DicValue> queryDicValueByTypeCode(String typecode);
}
