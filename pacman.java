import java.util.Random;
import java.util.Scanner;
class gamemap{
    private char[][] grid;
    private int rows=10;
    private int cols=20;

    public gamemap (){
        grid = new char[rows][cols];
        mazeform();
    }
     void mazeform() {
        for(int i=0;i<rows;i++){
            for(int j=0;j<cols;j++){
              if (i==0 || i==rows-1 || j==0 || j==cols-1 || (i==5 && j>5 && j<15) || (i==8 && j>2 && j<6)){
                  grid[i][j]='#';
              }  else {
                  grid[i][j]='.';
              }
            }
        }
    }
    public char getcell(int r, int c){
        return  grid[r][c];
    }
    public void setcell(int r, int c, char val){
        grid[r][c] = val;
    }
    public int getrows() {
        return rows;
    }
    public int getcols() {
        return cols;
    }
}
class food {
    private int points=10;

    public int getpoints() {
        return  points;
    }
}
class pacmann {
    private int x,y,score;
    public pacmann() {
        this.x =2;
        this.y =2;
        this.score = 0;
    }
       public void move(char direction, food fooditem, gamemap board) {
           int nextX = x, nextY = y;
           if (direction == 'U') nextX--;
           if (direction == 'D') nextX++;
           if (direction == 'L') nextY--;
           if (direction == 'R') nextY++;

           if (board.getcell(nextX, nextY) != '#') {
               if (board.getcell(nextX, nextY) == '.') {
                   score += fooditem.getpoints();
                   board.setcell(nextX, nextY, ' ');
               }
               x=nextX;
               y=nextY;
           }
       }
       public int getX() {
           return x;
       }
       public int getY() {
        return y;
       }
       public int getscore() {
        return score;
       }
}
class ghost {
    private int x, y;
    private Random num = new Random();

    ghost() {
        this.x = 1;
        this.y = 9;
    }
    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }
    public void moverandomly(gamemap board) {
        int direction = num.nextInt(4);
        int nextX = x, nextY = y;
        switch (direction) {
            case 0:
                nextX--;
                break;
            case 1:
                nextX++;
                break;
            case 2:
                nextY--;
                break;
            case 3:
                nextY++;
                break;
        }
        if (board.getcell(nextX, nextY) != '#') {
            x = nextX;
            y = nextY;
        }
    }

    public int getx() {
        return x;
    }

    public int gety() {
        return y;
    }
}
public class pacman {
    public static void main(String[] args) {
        gamemap board = new gamemap();
        pacmann p = new pacmann();
        ghost g = new ghost();
        food f = new food();
        Scanner input = new Scanner(System.in);
        boolean gameon = true;

        System.out.println("Welcome to the pacman game!");

        while (gameon) {
            drawscreen(board, p, g);
            System.out.println("Score: " + p.getscore());
            System.out.print("Move (UDLR) or Q to Quit: ");
            char key = input.next().toUpperCase().charAt(0);

            if (key == 'Q') {
                gameon = false;
                continue;
            }
            p.move(key, f, board);
            if (p.getX() == g.getX() && p.getY() == g.getY()) {
                drawscreen(board, p, g);
                System.out.println("Game Over, Caught by ghost.");
                gameon = false;
                break;
            }
            g.moverandomly(board);
            if (p.getX() == g.getX() && p.getY() == g.getY()) {
                drawscreen(board, p, g);
                System.out.println("Game Over, Caught by ghost.");
                gameon = false;
                break;
            }
        }
    }
    private static void drawscreen(gamemap board, pacmann p, ghost g) {
        for (int r = 0; r < board.getrows(); r++) {
            for (int c = 0; c < board.getcols(); c++) {
                if (r == p.getX() && c == p.getY()) {
                    System.out.print("P");
                } else if (r == g.getX() && c == g.getY()) {
                    System.out.print("G");
                } else {
                    System.out.print(board.getcell(r, c));
                }
            }
        }
    }
}