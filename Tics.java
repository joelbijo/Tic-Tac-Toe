import java.awt.*; //for main gui components like frame and buttons
import java.awt.event.*; // for ActionListener, ActionEvent
import javax.swing.*; // for font,colour and layout

public class Tics{ 
    protected JFrame frame;
    protected JButton[][] button=new JButton[3][3];
    protected boolean player1turn=true;//true for player 1,false for player 2
    protected int player1_Score=0;
    protected int player2_Score=0;

    protected JLabel statusLabel;
    protected JLabel scoreLabel;

    public Tics(){
        this.frame = new JFrame("Tic Tac Toe");//referring to the current frame
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600,800);
        frame.setLayout(new BorderLayout());

        JPanel gridP = new JPanel(new GridLayout(3,3));
        initializeButtons(gridP);//called to set visible later on
        
        statusLabel = new JLabel("Player 1's turn (X)", SwingConstants.CENTER);//center pos
        statusLabel.setFont(new Font("Arial", Font.BOLD, 24));
        statusLabel.setForeground(Color.BLUE);

        scoreLabel = new JLabel(getScoreText(), SwingConstants.CENTER);
        scoreLabel.setFont(new Font("Arial", Font.BOLD, 24));
        scoreLabel.setForeground(Color.MAGENTA);

        JPanel topP = new JPanel(new GridLayout(2,1));
        topP.add(statusLabel);
        topP.add(scoreLabel);

        frame.add(topP, BorderLayout.NORTH);
        frame.add(gridP, BorderLayout.CENTER);

        frame.setVisible(true);
    }
  
    private void initializeButtons(JPanel panel){
        for(int i=0;i<3;i++){
            for(int j=0;j<3;j++){
                button[i][j]=new JButton();
                button[i][j].setFont(new Font("Arial",Font.PLAIN,60));
                panel.add(button[i][j]);//button to panel, since panel in frame

                final int row=i, col=j;//values must not change after ActionListener
                button[i][j].addActionListener(new ActionListener(){//add click event
                    //ActionEvent: obj that carries info about a button clicked
                    @Override
                    public void actionPerformed(ActionEvent e){//response when button clicked
                    // e is needed in syntax, however not using,since row and column known
                        handleMove(row, col);
                    }
                });//close ActionListener definition and addActionListener call              
            }
        }
    }

    //method to handle player moves
    private void handleMove(int row, int col){//takes final values
        //if used button clicked again, display dialog msg
        if(!button[row][col].getText().equals("")){
            JOptionPane.showMessageDialog(frame,"Choose another cell");
            return;//Exit so no other changes occur
        }
        //set X or O for the current player's turn
        button[row][col].setText(player1turn ? "X" : "O");
        int[][] winningCells = getWinningCells();
        if(winningCells != null) {
            // Highlight winning buttons
            for(int[] cell : winningCells){
                button[cell[0]][cell[1]].setBackground(Color.GREEN);
                button[cell[0]][cell[1]].setOpaque(true);
                button[cell[0]][cell[1]].setBorderPainted(false);
            }
            if (player1turn){
                player1_Score++;
            } 
            else{
                player2_Score++;
            } 

            scoreLabel.setText(getScoreText());
            String winner = player1turn ? "Player 1 (X)" : "Player 2 (O)";
            Timer timer = new Timer(500, new ActionListener(){
                @Override
                public void actionPerformed(ActionEvent e){
                    playAgain(winner + " wins!");
                }
            });
            timer.setRepeats(false);
            timer.start();
        } 
        else if(isBoardFull()==true) {
            playAgain("It's a Draw!");
        }
        else {
            player1turn = !player1turn;  // Switch turn player1=player2
            updateStatus();
        }
    }

    private void updateStatus(){
        if(player1turn){
            statusLabel.setText("Player 1's Turn (X)");
            statusLabel.setForeground(Color.BLUE);
        } 
        else{
            statusLabel.setText("Player 2's Turn (O)");
            statusLabel.setForeground(Color.RED);
        }
    }

    private String getScoreText(){
         return "Player 1 (X): " + player1_Score + "   |   Player 2 (O): " + player2_Score;
    }


    //method to check if board is full
    private boolean isBoardFull(){
        for(int i=0;i<3;i++){
            for(int j=0;j<3;j++){
                if(button[i][j].getText().equals("")) {
                    return false;//[i][j] cell is empty
                }
            }
        }
        return true;
    }

    //method to check if current player won
    private int[][] getWinningCells() {//[row][col]
        String letter = player1turn ? "X" : "O";
        //check rows and columns
        for(int i=0;i<3;i++){
            //getText() returns string, & equals() to compare
            if(button[i][0].getText().equals(letter) && button[i][1].getText().equals(letter) && button[i][2].getText().equals(letter)){
                return new int[][]{{i,0},{i,1},{i,2}};//create new array everytime
            }
            else if(button[0][i].getText().equals(letter) && button[1][i].getText().equals(letter) && button[2][i].getText().equals(letter)){
                return new int[][]{{0,i},{1,i},{2,i}};
            }
        }
        //check diagonals
        if(button[0][0].getText().equals(letter) && button[1][1].getText().equals(letter) && button[2][2].getText().equals(letter)){
            return new int[][]{{0,0},{1,1},{2,2}};
        }
        else if(button[0][2].getText().equals(letter) && button[1][1].getText().equals(letter) && button[2][0].getText().equals(letter)){
            return new int[][]{{0,2},{1,1},{2,0}};
        }
        //if no conditions are met
        return null;
    }

    private void playAgain(String message){
        int response = JOptionPane.showConfirmDialog(frame, message+"\nDo you want to play again?","Game Over!",JOptionPane.YES_NO_OPTION);
        if(response==JOptionPane.YES_OPTION){
            resetBoard();
        }
        else{
            JOptionPane.showMessageDialog(frame, "Final Scores:\n" + getScoreText() + "\nThank you for playing!");
            frame.dispose();//release all resources, and free memory
            System.exit(0);//terminate JVM
        }
    }

    //method to reset the board for a new game
    private void resetBoard(){
        for(int i=0;i<3;i++){
            for(int j=0;j<3;j++){
                button[i][j].setText("");//make cells empty
                button[i][j].setBackground(null);//reset to default colour
                button[i][j].setOpaque(false);
                button[i][j].setBorderPainted(true);
            }
        }
        player1turn = true;//reset player1turn to true
        updateStatus();
    } 

    public static void main(String[] args){
        new Tics();
    }
}
