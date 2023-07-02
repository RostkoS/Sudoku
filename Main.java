import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;


public class Main extends JFrame implements ActionListener{


    private Game game;
    private int numOfHoles;
    private final String[] diff=new String[]{"Easy", "Medium", "Hard"};
    private JComboBox cb;
    private JButton button;

    public Main() {
        initUI(); //initializing UI
    }

    private void initUI() {
        //initializing reset button
        button = new JButton("Reset");
        button.setBounds(0,0,200,50);
        button.setFont( new Font(Font.SERIF,Font.BOLD,30));
        button.addActionListener(this);
        //initializing combo box with difficulties
        cb= new JComboBox<>(diff);
        cb.setFont( new Font(Font.SERIF,Font.BOLD,30));
        cb.setBounds(25,260,150,50);
        cb.addActionListener(this);
        //creating label for combo box
        JLabel l = new JLabel("Set difficulty");
        l.setBounds(25,220,150,50);
        //adding jcomponents to frame
        add(l);
        add(cb);
        add(button);
        //initializing default status of game
        numOfHoles =10;
        game =new Game(numOfHoles);
        //adding game to frame
        add(game);

        //setting window properties
        setTitle("Sudoku");
        setSize(668, 490);
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }

    public static void main(String[] args) {

            Main ex = new Main();
            ex.setVisible(true);

    }

    //toggling click on jcomponents
    @Override
    public void actionPerformed(ActionEvent e) {
        Object b = e.getSource();
        if(e.getSource()==cb){//change difficulty according to combo box
            if(cb.getSelectedIndex()==0) {
                numOfHoles = 20;//easy
                game.reset(numOfHoles);
            }else if(cb.getSelectedIndex()==1) {
                numOfHoles = 30;//medium
                game.reset(numOfHoles);
            }else if(cb.getSelectedIndex()==2) {
                numOfHoles = 40;//hard
                game.reset(numOfHoles);
            }
        }else if(e.getSource()==b){//action for reset button
            game.reset(numOfHoles);
        }
    }
}



