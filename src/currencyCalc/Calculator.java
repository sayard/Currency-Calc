package currencyCalc;

import org.json.JSONObject;

import java.io.*;
import java.net.URL;
import java.nio.charset.Charset;

/**
 * Created by Karol on 07.05.2017.
 */
public class Calculator {


    public static JSONObject readJsonFromURL(String urlEnding){
        try {
            InputStream inputStream = new URL("http://api.fixer.io/latest?base="+urlEnding).openStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, Charset.forName("UTF-8")));
            String jsonText = readAllData(bufferedReader);
            inputStream.close();
            return new JSONObject(jsonText);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static String readAllData(Reader bufferedReader) throws IOException {
        StringBuilder builder = new StringBuilder();
        int cp;
        while((cp = bufferedReader.read()) != -1){
            builder.append((char) cp);
        }
        return builder.toString();
    }
}
