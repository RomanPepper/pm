import java.io.*;
import java.util.*;

//  Этот класс представляет ориентированный граф с использованием списка смежности
class Graph {
    int vertices;
    List<Integer>[] adjList;

    public void addEdge(int u, int v) {
        adjList[u].add(v);
    }

    public Graph(int vertices) {
        this.vertices = vertices;
        adjList = new List[vertices];

        for (int i = 0; i < vertices; i++) {
            adjList[i] = new ArrayList<>();
        }
    }

    public List<Integer> topologicalSort() {
        List<Integer> sorted = new ArrayList<>();
        Queue<Integer> queue = new LinkedList<>();
        int[] incomingEdges = new int[this.vertices];

        // Подсчет входящих ребер для каждой вершины
        for (int i = 0; i < this.vertices; i++) {
            for (int j : this.adjList[i]) {
                incomingEdges[j]++;
            }
        }

        // Добавляем вершины без входящих ребер в очередь
        for (int i = 0; i < this.vertices; i++) {
            if (incomingEdges[i] == 0) {
                queue.offer(i);
            }
        }

        // Обрабатываем вершины в очереди
        while (!queue.isEmpty()) {
            int vertex = queue.poll();
            sorted.add(vertex);

            for (int i : this.adjList[vertex]) {
                incomingEdges[i]--;

                if (incomingEdges[i] == 0) {
                    queue.offer(i);
                }
            }
        }

        return sorted;
    }
}