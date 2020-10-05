import java.io.IOException;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.List;
import java.util.Stack;

public class IDA_star extends Algorithms {
    private boolean open;
    private boolean time;
    private GameBoard game_board;
    private GameBoard goal_board;

    IDA_star(boolean open, boolean time, GameBoard game_board, GameBoard goal_board, List<Integer> red) {
        super(red);
        this.time = time;
        this.open = open;
        this.goal_board = goal_board;
        this.game_board = game_board;
    }

    void IDA_star_alg() throws IOException {
        StringBuilder str = new StringBuilder();
        Stack<GameBoard> stack = new Stack<>();
        int num = 1;
        String Path="";
        Hashtable<GameBoard, String> openlist = new Hashtable<>();
        int threshold = Manhattan_distance(game_board.getGame_board(), goal_board);
        Output_Lists output=new Output_Lists();
        boolean GoalFound = false;
        long start = -1;
        if (time) {
            start = System.nanoTime();
        }
        while (threshold != Integer.MAX_VALUE && !GoalFound) {
            int minF = Integer.MAX_VALUE;
            game_board.setHeight(Manhattan_distance(game_board.getGame_board(), goal_board));
            stack.add(game_board);
            openlist.put(game_board, "");
            while (!stack.isEmpty() && !GoalFound) {
                game_board = stack.pop();
                if (game_board.getOut()) {
                    openlist.remove(game_board);
                } else {
                    game_board.setOut();
                    stack.add(game_board);

                    Node[][] ans = MoveBlock(game_board, Direction.LEFT, game_board.getBlankX(), game_board.getBlankY(), openlist, false);
                    if (ans != null) {
                        boolean b=true;
                        num++;
                        GameBoard temp = new GameBoard(ans, game_board.getBlankX(), game_board.getBlankY() - 1);
                        temp.setHeight(Manhattan_distance(temp.getGame_board(), goal_board) + MoveCost(temp,Direction.LEFT));
                        int cost = temp.getHeight();
                        if (cost > threshold) {
                            minF = Math.min(minF, cost);
                        } else {
                            GameBoard g = ExistDuplicate(Arrays.deepToString(temp.getGame_board()), openlist);
                            if (g != null && g.getOut()) {
                                if (g.getHeight() > temp.getHeight()) {
                                    stack.remove(g);
                                    openlist.remove(g);
                                }
                                else{
                                    b=false;
                                }
                            }
                            else if(g!=null && !g.getOut()){
                                b=false;
                            }
                            if(b) {
                                ans = MoveBlock(game_board, Direction.LEFT, game_board.getBlankX(), game_board.getBlankY(), openlist, true);
                                if (Arrays.deepToString(ans).equals(Arrays.deepToString(goal_board.getGame_board()))) {
                                    Path = conKey(temp, openlist);
                                    GoalFound = true;
                                }
                                stack.add(temp);
                            }
                        }
                    }
                    ans = MoveBlock(game_board, Direction.UP, game_board.getBlankX(), game_board.getBlankY(), openlist, false);
                    if (ans != null) {
                        num++;
                        boolean b=true;
                        GameBoard temp = new GameBoard(ans, game_board.getBlankX() - 1, game_board.getBlankY());
                        temp.setHeight(Manhattan_distance(temp.getGame_board(), goal_board) +MoveCost(temp,Direction.UP));
                        int cost = temp.getHeight();
                        if (cost > threshold) {
                            minF = Math.min(minF, cost);
                        } else {
                            GameBoard g = ExistDuplicate(Arrays.deepToString(temp.getGame_board()), openlist);
                            if (g != null && g.getOut()) {
                                if (g.getHeight() > temp.getHeight()) {
                                    stack.remove(g);
                                    openlist.remove(g);
                                }
                                else{
                                    b=false;
                                }
                            }
                            else if(g!=null && !g.getOut()){
                                b=false;
                            }
                        }
                        if(b) {
                            ans = MoveBlock(game_board, Direction.UP, game_board.getBlankX(), game_board.getBlankY(), openlist, true);
                            if (Arrays.deepToString(ans).equals(Arrays.deepToString(goal_board.getGame_board()))) {
                                Path = conKey(temp, openlist);
                                GoalFound = true;
                            }
                            stack.add(temp);
                        }
                    }

                    ans = MoveBlock(game_board, Direction.RIGHT, game_board.getBlankX(), game_board.getBlankY(), openlist, false);
                    if (ans != null) {
                        num++;
                        boolean b=true;
                        GameBoard temp = new GameBoard(ans, game_board.getBlankX(), game_board.getBlankY() + 1);
                        temp.setHeight(Manhattan_distance(temp.getGame_board(), goal_board) +MoveCost(temp,Direction.RIGHT));
                        int cost = temp.getHeight();
                        if (cost > threshold) {
                            minF = Math.min(minF, cost);
                        } else {
                            GameBoard g = ExistDuplicate(Arrays.deepToString(temp.getGame_board()), openlist);
                            if (g != null && g.getOut()) {
                                if (g.getHeight() >temp.getHeight()) {
                                    stack.remove(g);
                                    openlist.remove(g);
                                }
                                else{
                                    b=false;
                                }
                            }
                            else if(g!=null && !g.getOut()){
                                b=false;
                            }
                        }
                        if(b) {
                            ans = MoveBlock(game_board, Direction.RIGHT, game_board.getBlankX(), game_board.getBlankY(), openlist, true);
                            if (Arrays.deepToString(ans).equals(Arrays.deepToString(goal_board.getGame_board()))) {
                                Path = conKey(temp, openlist);
                                GoalFound = true;
                            }
                            stack.add(temp);
                        }
                    }

                    ans = MoveBlock(game_board, Direction.DOWN, game_board.getBlankX(), game_board.getBlankY(), openlist, false);
                    if (ans != null) {
                        num++;
                        boolean b = true;
                        GameBoard temp = new GameBoard(ans, game_board.getBlankX() + 1, game_board.getBlankY());
                        temp.setHeight(Manhattan_distance(temp.getGame_board(), goal_board) + MoveCost(temp, Direction.DOWN));
                        int cost = temp.getHeight();
                        if (cost > threshold) {
                            minF = Math.min(minF, cost);
                        } else {
                            GameBoard g = ExistDuplicate(Arrays.deepToString(temp.getGame_board()), openlist);
                            if (g != null && !temp.getOut()) {
                                if (g.getHeight() > temp.getHeight()) {
                                    stack.remove(g);
                                    openlist.remove(g);
                                } else {
                                    b = false;
                                }
                            }
                            else if(g!=null && !g.getOut()){
                                b=false;
                            }
                        }
                        if (b) {
                            ans = MoveBlock(game_board, Direction.DOWN, game_board.getBlankX(), game_board.getBlankY(), openlist, true);
                            if (Arrays.deepToString(ans).equals(Arrays.deepToString(goal_board.getGame_board()))) {
                                Path = conKey(temp, openlist);
                                GoalFound = true;
                            }
                            stack.add(temp);
                        }
                    }
                }
                if (open) {
                    output.append_2(openlist);
                }
            }
            threshold = minF;
        }
        if(GoalFound) {
            str.append(Path.substring(0, Path.length() - 1)).append("\n");
            str.append("Num: ").append(num).append("\n").append("Cost: ").append(getCost(Path));
            if (time) {
                double seconds = (double) (System.nanoTime() - start) / 1_000_000_000;
                str.append("\n").append(seconds).append(" seconds");
            }
            output.append_3(str);
        }
        else{
            str.append("no path").append("\n").append("Num: ").append(num);
            output=new Output_Lists();
            output.append_3(str);
        }
        output.Close();
    }
}