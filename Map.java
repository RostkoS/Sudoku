import java.awt.*;
import java.util.ArrayList;


public class Map {
    private final ArrayList<WrongInput>wrongs;//list for saving inputted wrong values
    private final int N = 9, n = 3;//sizes of board and keypad
    private int pickedI =-1,pickedJ=-1;
    private final int cellWidth = 50, cellHeight = 50;//sizes of cell
    private final int borderX=202, borderY=2;//borders for board
    private final Rectangle[][] rectangles;//array for board
    private final Rectangle[] numbers;//array for keypad

    public Map() {//constructor
        rectangles = new Rectangle[N][N];
        numbers= new Rectangle[N];
        wrongs = new ArrayList<>();
    }

    public void clear(){//clear the values
        wrongs.clear();
        pickedJ=-1;
        pickedI=-1;
    }
    public void draw(Graphics2D g, int[][]points) {
        //setting font
        g.setFont(new Font(Font.SERIF,Font.BOLD,30));

        //drawing keypad
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                 g.setStroke(new BasicStroke(1));
                numbers[i*n+j]=new Rectangle((int) ((j+0.5)* cellWidth), (int) (borderY+(i+1.2)* cellHeight), cellWidth, cellHeight);
                g.setColor(Color.LIGHT_GRAY);
                g.fillRect(numbers[i*n+j].x,numbers[i*n+j].y,numbers[i*n+j].width,numbers[i*n+j].height);
                g.setColor(Color.black);
                g.drawRect(numbers[i*n+j].x,numbers[i*n+j].y,numbers[i*n+j].width,numbers[i*n+j].height);
               g.drawString(String.valueOf(i*n+j+1), (int) ((j+0.9)* cellWidth), (int) (borderY+(i+1.9)* cellHeight));

            }

        }
        //drawing board
        g.setStroke(new BasicStroke(3));//
       g.drawRect(borderX,borderY,N* cellWidth,N* cellHeight);
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                 rectangles[i][j]=new Rectangle(j * cellWidth + borderX, i * cellHeight + borderY, cellWidth, cellHeight);
                if(j%n==0){
                    g.setStroke(new BasicStroke(2));
                    for (int p=0;p<N;p+=n)
                    {
                    g.drawRect(borderX+j* cellWidth,borderY+p* cellHeight,n* cellWidth,n* cellHeight);
                }
                }
                    if(pickedI==i&&pickedJ==j&&points[i][j]==0) {
                        g.setColor(Color.cyan);
                    }else  g.setColor(Color.white);
                    g.fillRect(j * cellWidth + borderX, i * cellHeight + borderY, cellWidth, cellHeight);
                    g.setStroke(new BasicStroke(1));
                    g.setColor(Color.black);
                    g.drawRect(rectangles[i][j].x,rectangles[i][j].y,rectangles[i][j].width,rectangles[i][j].height);
                    //displaying generated numbers
                if (points[i][j] > 0) {
                    g.drawString(String.valueOf(points[i][j]),(int) ((j+0.4) * cellWidth) + borderX, (int) ((i+0.7) * cellHeight +borderY));
                }
                //displaying wrong input
                for (WrongInput w:wrongs
                     ) {
                    g.setColor(Color.pink);
                    g.fillRect(w.getJ() * cellWidth + borderX, w.getI() * cellHeight + borderY, cellWidth, cellHeight);
                    g.setColor(Color.black);
                    g.drawRect(w.getJ() * cellWidth + borderX, w.getI() * cellHeight + borderY, cellWidth, cellHeight);
                    g.setColor(Color.red);
                    g.drawString(String.valueOf(w.getNum()), (int) (((w.getJ()+0.4)  * cellWidth) + borderX), (int) ((w.getI() +0.7) * cellHeight +borderY));
                    g.setColor(Color.black);
                }
            }
        }
    }
    public void checkClickOnBoard(int x, int y){//handle click on board

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if(rectangles[i][j].contains(x,y)) {
                    //save position of picked cell
                     pickedI=i;
                     pickedJ=j;

                     if(wrongs.size()>0){
                    for (WrongInput wr:wrongs){
                        if(wr.getI()==i&&wr.getJ()==j){
                            //if picked cell occupied by wrong input remove it
                            wrongs.remove(wr);
                            break;
                        }
                    }
                     }
                }

            }
        }

    }
    public int checkClickOnNum(int x, int y){
        for (int i = 0; i < N; i++) {
                if(numbers[i].contains(x,y)) {
                    //if clicked on keypad return picked number
                    return i+1;
                }

        }
        return 0;
    }

    public int getPickedI() {
        return pickedI;
    }

    public int getPickedJ() {
        return pickedJ;
    }

    public void drawWrong( int num) {
        //add new value to wrong
        WrongInput wr = new WrongInput(pickedI,pickedJ,num);
        wrongs.add(wr);
        pickedJ=-1;
        pickedI=-1;

    }
}
