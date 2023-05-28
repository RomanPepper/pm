import java.util.*;

public class Task {
    private int ID;
    private String name;
    private int duration;
    private int workersNeeded;
    private List<Task> predecessorList = new ArrayList<>();
    private int startTime = 0;
    private int endTime = Integer.MAX_VALUE;

    Task(int ID, String name, int duration, int workersNeeded) {
        this.ID = ID;
        this.name = name;
        this.duration = duration;
        this.workersNeeded = workersNeeded;
    }

    public List<Task> getPredecessorList() {
        return predecessorList;
    }

    public void setPredecessorList(List<Task> predecessorList) {
        this.predecessorList = predecessorList;
    }

    public static boolean arePredecessorsDone(Task task, List<Task> finishedTasks) {
        int count = 0;
        for (Task task1 : task.predecessorList) {
            for (Task task2 : finishedTasks) {
                if (task1.equals(task2)) {
                    count++;
                }
            }
        }
        return count == task.predecessorList.size();
    }

    public static int getNeededWorkersCount(List<Task> list) {
        int count = 0;
        for (Task task : list) {
            count += task.workersNeeded;
        }
        return count;
    }

    public static Queue<Task> parseIDList(List<Integer> idList, List<Task> taskList) {
        Queue<Task> taskQueue = new LinkedList<>();

        for (int i = 0; i < idList.size(); i++) {
            int currId = idList.get(i);
            for (Task task : taskList) {
                if (task.ID == currId) {
                    taskQueue.add(task);
                }
            }
        }

        return taskQueue;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getWorkersNeeded() {
        return workersNeeded;
    }

    public void setWorkersNeeded(int workersNeeded) {
        this.workersNeeded = workersNeeded;
    }

    public int getStartTime() {
        return startTime;
    }

    public void setStartTime(int startTime) {
        this.startTime = startTime;
    }

    public int getEndTime() {
        return endTime;
    }

    public void setEndTime(int endTime) {
        this.endTime = endTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return ID == task.ID && duration == task.duration && workersNeeded == task.workersNeeded && startTime == task.startTime && endTime == task.endTime && Objects.equals(name, task.name) && Objects.equals(predecessorList, task.predecessorList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ID, name, duration, workersNeeded, predecessorList, startTime, endTime);
    }
}
