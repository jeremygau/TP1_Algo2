import java.util.ArrayList;

public class Solver<Label> {

    /**
     * Resoud le systeme d'equation représenter par le graphe
     * @return true si le systeme est consistant, false sinon.
     */
    public boolean solve(    Graph<Label> graph) {
        ArrayList<ArrayList<Integer>> stronglyConnectedComponents = graph.searchStronglyConnectedComponents();
        for (ArrayList<Integer> component : stronglyConnectedComponents) {
            for (int sommet1 : component) {
                for (int sommet2 : component) {
                    if (sommet1 + sommet2 == graph.order() - 1) return false;
                }
            }
        }
        return true;
    }
}
