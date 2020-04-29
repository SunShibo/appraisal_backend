package com.wisewin.api.dao;

import com.wisewin.api.entity.bo.KeyValueBO;

import java.util.List;

public interface KeyValueDAO {
    Integer updKeyValue(KeyValueBO keyValueBO);
    List<KeyValueBO> getKeyValueList();
}
