package com.a1.apiscraper.service.Converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;

@Service
public class XMLAdapterService implements ConvertService {

    private XMLServiceImpl xmlServiceImpl;

    @Autowired
    public XMLAdapterService(XMLServiceImpl xmlServiceImpl) {
        this.xmlServiceImpl = xmlServiceImpl;
    }

    @Override
    public String convertData(String data) {
        return xmlServiceImpl.convertData(data);
    }
}
