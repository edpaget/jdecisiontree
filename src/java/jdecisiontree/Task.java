package jdecisiontree;

import java.util.ArrayList;
import java.util.HashMap;
import com.fasterxml.jackson.annotation.*;

@JsonIgnoreProperties(ignoreUnknown=true)
public abstract class Task implements ITask {
  public String question;
  public String key;
  public ArrayList<HashMap<String, Object>> choices;

  public Task() {
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

  public String getKey() {
    return this.key;
  }
}
