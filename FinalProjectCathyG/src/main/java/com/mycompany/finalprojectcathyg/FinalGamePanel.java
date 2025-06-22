/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.finalprojectcathyg;

//imports
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;
import apphelper.*;
import java.util.ArrayList;




public class FinalGamePanel extends JPanel implements MouseListener, KeyListener {

    
    //init sprite varibles
    private Sprite player;
    private Sprite npc1;
    private Sprite npc2;
    private Sprite npc3;
    private Sprite npc4;
    private Sprite background;
    private Sprite gym;
    private Sprite blockNPC;
    private Sprite battlebg;
    private Sprite mew;
    private Sprite pikachu;
    
    private Sound openworld;
    
    //boolean to find which stage of game you're in (ex. in tictactoe,riddle,openworld,battle,etc. 
    private boolean inTicTacToe = false;
    private boolean inRiddleGame = false;
     private boolean inPokemonBattle = false;
    private boolean inChallenge = false;
    private boolean inFinalBattle=false;
    
    private boolean winTicTacToe=false;
    private boolean winRiddle=false;
    private boolean winPokemonBattle=false;
    
    //find out how many mini games were won by user to determine # pkmn they ahve for final battle
    private int minigamesWon=0;
    
    //char board for tictactoe game

    private char[][] board;
    //jbuttons for tictactoe board
    private JButton[][] buttons;
    
    
    //other vars for other games
    private char currentPlayer;
    private Random random;
    private String riddleAnswer = "8"; 
    private JButton[] riddleOptions; 
    private String[] options = {"8","6","2"};

    
    private Battle battle;
    private JPanel battlePanel;
    private JTextArea battleLog;
            
    //loading in sprites -->remember to replace npc sprites as actual chars-->redX used as placeholder
    public FinalGamePanel() {
        // Initialize player and NPC sprites
        player = new Sprite(400, 300, "userchar.png", 45, 45);
        npc1 = new Sprite(0, 0, "redX.png", 80, 80);
        //npc1=new Sprite(0,0,"npc1.png",48,55);
        npc2 = new Sprite(500, 0, "redX.png", 80, 80);
        //npc2=new Sprite(500,0,"npc2.png",45,45);
        npc3 = new Sprite(250, 500, "redX.png", 80, 80);
        //npc4 = new Sprite(500, 500, "redX.png", 80, 80);
        background = new Sprite(0, 0, "bgfinal.jpeg", 600, 600);
        gym=new Sprite(210,290,"building.png",150,80);
        blockNPC=new Sprite(280,345,"npc2.png",45,45);
        pikachu=new Sprite(100,150,"pikachu.png",100,100);
        mew=new Sprite(400,150,"mew.png",100,100);
        
        
        openworld=new Sound("1-05.-Littleroot-Town.wav");
        //battlebg=new Sprite(0,0,"battlebg.png",600,400);
        //init other vars,addmouse/keylistener, focus in window
        board = new char[3][3];  // Tic-Tac-Toe board initialization
        buttons = new JButton[3][3]; // Tic-Tac-Toe buttons initialization
        currentPlayer = 'X';  // Player 'X' starts
        random = new Random();
        
        addMouseListener(this);
        addKeyListener(this);
        setFocusable(true);
        requestFocusInWindow();
        
        
        
    }
     
    //Arraylist of player pokemons
    private ArrayList<Pokemon> createPlayerPokemon() {
        ArrayList<Pokemon> playerPokemon = new ArrayList<>();
        playerPokemon.add(new Pokemon("Pikachu", 60));
        playerPokemon.add(new Pokemon("Bulbasaur", 60));
        playerPokemon.add(new Pokemon("Charmander", 60));
        return playerPokemon;
     }
    
     
    //araylist of gym leader pokemons 
    private ArrayList<Pokemon> createGymPokemon() {
        ArrayList<Pokemon> gymPokemon = new ArrayList<>();
        gymPokemon.add(new Pokemon("Mewtwo", 100));
        gymPokemon.add(new Pokemon("Dragonite",100));
        gymPokemon.add(new Pokemon("Moltres",100));
        return gymPokemon;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        setBackground(Color.WHITE);
        openworld.loop();
        //openworld user is initially loaded into
        //draw sprites
        
        //if user is not in a "minigame", then draw openworld
        if (!inChallenge) {
            setFocusable(true);
            requestFocusInWindow();
            background.draw(g);
            npc1.draw(g);
            npc2.draw(g);
            npc3.draw(g);
            
            gym.draw(g);
            player.draw(g);
            blockNPC.draw(g);
            
            
            
            //prevent user from going outside world
            if(player.crossingLeft(-10)){
                player.setX(0);
                repaint();
            }
            
            else if(player.crossingRight(615)){
                player.setX(570);
                repaint();
            }
            else if(player.crossingTop(-10)){
                player.setY(0);
                repaint();
            }
            else if(player.crossingBottom(600)){
                player.setY(545);
                repaint();
            }
            
            
          
        } 
        //check if user has initiated any games, if they are run the code for the game
        else if (inTicTacToe) {
            
            startTicTacToe();
        } 
        else if (inRiddleGame) {
            
            startRiddleGame(g);
        }
        else if(inPokemonBattle){
            
            startPokemonBattle();
        }
        
    }

    @Override
    public void keyPressed(KeyEvent e) {
        //if the user is not in a minigame, allow keys to be pressed
        //for movement in openworld
        //user sprite rotates depending on direction user is going
        if (!inChallenge && !inRiddleGame&&!inPokemonBattle&&!inTicTacToe) { 
            switch (e.getKeyCode()) {
                case KeyEvent.VK_LEFT:
                    player.setRotationAngle(90);
                    player.moveOneStepX();
                    repaint();
                    break;
                case KeyEvent.VK_RIGHT:
                    player.setRotationAngle(270);
                    player.moveOneStepXneg();
                    repaint();
                    break;
                case KeyEvent.VK_UP:
                    player.setRotationAngle(180);
                    player.moveOneStepYneg();
                    repaint();
                    break;
                case KeyEvent.VK_DOWN:
                    player.setRotationAngle(0);
                    player.moveOneStepY();
                    repaint();
                    break;
            }
        }
    }

    //mandatory methods
    @Override
    public void keyReleased(KeyEvent e) {
         //throw new UnsupportedOperationException("Not supported yet."); 
        // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody

    }

    @Override
    public void keyTyped(KeyEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); 
        // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    
    //if the users mouse is clicking any npc sprites, then initiate the appropriate minigame
    @Override
    public void mousePressed(MouseEvent e) {
        if ((npc1.inside(e.getX(), e.getY()))&&player.collidesWith(npc1)) {
            startChallenge("TicTacToe");
        } 
        else if ((npc2.inside(e.getX(), e.getY()))&&player.collidesWith(npc2)) {
            startChallenge("RiddleGame");  
        } 
        else if ((npc3.inside(e.getX(), e.getY()))&&player.collidesWith(npc3)) {
            inChallenge = true;
            startChallenge("PokemonBattle");
        } 
       //if the user clicks the gym/npc infront of gym
        else if((blockNPC.inside(e.getX(),e.getY()))||gym.inside(e.getX(), e.getY())){
            //if the user hasn't completed any minigames, prevent them from initiating final battle
            if((minigamesWon==0)){
            JOptionPane.showMessageDialog(null, "Hey! You can't challenge the final battle without winning a mini-game!");
            }
            //if user completed at least 1 game, allow them to enter final battle
            else if(minigamesWon>=1){
                inChallenge=true;
                inFinalBattle=true;
                ArrayList<Pokemon> playerPokemon = new ArrayList<>();
            // Add player's Pokémon based on mini-games won
           //Add pokmeon based on how many minigames won
            if (minigamesWon >= 1) {
            playerPokemon.add(new Pokemon("Pikachu", 60));
                 }
                if (minigamesWon >= 2) {
            playerPokemon.add(new Pokemon("Bulbasaur", 60));
                }
                if (minigamesWon >= 3) {
                playerPokemon.add(new Pokemon("Charmander", 60));
                }
                //add gym leader pokemons to team
                ArrayList<Pokemon> gymPokemon = new ArrayList<>();
                gymPokemon.add(new Pokemon("Mewtwo", 100));
                gymPokemon.add(new Pokemon("Dragonite",100));
                gymPokemon.add(new Pokemon("Moltres",100));
                    
                //create new frame, panel, textarea for final battle
                JPanel battlePanel2 = new JPanel();
                JTextArea battleLog = new JTextArea();
                battleLog.setEditable(false);
                JScrollPane logScroll = new JScrollPane(battleLog);
                JFrame newBattleFrame=new JFrame();
                //battlePanel.add(logScroll, BorderLayout.CENTER);
                //battlePanel.setLayout(new BorderLayout());

                // Initialize battle class
                Battle battle = new Battle(playerPokemon, gymPokemon, battlePanel2,battleLog,newBattleFrame,minigamesWon);
                //remove the finalgamepanel frame
                SwingUtilities.getWindowAncestor(this).dispose();
                //stop music, start final battle
                openworld.stop();
                battle.start();
            }
            
        }
        
    }
    
    
    
    @Override
    public void mouseReleased(MouseEvent e) {
        
        //throw new UnsupportedOperationException("Not supported yet."); 
        // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); 
        // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    
    }

    @Override
    public void mouseExited(MouseEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); 
        // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); 
        // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    
    }
    
    
    //checks the name of the challenge to determine which minigme to run
    private void startChallenge(String challengeName) {
        inChallenge = true;  

        if (challengeName.equals("TicTacToe")) {
            inTicTacToe = true;  
            startTicTacToe();
        } else if (challengeName.equals("RiddleGame")) {
            inRiddleGame = true;  
            startRiddleGame(null);
        }
        else if(challengeName.equals("PokemonBattle")){
            inPokemonBattle=true;
            startPokemonBattle();
        }
        else if(challengeName.equals("FinalBattle")){
            inFinalBattle=true;
            
        }
    }

    
    //code for tictactoe game
    private void startTicTacToe() {
        JFrame frame = new JFrame("Tic Tac Toe");
        
        //using gridlayout for easier formatting for game
        frame.setLayout(new GridLayout(3, 3));
        frame.setSize(300, 300);

        //init buttons, fonts, etc. 
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                buttons[i][j] = new JButton("");
                buttons[i][j].setFont(new Font("Arial", Font.PLAIN, 40));
                buttons[i][j].addActionListener(e -> {
                    JButton button = (JButton) e.getSource();
                    int row = -1, col = -1;
                    for (int r = 0; r < 3; r++) {
                        for (int c = 0; c < 3; c++) {
                            if (buttons[r][c] == button) {
                                row = r;
                                col = c;
                                break;
                            }
                        }
                    }
                   
                    if (board[row][col] == '\0') {
                        board[row][col] = currentPlayer;
                        button.setText(String.valueOf(currentPlayer));
                        //check for wins/ties/etc.
                        if (checkWin(currentPlayer)) {
                            JOptionPane.showMessageDialog(frame, "Player " + currentPlayer + " wins!");
                            
                            inChallenge = false;
                            minigamesWon++;
                            frame.dispose();
                            inTicTacToe = false;
                            return;
                        }
                        if (checkTie()) {
                            JOptionPane.showMessageDialog(frame, "It's a tie!");

                            inChallenge = false;

                            frame.dispose();
                            inTicTacToe = false;
                            return;
                        }
                        //check whos move it is
                        currentPlayer = (currentPlayer == 'X') ? 'O' : 'X';
                        if (currentPlayer == 'O'){
                            autoMove();
                        }
                    }
                });
                frame.add(buttons[i][j]);
                board[i][j] = '\0';
            }
        }

        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setVisible(true);
    }
    //code for random computer engine move
    private void autoMove() {
        int row, col;
        do {
            //get random row and column value
            row = random.nextInt(3);
            col = random.nextInt(3);
        } 
        //if the randomly generated space is not occupied, place an O there
        while (board[row][col] != '\0');
        board[row][col] = 'O';
        buttons[row][col].setText("O");
        
        //check if engine won, tied, etc. 
        if (checkWin('O')) {
            JOptionPane.showMessageDialog(null, "Player O wins!");
            inChallenge = false;
            resetGame();
            return;
        }
        if (checkTie()) {
            JOptionPane.showMessageDialog(null, "It's a tie!");
            inChallenge = false;
            resetGame();
            return;
        }
        //after computer turn, and no wins/ties, change current player to user
        currentPlayer = 'X';
    }

    
    //checking win conditions
    private boolean checkWin(char player) {
        
        //check each row and column
        for (int i = 0; i < 3; i++) {
            if ((board[i][0] == player && board[i][1] == player && board[i][2] == player) || (board[0][i] == player && board[1][i] == player && board[2][i] == player)) { 
                return true;
            }
        }
        //check the 2 diagonals
        return (board[0][0] == player && board[1][1] == player && board[2][2] == player) || (board[0][2] == player && board[1][1] == player && board[2][0] == player);  
    }

    //check if it is a tie (if the board is full, no 3 in a row
    private boolean checkTie() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == '\0') return false;
            }
        }
        return true;
    }
    //exits from the game
    private void resetGame() {
       
        inTicTacToe = false;
        inChallenge = false;  
        repaint(); 
    }

    
    //code for riddle game in new window
    private void startRiddleGame(Graphics g) {
        JFrame riddleFrame = new JFrame("Riddle Game");
        riddleFrame.setLayout(new FlowLayout());
        riddleFrame.setSize(300, 100);

        
        //"riddle" question asked
        
        
        JOptionPane.showMessageDialog(riddleFrame, "How many evolutions does eevee have?");
       

        
        //use JButtons for 4 possible answers
        riddleOptions = new JButton[3];
        for (int i = 0; i < 3; i++) {
            final String option = options[i];
            riddleOptions[i] = new JButton(option);
            riddleOptions[i].addActionListener(e -> {
                //check if button chosen matched correct answer
                if (option.equals(riddleAnswer)) {
                    JOptionPane.showMessageDialog(riddleFrame, "Correct!");
                    minigamesWon++;
                } else {
                    JOptionPane.showMessageDialog(riddleFrame, "Incorrect! Try again.");
                }
                riddleFrame.dispose();
                inRiddleGame = false;
                inChallenge = false;
                repaint();
            });
            riddleFrame.add(riddleOptions[i]);
        }

        riddleFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        riddleFrame.setVisible(true);
    }
    
    
    //pokemon battle code in new window
    private void startPokemonBattle() {
        JFrame battleFrame = new JFrame("Pokémon Battle");
        battleFrame.setSize(600, 400);
        battleFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        battleFrame.setLayout(new BorderLayout());

        BattlePanel battlePanel = new BattlePanel();
        //using borderlayout for easier formatting
        battleFrame.add(battlePanel, BorderLayout.CENTER);

        battleFrame.setVisible(true);
    }

    //sets both pokemon HP, attacks, etc. 
    private class BattlePanel extends JPanel {
    private int playerHP = 50;
    private int opponentHP = 50;
    private Random random = new Random();
    private String[] moves = {"Tackle", "Bite", "Slam", "Quick Attack"};
    private String battleMessage = "Battle begins!";

    public BattlePanel() {
        setLayout(new BorderLayout());

        // Control panel for player's moves
        JPanel attackDashboard = new JPanel();
        //gridlayout to line up the move buttons
        attackDashboard.setLayout(new GridLayout(1, 4));
        for (String move : moves) {
            JButton moveButton = new JButton(move);
            moveButton.addActionListener(e -> playerTurn(move));
            attackDashboard.add(moveButton);
        }
        //add the display panel for chosing moves at the bottom of screen
        add(attackDashboard, BorderLayout.SOUTH);
    }
    
    
    //calculates damage, displays text onscreen. If the user rolls a 15 dmg, it is considered a critical hit
    private void playerTurn(String move) {
        int damage = random.nextInt(11) + 5;
        
        
        //add crit mechanics-->if 10% chance crit happens, do x2 dmg
        Random r = new Random();
        int crit=r.nextInt(10);
        
        if(crit==9){
            opponentHP-=(damage*2);
            battleMessage = "Critical Hit! You used " + move + "! It did " + damage + " damage!";
        }
        else{
            opponentHP -= damage;
            battleMessage = "You used " + move + "! It did " + damage + " damage!";
        }
        
        
        repaint();

        
        //if opponent pkmn HP is 0, you win, close battle
        if (opponentHP <= 0) {
            JOptionPane.showMessageDialog(this, "Opponent Pokemon Fainted! You win!");
            minigamesWon++;
            closeBattle();
        } 
        
        else {
            //delay the opponent's turn by a sec because battle is too quick without
            Timer timer = new Timer(1000, e -> opponentTurn());
            timer.setRepeats(false);
            timer.start();
        }
    }

    //opponent attack code
    private void opponentTurn() {
        //generates random dmg for opponent pokemon
        int damage = random.nextInt(11) + 5;
        
        
        //crit mechanics for opponent
        Random r = new Random();
        int crit=r.nextInt(10);
        
        if(crit==9){
            playerHP-=(damage*2);
            battleMessage = "Opponent attacked! Critical Hit! It did " + damage + " damage!";
        }
        else{
            playerHP -= damage;
            battleMessage = "Opponent attacked! It did " + damage + " damage!";
        }
        
        repaint();

        
        //checks if user lost after opponent's turn
        if (playerHP <= 0) {
            JOptionPane.showMessageDialog(this, "Your Pokemon Fainted! You lose!");
            closeBattle();
        }
    }

    //closing window after battle
    private void closeBattle() {
        inPokemonBattle = false;
        inChallenge = false;
        SwingUtilities.getWindowAncestor(this).dispose();
    }
   
    //drawing for pokemon battle
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        //set background color
        g.setColor(new Color(242, 235, 177));
        g.fillRect(0, 0, getWidth(), getHeight());
        
        
        //draw sprites
        pikachu.draw(g);

        mew.draw(g);

        // Displayed HP above pokemon
        g.setColor(Color.BLACK);
        g.drawString("Player HP: " + Math.max(playerHP, 0), 100, 130);
        g.drawString("Opponent HP: " + Math.max(opponentHP, 0), 400, 130);

        //displaying the battle messages (ex. how much dmg, battle begin, etc. )
        g.drawString(battleMessage, 200, 300);
        
       
 
    }
}
     
}