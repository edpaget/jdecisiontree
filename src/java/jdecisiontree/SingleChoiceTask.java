package jdecisiontree;

import java.util.HashMap;

public class SingleChoiceTask extends Task {
  @Override
  public String getNext(Object answer) {
    for(HashMap<String, Object> choice : this.choices) {
      if ((choice.get("value") == answer) && (choice.get("next") != null)) {
        return (String) choice.get("next");
      }
    }
    return super.getNext(answer);
  }

}
