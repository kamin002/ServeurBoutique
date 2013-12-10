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
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;
import static thread.ConnexionThreadProduits.affiche;

/**
 *
 * @author Benjamin
 */
public class ServeurThreadCommandes extends Thread{
    
    private static int portEcoute;
    private static DatagramSocket socketServeur;
    private static Boutique bout;
    private byte[] tampon = new byte[3000];
    
    private static String portClient;
    private static String hostClient;

    public ServeurThreadCommandes(int port, Boutique boutique) {
        this.portEcoute=port;
        this.socketServeur=null;
        this.bout=boutique;
        //System.out.println("Lancement ServeurCommandes "+boutique.getNom()+" sur "+port);
        this.start();
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
              //this.portClient=msg.getS
              //ServeurThreadCommandes.hostClient=msg.getSocketAddress();
              
              System.out.println("connexion commande reçu pour "+bout.getNom()+" de "+msg.getSocketAddress().toString());
              
              String bloc = new String(msg.getSocketAddress().toString());
              char crt;
              
              //methode de la mort
              int i=0;
              while(bloc.charAt(i) != ':'){
                  i++;
              }
              
              hostClient= bloc.substring(1, i);
              portClient=bloc.substring(i+1, bloc.length());
              

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
		
        Element message = document.getRootElement();
        System.out.println(message.getAttributeValue("action"));

        if(message.getAttributeValue("action").equals("envoyerCommande"))
            passerCommande(message.getChild("commande"));
        if(message.getAttributeValue("action").equals("validerCommande"))
            validerCommande(message.getChild("commande"));
        if(message.getAttributeValue("action").equals("recevoirCommande"))
            envoyerCommandes();
        
        
        
    }
    
    public static void passerCommande(Element message){        
        
        List produits = message.getChild("produits").getChildren("produit");
        
        ArrayList<Produit> listeProduits = new ArrayList<Produit>();
        
        Iterator pro = produits.iterator();
            
        while(pro.hasNext()){
            Element produit = (Element)pro.next();
            
            if(bout.rechercherProduit(produit.getAttributeValue("id"))!=null)
                listeProduits.add(bout.rechercherProduit(produit.getAttributeValue("id")));
        }
        //ajout de la commande
        bout.ajouterCommande(new Commande(message.getChildText("idCli"),new Date(Long.parseLong(message.getChild("date").getText())), (ArrayList<Produit>) listeProduits.clone()));
    }
         
    public static void validerCommande(Element message){
        bout.rechercherCommande(message.getAttributeValue("id")).validerCommande();
    }
    
    public static void envoyerCommandes(){
        
        Element racine=new Element("commandes");
        org.jdom2.Document doc= new Document(racine);
        
        for(Commande cmd:bout.getListeCommandes()){
            
            //on ajoute l'élément commande à l'élément commandes
            Element commande= new Element("commande");
            racine.addContent(commande);
            
            //on ajoute l'élément id à l'élément commande
            Element id= new Element("id");
            commande.addContent(id);
            id.setText(cmd.getId());
            
            //on ajoute l'élément idCli à l'élément commande
            Element idCli= new Element("idCli");
            commande.addContent(idCli);
            if(cmd.getIdCli()==null)
                idCli.setText("null");
            else
                idCli.setText(cmd.getIdCli());
            
            //on ajoute l'élément validation à l'élément commande
            Element valide= new Element("valide");
            commande.addContent(valide);
            valide.setText(String.valueOf(cmd.isValidation()));

            //on ajoute l'élément date à l'élément commande
            Element date= new Element("date");
            commande.addContent(date);
            date.setText(String.valueOf(cmd.getDateCmd().getTime()));
        }
        
        affiche(doc);
        
        XMLOutputter sortie= new XMLOutputter(Format.getCompactFormat());
        ByteArrayOutputStream baos= new ByteArrayOutputStream();
        
        
        try {
            sortie.output(doc, baos);
        } catch (IOException ex) {
            Logger.getLogger(ServeurThreadCommandes.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        byte[] tampon= baos.toByteArray();
        
        DatagramPacket DP;
        try {
            DP = new DatagramPacket(tampon, tampon.length, InetAddress.getByName(hostClient),Integer.valueOf(portClient));
            socketServeur.send(DP);
        } catch (UnknownHostException ex) {
            Logger.getLogger(ServeurThreadCommandes.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ServeurThreadCommandes.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
        static void affiche(org.jdom2.Document document) {
        try {
            //On utilise ici un affichage classique avec getPrettyFormat()
            XMLOutputter sortie = new XMLOutputter(Format.getPrettyFormat());
            sortie.output(document, System.out);
        } catch (java.io.IOException e) {
        }
    }
  
}