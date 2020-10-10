import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {

        FileParser fileParser = new FileParser();

        Graph<String> graph_formule_2_sat = fileParser.createGraph("formule-2-sat.txt");
        Graph<String> graph_formule_pos = fileParser.createGraph("formule-pos.txt");
        Graph<String> graph_formule_neg = fileParser.createGraph("formule-neg.txt");
        Graph<String> graph_formule_conflict = fileParser.createGraph("formule-conflict.txt");

        Solver<String> solver = new Solver<>();

        System.out.println(solver.solve(graph_formule_2_sat));
        System.out.println(solver.solve(graph_formule_pos));
        System.out.println(solver.solve(graph_formule_neg));
        System.out.println(solver.solve(graph_formule_conflict));
    }
}
