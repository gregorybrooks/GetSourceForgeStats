package edu.umass.ciir;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.net.URL;

public class GetSourceForgeStats {

    private void getStats() throws IOException, ParseException {
        String sourceForgeUrl="https://sourceforge.net/projects/lemur/files/lemur/galago-3.10/stats/json?start_date=2019-09-01&end_date=2020-08-31";
        URL url = new URL(sourceForgeUrl);

        BufferedReader reader = new BufferedReader(new InputStreamReader(
                url.openStream()));
        JSONParser parser = new JSONParser();
        JSONObject statsJSON = (JSONObject) parser.parse(reader);
        String endDate = (String) statsJSON.get("end_date");
        String period = (String) statsJSON.get("period");
        JSONArray aCountries = (JSONArray) statsJSON.get("countries");
        for (Object oCountryCount : aCountries) {
            JSONArray aCountryCount = (JSONArray) oCountryCount;
            String country = (String) aCountryCount.get(0);
            long count = (long) aCountryCount.get(1);
            System.out.println(country + ": " + String.valueOf(count));
        }
        JSONArray oses_with_downloads = (JSONArray) statsJSON.get("oses_with_downloads");
        JSONArray downloads = (JSONArray) statsJSON.get("downloads");
        JSONObject summaries = (JSONObject) statsJSON.get("summaries");
        long total = (long) statsJSON.get("total");
        System.out.println("Total: " + String.valueOf(total));
/*
        for (Object oTask : tasksJSON) {
            Task t = new Task((JSONObject) oTask);  // this gets Requests, too
            tasks.put(t.taskNum, t);
        }
*/
    }
    public static void main(String[] args) throws IOException, ParseException {
        GetSourceForgeStats g = new GetSourceForgeStats();
	    g.getStats();
    }
}
