package testRunner;


import common.FiniteAutomata;
import common.Parser;
import common.Utils;
import dfa.DFA;
import dfa.Transition;
import nfa.NFA;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;

public class TestRunner {


  public static void main(String[] args) throws IOException, JSONException{

    ArrayList<JSONObject> jsonObjects = new Parser().parse("examples.json");

    int totalPassCount=0,totalFailCount=0,count = 0;
    for (JSONObject rootObject : jsonObjects) {
      JSONArray pass_cases = rootObject.getJSONArray("pass-cases");
      JSONArray fail_cases = rootObject.getJSONArray("fail-cases");
      String type = rootObject.get("type").toString();
      String name = rootObject.get("name").toString();
      JSONObject tuple = rootObject.getJSONObject("tuple");

      JSONArray jsonStates = tuple.getJSONArray("states");
      JSONArray jsonAlphabets = tuple.getJSONArray("alphabets");
      JSONArray jsonFinalStates = tuple.getJSONArray("final-states");
      JSONObject jsonTransition = tuple.getJSONObject("delta");
      String jsonInitialState = tuple.get("start-state").toString();

      Utils utils = new Utils();
      HashSet<String> states = utils.getAlphabets(jsonStates);
      HashSet<String> finalStates = utils.getAlphabets(jsonFinalStates);
      HashSet<String> alphabets = utils.getAlphabets(jsonAlphabets);
      Transition transition = utils.getTransition(jsonTransition);

      FiniteAutomata fa ;
      if(type.equals("dfa")){
        fa = new DFA(states,alphabets,transition,jsonInitialState,finalStates);
      }else{
        fa = new NFA(states,alphabets,transition,jsonInitialState,finalStates);
      }

      boolean result = utils.isRecognised(fa,pass_cases) && utils.isNotRecognised(fa,fail_cases);

      String message = (count++) +". "+ name+": ";
      if(result){
        totalPassCount++;
        System.out.println(message+"pass");
      }else {
        totalFailCount++;
        System.out.println(message+"fail");
      }
    }
    System.out.println("\nTotal tests passing: " + totalPassCount);
    System.out.println("Total tests failing: " + totalFailCount);
  }
}