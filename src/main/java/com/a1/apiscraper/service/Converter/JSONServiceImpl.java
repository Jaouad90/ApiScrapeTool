package com.a1.apiscraper.service.Converter;

import com.a1.apiscraper.domain.ResultExport;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.XML;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class JSONServiceImpl
{
        public ResultExport convertData(String data)
        {
                return  convertXMLToJSON(data);
        }

        private static ResultExport convertXMLToJSON(String data)
        {
                ResultExport resultExport = new ResultExport();

                if(checkIfJSON(data))
                {
                        resultExport.setDataFormat("JSON");
                        resultExport.setResult(data);
                        return resultExport;
                }
                else
                {
                        int PRETTY_PRINT_INDENT_FACTOR = 4;

                        //Convert to JSON
                        try {
                                JSONObject xmlJSONObj = XML.toJSONObject(data);
                                resultExport.setResult(xmlJSONObj.toString(PRETTY_PRINT_INDENT_FACTOR));
                                resultExport.setDataFormat("JSON");
                                return resultExport;

                        } catch (JSONException je) {
                                System.out.println(je.toString());

                                return null;
                        }
                }
        }

        private static boolean checkIfJSON(String data) {
                try {
                        new JSONObject(data);
                } catch (JSONException ex) {
                        // edited, to include @Arthur's comment
                        // e.g. in case JSONArray is valid as well...
                        try {
                                new JSONArray(data);
                        } catch (JSONException ex1) {
                                return false;
                        }
                }
                return true;
        }

}
