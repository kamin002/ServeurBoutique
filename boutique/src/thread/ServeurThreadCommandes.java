/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package thread;

import boutique.Boutique;
import boutique.Commande;
import boutique.Produit;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import static thread.ConnexionThreadProduits.affiche;

/**
 *
 * @author Benjamin
 */
public class ServeurThreadCommandes extends Thread{
    
    private static int portEcoute;
    private DatagramSocket socketServeur;
    private static Boutique bout;
    private byte[] tampon = new byte[3000];

    public ServeurThreadCommandes(int port, Boutique boutique) {
        this.portEcoute=port;
        this.socketServeur=null;
        this.bout=boutique;
        
    }
    
    
    
     //** Methode : la première méthode exécutée, elle attend les connections **
  public void run()
  {
      
      
	// Creation du socket
	try {
	    socketServeur = new DatagramSocket(portEcoute);
            System.out.println("Lancement serveur commandes sur "+this.portEcoute+" pour "+this.bout.getNom());
            
	} catch(Exception e) {
	    System.err.println("Erreur lors de la creation du socket");
	    System.exit(-1);
	}
        
        // Creation du message
	DatagramPacket msg = new DatagramPacket(tampon, tampon.length);
        
        while(true){
          try {
              socketServeur.receive(msg);
              System.out.println("connexion commande reçu pour "+bout.getNom());
              
              String texte = new String(msg.getData(), 0, msg.getLength());
              ByteArrayInputStream bais = new ByteArrayInputStream(texte.getBytes());
                SAXBuilder sxb = new SAXBuilder();
              try {
                  lireMessage(sxb.build(bais));

              } catch (JDOMException ex) {
                  Logger.getLogger(ServeurThreadCommandes.class.getName()).log(Level.SEVERE, null, ex);
              }
          } catch (IOException ex) {
              Logger.getLogger(ServeurThreadCommandes.class.getName()).log(Level.SEVERE, null, ex);
          }
            //Thread t = new Thread(new ConnexionThreadCommandes(this.socketServeur,this.boutique));
            //t.start();
        }
    }
  
  public static void lireMessage(org.jdom2.Document document){
		
        affiche(document);
        Element message = document.getRootElement();

        if(message.getAttributeValue("action").equals("envoyerCommande"))
            passerCommande(message.getChild("commande"));
        if(message.getAttributeValue("action").equals("validerCommande"))
            validerCommande(message.getChild("commande"));

        System.out.println(message.getAttributeValue("action"));					
    }
    
    public static void passerCommande(Element message){        
        
        List produits = message.getChild("produits").getChildren("produit");
        
        ArrayList<Produit> listeProduits = new ArrayList<Produit>();
        
        Iterator pro = produits.iterator();
            
        while(pro.hasNext()){
            Element produit = (Element)pro.next();
            listeProduits.add(bout.rechercherProduit(produit.getAttributeValue("id")));
        }
        
        //ajout de la commande
        bout.ajouterCommande(new Commande(new Date(Long.parseLong(message.getChild("date").getText())), (ArrayList<Produit>) listeProduits.clone()));
    }
         
    public static void validerCommande(Element message){
        bout.rechercherCommande(message.getAttributeValue("id")).validerCommande();
    }
  
}