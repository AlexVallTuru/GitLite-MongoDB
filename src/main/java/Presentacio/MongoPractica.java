/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package Presentacio;

import Logica.MenuLogica;



/**
 *
 * @author di carlo
 */
public class MongoPractica {
    MenuLogica menuLogica = new MenuLogica();
    

    public static void main(String[] args) {
       int opc = 0;
       while(opc != 7){
           opc=Menus.menuPrincipal();
           MenuLogica.getOption(opc);
       }
    }
}
