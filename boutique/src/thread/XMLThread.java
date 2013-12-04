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
    }
    
    public void run(){
        while(true){
            try {
                this.sleep(60000);
                GestionBoutique.sauvegarderXML(bout);
            } catch (InterruptedException ex) {
                Logger.getLogger(XMLThread.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
