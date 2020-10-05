class GameBoard {
    private Node[][] game_board;
    private int BlankX;
    private int BlankY;
    private int height; //For A* IDA* Algorithm Only
    private boolean out;//For IDA*
    private Algorithms.Direction dir;//For DFBnB
    GameBoard(Node[][] game_board,int BlankX,int BlankY){
        this.game_board=game_board;
        this.BlankX=BlankX;
        this.BlankY=BlankY;
        this.out=false;
    }

    int getBlankY() {
        return BlankY;
    }

    int getBlankX() {
        return BlankX;
    }

    Node[][] getGame_board() {
        return game_board;
    }
    void setHeight(int height) {
        this.height=height;
    }

    int getHeight() {
        return height;
    }
    void setOut(){
        this.out=true;
    }
    boolean getOut(){
        return this.out;
    }
    void setDirection(Algorithms.Direction d){
        this.dir=d;
    }
    Algorithms.Direction getDirection(){
        return this.dir;
    }
}
