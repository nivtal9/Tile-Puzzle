import java.io.IOException;
import java.io.PrintWriter;
import java.util.Hashtable;
import java.util.Set;

public class Output_Lists {
    private PrintWriter write;
    private int iteration;
    Output_Lists() throws IOException {
        write =new PrintWriter("output.txt");
        iteration=0;
    }

    /**
     * for A* / BFS / DFIF
     * @param hash
     */
    void append_1(Hashtable<Node[][], String> hash){
        int count=0;
        System.out.println("I   t   e   r   a   t   i   o   n   "+iteration+"   :");
        System.out.println();
        Set<Node[][]> openkeys = hash.keySet();
        for (Node[][] nodes:openkeys) {
            System.out.println("GameBoard " + count + " :");
            for (Node[] node : nodes) {
                for (int j = 0; j < nodes[0].length; j++) {
                    if (j == nodes[0].length - 1) {
                        System.out.print(node[j] + ". ");
                    } else {
                        System.out.print(node[j] + ", ");
                    }
                }
                System.out.println();
            }
            count++;
        }
        iteration++;
        System.out.println();
    }

    /**
     * for IDA* / DFBnB
     * @param hash
     */
    void append_2(Hashtable<GameBoard,String> hash){
        int count=0;
        System.out.println("I   t   e   r   a   t   i   o   n   "+iteration+"   :");
        System.out.println();
        Set<GameBoard> openkeys = hash.keySet();
        for (GameBoard nodes:openkeys) {
            System.out.println("GameBoard " + count + " :");
            for (Node[] node : nodes.getGame_board()) {
                for (int j = 0; j < nodes.getGame_board()[0].length; j++) {
                    if (j == nodes.getGame_board()[0].length - 1) {
                        System.out.print(node[j] + ". ");
                    } else {
                        System.out.print(node[j] + ", ");
                    }
                }
                System.out.println();
            }
            count++;
        }
        iteration++;
        System.out.println();
    }

    /**
     * append for Solution (all algorithms)
     * @param Sol
     */
    void append_3(StringBuilder Sol){
        write.println(Sol);
    }
    void Close(){
        write.close();
    }
}
