/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package thread;

import boutique.Boutique;
import gestion.GestionBoutique;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Benjamin
 */
public class XMLThread extends Thread{
    
    private Boutique bout;

    public XMLThread(Boutique bout) {
        this.bout = bout;
        this.start();
    }
    
    public void run(){
        while(true){
            try {
                this.sleep(60000);
                GestionBoutique.sauvegarderXML(bout);
                System.out.println("Sauvegarde XML de "+bout.getNom());
            } catch (InterruptedException ex) {
                System.out.println("Erreur XML "+bout.getNom());
                Logger.getLogger(XMLThread.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
