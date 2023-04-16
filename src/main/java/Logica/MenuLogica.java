/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Logica;

import DAO.MenuDAO;
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
                    Menus.menuPull();
                case 4 ->
                    Menus.menuCompare();
                case 5 ->
                    Menus.menuClone();
                case 6 ->
                    Menus.menuAyuda();

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
                    selectRepository();

            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    public static void selectRepository() {
        try {
            MenuDAO.repositoryList();
            int op = 0;
            while (op != 7) {
                op = Menus.menuPrincipal();
                MenuLogica.repositoryOptions(op);
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
                    Menus.menuPullAyuda();
                case 5 ->
                    Menus.menuCompareAyuda();
                case 6 ->
                    Menus.menuCloneAyuda();
                case 7 ->
                    Menus.menuPrincipal();
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }
}
