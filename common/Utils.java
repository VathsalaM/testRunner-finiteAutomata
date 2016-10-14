package common;

import dfa.State;
import dfa.Transition;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashSet;
import java.util.Iterator;

public class Utils {

  public HashSet<String> getAlphabets(JSONArray jsonAlphabets) throws JSONException {
    HashSet<String> alphabets = new HashSet<>();
    for (int i = 0; i < jsonAlphabets.length(); i++) {
      alphabets.add(jsonAlphabets.get(i).toString());
    }
    return alphabets;
  }

  public Transition getTransition(JSONObject jsonTransition) throws JSONException{
    Transition transition = new Transition();
    Iterator<?> keys = jsonTransition.keys();
    while( keys.hasNext() ) {
      String key = (String)keys.next();
      JSONObject states = jsonTransition.getJSONObject(key);
      Iterator<?> stateKeys = states.keys();
      while( stateKeys.hasNext()){
        String alphabet = (String)stateKeys.next();
        String nextState = states.get(alphabet).toString();
        transition.Add(key,alphabet,nextState);
      }
    }
    return transition;
  }

  public boolean isRecognised(FiniteAutomata fa, JSONArray pass_cases) throws JSONException{
    for (int i = 0; i < pass_cases.length(); i++) {
      if (!fa.Verify(pass_cases.get(i).toString())){
        return false;
      }
    }
    return true;
  }

  public boolean isNotRecognised(FiniteAutomata fa, JSONArray fail_cases) throws JSONException {
    for (int i = 0; i < fail_cases.length(); i++) {
      if (fa.Verify(fail_cases.get(i).toString())){
        return false;
      }
    }
    return true;
  }
}
