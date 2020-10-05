import java.io.IOException;
import java.util.*;

public class DFBnB extends Algorithms {
    private boolean time;
    private boolean open;
    private GameBoard game_board;
    private GameBoard goal_board;
    DFBnB(boolean open, boolean time, GameBoard game_board, GameBoard goal_board, List<Integer> red) {
        super(red);
        this.time=time;
        this.open=open;
        this.goal_board=goal_board;
        this.game_board=game_board;
    }
    void DFBnB_alg() throws IOException {
        StringBuilder str=new StringBuilder();Stack<GameBoard>stack = new Stack<>();
        String Path="";boolean GoalFound=false;int threshold=Integer.MAX_VALUE;long start = -1;
        Hashtable<GameBoard, String> openlist = new Hashtable<>();int num = 1;
        PriorityQueue<GameBoard> IncreaseOrder = new PriorityQueue<>(new DFBnB.GameBoardComparator());
        game_board.setHeight(Manhattan_distance(game_board.getGame_board(),goal_board));
        Output_Lists output=new Output_Lists();
        if(time){
            start = System.nanoTime();
        }
        stack.add(game_board);
        openlist.put(game_board,"");
        while (!stack.isEmpty()&&!GoalFound) {
            game_board = stack.pop();
            if (game_board.getOut()) {
                openlist.remove(game_board);
            } else {
                game_board.setOut();
                stack.add(game_board);
                Node[][] ans = MoveBlock(game_board, Direction.LEFT, game_board.getBlankX(), game_board.getBlankY(), openlist, false);
                if (ans != null) {
                    num++;
                    GameBoard temp = new GameBoard(ans, game_board.getBlankX(), game_board.getBlankY() - 1);
                    temp.setHeight(MoveCost(temp,Direction.LEFT)+Manhattan_distance(temp.getGame_board(),goal_board));
                    temp.setDirection(Direction.LEFT);
                    IncreaseOrder.add(temp);
                }
                ans = MoveBlock(game_board, Direction.UP, game_board.getBlankX(), game_board.getBlankY(), openlist, false);
                if (ans != null) {
                    num++;
                    GameBoard temp = new GameBoard(ans, game_board.getBlankX() - 1, game_board.getBlankY());
                    temp.setHeight(MoveCost(temp,Direction.UP)+Manhattan_distance(temp.getGame_board(),goal_board));
                    temp.setDirection(Direction.UP);
                    IncreaseOrder.add(temp);
                }
                ans = MoveBlock(game_board, Direction.RIGHT, game_board.getBlankX(), game_board.getBlankY(), openlist, false);
                if (ans != null) {
                    num++;
                    GameBoard temp = new GameBoard(ans, game_board.getBlankX(), game_board.getBlankY() + 1);
                    temp.setHeight(MoveCost(temp,Direction.RIGHT)+Manhattan_distance(temp.getGame_board(),goal_board));
                    temp.setDirection(Direction.RIGHT);
                    IncreaseOrder.add(temp);
                }
                ans = MoveBlock(game_board, Direction.DOWN, game_board.getBlankX(), game_board.getBlankY(), openlist, false);
                if (ans != null) {
                    num++;
                    GameBoard temp = new GameBoard(ans, game_board.getBlankX() + 1, game_board.getBlankY());
                    temp.setHeight(MoveCost(temp,Direction.DOWN)+Manhattan_distance(temp.getGame_board(),goal_board));
                    temp.setDirection(Direction.DOWN);
                    IncreaseOrder.add(temp);
                }
                Stack<GameBoard> reverse = new Stack<>();
                while (!IncreaseOrder.isEmpty()&&!GoalFound) {
                    int size = IncreaseOrder.size();
                    GameBoard peek = IncreaseOrder.peek();
                    if (peek.getHeight() >= threshold) {
                        IncreaseOrder.clear();
                    } else {
                        GameBoard temp = ExistDuplicate(Arrays.deepToString(peek.getGame_board()), openlist);
                        if (temp != null && temp.getOut()) {
                            IncreaseOrder.poll();
                        } else if (temp != null && !temp.getOut()) {
                            if (temp.getHeight() <= peek.getHeight()) {
                                IncreaseOrder.poll();
                            } else {
                                openlist.remove(temp);
                                stack.remove(temp);
                            }
                        } else if (Arrays.deepToString(peek.getGame_board()).equals(Arrays.deepToString(goal_board.getGame_board()))) {
                            threshold = peek.getHeight();
                            switch (peek.getDirection()) {
                                case RIGHT:
                                    MoveBlock(game_board, Direction.RIGHT, game_board.getBlankX(), game_board.getBlankY(), openlist, true);
                                    break;
                                case DOWN:
                                    MoveBlock(game_board, Direction.DOWN, game_board.getBlankX(), game_board.getBlankY(), openlist, true);
                                    break;
                                case LEFT:
                                    MoveBlock(game_board, Direction.LEFT, game_board.getBlankX(), game_board.getBlankY(), openlist, true);
                                    break;
                                case UP:
                                    MoveBlock(game_board, Direction.UP, game_board.getBlankX(), game_board.getBlankY(), openlist, true);
                                    break;
                            }
                            Path = conKey(peek, openlist);
                            GoalFound = true;
                            IncreaseOrder.clear();
                        }
                    }
                    if (size==IncreaseOrder.size()){
                        reverse.add(IncreaseOrder.poll());
                    }
                }
                while (!reverse.isEmpty()&&!GoalFound){
                    stack.add(reverse.peek());
                    switch (reverse.peek().getDirection()){
                        case RIGHT:
                            MoveBlock(game_board, Direction.RIGHT, game_board.getBlankX(), game_board.getBlankY(), openlist, true);
                            break;
                        case DOWN:
                            MoveBlock(game_board, Direction.DOWN, game_board.getBlankX(), game_board.getBlankY(), openlist, true);
                            break;
                        case LEFT:
                            MoveBlock(game_board, Direction.LEFT, game_board.getBlankX(), game_board.getBlankY(), openlist, true);
                            break;
                        case UP:
                            MoveBlock(game_board, Direction.UP, game_board.getBlankX(), game_board.getBlankY(), openlist, true);
                            break;
                    }
                    reverse.pop();
                }
            }
            if(open){
                output.append_2(openlist);
            }
        }
        if(Path.equals("")){
            output=new Output_Lists();
            str.append("no path").append("\n").append("Num: ").append(num);
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
    class GameBoardComparator implements Comparator<GameBoard> {
        // Overriding compare()method of Comparator
        // for ascending order of Manhattan_distance+height
        public int compare(GameBoard s1, GameBoard s2) {
            if (s1.getHeight() < s2.getHeight())
                return -1;
            else if (s1.getHeight() > s2.getHeight())
                return 1;
            return 0;
        }
    }
}
