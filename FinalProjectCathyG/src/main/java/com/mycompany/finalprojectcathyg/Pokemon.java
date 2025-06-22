/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.finalprojectcathyg;

/**
 *
 * @author cathyguo
 */

//pkmn class
public class Pokemon {
    //vars for pkmn name, hps, move PP, dmg
    private String name;
    private int hp;
    private int maxHp;
    private int[] movePp = new int[4]; 
    private int[] moveDamage = {5, 10, 15, 20}; 

    //constructor
    public Pokemon(String name, int hp) {
        this.name = name;
        this.hp = hp;
        this.maxHp = hp;
        for (int i = 0; i < 4; i++) {
            this.movePp[i] = 5;
        }
    }

    public String getName() {
        return this.name;
    }

    public int getHp() {
        return this.hp;
    }

    //let pokemon take dmg
    public void takeDamage(int damage) {
        this.hp = Math.max(0, this.hp - damage);
    }
    //check if pkmn is aive
    public boolean isAlive() {
        return this.hp > 0;
    }

    //pkmn attack method
    public int attack(int moveIndex) {
        //fails if not valid move (ex. no more pp)
        if (moveIndex < 0 || moveIndex >= 4 || movePp[moveIndex] <= 0) {
            return 0; 
        }
        //if attack goes through, remove 1 PP from the specific move, then return dmg done for move
        movePp[moveIndex]--; 
        return moveDamage[moveIndex]; 
       
   

    }
    //check if  move can be used
    public boolean canUseMove(int moveIndex) {
        return movePp[moveIndex] > 0;
    }
}
