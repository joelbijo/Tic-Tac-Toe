import javax.swing.*; //for main gui components like frame and buttons
import java.awt.event.*; // for ActionListener, ActionEvent
import java.awt.*; // for font,colour and layout

public class Tics{ //class is not public, so file name is not t.java
    protected JFrame frame;
    protected JButton[][] button=new JButton[3][3];
    protected boolean player1turn=true;//true for player 1,false for player 2
    public Tics(){
        this.frame = new JFrame("Tic Tac Toe");//referring to the current frame
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500,650);
        frame.setLayout(new GridLayout(3,3));//for 3x3 grid
        initializeButtons();//called to set visible later on
        frame.setVisible(true);
    }
  
    private void initializeButtons(){
        for(int i=0;i<3;i++){
            for(int j=0;j<3;j++){
                button[i][j]=new JButton();
                button[i][j].setFont(new Font("Arial",Font.PLAIN,60));
                frame.add(button[i][j]);
                final int row=i, col=j;//values must not change after ActionListener
                button[i][j].addActionListener(new ActionListener(){//add click event
                    //ActionEvent: obj that carries info about a button clicked
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
        if(checkWinner()==true) {
            JOptionPane.showMessageDialog(frame,(player1turn ? "X" : "O")+" wins!");
            resetBoard();
        } 
        else if(isBoardFull()==true) {
            JOptionPane.showMessageDialog(frame,"It's a draw!");
            resetBoard();
        }
        else {
            player1turn = !player1turn;  // Switch turn player1=player2
        }
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
    private boolean checkWinner() {
        String letter = player1turn ? "X" : "O";
        //check rows and columns
        for(int i=0;i<3;i++){
            //getText() returns string, & equals() to compare
            if(button[i][0].getText().equals(letter) && button[i][1].getText().equals(letter) && button[i][2].getText().equals(letter)){
                return true;
            }
            else if(button[0][i].getText().equals(letter) && button[1][i].getText().equals(letter) && button[2][i].getText().equals(letter)){
                return true;
            }
        }
        //check diagonals
        if(button[0][0].getText().equals(letter) && button[1][1].getText().equals(letter) && button[2][2].getText().equals(letter)){
            return true;
        }
        else if(button[0][2].getText().equals(letter) && button[1][1].getText().equals(letter) && button[2][0].getText().equals(letter)){
            return true;
        }
        //if no conditions are met
        return false;
    }

    //method to reset the board for a new game
    private void resetBoard(){
        for(int i=0;i<3;i++){
            for(int j=0;j<3;j++){
                button[i][j].setText("");//make cells empty
            }
        }
        player1turn = true;//reset player1turn to true
    } 

    public static void main(String[] args){
        new Tics();
    }
}