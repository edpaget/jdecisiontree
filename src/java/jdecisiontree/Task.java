package jdecisiontree;

import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;
import com.fasterxml.jackson.annotation.*;

@JsonIgnoreProperties(ignoreUnknown=true)
public abstract class Task implements ITask {
  public String question;
  public String key;
  public ArrayList<HashMap<String, Object>> choices;
  public String readableKey;
  private String next;

  public Task() {
  }

  public Task(String key, String question, List<Map<String, Object>> choices) {
    this.key = key;
    this.question = question;
    this.choices = formatChoiceList(choices);
  }

  public Task(String key, String question, ArrayList<HashMap<String, Object>> choices) {
    this.key = key;
    this.question = question;
    this.choices = choices;
  }

  public Task withKey(String key) {
    this.key = key;
    return this;
  }

  public Task withQuestion(String question) {
    this.question = question;
    return this;
  }

  public Task withChoices(ArrayList<HashMap<String, Object>> choices) {
    this.choices = choices;
    return this;
  }

  public Task withChoices(List<Map<String, Object>> choices) {
    this.choices = formatChoiceList(choices);
    return this;
  }

  public Task withNext(String next) {
    this.next = next;
    return this;
  }

  public Task withReadableKey(String rkey) {
    this.readableKey = rkey;
    return this;
  }

  public String getKey() {
    return this.key;
  }

  public String getNext(Object answer) {
    return getNext();
  }

  public String getNext() {
    return this.next;
  }

  private ArrayList<HashMap<String, Object>> formatChoiceList(List<Map<String, Object>> choices) {
    ArrayList cs = new ArrayList(choices.toArray().length);
    for(Map<String, Object> choice : choices) {
      cs.add(new HashMap(choice));
    }
    return cs;
  }

}
