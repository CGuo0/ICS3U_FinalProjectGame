/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.finalprojectcathyg;


import java.awt.BorderLayout;
import java.awt.Color;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
/**
 *
 * @author cathyguo
 */
public class FinalProjectCathyG {

    public static void main(String[] args) {
        
        //add finalgamepanel to frame
        
        JFrame frame = new JFrame("Pokémon Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 600);
        frame.setLocation(600, 300);
        
        
        FinalGamePanel s = new FinalGamePanel();
        s.setBackground(Color.white);
        frame.add(s);

        
        frame.setVisible(true);
       
        
    }
}