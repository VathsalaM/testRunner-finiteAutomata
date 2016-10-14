package common;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Parser {
  public ArrayList<JSONObject> parse(String fileName)throws IOException,JSONException {
    File file = new File(fileName);
    FileReader fileReader = new FileReader(file.getAbsolutePath());
    int fileLength = (int) file.length();
    char[] cbuf = new char[fileLength];
    fileReader.read(cbuf);
    String jsonStr = new String(cbuf).substring(2, fileLength - 2);
    jsonStr = jsonStr.replace(String.valueOf(jsonStr.charAt(1)),"");
    jsonStr = jsonStr.replace(",{","next{");
    String[] jsonStrings = jsonStr.split("next");
    ArrayList<JSONObject> jsonObjects = new ArrayList<>();
    for (String jsonString : jsonStrings) {
      jsonObjects.add(new JSONObject(jsonString));
    }
    return jsonObjects;
  }
}
