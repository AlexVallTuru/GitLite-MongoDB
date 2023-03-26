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

    public static void getOption(int option) {
        try {
            switch (option) {
                case 1 ->
                    Menus.menuCreate();
                case 2 ->
                    Menus.menuDrop();
                case 3 ->
                    Menus.menuPush();
                case 4 ->
                    Menus.menuCompare();
                case 5 ->
                    Menus.menuClone();
                case 6 ->
                    getOptionAyuda(Menus.menuAyuda());
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    public static void getOptionAyuda(int option) {
        try {
            switch (option) {
                case 1 ->
                    Menus.menuCreateAyuda();
                case 2 ->
                    Menus.menuDropAyuda();
                case 3 ->
                    Menus.menuPushAyuda();
                case 4 ->
                    Menus.menuCompareAyuda();
                case 5 ->
                    Menus.menuCloneAyuda();
                case 6 ->
                    Menus.menuPrincipal();
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }
}
