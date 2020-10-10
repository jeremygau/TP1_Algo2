import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class FileParser {

    Graph<String> graph;

    /**
     * Lit le fichier donné en entrée et créé un graphe associé au systeme d'equation du fichier
     * @param filename le nom du fichier
     * @return le graphe associé au systeme d'equation du fichier
     * @throws IOException
     */
    public Graph<String> createGraph(String filename) throws IOException {
        String commentaire;
        int nbVariables = 1;
        int nbClauses;
        ArrayList<Pair<Integer, Integer>> implications = new ArrayList<>();

        BufferedReader bufferedReader = new BufferedReader(new FileReader(filename));
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            String[] champs = line.split(" ");
            switch (champs[0]) {
                case "c" :
                    commentaire = champs[1];
                    break;
                case "p" :
                    nbVariables = Integer.parseInt(champs[2]);
                    nbClauses = Integer.parseInt(champs[3]);
                    break;
                default:
                    implications.add(new Pair<>(Integer.parseInt(champs[0]), Integer.parseInt(champs[1])));
                    break;
            }
        }
        return constructGraph(nbVariables, implications);
    }

    /**
     * Construit le graphe à partir des données données en entrées
     * @param nbVertices le nombres de sommets du graphe
     * @param implications la liste des implications
     * @return le graphe construit à partir de nbVertices et implications
     */
    public Graph<String> constructGraph(int nbVertices, ArrayList<Pair<Integer, Integer>> implications) {
        this.graph = new Graph<>(nbVertices * 2);
        for (Pair<Integer, Integer> pair : implications) {
            int key = pair.getKey();
            int value = pair.getValue();

            graph.addArc(convert(-key), convert(value), String.format("\"%d => %d\"", -key, value));
            graph.addArc(convert(-value), convert(key), String.format("\"%d => %d\"", -value, key));
        }
        return graph;
    }

    /**
     * Convertit les valeurs lues dans le fichier pour qu'elles soient utilisées dans le graphe
     * @param value : la valeur lue dans le graphe
     * @return la valeur convertie
     */
    private int convert(int value) {
        if (value < 0) return value + this.graph.order();
        else return value - 1;
    }
}
