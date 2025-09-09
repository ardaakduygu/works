import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;

public class Graph {
    private HashMap<String, Integer> hashTable;
    private int V; // Number of vertices
    private LinkedList<Edge> adjListArray[]; // Adjacency list

    public Graph(int v) {
        V = v;
        adjListArray = new LinkedList[v];
        for (int i = 0; i < v; ++i)
            adjListArray[i] = new LinkedList<>();
    }

    private int hash(String city) {
        // Basit bir hash fonksiyonu. İsterseniz daha karmaşık bir fonksiyon kullanabilirsiniz.
        int hash = 0;
        for (int i = 0; i < city.length(); i++) {
            hash = (hash * 31 + city.charAt(i)) % V;
        }
        return hash;
    }

    public void readFile(String fileName) {
        hashTable = new HashMap<>();
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split("->");
                String cityA = parts[0].trim();
                String[] connections = parts[1].split(",");
                int indexA = getCityIndex(cityA);

                for (String connection : connections) {
                    String[] pair = connection.split(": ");
                    String cityB = pair[0].trim();
                    int distance = Integer.parseInt(pair[1].trim());
                    int indexB = getCityIndex(cityB);

                    addEdge(indexA, indexB, distance);
                  //  addEdge(indexB, indexA, distance);

                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private int getCityIndex(String city) {
        int index = hash(city);
        if (!hashTable.containsKey(city)) {
            hashTable.put(city, index);
        }
        return index;
    }

    private void addEdge(int src, int dest, int weight) {
        Edge newEdge = new Edge(dest, weight);
        adjListArray[src].add(newEdge);
    }

    public void printHashTable() {
        System.out.println("HashTable:");
        for (String city : hashTable.keySet()) {
            int index = hashTable.get(city);
            System.out.println(city + " -> " + index);
        }
    }

    public void printGraph() {
        System.out.println("Graph:");
        for (int i = 0; i < V; ++i) {
            System.out.print(i + " -> ");
            for (Edge edge : adjListArray[i]) {
                System.out.print("(" + edge.destination + ", " + edge.weight + ") ");
            }
            System.out.println();
        }

    }

    public void printGraphWithCities() {
        System.out.println("Graph with Cities:");
        for (int i = 0; i < V; ++i) {
            System.out.print(i + " (" + getCityName(i) + ") -> ");
            for (Edge edge : adjListArray[i]) {
                System.out.print("(" + edge.destination + " " + getCityName(edge.destination) + ", " + edge.weight + ") ");
            }
            System.out.println();
        }
    }

    public boolean isThereAPath(String v1, String v2) {
        Integer indexV1 = hashTable.get(v1);
        Integer indexV2 = hashTable.get(v2);

        if (indexV1 == null || indexV2 == null) {
            System.out.println("Invalid vertices.");
            return false;
        }

        boolean[] visited = new boolean[V];
        return isThereAPathUtil(indexV1, indexV2, visited);
    }

    private boolean isThereAPathUtil(int src, int dest, boolean[] visited) {
        visited[src] = true;

        if (src == dest) {
            return true;
        }

        for (Edge neighbor : adjListArray[src]) {
            if (!visited[neighbor.destination]) {
                if (isThereAPathUtil(neighbor.destination, dest, visited)) {
                    return true;
                }
            }
        }

        return false;
    }

    public int NumberOfSimplePaths(String v1, String v2) {
        Integer indexV1 = hashTable.get(v1);
        Integer indexV2 = hashTable.get(v2);

        if (indexV1 == null || indexV2 == null) {
            System.out.println("Invalid vertices.");
            return 0;
        }

        boolean[] visited = new boolean[V];
        return NumberOfSimplePathsUtil(indexV1, indexV2, visited);
    }

    private int NumberOfSimplePathsUtil(int src, int dest, boolean[] visited) {
        if (src == dest) {
            return 1;
        }

        visited[src] = true;
        int count = 0;

        for (Edge neighbor : adjListArray[src]) {
            if (!visited[neighbor.destination]) {
                count += NumberOfSimplePathsUtil(neighbor.destination, dest, visited);
            }
        }

        visited[src] = false; 
        return count;
    }



    public int WhatIsShortestPathLength(String v1, String v2) {
        Integer indexV1 = hashTable.get(v1);
        Integer indexV2 = hashTable.get(v2);

        if (indexV1 == null || indexV2 == null) {
            System.out.println("Invalid vertices.");
            return -1;
        }

        return BFS(indexV1, indexV2);
    }

    private int BFS(int src, int dest) {
        boolean[] visited = new boolean[V];
        int[] distance = new int[V];

        Queue<Integer> queue = new LinkedList<>();
        queue.add(src);
        visited[src] = true;
        distance[src] = 0;

        while (!queue.isEmpty()) {
            int currentVertex = queue.poll();

            for (Edge neighbor : adjListArray[currentVertex]) {
                if (!visited[neighbor.destination]) {
                    visited[neighbor.destination] = true;
                    distance[neighbor.destination] = distance[currentVertex] + neighbor.weight;
                    queue.add(neighbor.destination);

                    if (neighbor.destination == dest) {
                        // En kısa mesafe bulundu
                        return distance[dest];
                    }
                }
            }
        }

        // BFS tamamlandı ancak v1'den v2'ye bir yol bulunamadı.
        System.out.println(" BFS tamamlandi ancak v1'den v2'ye bir yol bulunamadi.  --x-- " );
        return -1;
    }

    

    public void DFSfromTo(String v1, String v2) {
        Integer indexV1 = hashTable.get(v1);
        Integer indexV2 = hashTable.get(v2);

        if (indexV1 == null || indexV2 == null) {
            System.out.println("Invalid vertices.");
            return;
        }

        boolean[] visited = new boolean[V];
        Stack<Integer> stack = new Stack<>();

        System.out.println("DFS Path from " + v1 + " to " + v2 + ":");
        DFSUtil(indexV1, indexV2, visited, stack);

        if (!stack.isEmpty()) {
            while (!stack.isEmpty()) {
                int vertex = stack.pop();
                System.out.print(getCityName(vertex) + " ");

                if (!stack.isEmpty()) {
                    System.out.print("--(" + getEdgeWeight(stack.peek(), vertex) + " km)--> ");
                }
            }
            System.out.println();
        } else {
            System.out.println("No path found from " + v1 + " to " + v2);
        }
    }


    private void DFSUtil(int src, int dest, boolean[] visited, Stack<Integer> stack) {
        visited[src] = true;
        stack.push(src);

        if (src == dest) {
            return;
        }

        for (Edge neighbor : adjListArray[src]) {
            if (!visited[neighbor.destination]) {
                DFSUtil(neighbor.destination, dest, visited, stack);
            }
        }

        if (stack.peek() != dest) {
            stack.pop();
        }
    }

    private String getCityName(int index) {
        for (String city : hashTable.keySet()) {
            if (hashTable.get(city) == index) {
                return city;
            }
        }
        return null;
    }

    private int getEdgeWeight(int src, int dest) {
        for (Edge edge : adjListArray[src]) {
            if (edge.destination == dest) {
                return edge.weight;
            }
        }
        return -1; // Handle the case where the edge does not exist
    }

    public void BFSfromTo(String v1, String v2) {
        Integer indexV1 = hashTable.get(v1);
        Integer indexV2 = hashTable.get(v2);

        if (indexV1 == null || indexV2 == null) {
            System.out.println("Invalid vertices.");
            return;
        }

        boolean[] visited = new boolean[V];
        Queue<Integer> queue = new LinkedList<>();

        System.out.println("BFS Path from " + v1 + " to " + v2 + ":");
        BFSUtil(indexV1, indexV2, visited, queue);

        if (!queue.isEmpty()) {
            while (!queue.isEmpty()) {
                int vertex = queue.poll();
                System.out.print(getCityName(vertex) + " ");

                if (!queue.isEmpty()) {
                    System.out.print("--(" + getEdgeWeight(vertex, queue.peek()) + " km)--> ");
                }
            }
            System.out.println();
        } else {
            System.out.println("No path found from " + v1 + " to " + v2);
        }
    }

    private void BFSUtil(int src, int dest, boolean[] visited, Queue<Integer> queue) {
        visited[src] = true;
        queue.add(src);

        while (!queue.isEmpty()) {
            int currentVertex = queue.poll();

            for (Edge neighbor : adjListArray[currentVertex]) {
                if (!visited[neighbor.destination]) {
                    visited[neighbor.destination] = true;
                    queue.add(neighbor.destination);

                    if (neighbor.destination == dest) {
                        // BFS tamamlandı, hedefe ulaşıldı
                        return;
                    }
                }
            }
        }
    }
   
    public List<String> Neighbors(String v1) {
        Integer indexV1 = hashTable.get(v1);

        if (indexV1 == null) {
            System.out.println("Invalid vertex.");
            return null;
        }

        List<String> neighbors = new ArrayList<>();
        for (Edge neighbor : adjListArray[indexV1]) {
            neighbors.add(getCityName(neighbor.destination));
        }
        return neighbors;
    }


    public List<String> HighestDegree() {
        int maxDegree = -1;
        List<String> highestDegreeVertices = new ArrayList<>();

        for (int i = 0; i < V; i++) {
            int degree = adjListArray[i].size();
            if (degree > maxDegree) {
                maxDegree = degree;
                highestDegreeVertices.clear();
                highestDegreeVertices.add(getCityName(i));
            } else if (degree == maxDegree) {
                highestDegreeVertices.add(getCityName(i));
            }
        }

        return highestDegreeVertices;
    }

    public boolean IsDirected() {
        for (int i = 0; i < V; i++) {
            for (Edge neighbor : adjListArray[i]) {
                // Eğer bir kenar (edge) bulunursa ve başlangıç düğümü (source) ve
                // hedef düğümü (destination) farklı ise, graf yönlüdür.
                if (i != neighbor.destination) {
                    return true;
                }
            }
        }
        return false;
    }
    

    public boolean AreTheyAdjacent(String v1, String v2) {
        Integer indexV1 = hashTable.get(v1);
        Integer indexV2 = hashTable.get(v2);

        if (indexV1 == null || indexV2 == null) {
            System.out.println("Invalid vertices.");
            return false;
        }

        List<String> neighbors = Neighbors(v1);
        return neighbors.contains(v2);
    }

    public boolean IsThereACycle(String v1) {
        Integer indexV1 = hashTable.get(v1);
    
        if (indexV1 == null) {
            System.out.println("Invalid vertex.");
            return false;
        }
    
        boolean[] visited = new boolean[V];
        boolean[] recursionStack = new boolean[V];
    
        for (int i = 0; i < V; i++) {
            if (!visited[i] && DFSForCycle(i, visited, recursionStack)) {
                return true;
            }
        }
    
        return false;
    }
    
    private boolean DFSForCycle(int currentVertex, boolean[] visited, boolean[] recursionStack) {
        visited[currentVertex] = true;
        recursionStack[currentVertex] = true;
    
        for (Edge neighbor : adjListArray[currentVertex]) {
            int neighborIndex = neighbor.destination;
    
            if (!visited[neighborIndex]) {
                if (DFSForCycle(neighborIndex, visited, recursionStack)) {
                    return true;
                }
            } else if (recursionStack[neighborIndex]) {
                // Eğer ziyaret edilen bir düğüm, rekürsif çağrı yığınında ise, bir döngü bulunmuştur.
                return true;
            }
        }
    
        recursionStack[currentVertex] = false; // Bu düğümü yığından çıkar.
        return false;
    }
    

    public void NumberOfVerticesInComponent(String v1) {
        Integer indexV1 = hashTable.get(v1);

        if (indexV1 == null) {
            System.out.println("Invalid vertex.");
            return;
        }

        boolean[] visited = new boolean[V];
        int componentSize = DFSForComponentSize(indexV1, visited);

        System.out.println("Number of vertices in the component that contains " + v1 + ": " + componentSize);
    }

    private int DFSForComponentSize(int currentVertex, boolean[] visited) {
        visited[currentVertex] = true;
        int componentSize = 1;

        for (Edge neighbor : adjListArray[currentVertex]) {
            if (!visited[neighbor.destination]) {
                componentSize += DFSForComponentSize(neighbor.destination, visited);
            }
        }

        return componentSize;
    }

    class Edge {
        int destination;
        int weight;

        public Edge(int destination, int weight) {
            this.destination = destination;
            this.weight = weight;
        }
    }
}
