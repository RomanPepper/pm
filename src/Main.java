import Utils.FileUtils;

import java.io.IOException;
import java.util.*;

public class Main {
    private static final String inputDataRegular = "\\s*;\\s*";

    public static void main(String[] params) throws IOException {
        // 1) Обработка входных данных
        List<String> inputLines = FileUtils.readAllLinesFromFile("input1.txt");


        int n = Integer.parseInt(inputLines.get(0).split(inputDataRegular)[0]);
        int m = Integer.parseInt(inputLines.get(0).split(inputDataRegular)[1]);
        List<Task> taskList = new ArrayList<>();
        inputLines.remove(0);

        //1.1) Соберём таск-лист из входных данных
        int localID = 0;
        for (String currLine : inputLines) {
            String[] taskTextArray = currLine.split(inputDataRegular);
            String name = taskTextArray[0];
            int duration = Integer.parseInt(taskTextArray[1]);
            int workersNeeded = Integer.parseInt(taskTextArray[2]);

            taskList.add(new Task(localID, name, duration, workersNeeded));
            localID++;
        }

        //1.2) Добавим предшественников каждой из задач
        for (String currLine : inputLines) {
            //Разберём входные параметры
            String[] taskTextArray = currLine.split(inputDataRegular);
            String name = taskTextArray[0];

            //Соберём список задач-предшественников
            List<Task> prerequisites = new ArrayList<>();
            String[] prerequisitesArr = taskTextArray[3].replaceAll("\\[", "").replaceAll("\\]", "").split("\\s*,\\s*");

            if (!(prerequisitesArr.length == 0 || prerequisitesArr.length == 1 && prerequisitesArr[0].equals(""))) {
                for (String prerequisiteStr : prerequisitesArr) {
                    for (Task task : taskList) {
                        if (task.getName().equals(prerequisiteStr.replaceAll("\"", ""))) {
                            prerequisites.add(task);
                        }
                    }
                }
            }

            //Найдём задачу, для которой сейчас собрали список задач-предшественников
            Task task = null;
            for (Task currTask : taskList) {
                if (currTask.getName().equals(name)) {
                    task = currTask;
                    break;
                }
            }
            task.setPredecessorList(prerequisites);
        }

        //2) Логика
        //2.1) Выполним топологическую сортировку и по её итогам заполним очередь выполнения задач
        Graph currentGraph = new Graph(n);

        for (int i = 0; i < n; i++) {
            Task taskNext = taskList.get(i);
            for (int j = 0; j < taskNext.getPredecessorList().size(); j++) {
                // j-ый приходится предшественником i-ому элементу
                Task taskPred = taskNext.getPredecessorList().get(j);
                currentGraph.addEdge(taskPred.getID(), taskNext.getID());
            }
        }

        List<Integer> sortedIDArray = currentGraph.topologicalSort();
        Queue<Task> queue = Task.parseIDList(sortedIDArray, taskList); //Очередь на выполнение


        //2.2) Симуляция процесса выполнения задач с определением временных промежутков для каждой из задач
        int currentTime = 0;

        //Массив работников
        Worker[] workersList = new Worker[m];
        for (int i = 0; i < m; i++) {
            workersList[i] = new Worker();
        }

        List<Task> unfinishedTasks = new ArrayList<>();
        List<Task> finishedTasks = new ArrayList<>();

        while (!queue.isEmpty() || !unfinishedTasks.isEmpty()) {
            // Список задач, которые будут сейчас (на этой итерации) выполняться
            List<Task> executableTasks = new ArrayList<>();
            if (!unfinishedTasks.isEmpty()) {
                executableTasks.addAll(unfinishedTasks);
                unfinishedTasks.clear();
            }
            while (!queue.isEmpty() && Task.getNeededWorkersCount(executableTasks) + queue.peek().getWorkersNeeded() < Worker.getFreeWorkersCount(workersList, currentTime)) {
                if (Task.arePredecessorsDone(queue.peek(), finishedTasks)) {
                    Task task = queue.poll();
                    executableTasks.add(task);
                } else {
                    break;
                }
            }
            if (executableTasks.isEmpty()) { // Нет доступных задач в данный момент времени, нужно дождаться первой завершившейся задачи
                int nextFreeTime = Integer.MAX_VALUE;
                for (Worker worker : workersList) {
                    nextFreeTime = Math.min(worker.getFreeAfter(), nextFreeTime);
                }
                currentTime = nextFreeTime;
                continue;
            }


            // Назначаем выполняемые задачи свободным исполнителям
            for (Task task : executableTasks) {
                if (task.getStartTime() == 0 && task.getEndTime() == Integer.MAX_VALUE) {
                    //Назначаем время начала и окончания
                    task.setStartTime(currentTime);
                    task.setEndTime(currentTime + task.getDuration());

                    //Выбираем подходящих исполнителей и определяем им задачи
                    for (int i = 0; i < task.getWorkersNeeded(); i++) {
                        for (Worker worker : workersList) {
                            if (worker.isFreeNow(currentTime)) {
                                worker.setTask(task);
                                break;
                            }
                        }
                    }
                    System.out.println(task.getName() + " starts at "
                            + task.getStartTime() + " and ends at " + task.getEndTime());
                }
            }

            // Выполняем сдвиг вперед по временной оси
            int nextFreeTime = Integer.MAX_VALUE;
            for (Worker worker : workersList) {
                if (worker.getFreeAfter() > currentTime) {
                    nextFreeTime = Math.min(nextFreeTime, worker.getFreeAfter());
                }
            }
            currentTime = nextFreeTime;
            // После сдвига сортируем задачи на законченные и незаконченные
            for (Task task : executableTasks) {
                if (nextFreeTime < task.getEndTime()) {
                    unfinishedTasks.add(task);
                } else {
                    finishedTasks.add(task);
                }
            }
        }
    }
}