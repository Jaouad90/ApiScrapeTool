package com.a1.apiscraper.service.Converter;

import com.a1.apiscraper.domain.ResultExport;
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
    public ResultExport convertData(String data) {
        return jsonServiceImpl.convertData(data);
    }
}
