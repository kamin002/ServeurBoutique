
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package thread;
import boutique.Boutique;
import java.net.*;
import java.io.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Benjamin
 */

//** Classe principale du serveur, gère les infos globales **
public class ServeurThreadProduits extends Thread {
 
    private static int portEcoute;
    private ServerSocket socketServeur;
    private Boutique boutique;

    public ServeurThreadProduits(int port, Boutique boutique) {
        this.portEcoute=port;
        this.socketServeur=null;
        this.boutique=boutique;
        //System.out.println("Lancement ServeurProduit "+boutique.getNom()+" sur "+port);
        this.start();
    }
  
  //** Methode : la première méthode exécutée, elle attend les connections **
  public void run()
  {    
	try {	
	    socketServeur = new ServerSocket(portEcoute);
            System.out.println("Lancement serveur produits sur "+portEcoute+" pour "+this.boutique.getNom());
	} catch(IOException e) {
	    System.err.println("Creation de la socket impossible : " + e);
	    System.exit(-1);
	}
        
        while(true){
          try {
              Socket s= this.socketServeur.accept();
              System.out.println("connexion produit reçu pour la boutique "+boutique.getNom());
              Thread t = new Thread(new ConnexionThreadProduits(s,this.boutique));
              t.start();
          } catch (IOException ex) {
              Logger.getLogger(ServeurThreadProduits.class.getName()).log(Level.SEVERE, null, ex);
          }
        }
    }
 }