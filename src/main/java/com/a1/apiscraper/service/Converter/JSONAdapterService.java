package com.a1.apiscraper.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class JSONAdapterService implements ConvertService {

    private JSONServiceImpl jsonServiceImpl;

    @Autowired
    public JSONAdapterService(JSONServiceImpl jsonServiceImpl) {
        this.jsonServiceImpl = jsonServiceImpl;
    }

    @Override
    public void convertData(String data) {
        jsonServiceImpl.
    }
}
