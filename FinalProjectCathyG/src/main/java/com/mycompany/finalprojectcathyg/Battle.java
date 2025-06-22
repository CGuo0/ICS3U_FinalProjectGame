/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.finalprojectcathyg;

/**
 *
 * @author cathyguo
 */
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.Timer;
import apphelper.*;

//battle class for final battle
public class Battle extends JPanel{
    //variables, arraylists, etc. for battle
    private ArrayList<Pokemon> playerPokemon;
    private ArrayList<Pokemon> gymPokemon;
    private Pokemon currentPlayerPokemon;
    private Pokemon currentGymPokemon;
    private int currentPlayerIndex = 0; 
    private JPanel battlePanel;
    private JTextArea battleLog;
    private JButton[] moveButtons;
    private JButton switchButton;
    private JFrame newBattleFrame;
    
    //sprites
    Sprite pikachu=new Sprite(150,250,"pikachuBattle.png",125,125);
    Sprite bulbasaur=new Sprite(150,250,"bulbasaurBattle.png",125,125);
    Sprite charmander=new Sprite(150,250,"charmanderBattle.png",125,125);
    Sprite battlebg=new Sprite(0,0,"battlebg.png",800,375);
    
    Sprite mewtwo=new Sprite(565,100,"mewtwo.png",145,145);
    Sprite dragonite=new Sprite(565,100,"dragoniteBattle.png",125,125);
    Sprite moltres=new Sprite(565,100,"moltresBattle.png",125,125);
    
    
    //sounds
    private Sound battle=new Sound("1-09.-Battle_-_Wild-Pokémon_.wav");
    private Sound win=new Sound("1-37.-Win.wav");
    private Sound lose=new Sound("1-38.-Lose.wav");
    
    
    
    private int numberOfMiniGamesWon;  
    private ArrayList<Sprite> playerSprites;
    Timer finaltimer; 
    private int battleStatus=0;
    
    //constructor for battle
    public Battle(ArrayList<Pokemon> playerPokemon, ArrayList<Pokemon> gymPokemon, JPanel battlePanel, JTextArea battleLog,JFrame newBattleFrame,int miniGamesWon) {
        
        //bring arrays created from finalgamepanel to battle class
        this.playerPokemon = playerPokemon;
        this.gymPokemon = gymPokemon;
        this.newBattleFrame=newBattleFrame;
        this.battlePanel = battlePanel;
        this.battleLog = battleLog;
        this.currentPlayerPokemon = playerPokemon.get(currentPlayerIndex);
        this.currentGymPokemon = gymPokemon.get(0);
        
        this.numberOfMiniGamesWon=miniGamesWon;
        this.playerSprites=new ArrayList<>();
        
     
        
        this.moveButtons = new JButton[4];
        
        setupUI();
    }
    
    @Override
    protected void paintComponent(Graphics g){
        
        super.paintComponent(g);
        
        //play sounds, draw sprites
        battle.loop();
        battlebg.draw(g);
        
        g.setColor(Color.WHITE);
       
        
        pikachu.draw(g);
        bulbasaur.draw(g);
        charmander.draw(g);
        
        mewtwo.draw(g);
        dragonite.draw(g);
        moltres.draw(g);
        
        //draw current pokemon for user and gym leader on screen
        //if user switches out, switch to correct pokemon onscreen
        
        if(currentPlayerPokemon.getName().equals("Pikachu")){
            pikachu.setX(150);
            bulbasaur.setX(1000);
            charmander.setX(1000);
            
            g.drawString("Pikachu HP: " + currentPlayerPokemon.getHp(), 165, 215);
            repaint();
            
        }
        else if(currentPlayerPokemon.getName().contains("Bulbasaur")){
            bulbasaur.setX(150);
            pikachu.setX(1000);
            charmander.setX(1000);
            
            g.drawString("Bulbasaur HP: " + currentPlayerPokemon.getHp(), 165, 215);
            repaint();
        }
        else if(currentPlayerPokemon.getName().contains("Charmander")){
            charmander.setX(150);
            pikachu.setX(1000);
            bulbasaur.setX(1000);
            
            g.drawString("Charmander HP: " + currentPlayerPokemon.getHp(), 165, 215);
            repaint();
        }
        
        mewtwo.draw(g);
        dragonite.draw(g);
        moltres.draw(g);
        
        if(currentGymPokemon.getName().contains("Mewtwo")){
            mewtwo.setX(565);
            dragonite.setX(1000);
            moltres.setX(1000);
            
            g.drawString("Mewtwo HP: " + currentGymPokemon.getHp(), 565, 100);
            repaint();
        }
        else if(currentGymPokemon.getName().contains("Dragonite")){
            mewtwo.setX(1000);
            dragonite.setX(565);
            moltres.setX(1000);
            
            
            g.drawString("Dragonite HP: " + currentGymPokemon.getHp(), 565, 100);
            repaint();
        }
        else if(currentGymPokemon.getName().contains("Moltres")){
            mewtwo.setX(1000);
            dragonite.setX(1000);
            moltres.setX(565);
            
            g.drawString("Moltres HP: " + currentGymPokemon.getHp(), 565, 100);
            
            repaint();
            
        }
        
                
        
        //display if battle is lost
        if(battleStatus==2){
            battle.stop();
            lose.start();
            g.setColor(Color.black);
            g.fillRect(0,0,getWidth(),getHeight());
            g.setColor(Color.white);
            int fontSize=20;
            Font endFont=new Font("Arial",Font.BOLD,fontSize);
            g.setFont(endFont);
            g.drawString("Game Over",325,230);
        }
        //display if battle is won
        if(battleStatus==1){
            battle.stop();
            win.start();
            g.setColor(Color.black);
            g.fillRect(0,0,getWidth(),getHeight());
            g.setColor(Color.white);
            int fontSize=20;
            Font endFont=new Font("Arial",Font.BOLD,fontSize);
            g.setFont(endFont);
            g.drawString("You Win!",325,230);
            
        }
     
    
    }
    

    private void setupUI() {
        //make new frame, add panels, buttons, etc. 
       
        
        newBattleFrame.setLayout(new BorderLayout());
     
        
        JPanel actionPanel = new JPanel();
        actionPanel.setLayout(new GridLayout(2, 3));

        
        
        
        for (int i = 0; i < 4; i++) {
            int moveIndex = i;
            moveButtons[i] = new JButton("Move " + (i + 1));
            moveButtons[i].addActionListener(e -> handleMoveClick(moveIndex));
            actionPanel.add(moveButtons[i]);
            
        }

        // Switch button to switchoutu pokemon
        switchButton = new JButton("Switch Pokémon");
        switchButton.addActionListener(e -> handleSwitchClick());
        actionPanel.add(switchButton);

      

        // Add battle log to document what happens in battle --> dmg, pkmn used, KO's, switches
        battleLog.setEditable(false);
        JScrollPane logScroll = new JScrollPane(battleLog);
        
          

        
        
        
 
        newBattleFrame.add(this);
        newBattleFrame.setSize(1000,500);
   
        
        
        newBattleFrame.add(logScroll,BorderLayout.EAST);
        newBattleFrame.add(actionPanel,BorderLayout.SOUTH);
        
        
        newBattleFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        newBattleFrame.setVisible(true);
    }

    
    //method to start battle
    public void start() {
        updateBattleLog("Battle Started: " + currentPlayerPokemon.getName() + " vs " + currentGymPokemon.getName());
        updateBattleUI();
    }

  
    
    //if move is clicked, deal dmg based on move index, display info on battle log, do damage to opponent pkmn
    private void handleMoveClick(int moveIndex) {
    if (currentPlayerPokemon == null || currentGymPokemon == null) {
        updateBattleLog("Error: Invalid Pokémon state!");
        return;
    }

    
   //if user can use move
    if (currentPlayerPokemon.canUseMove(moveIndex)) {
        int damage = currentPlayerPokemon.attack(moveIndex);
       //crit mechanics-->10% chance to do x2 dmg
        int crit=new Random().nextInt(10);
        
        if(crit==9){
            currentGymPokemon.takeDamage(damage*2);
            updateBattleLog("Critical Hit! "+currentPlayerPokemon.getName() + " used Move " + (moveIndex + 1) + " and dealt " + (damage*2) + " damage!");
        }
        else{
            currentGymPokemon.takeDamage(damage);
            updateBattleLog(currentPlayerPokemon.getName() + " used Move " + (moveIndex + 1) + " and dealt " + damage + " damage!");
        }
        
        
        

        if (currentGymPokemon.isAlive()) {
            int gymMove = new Random().nextInt(4); 
            int gymDamage = currentGymPokemon.attack(gymMove);
            
            //crit mechanics -->same as user crit mechanics
            int crit2=new Random().nextInt(10);
            
            if(crit2==9){
            
                currentPlayerPokemon.takeDamage(gymDamage*2);
                updateBattleLog("Critical Hit! "+currentGymPokemon.getName() + " attacked with Move " + (gymMove + 1) + " and dealt " + (gymDamage*2)+ " damage!");
            }
            else{
                currentPlayerPokemon.takeDamage(gymDamage);
                updateBattleLog(currentGymPokemon.getName() + " attacked with Move " + (gymMove + 1) + " and dealt " + gymDamage + " damage!");
            }
            }
        //check if pokemon fainted
        checkForBattleEnd();
    } else {
        updateBattleLog("Move " + (moveIndex + 1) + " can't be used anymore!");
    }
}


   
    //handle switching pokemon in
    private void handleSwitchClick() {
    int initialIndex = currentPlayerIndex; 
    do {
        currentPlayerIndex = (currentPlayerIndex + 1) % playerPokemon.size();
        currentPlayerPokemon = playerPokemon.get(currentPlayerIndex);
    } while (!currentPlayerPokemon.isAlive() && currentPlayerIndex != initialIndex);

    if (currentPlayerPokemon.isAlive()) {
        updateBattleLog("You switched to " + currentPlayerPokemon.getName());
        updateBattleUI();
    } else {
        updateBattleLog("No other Pokémon are available to switch into!");
    }
}

    
    
    
    
    
    

    //method to update most recent battle log
    private void updateBattleLog(String message) {
        battleLog.append(message + "\n");
        battleLog.setCaretPosition(battleLog.getDocument().getLength()); // Auto-scroll
    }

    //check if any pkmn is fainted, or if user/opponent pokemons are all KO'd
    private void checkForBattleEnd() {
        
        
        //check if user pkmn fainted
        
         if (!currentPlayerPokemon.isAlive()) {
        updateBattleLog(currentPlayerPokemon.getName() + " fainted!");
        
        // Find the next available Pokémon that isn't fainted
        boolean foundAlivePokemon = false;
        for (int i = 0; i < playerPokemon.size(); i++) {
            //cycle to next pkmn if you still have another left
            currentPlayerIndex = (currentPlayerIndex + 1) % playerPokemon.size(); 
            if (playerPokemon.get(currentPlayerIndex).isAlive()) {
                currentPlayerPokemon = playerPokemon.get(currentPlayerIndex);
                foundAlivePokemon = true;
                updateBattleLog("You switched to " + currentPlayerPokemon.getName());
                break;
            }
        }

        // if no pokemon are found alive, you lose battle
        if (!foundAlivePokemon) {
            updateBattleLog("You lost the battle!");
            battle.stop();
            battleStatus=2;
            
            //timer before battle frame deltes
            finaltimer = new javax.swing.Timer(5000, e -> newBattleFrame.dispose());
            finaltimer.setRepeats(false);
            finaltimer.start();
            return;
        }
    }

        
        
        
        //check if gym leader pokemon is alive
        if (!currentGymPokemon.isAlive()) {
            updateBattleLog(currentGymPokemon.getName() + " fainted!");
            gymPokemon.remove(currentGymPokemon);
            
            
            //if gym leader has no more pkmn, you win
            if (gymPokemon.isEmpty()) {
                updateBattleLog("You won the battle!");
                battleStatus=1;
                battle.stop();
                
                //dispose of frame after timer
                finaltimer = new javax.swing.Timer(5000, e -> newBattleFrame.dispose());
                finaltimer.setRepeats(false);
                finaltimer.start();
                
                
                //if the gym leader still has alive pkmn, send it out
            } else {
                currentGymPokemon = gymPokemon.get(0);
                updateBattleLog("Gym Leader Sent out "+ currentGymPokemon.getName());
            }
        }
        updateBattleUI();
    }
   
   
    private void updateBattleUI() {
      
        //check if can use move for each move button
        for (int i = 0; i < 4; i++) {
            moveButtons[i].setEnabled(currentPlayerPokemon.canUseMove(i));
        }
    }

   
}
