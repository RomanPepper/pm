public class Worker {
    private int freeAfter;
    private Task task;

    public void setTask(Task task) {
        this.task = task;
        this.freeAfter = task.getEndTime();
    }

    public int getFreeAfter() {
        if (freeAfter == 0) {
            return Integer.MAX_VALUE;
        } else {
            return freeAfter;
        }
    }

    public boolean isFreeNow(int currentTime) {
        return currentTime >= freeAfter;
    }

    public static int getFreeWorkersCount(Worker[] list, int currentTime) {
        int count = 0;
        for (Worker worker: list) {
            if (worker.isFreeNow(currentTime)) {
                count++;
            }
        }
        return count;
    }
}
