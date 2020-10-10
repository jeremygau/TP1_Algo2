import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Stack;

public class Graph<Label> {

    class Edge {
        public int source;
        public int destination;
        public Label label;

        public Edge(int from, int to, Label label) {
            this.source = from;
            this.destination = to;
            this.label = label;
        }
    }

    private int cardinal;
    private ArrayList<LinkedList<Edge>> incidence;


    public Graph(int size) {
        cardinal = size;
        incidence = new ArrayList<>(size + 1);
        for (int i = 0; i < cardinal; i++) {
            incidence.add(i, new LinkedList<>());
        }
    }

    public int order() {
        return cardinal;
    }

    public void addArc(int source, int destination, Label label) {
        incidence.get(source).addLast(new Edge(source,destination,label));
    }

    public String toString() {
        String result = "";
        result = result.concat(cardinal + "\n");
        for (int i = 0; i < cardinal; i++) {
            for (Edge e : incidence.get(i)) {
                result = result.concat(e.source + " " + e.destination + " "
                        + e.label.toString() + "\n");
            }
        }
        return result;
    }

    /**
     * Retourne le parcours en profondeur de ce graphe
     * @return le parcours en profondeur de ce graphe
     */
    public ArrayList<Integer> depthFirstSearch() {
        ArrayList<Integer> vertices = new ArrayList<>();
        ArrayList<Integer> visited = new ArrayList<>();
        for (int vertex = 0; vertex < cardinal; vertex++) {
            if (!visited.contains(vertex)) {
                depthFirstExplore(vertex, visited, vertices);
            }
        }
        return vertices;
    }

    /**
     * Retourne le parcours en profondeur de ce graphe en partant d'un sommet en évitant les sommets deja visités
     * @param start le sommet de départ
     * @param visited les sommets déjà visités
     * @return le parcours en profondeur de ce graphe en partant du sommet start en évitant les sommets deja visités
     */
    public ArrayList<Integer> depthFirstSearchForOneVertex(int start, ArrayList<Integer> visited) {
        ArrayList<Integer> vertices = new ArrayList<>();
        depthFirstExplore(start, visited, vertices);
        return vertices;
    }

    /**
     * Explore recursivement le graphe en partant d'un sommet et ajoute les sommets visités à la liste des visités
     * @param vertex le sommet de départ
     * @param visited les sommets déjà visités
     * @param vertices la liste en construction des sommets rangés dans l'ordre du parcours en profondeur
     */
    private void depthFirstExplore(int vertex, ArrayList<Integer> visited, ArrayList<Integer> vertices) {
        visited.add(vertex);
        vertices.add(vertex);
        for (Edge outEdge: incidence.get(vertex)) {
            int neighbour = outEdge.destination;
            if (!visited.contains(neighbour)) {
                depthFirstExplore(neighbour, visited, vertices);
            }
        }
    }

    /**
     * Retourne un tri topologique de ce graphe
     * @return un tri topologique de ce graphe
     */
    public Stack<Integer> topologicalSort() {
        ArrayList<Integer> visited = new ArrayList<>();
        Stack<Integer> stack = new Stack<>();
        for (int vertex = 0; vertex < cardinal; vertex++) {
            if (!visited.contains(vertex)) {
                topologicalExplore(vertex, stack, visited);
            }
        }
        return stack;
    }

    /**
     * Explore recursivement le graphe en partant d'un sommet et ajoute les sommets visités à la pile des visités
     * @param vertex le sommet de départ
     * @param sorted la pile en construction de sommets rangés dans l'ordre topologique
     * @param visited les sommets déjà visités
     */
    private void topologicalExplore(int vertex, Stack<Integer> sorted, ArrayList<Integer> visited) {
        visited.add(vertex);
        for (Edge outEdge: incidence.get(vertex)) {
            if (!visited.contains(outEdge.destination)) {
                topologicalExplore(outEdge.destination, sorted, visited);
            }
        }
        sorted.push(vertex);
    }

    /**
     * Retourne le transposé de ce graphe
     * @return le transposé de ce graphe
     */
    public Graph<Label> transpose() {
        Graph<Label> transposedGraph = new Graph<>(this.cardinal);
        for (LinkedList<Edge> edges : incidence) {
            for (Edge edge : edges) {
                StringBuilder newLabel = new StringBuilder((String)edge.label);
                transposedGraph.addArc(edge.destination, edge.source, (Label)newLabel.reverse().toString());
            }
        }
        return transposedGraph;
    }

    /**
     * Cherche les composantes fortement connexes de ce graphe
     * @return les composantes fortement connexes de ce graphe
     */
    public ArrayList<ArrayList<Integer>> searchStronglyConnectedComponents() {
        ArrayList<ArrayList<Integer>> stronglyConnectedComponents = new ArrayList<>();
        ArrayList<Integer> visited = new ArrayList<>();
        Stack<Integer> topologicalSort = this.topologicalSort();
        Graph<Label> transposedGraph = this.transpose();
        while (!topologicalSort.isEmpty()) {
            int vertex = topologicalSort.pop();
            if (!visited.contains(vertex)) {
                visited.add(vertex);
                ArrayList<Integer> vertices = transposedGraph.depthFirstSearchForOneVertex(vertex, visited);
                stronglyConnectedComponents.add(vertices);
                visited.addAll(vertices);
            }
        }
        return stronglyConnectedComponents;
    }
}
