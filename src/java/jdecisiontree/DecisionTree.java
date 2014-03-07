package jdecisiontree;

import java.util.HashMap;
import java.util.Map;
import java.util.Iterator;
import java.util.Collection;
import java.util.ArrayList;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.core.JsonProcessingException;

public class DecisionTree {
  public String name;
  private String firstTask;
  private Map<String, ITask> tasks;

  public DecisionTree() {}

  public DecisionTree(String name, String firstTask, Map<String, ITask> tasks) {
    this.name = name;
    this.firstTask = firstTask;
    this.tasks = tasks;
  }

  public DecisionTree(String name, String firstTask, Collection<ITask> tasks) {
    this.name = name;
    this.firstTask = firstTask;
    this.tasks = taskCollectionToMap(tasks);
  }

  public DecisionTree withName(String name) {
    this.name = name;
    return this;
  }

  public DecisionTree withFirstTask(String taskKey) {
    this.firstTask = taskKey;
    return this;
  }

  public DecisionTree withTasks(Map<String, ITask> tasks) {
    this.tasks = tasks;
    return this;
  }

  public DecisionTree withTasks(Collection<ITask> tasks) {
    this.tasks = taskCollectionToMap(tasks);
    return this;
  }

  public Map<String, ITask> getTasks() {
    return this.tasks;
  }

  public String nextStep(String current, String answer) {
    ITask currentTask = tasks.get(current);
    return currentTask.getNext(answer);
  }

  public ArrayList<String> toColumns() {
    ArrayList<String> columns = new ArrayList<String>(this.tasks.size());
    for(ITask task : this.tasks.values()) {
      columns.add(task.toCountColumn());
      columns.addAll(task.toAnswerColumns());
    }
    return columns;
  }

  public static DecisionTree FromJSON(String json) throws java.io.IOException {
    ObjectMapper mapper = new ObjectMapper();
    JsonNode root = mapper.readTree(json);

    JsonNode JSONTasks = root.get("tasks");
    Map<String, ITask> tasks = new HashMap<String, ITask>();
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

  private Map<String, ITask> taskCollectionToMap(Collection<ITask> tasks) {
    Map<String, ITask> map = new HashMap<String, ITask>(tasks.toArray().length);
    for(ITask task : tasks) {
      map.put(task.getKey(), task);
    }
    return map;
  }
}
