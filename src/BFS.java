import java.io.IOException;
import java.util.*;

public class BFS extends Algorithms{
    private boolean open;
    private boolean time;
    private GameBoard game_board;
    private GameBoard goal_board;
    private int count;

    BFS(boolean open, boolean time, GameBoard game_board, GameBoard goal_board, List<Integer> red) {
        super(red);
        this.time=time;
        this.open=open;
        this.goal_board=goal_board;
        this.game_board=game_board;
    }

    void BFS_alg() throws IOException {
        StringBuilder str = new StringBuilder();
        boolean GoalFound = false;
        int num = 1;count=0;
        long start = -1;
        String Path = null;
        Output_Lists output=new Output_Lists();
        if (time) {
            start = System.nanoTime();
        }
        Hashtable<Node[][], String> openlist = new Hashtable<>();
        Hashtable<Node[][], String> closedlist = new Hashtable<>();
        Queue<GameBoard> queue = new LinkedList<>();
        queue.add(game_board);
        openlist.put(game_board.getGame_board(),"");
        while (!queue.isEmpty() && !GoalFound) {
            game_board=queue.poll();
            if (!Arrays.deepToString(goal_board.getGame_board()).equals(Arrays.deepToString(game_board.getGame_board()))) {
                Node[][] ans = MoveBlock(game_board.getGame_board(), Direction.LEFT, game_board.getBlankX(), game_board.getBlankY(),openlist,closedlist);
                if (ans!=null) {
                    printans(ans,openlist);
                    num++;
                    if (!Arrays.deepToString(ans).equals(Arrays.deepToString(goal_board.getGame_board()))) {
                        queue.add(new GameBoard(ans, game_board.getBlankX(), game_board.getBlankY() - 1));
                    }
                    else {
                        game_board=new GameBoard(ans, game_board.getBlankX(), game_board.getBlankY() - 1);
                        Path = openlist.get(game_board.getGame_board());
                        GoalFound = true;
                    }
                }
                if (!GoalFound) {
                    ans = MoveBlock(game_board.getGame_board(), Direction.UP, game_board.getBlankX(), game_board.getBlankY(),openlist,closedlist);
                    if (ans!=null) {
                        printans(ans,openlist);
                        num++;
                        if (!Arrays.deepToString(ans).equals(Arrays.deepToString(goal_board.getGame_board()))) {
                            queue.add(new GameBoard(ans, game_board.getBlankX() - 1, game_board.getBlankY()));
                        }
                        else {
                            game_board=new GameBoard(ans, game_board.getBlankX() - 1, game_board.getBlankY());
                            Path = openlist.get(game_board.getGame_board());
                            GoalFound = true;
                        }
                    }

                }
                if(!GoalFound) {
                    ans = MoveBlock(game_board.getGame_board(), Direction.RIGHT, game_board.getBlankX(), game_board.getBlankY(),openlist,closedlist);
                    if (ans!=null) {
                        printans(ans,openlist);
                        num++;
                        if (!Arrays.deepToString(ans).equals(Arrays.deepToString(goal_board.getGame_board()))) {
                            queue.add(new GameBoard(ans, game_board.getBlankX(), game_board.getBlankY() + 1));
                        }
                        else{
                            game_board=new GameBoard(ans, game_board.getBlankX(), game_board.getBlankY() + 1);
                            Path = openlist.get(game_board.getGame_board());
                            GoalFound=true;
                        }
                    }
                }
                if(!GoalFound) {
                    ans = MoveBlock(game_board.getGame_board(), Direction.DOWN, game_board.getBlankX(), game_board.getBlankY(),openlist,closedlist);
                    if (ans!=null) {
                        printans(ans,openlist);
                        num++;
                        if (!Arrays.deepToString(ans).equals(Arrays.deepToString(goal_board.getGame_board()))) {
                            queue.add(new GameBoard(ans, game_board.getBlankX() + 1, game_board.getBlankY()));
                        }
                        else{
                            game_board=new GameBoard(ans, game_board.getBlankX() + 1, game_board.getBlankY());
                            Path = openlist.get(game_board.getGame_board());
                            GoalFound=true;
                        }
                    }
                }
                if(!GoalFound) {
                    closedlist.put(game_board.getGame_board(), "");
                    openlist.remove(game_board.getGame_board());
                    if(open) {
                        output.append_1(openlist);
                    }
                }
            }
        }
        if (!GoalFound) {
            str.append("no path").append("\n").append("Num: ").append(num);
            output=new Output_Lists();
        }
        else {
            str.append(Path.substring(0, Path.length() - 1)).append("\n");
            str.append("Num: ").append(num).append("\n").append("Cost: ").append(getCost(Path));
        }
        if (time) {
            double seconds = (double) (System.nanoTime() - start) / 1_000_000_000;
            str.append("\n").append(seconds).append(" seconds");
        }
        output.append_3(str);
        output.Close();
    }

    private void printans(Node[][] ans,Hashtable<Node[][],String > openlist) {
        System.out.println("Board"+count+": "+"("+openlist.get(ans)+")");
        for (Node[] an : ans) {
            for (int j = 0; j < ans[0].length; j++) {
                System.out.print(an[j]+", ");
            }
            System.out.println();
        }
        System.out.println();
        count++;
    }
/*    int FindDup(Node[][] ans,Hashtable<Node[][],String > openlist){
        int c=0;
        for (Node[][] an:openlist.keySet()) {
            if(Arrays.deepToString(an).equals(Arrays.deepToString(ans))){
                c++;
            }
        }
        if(c>1){
            return 3274583;
        }
        else {
            return c;
        }
    }*/
}
