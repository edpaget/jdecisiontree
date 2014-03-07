package jdecisiontree;

import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.HashMap;

public interface ITask {
  public String getKey();
  public ITask withKey(String key);
  public ITask withQuestion(String question);
  public ITask withChoices(ArrayList<HashMap<String, String>> choices);
  public ITask withChoices(List<Map<String, String>> choices);
  public ITask withReadableKey(String key);
  public ITask withNext(String next);
  public String getNext(Object answer);
  public String getNext();
  public String toCountColumn();
  public ArrayList<String> toAnswerColumns();
  public Object getChoiceLabel(Object choiceValue);
  public HashMap<String, String> choiceAtValue(Object value);
}
