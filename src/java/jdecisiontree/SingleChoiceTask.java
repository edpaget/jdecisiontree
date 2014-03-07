package jdecisiontree;

import java.util.HashMap;

public class SingleChoiceTask extends Task {
  @Override
  public String getNext(Object answer) {
    HashMap<String, Object> choice = choiceAtValue(answer);

    if ((choice != null) && (choice.get("next") != null)) {
      return (String) choice.get("next");
    } else {
      return super.getNext(answer);
    }
  }
}
