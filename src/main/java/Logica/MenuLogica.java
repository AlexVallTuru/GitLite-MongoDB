/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Logica;

import Presentacio.Menus;

/**
 *
 * @author carlo
 */
public class MenuLogica {

    public static void repositoryOptions(int option) {
        try {
            switch (option) {
                case 1 ->
                    Menus.menuDrop();
                case 2 ->
                    Menus.menuPush();
                case 3 ->
                    Menus.menuCompare();
                case 4 ->
                    Menus.menuClone();

            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }
    
        public static void getInitialOption(int option) {
        try {
            switch (option) {
                case 1 ->
                    Menus.menuCreate();
                    
                    
                case 2 ->
                    Menus.selectRepository();


            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

}
