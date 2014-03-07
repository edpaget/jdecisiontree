package jdecisiontree;

import java.util.ArrayList;
import java.util.Map;

public interface ITask {
  public String getKey();
  public ITask withKey(String key);
  public ITask withQuestion(String question);
  public ITask withChoices(ArrayList<Map<String, Object>> choices);
  public ITask withReadableKey(String key);
  public ITask withNext(String next);
  public String getNext(Object answer);
}
