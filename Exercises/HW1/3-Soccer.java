import java.util.*;
import java.util.Queue;

public class Bfs {
    private LinkedList<Integer> adjList[];
    private Queue<Integer> queue = new LinkedList();

    public static void main(String args[]) {
        final int begin = 0;
        final int end = 10;
        final int vertexNumber = 11;
        boolean hasEdge = false;
        int hop[] = new int[vertexNumber];
        hop[end] = 65000;
        Bfs obj = new Bfs();
        obj.makeGraph(vertexNumber);
        Scanner scan = new Scanner(System.in);
        int t = scan.nextInt();
        int m = scan.nextInt();
        scan.nextLine();

        for (int i = 0; i < m; i++) {
            int u = scan.nextInt() - 1;
            int v = scan.nextInt() - 1;
            scan.nextLine();
            if (u >= 0 && u <= 10 && v >= 0 && v <= 10 && u != v) {
                obj.addEdgeToGraph(u, v);
                hasEdge = true;
            }
        }
        if (!hasEdge) return;
        obj.BFtraversal(begin, hop);
        if (t != 0 && hop[end] != 1) System.out.println(90 / (hop[end] - 1) / t);
    }

    public void makeGraph(int number) {
        adjList = new LinkedList[number];
        for (int i = 0; i < number; i++) {
            adjList[i] = new LinkedList();
        }
    }

    public boolean addEdgeToGraph(int u, int v) {
        Iterator<Integer> iterator = adjList[u].listIterator();
        while (iterator.hasNext()) {
            if (iterator.next() == v) return false;
        }
        adjList[u].add(v);
        adjList[v].add(u);
        return true;
    }

    public void BFtraversal(int v, int[] hop) {
        queue.add(v);
        hop[v] = 1;
        while (!queue.isEmpty()) {
            int queueRemove = queue.remove();
            int hopQueueRemove = hop[queueRemove];
            Iterator<Integer> iterator = adjList[queueRemove].listIterator();
            while (iterator.hasNext()) {
                int vetrtexNext = iterator.next();
                if (hop[vetrtexNext] == 0) {
                    queue.add(vetrtexNext);
                    hop[vetrtexNext] = hopQueueRemove + 1;
                } else if (hop[vetrtexNext] > hopQueueRemove + 1) hop[vetrtexNext] = hopQueueRemove + 1;
            }
        }
    }

}