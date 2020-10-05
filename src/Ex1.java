import java.io.IOException;


public class Ex1 {
    public static void main(String[] args) throws IOException {
        inputDetails ID=new inputDetails();
        switch (ID.get_Alg_num()){
            case 1:
                new BFS(ID.get_open(),ID.get_time(),ID.get_game_board(),ID.get_goal_board(),ID.get_red_list()).BFS_alg();
                break;
            case 2:
                new DFID(ID.get_open(),ID.get_time(),ID.get_game_board(),ID.get_goal_board(),ID.get_red_list()).DFID_alg();
                break;
            case 3:
                new A_star(ID.get_open(),ID.get_time(),ID.get_game_board(),ID.get_goal_board(),ID.get_red_list()).A_star_alg();
                break;
            case 4:
                new IDA_star(ID.get_open(),ID.get_time(),ID.get_game_board(),ID.get_goal_board(),ID.get_red_list()).IDA_star_alg();
                break;
            case 5:
                new DFBnB(ID.get_open(),ID.get_time(),ID.get_game_board(),ID.get_goal_board(),ID.get_red_list()).DFBnB_alg();
                break;
        }
    }
}
