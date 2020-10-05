import java.io.IOException;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.List;

public class DFID extends Algorithms {
    private boolean open;
    private boolean time;
    private GameBoard game_board;
    private GameBoard goal_board;
    private int num;

    DFID(boolean open, boolean time, GameBoard game_board, GameBoard goal_board, List<Integer> red) {
        super(red);
        this.time=time;
        this.open=open;
        this.goal_board=goal_board;
        this.game_board=game_board;
    }
    String DFID_alg() throws IOException {
        StringBuilder str = new StringBuilder();
        int count = 0;
        boolean cutoff = true;
        long start = -1;
        Output_Lists output=new Output_Lists();
        if(time){
            start = System.nanoTime();
        }
        num=1;
        while (count != Integer.MAX_VALUE && cutoff) {
            Hashtable<Node[][], String> openlist = new Hashtable<>();
            String temp = Limited_DFS(game_board,count, openlist,output);
            if (!temp.equals("Cutoff")) {
                cutoff = false;
                if(temp.equals("fail")){
                    str.append("no path").append("\n").append("Num: ").append(num);
                    output=new Output_Lists();
                }
                else {
                    str.append(temp.substring(0, temp.length() - 1)).append("\n");
                    str.append("Num: ").append(num).append("\n").append("Cost: ").append(getCost(temp));
                }
                if(time){
                    double seconds = (double) (System.nanoTime() - start) / 1_000_000_000;
                    str.append("\n").append(seconds).append(" seconds");
                }
                output.append_3(str);
                output.Close();
            }
            count++;
        }
        return str.toString();
    }

    private String Limited_DFS(GameBoard Game_board,int count, Hashtable<Node[][], String> openlist,Output_Lists output) {
        if(Arrays.deepToString(Game_board.getGame_board()).equals(Arrays.deepToString(goal_board.getGame_board()))){
            return openlist.get(Game_board.getGame_board());
        }
        else {
            if (count == 0) {
                return "Cutoff";
            } else {
                boolean isCutoff=false;
                Node[][] ans = MoveBlock(Game_board.getGame_board(), Direction.LEFT, Game_board.getBlankX(), Game_board.getBlankY(), openlist, null);
                //if (!Arrays.deepToString(ans).equals(Arrays.deepToString(goal_board.getGame_board()))) {
                    if (ans != null) {
                        num++;
                       String temp=Limited_DFS(new GameBoard(ans, Game_board.getBlankX(), Game_board.getBlankY() - 1),count-1,openlist,output);
                       if(temp.equals("Cutoff")){
                           isCutoff=true;
                       }
                       else{
                           if(!temp.equals("fail")) {
                               return temp;
                           }
                       }
                    }
                //}
                ans = MoveBlock(Game_board.getGame_board(), Direction.UP, Game_board.getBlankX(), Game_board.getBlankY(), openlist, null);
                //if (!Arrays.deepToString(ans).equals(Arrays.deepToString(goal_board.getGame_board()))) {
                    if (ans != null) {
                        num++;
                        String temp=Limited_DFS(new GameBoard(ans, Game_board.getBlankX()-1, Game_board.getBlankY()),count-1,openlist,output);
                        if(temp.equals("Cutoff")){
                            isCutoff=true;
                        }
                        else{
                            if(!temp.equals("fail")) {
                                return temp;
                            }
                        }
                    }
                //}
                ans = MoveBlock(Game_board.getGame_board(), Direction.RIGHT, Game_board.getBlankX(), Game_board.getBlankY(), openlist, null);
                //if (!Arrays.deepToString(ans).equals(Arrays.deepToString(goal_board.getGame_board()))) {
                    if (ans != null) {
                        num++;
                        String temp=Limited_DFS(new GameBoard(ans, Game_board.getBlankX(), Game_board.getBlankY()+1),count-1,openlist,output);
                        if(temp.equals("Cutoff")){
                            isCutoff=true;
                        }
                        else{
                            if(!temp.equals("fail")) {
                                return temp;
                            }
                        }
                    }
                //}
                ans = MoveBlock(Game_board.getGame_board(), Direction.DOWN, Game_board.getBlankX(), Game_board.getBlankY(), openlist, null);
                //if (!Arrays.deepToString(ans).equals(Arrays.deepToString(goal_board.getGame_board()))) {
                    if (ans != null) {
                        num++;
                        String temp = Limited_DFS(new GameBoard(ans, Game_board.getBlankX() - 1, Game_board.getBlankY()), count - 1, openlist,output);
                        if (temp.equals("Cutoff")) {
                            isCutoff = true;
                        } else {
                            if (!temp.equals("fail")) {
                                return temp;
                            }
                        }
                    }
                    if(open){
                        output.append_1(openlist);
                    }
                if(isCutoff){
                    return "Cutoff";
                }
                else{
                    return "fail";
                }
            }
        }
    }
}
