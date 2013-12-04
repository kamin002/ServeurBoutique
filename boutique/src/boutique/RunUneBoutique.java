/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package boutique;

import gestion.GestionBoutique;
import java.util.ArrayList;
import java.util.Date;
import thread.ServeurThreadProduits;

/**
 *
 * @author Benjamin
 */
public class RunUneBoutique {
    
        
      public static void main(String[] args) throws CloneNotSupportedException, InterruptedException{
        
          GestionBoutique.chargerBoutiqueXML();
          
          //GestionBoutique.ajouterBoutique("Carrefour",5005,5006);
          //GestionBoutique.ajouterBoutique("Leclerc",5007,5008);
          //Boutique boutique1=new Boutique("Carrefour");
        //GestionBoutique.sauvegarderXML(boutique);
       //GestionBoutique.chargerXML(bout);
        
        //bout.ajouterProduit(new Produit("toto",15));
        //System.out.println(bout.getId()); 
    }   
}