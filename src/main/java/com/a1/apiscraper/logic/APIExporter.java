package com.a1.apiscraper.logic;

import com.a1.apiscraper.service.Converter.ConvertService;
import org.springframework.beans.factory.annotation.Autowired;

public class APIExporter
{
    @Autowired
    private ConvertService convertService;

    public String convertedData(String data)
    {
        return convertService.convertData(data);
    }
}
