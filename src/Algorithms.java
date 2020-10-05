import java.util.Arrays;
import java.util.Hashtable;
import java.util.List;
import java.util.Set;


class Algorithms {
    private List<Integer> red;

    Algorithms(List<Integer> red) {
        this.red = red;
    }
    public enum Direction {RIGHT,DOWN,LEFT,UP}
    /**
     * help for Algorithm
     *
     * @param str
     * @return
     */
    int getCost(String str) {
        int cost = 0;
        str = str.replaceAll("-", "");
        int j = 0;
        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) == 'R' || str.charAt(i) == 'U' || str.charAt(i) == 'D' || str.charAt(i) == 'L') {
                int num = Integer.parseInt(str.substring(j, i));
                if (red.contains(num)) {
                    cost += 30;
                } else {
                    cost++;
                }
                j = i + 1;
            }

        }
        return cost;
    }

    /**
     * Help for Algorithm
     * function that return the game board status after he got moved and insert
     * him into the openlist hashtable with the string of the movement he got so far
     * also checking that we not putting duplicate game-boards on openlist.
     *
     * @param arr
     * @param d
     * @param i
     * @param j
     * @return
     */
    Node[][] MoveBlock(Node[][] arr, Direction d, int i, int j, Hashtable<Node[][], String> openlist, Hashtable<Node[][], String> closedlist) {
        Node[][] ans = new Node[arr.length][arr[0].length];
        switch (d) {
            case UP:
                try {
                    if (arr[i - 1][j].getType() == Node.Type.Black) {
                        return null;
                    }
                    ans = deepCopy(arr);
                    Node temp = ans[i - 1][j];
                    ans[i - 1][j] = new Node(-1, Node.Type.BLANK);
                    ans[i][j] = temp;
                    if (!ExistDuplicate(Arrays.deepToString(ans), openlist, closedlist)) {
                        if (openlist.containsKey(arr)) {
                            openlist.put(ans, openlist.get(arr) + temp.getNum() + "D-");
                        } else
                            openlist.put(ans, temp.getNum() + "D-");
                    } else {
                        ans = null;
                    }
                } catch (Exception e) {
                    ans = null;
                }
                break;
            case DOWN:
                try {
                    if (arr[i + 1][j].getType() == Node.Type.Black) {
                        return null;
                    }
                    ans = deepCopy(arr);
                    Node temp = ans[i + 1][j];
                    ans[i + 1][j] = new Node(-1, Node.Type.BLANK);
                    ans[i][j] = temp;
                    if (!ExistDuplicate(Arrays.deepToString(ans), openlist, closedlist)) {
                        if (openlist.containsKey(arr)) {
                            openlist.put(ans, openlist.get(arr) + temp.getNum() + "U-");
                        } else
                            openlist.put(ans, temp.getNum() + "U-");
                    } else {
                        ans = null;
                    }
                } catch (Exception e) {
                    ans = null;
                }
                break;
            case LEFT:
                try {
                    if (arr[i][j - 1].getType() == Node.Type.Black) {
                        return null;
                    }
                    ans = deepCopy(arr);
                    Node temp = ans[i][j - 1];
                    ans[i][j - 1] = new Node(-1, Node.Type.BLANK);
                    ans[i][j] = temp;
                    if (!ExistDuplicate(Arrays.deepToString(ans), openlist, closedlist)) {
                        if (openlist.containsKey(arr)) {
                            openlist.put(ans, openlist.get(arr) + temp.getNum() + "R-");
                        } else
                            openlist.put(ans, temp.getNum() + "R-");
                    } else {
                        ans = null;
                    }
                } catch (Exception e) {
                    ans = null;
                }
                break;
            case RIGHT:
                try {
                    if (arr[i][j + 1].getType() == Node.Type.Black) {
                        return null;
                    }
                    ans = deepCopy(arr);
                    Node temp = ans[i][j + 1];
                    ans[i][j + 1] = new Node(-1, Node.Type.BLANK);
                    ans[i][j] = temp;
                    if (!ExistDuplicate(Arrays.deepToString(ans), openlist, closedlist)) {
                        if (openlist.containsKey(arr)) {
                            openlist.put(ans, openlist.get(arr) + temp.getNum() + "L-");
                        } else
                            openlist.put(ans, temp.getNum() + "L-");
                    } else {
                        ans = null;
                    }
                } catch (Exception e) {
                    ans = null;
                }
                break;
        }
        return ans;
    }

    /**
     * help for Algorithms BFS A*
     *
     * @param mat
     * @return
     */
    boolean ExistDuplicate(String mat, Hashtable<Node[][], String> openlist, Hashtable<Node[][], String> closedlist) {
        Set<Node[][]> openkeys = openlist.keySet();
        if (closedlist != null) {
            Set<Node[][]> closedkeys = closedlist.keySet();
            for (Node[][] key : closedkeys) {
                if (Arrays.deepToString(key).equals(mat)) {
                    return true;
                }
            }
        }
        for (Node[][] key : openkeys) {
            if (Arrays.deepToString(key).equals(mat)) {
                return true;
            }
        }
        return false;
    }

    /**
     * help for BFS
     *
     * @param oldone
     * @return
     */
    private Node[][] deepCopy(Node[][] oldone) {
        Node[][] newone = new Node[oldone.length][oldone[0].length];
        for (int i = 0; i < oldone.length; i++) {
            System.arraycopy(oldone[i], 0, newone[i], 0, oldone[0].length);
        }
        return newone;
    }

    int Manhattan_distance(Node[][] mat, GameBoard goal_board) {
        int count = 0;
        Node[][] temp = goal_board.getGame_board();
        for (int i = 0; i < mat.length; i++) {
            for (int j = 0; j < mat[0].length; j++) {
                if (mat[i][j].getNum()!=temp[i][j].getNum()&&mat[i][j].getType()!= Node.Type.BLANK){
                    for (int k = 0; k <temp.length ; k++) {
                        for (int l = 0; l <temp[0].length ; l++) {
                            if(temp[k][l].getNum()==mat[i][j].getNum()){
                                if(red.contains(mat[k][l].getNum())){
                                    count+=30*(Math.abs(i-k)+Math.abs(j-l));
                                }
                                else{
                                    if(mat[k][l].getType()== Node.Type.Black){
                                        return Integer.MAX_VALUE;
                                    }
                                    else{
                                        count+=Math.abs(i-k)+Math.abs(j-l);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return count;
    }

    /**
     * Help for IDA* DFBnB Algorithem (no ExistDuplicate Check)
     * @param arr
     * @param d
     * @param i
     * @param j
     * @param openlist
     * @return
     */
    Node[][] MoveBlock(GameBoard arr, Direction d, int i, int j, Hashtable<GameBoard, String> openlist,boolean Put) {
        Node[][] ans = new Node[arr.getGame_board().length][arr.getGame_board()[0].length];
        switch (d) {
            case UP:
                try {
                    if (arr.getGame_board()[i - 1][j].getType() == Node.Type.Black) {
                        return null;
                    }
                    ans = deepCopy(arr.getGame_board());
                    Node temp = ans[i - 1][j];
                    ans[i - 1][j] = new Node(-1, Node.Type.BLANK);
                    ans[i][j] = temp;
                    if(ExistDuplicate(Arrays.deepToString(ans),openlist)!=null){
                        return null;
                    }
                    if(Put) {
/*                        if (openlist.containsKey(arr)) {
                            openlist.put(new GameBoard(ans, arr.getBlankX() - 1, arr.getBlankY()), openlist.get(arr) + temp.getNum() + "D-");
                        } else
                            openlist.put(new GameBoard(ans, arr.getBlankX() - 1, arr.getBlankY()), temp.getNum() + "D-");*/
                        openlist.put(new GameBoard(ans,arr.getBlankX()-1,arr.getBlankY()),conKey(arr, openlist)+temp.getNum()+"D-");
                    }
                } catch (Exception e) {
                    ans = null;
                }
                break;
            case DOWN:
                try {
                    if (arr.getGame_board()[i + 1][j].getType() == Node.Type.Black) {
                        return null;
                    }
                    ans = deepCopy(arr.getGame_board());
                    Node temp = ans[i + 1][j];
                    ans[i + 1][j] = new Node(-1, Node.Type.BLANK);
                    ans[i][j] = temp;
                    if(ExistDuplicate(Arrays.deepToString(ans),openlist)!=null){
                        return null;
                    }
                    if(Put) {
/*                        if (openlist.containsKey(arr)) {
                            openlist.put(new GameBoard(ans, arr.getBlankX() + 1, arr.getBlankY()), openlist.get(arr) + temp.getNum() + "U-");
                        } else
                            openlist.put(new GameBoard(ans, arr.getBlankX() + 1, arr.getBlankY()), temp.getNum() + "U-");*/
                        openlist.put(new GameBoard(ans,arr.getBlankX()+1,arr.getBlankY()),conKey(arr, openlist)+temp.getNum()+"U-");
                    }
                } catch (Exception e) {
                    ans = null;
                }
                break;
            case LEFT:
                try {
                    if (arr.getGame_board()[i][j - 1].getType() == Node.Type.Black) {
                        return null;
                    }
                    ans = deepCopy(arr.getGame_board());
                    Node temp = ans[i][j - 1];
                    ans[i][j - 1] = new Node(-1, Node.Type.BLANK);
                    ans[i][j] = temp;
                    if(ExistDuplicate(Arrays.deepToString(ans),openlist)!=null){
                        return null;
                    }
                    if(Put) {
/*                        if (conKey(arr)) {
                            openlist.put(new GameBoard(ans, arr.getBlankX(), arr.getBlankY() - 1), openlist.get(arr) + temp.getNum() + "R-");
                        } else
                            openlist.put(new GameBoard(ans, arr.getBlankX(), arr.getBlankY() - 1), temp.getNum() + "R-");*/
                        openlist.put(new GameBoard(ans,arr.getBlankX(),arr.getBlankY()-1),conKey(arr, openlist)+temp.getNum()+"R-");
                    }
                } catch (Exception e) {
                    ans = null;
                }
                break;
            case RIGHT:
                try {
                    if (arr.getGame_board()[i][j + 1].getType() == Node.Type.Black) {
                        return null;
                    }
                    ans = deepCopy(arr.getGame_board());
                    Node temp = ans[i][j + 1];
                    ans[i][j + 1] = new Node(-1, Node.Type.BLANK);
                    ans[i][j] = temp;
                    if(ExistDuplicate(Arrays.deepToString(ans),openlist)!=null){
                        return null;
                    }
                    if(Put) {
/*                        if (openlist.containsKey(arr)) {
                            openlist.put(new GameBoard(ans, arr.getBlankX(), arr.getBlankY() + 1), openlist.get(arr) + temp.getNum() + "L-");
                        } else
                            openlist.put(new GameBoard(ans, arr.getBlankX(), arr.getBlankY() + 1), temp.getNum() + "L-");*/
                        openlist.put(new GameBoard(ans,arr.getBlankX(),arr.getBlankY()+1),conKey(arr, openlist)+temp.getNum()+"L-");
                    }
                } catch (Exception e) {
                    ans = null;
                }
                break;
        }
        return ans;
    }

    String conKey(GameBoard arr, Hashtable<GameBoard, String> openlist){
        Set<GameBoard> openkeys = openlist.keySet();
        for (GameBoard key : openkeys) {
            if (Arrays.deepToString(key.getGame_board()).equals(Arrays.deepToString(arr.getGame_board()))) {
                return openlist.get(key);
            }
        }
        return "";
    }

    /**
     * help for Algorithms IDA*
     * @param mat
     * @return
     */
    GameBoard ExistDuplicate(String mat, Hashtable<GameBoard, String> openlist) {
        Set<GameBoard> openkeys = openlist.keySet();
        for (GameBoard key : openkeys) {
            if (Arrays.deepToString(key.getGame_board()).equals(mat)) {
                return key;
            }
        }
        return null;
    }
    int MoveCost(GameBoard gameBoard,Direction d){
        switch (d){

            case RIGHT: {
                if(red.contains(gameBoard.getGame_board()[gameBoard.getBlankX()][gameBoard.getBlankY()-1].getNum())){
                    return 30;
                }
                else{
                    return 1;
                }
            }
            case DOWN: {
                if(red.contains(gameBoard.getGame_board()[gameBoard.getBlankX()-1][gameBoard.getBlankY()].getNum())){
                    return 30;
                }
                else{
                    return 1;
                }
            }
            case LEFT: {
                if(red.contains(gameBoard.getGame_board()[gameBoard.getBlankX()][gameBoard.getBlankY()+1].getNum())){
                    return 30;
                }
                else{
                    return 1;
                }
            }
            case UP: {
                if(red.contains(gameBoard.getGame_board()[gameBoard.getBlankX()+1][gameBoard.getBlankY()].getNum())){
                    return 30;
                }
                else{
                    return 1;
                }
            }
        }
        return Integer.MAX_VALUE;
    }
}