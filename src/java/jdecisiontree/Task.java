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
  public ArrayList<HashMap<String, String>> choices;
  public String readableKey;
  private String next;

  public Task() {
  }

  public Task(String key, String question, List<Map<String, String>> choices) {
    this.key = key;
    this.question = question;
    this.choices = formatChoiceList(choices);
  }

  public Task(String key, String question, ArrayList<HashMap<String, String>> choices) {
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

  public Task withChoices(ArrayList<HashMap<String, String>> choices) {
    this.choices = choices;
    return this;
  }

  public Task withChoices(List<Map<String, String>> choices) {
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

  public String getNext(String answer) {
    return getNext();
  }

  public String getNext() {
    return this.next;
  }

  public String toCountColumn() {
    return columnPrefix().concat("count");
  }

  public ArrayList<String> toAnswerColumns() {
    ArrayList<String> columns = new ArrayList<String>(this.choices.size());
    for (Map<String, String> choice : this.choices) {
      columns.add(columnPrefix().concat(choice.get("label").replaceAll(" ", "_").toLowerCase()));
    }
    return columns;
  }

  public String getChoiceLabel(String choiceValue) {
    Map<String, String> choice = choiceAtValue(choiceValue);
    if (choice != null) {
      return choice.get("label");
    } else {
      return null;
    }
  }

  public HashMap<String, String> choiceAtValue(String value) {
    for(HashMap<String, String> choice : this.choices) {
      if (choice.get("value") == value) {
        return choice;
      }
    }
    return null;
  }

  private ArrayList<HashMap<String, String>> formatChoiceList(List<Map<String, String>> choices) {
    ArrayList<HashMap<String,String>> cs = new ArrayList<HashMap<String,String>>(choices.toArray().length);
    for(Map<String, String> choice : choices) {
      cs.add(new HashMap<String,String>(choice));
    }
    return cs;
  }

  private String columnPrefix() {
    String column = "".concat(this.key);
    if (this.readableKey != null) {
      column = column.concat("_").concat(this.readableKey);
    }
    return column.concat("_");
  }
}
