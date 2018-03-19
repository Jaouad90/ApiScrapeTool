package com.a1.apiscraper.service;

import com.a1.apiscraper.domain.Result;
import com.a1.apiscraper.domain.ResultExport;
import com.a1.apiscraper.service.Converter.*;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;

@Service
public class APIExporter
{

    @Autowired
    RepositoryServiceInterface repositoryServiceInterface;

    private ConvertService convertService;
    private String XML_FORMAT = ".xml";
    private String JSON_FORMAT = ".json";
    private String VARIABLE_PATH;
    private String DEFAULT_PATH = "/Users/Expl0rer/Dropbox/Avans/VH11/Proftaak/APIscrapeTool/src/main/resources/exportFile/result";
    private File file;
    private String format;

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
        saveFileAsFormat(resultExport);
        return file;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public void removeIfFileExists()
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

    public void saveFileAsFormat(ResultExport resultExport)
    {
        String convertedData;
        JSONParser parser = new JSONParser();
        JSONObject jsonObj;
        Writer writer;

        if(resultExport.getDataFormat().equals("JSON"))
        {
            convertedData = resultExport.getResult();

            try {
                jsonObj = (JSONObject) parser.parse(convertedData);
                writer = new BufferedWriter(new FileWriter(VARIABLE_PATH));
                writer.write(jsonObj.toJSONString());
                writer.close();

            } catch (IOException e) {
                e.printStackTrace();
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        else if(resultExport.getDataFormat().equals("XML"))
        {
            try {
                FileWriter fileWriter = new FileWriter(VARIABLE_PATH);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

    }
}
