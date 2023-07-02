import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class Game extends JPanel implements MouseListener {

    private int[][] board; //array for board values displayed
    private int [][] fullBoard;//array for full board
    private final int N=9; // number of columns/rows
    private final int SRN; // square root of N
    private int numOfHoles; // Number of missing digits
    private final Map map;//object for visual presentation

    public Game(int A){//constructor
        this.numOfHoles = A;
        addMouseListener(this);
        setFocusable(true);
        setBackground(Color.BLACK);
        setDoubleBuffered(true);
        Double SRNd = Math.sqrt(N);
        SRN = SRNd.intValue();
        board =new int[N][N];//creating new board
        fullBoard =new int[N][N];//creating new fullboard
        fillValues();//filling new boards
        map = new Map();
        repaint();

    }
    private void fillValues()
    {
        //fill the diagonal
        fillDiagonal();

        //fill remaining blocks
        fillRemaining(0, SRN);
        for (int i=0; i<N;i++){
            System.arraycopy(board[i], 0, fullBoard[i], 0, N);
        }
        //make holes in board
        removeDigits();
    }
    private void removeDigits()//make holes in board
    {
        int count = numOfHoles;
        while (count != 0)
        {
            int cellId = randomGenerator(N*N)-1;
            int i = (cellId/N);
            int j = cellId%N;
            if (j != 0)
                j = j - 1;
            if (board[i][j] != 0)
            {
                count--;
                board[i][j] = 0;
            }
        }
    }

    private boolean fillRemaining(int i, int j)
    {
        if (j>=N && i<N-1)
        {
            i = i + 1;
            j = 0;
        }
        if (i>=N && j>=N)
            return true;

        if (i < SRN)
        {
            if (j < SRN)
                j = SRN;
        }
        else if (i < N-SRN)
        {
            if (j==(i/SRN)*SRN)
                j =  j + SRN;
        }
        else
        {
            if (j == N-SRN)
            {
                i = i + 1;
                j = 0;
                if (i>=N)
                    return true;
            }
        }

        for (int num = 1; num<=N; num++)
        {
            if (CheckIfSafe(i, j, num))
            {
                board[i][j] = num;
                if (fillRemaining(i, j+1))
                    return true;

                board[i][j] = 0;
            }
        }
        return false;
    }

    //check if number can be inserted
    private boolean CheckIfSafe(int i,int j,int num)
    {
        return (unUsedInRow(i, num) &&
                unUsedInCol(j, num) &&
                unUsedInBox(i-i%SRN, j-j%SRN, num));
    }

    // check in the row for existence
    private boolean unUsedInRow(int i,int num)
    {
        for (int j = 0; j<N; j++)
            if (board[i][j] == num)
                return false;
        return true;
    }

    // check in the column for existence
    private boolean unUsedInCol(int j,int num)
    {
        for (int i = 0; i<N; i++)
            if (board[i][j] == num)
                return false;
        return true;
    }
    // check in the box for existence
    private boolean unUsedInBox(int rowStart, int colStart, int num)
    {
        for (int i = 0; i<SRN; i++)
            for (int j = 0; j<SRN; j++)
                if (board[rowStart+i][colStart+j]==num)
                    return false;

        return true;
    }
    private void fillDiagonal()
    {
        for (int i = 0; i<N; i=i+SRN)
            // for diagonal box, start coordinates->i==j
            fillBox(i, i);
    }
    private void fillBox(int row,int col)//putting number into board
    {
        int num;
        for (int i=0; i<SRN; i++)
        {
            for (int j=0; j<SRN; j++)
            {
                do
                {
                    num = randomGenerator(N);
                }
                while (!unUsedInBox(row, col, num));

                board[row+i][col+j] = num;
            }
        }
    }
    private int randomGenerator(int num)//random generator for int 1-9
    {
        return (int) Math.floor((Math.random()*num+1));
    }

    public void reset(int K) {//reset method
        //creates new board
        this.numOfHoles = K;
        board =new int[N][N];
        fullBoard =new int[N][N];
        map.clear();
        fillValues();
        repaint();
    }

    public void paint(Graphics g) {
        map.draw((Graphics2D) g, board);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        map.checkClickOnBoard(e.getX(),e.getY());//if cell is empty highlight it
            repaint();
        int num=map.checkClickOnNum(e.getX(),e.getY());//get number from keypad
        if(num>0&&map.getPickedI()>=0&&map.getPickedJ()>=0){
           if(num==fullBoard[map.getPickedI()][map.getPickedJ()]){
               board[map.getPickedI()][map.getPickedJ()]=fullBoard[map.getPickedI()][map.getPickedJ()];
               repaint();//fill the cell if correct number is chosen
           }else {
               //fill the cell and highlight it red if wrong
               map.drawWrong(num);
           }
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
