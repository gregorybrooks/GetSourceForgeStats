package edu.umass.ciir;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;

public class GetSourceForgeStats {
    String sourceForgeLemurDirectory = "https://sourceforge.net/projects/lemur/files/lemur/";
    List<String> galagoReleases = new ArrayList<String>(Arrays.asList(
            "galago-3.2",
            "galago-3.3",
            "galago-3.4",
            "galago-3.5",
            "galago-3.6",
            "galago-3.7",
            "galago-3.8",
            "galago-3.9",
            "galago-3.10",
            "galago-3.11",
            "galago-3.12",
            "galago-3.13",
            "galago-3.14",
            "galago-3.14159",
            "galago-3.15",
            "galago-3.16",
            "galago-3.17",
            "galago-3.18",
            "galago-3.19"
    ));
    List<String> indriReleases = new ArrayList<String>(Arrays.asList(
            "indri-5.1",
            "indri-5.2",
            "indri-5.3",
            "indri-5.4",
            "indri-5.5",
            "indri-5.6",
            "indri-5.7",
            "indri-5.8",
            "indri-5.9",
            "indri-5.10",
            "indri-5.11",
            "indri-5.12",
            "indri-5.13",
            "indri-5.14",
            "indri-5.15",
            "indri-5.16",
            "indri-5.17",
            "indri-5.18"));
    List<String> stemmerReleases = new ArrayList<String>(Arrays.asList(
            "KrovetzStemmer-3.4"));
    List<String> lucindriReleases = new ArrayList<>(Arrays.asList(
            "lucindri-1.0",
            "lucindri-1.1",
            "lucindri-1.2"
    ));
    List<String> RankLibReleases = new ArrayList<>(Arrays.asList(
            "RankLib-1.0",
            "RankLib-2.1",
// missing                "RankLib-2.2",
            "RankLib-2.3",
            "RankLib-2.4",
            "RankLib-2.5",
            "RankLib-2.6",
            "RankLib-2.7",
            "RankLib-2.8",
            "RankLib-2.9",
            "RankLib-2.10",
            "RankLib-2.11",
            "RankLib-2.12",
            "RankLib-2.13",
            "RankLib-2.14",
            "RankLib-2.15"
    ));
    List<String> sifakaReleases = new ArrayList<>(Arrays.asList(
            "sifaka-1.0",
            "sifaka-1.1",
            "sifaka-1.2",
            "sifaka-1.3",
            "sifaka-1.4",
            "sifaka-1.5",
            "sifaka-1.6",
            "sifaka-1.7",
            "sifaka-1.8",
            "sifaka-1.9"
    ));
    List<String> wordEntityDuetReleases = new ArrayList<>(Arrays.asList(
            "WordEntityDuet-1.0"
    ));
    Set<String> totalCountries = new HashSet<String>();
    long grandTotal = 0;

    private void calc(String product, List<String> productReleases) throws IOException, ParseException {
        Set<String> countries = new HashSet<String>();
        Map<String, Long> releaseDownloads = new LinkedHashMap<>();
        long totalDownloads = 0;
        for (String release : productReleases) {
            String sourceForgeUrl = sourceForgeLemurDirectory
//                        + release + "/stats/json?start_date=2015-03-01&end_date=2021-09-01";
//            + release + "/stats/json?start_date=2019-09-01&end_date=2020-08-31";
// For the 2021 annual report:            + release + "/stats/json?start_date=2020-06-01&end_date=2021-05-31";
// For the 2021 annual report:            + release + "/stats/json?start_date=2012-06-20&end_date=2021-08-31";
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
                countries.add(country);
                totalCountries.add(country);
    //                System.out.println(country + ": " + String.valueOf(count));
            }
            JSONArray oses_with_downloads = (JSONArray) statsJSON.get("oses_with_downloads");
            JSONArray downloads = (JSONArray) statsJSON.get("downloads");
            JSONObject summaries = (JSONObject) statsJSON.get("summaries");
            long total = (long) statsJSON.get("total");
            releaseDownloads.put(release,total);
            totalDownloads += total;
    //            System.out.println("Total: " + String.valueOf(total));
        }
        grandTotal += totalDownloads;

     //   releaseDownloads.forEach((release, downloads) -> { System.out.println(release + ": "
     //           + String.valueOf(downloads) ); });

        System.out.println(product + ": " + totalDownloads + " total downloads in " + countries.size()
                + " different countries");
    }

    private void getStats() throws IOException, ParseException {
        calc("Galago", galagoReleases);
        calc("Indri", indriReleases);
        calc("Krovetz Stemmer", stemmerReleases);
        calc("LucIndri", lucindriReleases);
        calc("RankLib", RankLibReleases);
        calc("Sifaka", sifakaReleases);
        calc("Word Entity Duet", wordEntityDuetReleases);
        System.out.println("Total downloads: " + grandTotal);
        System.out.println(" in " + totalCountries.size() + " different countries");
//        countries.forEach((c) -> { System.out.println(c); } );

    }
    public static void main(String[] args) throws IOException, ParseException {
        GetSourceForgeStats g = new GetSourceForgeStats();
	    g.getStats();
    }
}
