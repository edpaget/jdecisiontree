package jdecisiontree;

import java.util.HashMap;
import java.util.Map;
import java.util.Iterator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.core.JsonProcessingException;

public class DecisionTree {
  public String name;
  private String firstTask;
  private HashMap<String, ITask> tasks;

  public DecisionTree() {}

  public DecisionTree(String name, String firstTask, HashMap<String, ITask> tasks) {
    this.name = name;
    this.firstTask = firstTask;
    this.tasks = tasks;
  }

  public DecisionTree(String name, String firstTask, ITask[] tasks) {
    this.name = name;
    this.firstTask = firstTask;
    this.tasks = taskListToMap(tasks);
  }

  public DecisionTree withName(String name) {
    this.name = name;
    return this;
  }

  public DecisionTree withFirstTask(String taskKey) {
    this.firstTask = taskKey;
    return this;
  }

  public DecisionTree withTasks(HashMap<String, ITask> tasks) {
    this.tasks = tasks;
    return this;
  }

  public DecisionTree withTasks(ITask[] tasks) {
    this.tasks = taskListToMap(tasks);
    return this;
  }

  public HashMap<String, ITask> getTasks() {
    return this.tasks;
  }

  public static DecisionTree FromJSON(String json) throws java.io.IOException {
    ObjectMapper mapper = new ObjectMapper();
    JsonNode root = mapper.readTree(json);

    JsonNode JSONTasks = root.get("tasks");
    HashMap<String, ITask> tasks = new HashMap<String, ITask>();
    for(Iterator<Map.Entry<String, JsonNode>> f = JSONTasks.fields(); f.hasNext();) {
      Map.Entry<String, JsonNode> field = f.next();
      ITask task = taskFromJSONNodeByType(field.getKey(), field.getValue());
      tasks.put(field.getKey(), task);
    } 

    DecisionTree tree = new DecisionTree()
      .withName(root.get("id").asText())
      .withFirstTask(root.get("firstTask").asText())
      .withTasks(tasks);

    return tree;
  }

  private static ITask taskFromJSONNodeByType(String key, JsonNode task) {
    ObjectMapper mp = new ObjectMapper();
    ITask t;

    try {
      switch(task.get("type").asText()) {
        case "button" :
          t = mp.treeToValue(task, SingleChoiceTask.class);
          break;
        case "radio" :
          t = mp.treeToValue(task, SingleChoiceTask.class);
          break;
        case "checkbox" :
          t = mp.treeToValue(task, MultiChoiceTask.class);
          break;
        case "drawing" :
          t = mp.treeToValue(task, MultiChoiceTask.class);
          break;
        default :
          t = mp.treeToValue(task, SingleChoiceTask.class);
          break;
      }
    } catch (JsonProcessingException e) {
      return null;
    }

    return t.withKey(key);
  }

  private HashMap<String, ITask> taskListToMap(ITask[] tasks) {
    HashMap<String, ITask> map = new HashMap<String, ITask>(tasks.length);
    for(ITask task : tasks) {
      map.put(task.getKey(), task);
    }
    return map;
  }

}
