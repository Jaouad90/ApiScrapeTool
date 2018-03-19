package com.a1.apiscraper.service;

import com.a1.apiscraper.domain.Result;
import com.a1.apiscraper.domain.ResultExport;
import com.a1.apiscraper.service.Converter.*;
import com.sun.istack.internal.Nullable;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.jsoup.Jsoup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.io.*;

@Service
public class APIExporter
{

    @Autowired
    RepositoryServiceInterface repositoryServiceInterface;

    private static ConvertService convertService;
    private static String XML_FORMAT = ".xml";
    private static String JSON_FORMAT = ".json";
    private static String VARIABLE_PATH;
    private static String DEFAULT_PATH = "/Users/Expl0rer/Dropbox/Avans/VH11/Proftaak/APIscrapeTool/src/main/resources/exportFile/result";
    private static File file;
    private static String format;
    private static JSONObject jsonObj;
    private static Writer writer;

    public File convertedData(Result result)
    {
        VARIABLE_PATH = DEFAULT_PATH;
        Result resultModel = repositoryServiceInterface.getResultByID(result.getId());
        if(format.equals("json")) {
            VARIABLE_PATH = VARIABLE_PATH + JSON_FORMAT;
            file = new File(VARIABLE_PATH);
            this.convertService = new JSONAdapterService(new JSONServiceImpl());
        } else if(format.equals("xml")) {
            VARIABLE_PATH = VARIABLE_PATH + XML_FORMAT;
            file = new File(VARIABLE_PATH);
            this.convertService  = new XMLAdapterService(new XMLServiceImpl());
        }
        ResultExport resultExport = convertService.convertData(resultModel.getResult());
        removeIfFileExists();
        convertToObject(resultExport);
        return file;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    private void removeIfFileExists()
    {
        if(file.exists())
        {
            file.delete();
            System.out.println("File deleted successfully");
        }
        else
        {
            System.out.println("Failed to delete the file");
        }
    }

    private void convertToObject(ResultExport resultExport)
    {
        String convertedData;
        JSONParser parser = new JSONParser();

        convertedData = resultExport.getResult();

        if(resultExport.getDataFormat().equals("JSON"))
        {
            try {
                jsonObj = (JSONObject) parser.parse(convertedData);
                saveFile(jsonObj, null);

            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        else if(resultExport.getDataFormat().equals("XML"))
        {
                org.jsoup.nodes.Document xmlDoc = Jsoup.parse(convertedData);
                saveFile(null, xmlDoc);

        }

    }

    private void saveFile(@Nullable JSONObject jsonObj, @Nullable org.jsoup.nodes.Document xmlDoc)
    {
        try {
            writer = new BufferedWriter(new FileWriter(VARIABLE_PATH));
            if(jsonObj != null)
            {
                writer.write(jsonObj.toJSONString());
            }
            else
            {
                writer.write(xmlDoc.toString());
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
