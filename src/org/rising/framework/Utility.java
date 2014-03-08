/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.rising.framework;

import java.util.Random;

/**
 *
 * @author Riseremi
 */
public class Utility {

    public static String getRandomName() {
        String[] names = {"Aaron", "Abbey", "Acacia", "Adam", "Aden", "Adolph",
            "Alexia", "Alf", "Alexandria", "Amber", "Azura"};
        String[] surnames = {"Setters", "Shann", "Shaw", "Shield", "Settle",
            "Shady", "Share", "Shark", "Shill", "Sherwin"};
        Random rnd = new Random();
        return names[rnd.nextInt(names.length)] + " " + surnames[rnd.nextInt(surnames.length)];
    }

}
