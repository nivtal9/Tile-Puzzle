import java.io.IOException;
import java.util.*;

public class A_star extends Algorithms {
    private boolean open;
    private boolean time;
    private GameBoard game_board;
    private GameBoard goal_board;

    A_star(boolean open, boolean time, GameBoard game_board, GameBoard goal_board, List<Integer> red) {
        super(red);
        this.time=time;
        this.open=open;
        this.goal_board=goal_board;
        this.game_board=game_board;
    }
    void A_star_alg() throws IOException {
        StringBuilder str = new StringBuilder();boolean GoalFound = false;int num = 1;int cost = 0;long start = -1;
        String Path=null;Output_Lists output=new Output_Lists();
        if (time) {
            start = System.nanoTime();
        }
        Hashtable<Node[][], String> openlist = new Hashtable<>();
        Hashtable<Node[][], String> closedlist = new Hashtable<>();
        PriorityQueue<GameBoard> queue = new PriorityQueue<>(new GameBoardComparator());
        game_board.setHeight(Manhattan_distance(game_board.getGame_board(),goal_board));
        queue.add(game_board);
        while (!queue.isEmpty() && !GoalFound) {
            game_board=queue.poll();
            if (!Arrays.deepToString(goal_board.getGame_board()).equals(Arrays.deepToString(game_board.getGame_board()))) {
                Node[][] ans = MoveBlock(game_board.getGame_board(), Direction.LEFT, game_board.getBlankX(), game_board.getBlankY(),openlist,closedlist);
                if(ans!=null) {
                    num++;
                    if (!Arrays.deepToString(ans).equals(Arrays.deepToString(goal_board.getGame_board()))) {
                        GameBoard temp=new GameBoard(ans, game_board.getBlankX(), game_board.getBlankY() - 1);
                        temp.setHeight(MoveCost(temp,Direction.LEFT)+Manhattan_distance(temp.getGame_board(),goal_board));
                        queue.add(temp);
                    }
                    else{
                        GoalFound=true;
                        game_board=new GameBoard(ans, game_board.getBlankX(), game_board.getBlankY() - 1);
                        Path = openlist.get(game_board.getGame_board());
                    }
                }
                if(!GoalFound) {
                    ans = MoveBlock(game_board.getGame_board(), Direction.UP, game_board.getBlankX(), game_board.getBlankY(), openlist, closedlist);
                    if(ans!=null) {
                        num++;
                        if (!Arrays.deepToString(ans).equals(Arrays.deepToString(goal_board.getGame_board()))) {
                            GameBoard temp=new GameBoard(ans, game_board.getBlankX() - 1, game_board.getBlankY());
                            temp.setHeight(MoveCost(temp,Direction.UP)+Manhattan_distance(temp.getGame_board(),goal_board));
                            queue.add(temp);
                        }
                        else{
                            GoalFound=true;
                            game_board=new GameBoard(ans, game_board.getBlankX() - 1, game_board.getBlankY());
                            Path = openlist.get(game_board.getGame_board());
                        }
                    }
                }
                if(!GoalFound) {
                    ans = MoveBlock(game_board.getGame_board(), Direction.RIGHT, game_board.getBlankX(), game_board.getBlankY(), openlist, closedlist);
                    if(ans!=null) {
                        num++;
                        if (!Arrays.deepToString(ans).equals(Arrays.deepToString(goal_board.getGame_board()))) {
                            GameBoard temp=new GameBoard(ans, game_board.getBlankX(), game_board.getBlankY() + 1);
                            temp.setHeight(MoveCost(temp,Direction.RIGHT)+Manhattan_distance(temp.getGame_board(),goal_board));
                            queue.add(temp);
                        }
                        else{
                            GoalFound=true;
                            game_board=new GameBoard(ans, game_board.getBlankX(), game_board.getBlankY() + 1);
                            Path = openlist.get(game_board.getGame_board());
                        }
                    }
                }
                if(!GoalFound) {
                    ans = MoveBlock(game_board.getGame_board(), Direction.DOWN, game_board.getBlankX(), game_board.getBlankY(), openlist, closedlist);
                    if(ans!=null) {
                        num++;
                        if (!Arrays.deepToString(ans).equals(Arrays.deepToString(goal_board.getGame_board()))) {
                            GameBoard temp=new GameBoard(ans, game_board.getBlankX() + 1, game_board.getBlankY());
                            temp.setHeight(MoveCost(temp,Direction.DOWN)+Manhattan_distance(temp.getGame_board(),goal_board));
                            queue.add(temp);
                        }
                        else{
                            GoalFound=true;
                            game_board=new GameBoard(ans, game_board.getBlankX() + 1, game_board.getBlankY());
                            Path = openlist.get(game_board.getGame_board());
                        }
                    }
                }
                if (!GoalFound) {
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
            cost = getCost(Path);
            str.append(Path.substring(0, Path.length() - 1)).append("\n");
            str.append("Num: ").append(num).append("\n").append("Cost: ").append(cost);
        }
        if (time) {
            double seconds = (double) (System.nanoTime() - start) / 1_000_000_000;
            str.append("\n").append(seconds).append(" seconds");
        }
        output.append_3(str);
        output.Close();
    }
    static class GameBoardComparator implements Comparator<GameBoard>{
        // Overriding compare()method of Comparator
        // for ascending order of Manhattan_distance+height
        public int compare(GameBoard s1, GameBoard s2) {
            if (+s1.getHeight() < s2.getHeight())
                return -1;
            else if (s1.getHeight() > s2.getHeight())
                return 1;
            return 0;
        }
    }

/*    private void Print(PriorityQueue<GameBoard> games){
        System.out.println("///////////////////////NEW ONE/////////////////////////");
        int count=1;
        while(!games.isEmpty()) {
            Node[][] ans=games.peek().getGame_board();
            System.out.println(count + ") ");
            for (int i=0;i<ans.length;i++) {
                System.out.println(Arrays.toString(ans[i]));
            }
            System.out.println("Distance: " + (Manhattan_distance(ans,goal_board) +games.peek().getHeight() ));
            count++;
            games.poll();
        }
    }*/
}
