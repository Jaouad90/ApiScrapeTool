package com.a1.apiscraper.service.Converter;

import com.a1.apiscraper.repository.ResultRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.StringReader;

@Service
public class JSONServiceImpl
{

        @Autowired
        private ResultRepository resultRepository;

        public Document convertData(String data)
        {
                Document JSONdoc;
                return JSONdoc = convertStringToDocument(data);
        }

        private static Document convertStringToDocument(String data) {
                DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
                DocumentBuilder builder;
                try
                {
                        builder = factory.newDocumentBuilder();
                        Document doc = builder.parse( new InputSource( new StringReader( data ) ) );
                        return doc;
                } catch (Exception e) {
                        e.printStackTrace();
                }
                return null;
        }

}
