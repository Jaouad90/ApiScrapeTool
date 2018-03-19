package com.a1.apiscraper.service.Converter;

import com.a1.apiscraper.domain.ResultExport;
import org.json.JSONObject;
import org.json.XML;
import org.springframework.stereotype.Service;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class XMLServiceImpl {

    public ResultExport convertData(String data) {
        return convertJSONToXML(data);
    }

    private static ResultExport convertJSONToXML(String data) {

        ResultExport resultExport = new ResultExport();

        if (checkStringIfXML(data))
        {
            resultExport.setDataFormat("XML");
            resultExport.setResult(data);
            return resultExport;
        }
        else
        {
            //Convert JSON to XML
            JSONObject json = new JSONObject(data);
            resultExport.setResult(XML.toString(json));
            resultExport.setDataFormat("XML");

            return resultExport;
        }
    }

    private static boolean checkStringIfXML(String data) {
        boolean retBool = false;
        Pattern pattern;
        Matcher matcher;

        // REGULAR EXPRESSION TO SEE IF IT AT LEAST STARTS AND ENDS
        // WITH THE SAME ELEMENT
        final String XML_PATTERN_STR = "<(\\S+?)(.*?)>(.*?)</\\1>";



        // IF WE HAVE A STRING
        if (data != null && data.trim().length() > 0) {

            // IF WE EVEN RESEMBLE XML
            if (data.trim().startsWith("<")) {

                pattern = Pattern.compile(XML_PATTERN_STR,
                        Pattern.CASE_INSENSITIVE | Pattern.DOTALL | Pattern.MULTILINE);

                // RETURN TRUE IF IT HAS PASSED BOTH TESTS
                matcher = pattern.matcher(data);
                retBool = matcher.matches();
            }
            // ELSE WE ARE FALSE
        }

        return retBool;
    }


}
