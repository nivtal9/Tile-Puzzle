import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

class inputDetails {
    private int alg_num;
    private boolean time;
    private boolean open;
    private List<Integer> black;
    private List<Integer> red;
    private Node[][] game_board;
    private GameBoard Game_Board;
    private GameBoard Goal_Board;
    inputDetails(){
        try {
            File myObj = new File("input.txt");
            Scanner myReader = new Scanner(myObj);
            alg_num = getAlgType(myReader.nextLine());
            time = getTimeType(myReader.nextLine());
            open = getOpenType(myReader.nextLine());
            String row_col = myReader.nextLine();
            game_board = new Node[getRow(row_col)][getCol(row_col)];
            getBlackNodes(myReader.nextLine());
            getRedNodes(myReader.nextLine());
            int row=0;
            while(myReader.hasNextLine()) {
                GenarateBoard(myReader.nextLine(),row);
                row++;
            }
            GenarateGoalBoard();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    private void GenarateGoalBoard() {
        int[] temp =new int[game_board.length*game_board[0].length];
        for (int i = 0; i <game_board.length ; i++) {
            for (int j = 0; j <game_board[0].length ; j++) {
                temp[(game_board.length+1)*i+j]=game_board[i][j].getNum();
            }
        }
        Arrays.sort(temp);
        int count=1;
        Node[][] goal_board = new Node[game_board.length][game_board[0].length];
        for (int i = 0; i <game_board.length ; i++) {
            for (int j = 0; j <game_board[0].length ; j++) {
                if(count!=game_board.length*game_board[0].length){
                    if(RedContains(temp[count])||BlackContains(temp[count])){
                        if(RedContains(temp[count])){
                            goal_board[i][j]=new Node(temp[count], Node.Type.Red);
                        }
                        else{
                            goal_board[i][j]=new Node(temp[count], Node.Type.Black);
                        }
                    }
                    else{
                        goal_board[i][j]=new Node(temp[count], Node.Type.Green);
                    }
                }
                else{
                    goal_board[game_board.length-1][game_board[0].length-1]=new Node(-1, Node.Type.BLANK);
                }
                count++;
            }
        }
        Goal_Board=new GameBoard(goal_board,game_board.length-1,game_board[0].length-1);
    }

    private void getBlackNodes(String data) {
        data=data.replaceAll(" ","");
        black=new ArrayList<>();
        if(data.length()>6){
            data=data.substring(6);
            if(data.contains(",")){
                black= new ArrayList(Arrays.asList((data.split(","))));
            }
            else{
                black.add(Integer.parseInt(data));
            }
        }
    }

    private void getRedNodes(String data) {
        data=data.replaceAll(" ","");
        red=new LinkedList<>();
        if(data.length()>4){
            data=data.substring(4);
            if(data.contains(",")){
                red= new ArrayList(Arrays.asList((data.split(","))));
            }
            else{
                red.add(Integer.parseInt(data));
            }
        }
    }
    private boolean RedContains(int num){
        try {
            for (Integer i : red) {
                if (Integer.toString(i).equals(Integer.toString(num))) {
                    return true;
                }
            }
        }
        catch (Exception e){
            for (Object i:red) {
                if(Integer.parseInt((String)i)==num){
                    return true;
                }
            }
        }
        return false;
    }
    private boolean BlackContains(int num){
        try {
            for (Integer i : black) {
                if (Integer.toString(i).equals(Integer.toString(num))) {
                    return true;
                }
            }
        }
        catch (Exception e){
            for (Object i:black) {
                if(Integer.parseInt((String)i)==num){
                    return true;
                }
            }
        }
        return false;
    }
    private void GenarateBoard(String data,int row) {
        int BlankX = 0;int BlankY=0;
        if(data.contains(",")) {
            String[] temp = data.split(",");
            int col = 0;
            for (String s : temp) {
                if (s.equals("_")) {
                    game_board[row][col] = new Node(-1, Node.Type.BLANK);
                    BlankX = row;BlankY = col;
                } else {
                    int num = Integer.parseInt(s);
                    if (RedContains(num) || BlackContains(num)) {
                        if (RedContains(num)) {
                            game_board[row][col] = new Node(num, Node.Type.Red);
                            col++;
                        } else {
                            game_board[row][col] = new Node(num, Node.Type.Black);
                            col++;
                        }
                    } else {
                        game_board[row][col] = new Node(num, Node.Type.Green);
                        col++;
                    }
                }
            }
        }
        else {
            int temp = Integer.parseInt(data);
            if (RedContains(temp) || BlackContains(temp)) {
                if (RedContains(temp)) {
                    game_board[row][0] = new Node(temp, Node.Type.Red);
                }
                else {
                    game_board[row][0] = new Node(temp, Node.Type.Black);
                }
            }
            else{
                game_board[row][0] = new Node(temp, Node.Type.Green);
            }
        }
        Game_Board=new GameBoard(game_board,BlankX,BlankY);
    }

    private int getRow(String data) {
        return Integer.parseInt(data.substring(0,data.indexOf('x')));
    }
    private int getCol(String data) {
        return Integer.parseInt(data.substring(data.indexOf('x')+1,data.length()));
    }
    private static int getAlgType(String data){
        int alg_num = -1;
        if(data.equals("BFS")){
            alg_num=1;
        }
        if(data.equals("DFID")){
            alg_num=2;
        }
        if(data.equals("A*")){
            alg_num=3;
        }
        if(data.equals("IDA*")){
            alg_num=4;
        }
        if(data.equals("DFBnB")){
            alg_num=5;
        }
        return alg_num;
    }
    private static boolean getTimeType(String data){
        return data.equals("with time");
    }
    private static boolean getOpenType(String data){
        return data.equals("with open");
    }
    int get_Alg_num() { return alg_num; }
    boolean get_time() {
        return time;
    }
    GameBoard get_goal_board() {
        return Goal_Board;
    }
    boolean get_open() { return open; }
    GameBoard get_game_board() {
        return Game_Board;
    }
    List<Integer> get_red_list() {
        return red;
    }
}