
public class Node {
    private int num;
    private Type type;
    Node(int num,Type type){
        this.num=num;
        this.type=type;
    }
    public enum Type {Green,Red,Black,BLANK}
    int getNum() {
        return num;
    }

    Type getType() {
        return type;
    }
    public String toString(){
        return " num: "+this.getNum()+" Type: "+this.getType();
    }
}
