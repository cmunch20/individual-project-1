package edu.jsu.mcis.cs310;

import com.github.cliftonlabs.json_simple.*;
import com.opencsv.*;
import com.opencsv.exceptions.CsvException;
import java.util.*;
import java.io.*;

public class Converter {
    
    /*
        
        Consider the following CSV data, a portion of a database of episodes of
        the classic "Star Trek" television series:
        
        "ProdNum","Title","Season","Episode","Stardate","OriginalAirdate","RemasteredAirdate"
        "6149-02","Where No Man Has Gone Before","1","01","1312.4 - 1313.8","9/22/1966","1/20/2007"
        "6149-03","The Corbomite Maneuver","1","02","1512.2 - 1514.1","11/10/1966","12/9/2006"
        
        (For brevity, only the header row plus the first two episodes are shown
        in this sample.)
    
        The corresponding JSON data would be similar to the following; tabs and
        other whitespace have been added for clarity.  Note the curly braces,
        square brackets, and double-quotes!  These indicate which values should
        be encoded as strings and which values should be encoded as integers, as
        well as the overall structure of the data:
        
        {
            "ProdNums": [
                "6149-02",
                "6149-03"
            ],
            "ColHeadings": [
                "ProdNum",
                "Title",
                "Season",
                "Episode",
                "Stardate",
                "OriginalAirdate",
                "RemasteredAirdate"
            ],
            "Data": [
                [
                    "Where No Man Has Gone Before",
                    1,
                    1,
                    "1312.4 - 1313.8",
                    "9/22/1966",
                    "1/20/2007"
                ],
                [
                    "The Corbomite Maneuver",
                    1,
                    2,
                    "1512.2 - 1514.1",
                    "11/10/1966",
                    "12/9/2006"
                ]
            ]
        }
        
        Your task for this program is to complete the two conversion methods in
        this class, "csvToJson()" and "jsonToCsv()", so that the CSV data shown
        above can be converted to JSON format, and vice-versa.  Both methods
        should return the converted data as strings, but the strings do not need
        to include the newlines and whitespace shown in the examples; again,
        this whitespace has been added only for clarity.
        
        NOTE: YOU SHOULD NOT WRITE ANY CODE WHICH MANUALLY COMPOSES THE OUTPUT
        STRINGS!!!  Leave ALL string conversion to the two data conversion
        libraries we have discussed, OpenCSV and json-simple.  See the "Data
        Exchange" lecture notes for more details, including examples.
        
    */
    
    @SuppressWarnings("unchecked")
    public static String csvToJson(String csvString) throws IOException, CsvException {
        
        String result = ""; // default return value; replace later!

         StringBuilder csvBuilder = new StringBuilder();
         
        try 
        {
            BufferedReader reader = new BufferedReader(new FileReader("input.csv"));
            String line;
            while ((line = reader.readLine()) != null)
            {
                csvBuilder.append(line).append("\n");
            }
            
        }
        catch (Exception e) {
            e.printStackTrace();
        }
                
        String aCsvString = csvBuilder.toString().trim();
        
        CSVReader reader = new CSVReader(new StringReader(aCsvString));

        
            List <String[]> full = reader.readAll(); 
            Iterator<String[]> iterator = full.iterator();
        
            String[] line = iterator.next();
            
            JsonArray records = new JsonArray();
            
            if (iterator.hasNext())
               {
                   String[] headings = iterator.next();
                   while (iterator.hasNext())
                   {
                       String[] csvRecord = iterator.next();
                       JsonObject jsonRecord = new JsonObject();
                       for (int o = 0; o < headings.length; ++o)
                       {
                           jsonRecord.put(headings[o].toLowerCase(), csvRecord[o]);
                       }
                       records.add(jsonRecord);
                   }
               }
            
            String jsonString = Jsoner.serialize(records);
            
            result = jsonString;
            
            return result.trim();
    }
    
@SuppressWarnings("unchecked")
    public static String jsonToCsv(String jsonString) 
    {
        String result = ""; // default return value; replace later!

        
        JsonObject jsonObject = Jsoner.deserialize(jsonString, new JsonObject());

        int size = jsonObject.size();
        int counter = 0;
    
        StringWriter writer = new StringWriter();
        CSVWriter csvWriter = new CSVWriter(writer, ',', '"', '\\', "\n");

        Object ProdNumsValues = jsonObject.get("ProdNums");
        String ProdNums = ProdNumsValues.toString();
            Object TitleValues = jsonObject.get("Title");
            String Titles = TitleValues.toString();
            Object SeasonValues = jsonObject.get("Season");
            String Season = ProdNumsValues.toString();
            Object EpisodeValues = jsonObject.get("Episode");
            String Episode = TitleValues.toString();
            Object StarDateValues = jsonObject.get("StarDate");
            String StarDate = ProdNumsValues.toString();
            Object OADValues = jsonObject.get("OriginalAirdate");
            String OAD = TitleValues.toString();
            Object RADValues = jsonObject.get("RemasteredAirdate");
            String RAD = TitleValues.toString();
            Object ColHeadingsValues = jsonObject.get("ColHeadings");
            String ColumnHeadings = ColHeadingsValues.toString();
            
            Set<String> keys = jsonObject.keySet();
            String[] columns = ColumnHeadings.split("");
            csvWriter.writeNext(columns);
            
            //Get column headers in a list
            //Print first ProdNum, Title, Season, Episode, SD, OAD, and Rad on first line 
            //Print second of each on second line and so on until the end of the file
            //Write it all with a CSVWriter to format it and return it as result var
        
            
            result = writer.toString();
        try 
        {
            
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return result.trim();
    }
}