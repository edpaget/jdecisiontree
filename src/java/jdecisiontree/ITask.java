package jdecisiontree;

import java.util.ArrayList;
import java.util.HashMap;

public interface ITask {
  public String getKey();
  public ITask withKey(String key);
  public ITask withQuestion(String question);
  public ITask withChoices(ArrayList<HashMap<String, Object>> choices);
}
