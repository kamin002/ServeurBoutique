/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package boutique;

import gestion.GestionBoutique;
import gestion.GestionProduits;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import thread.ServeurThreadCommandes;
import thread.ServeurThreadProduits;
import thread.ThreadGenID;
import thread.XMLThread;


/*caractérisées par leur nom, l'adresse IP, le port pour la gestion des commandes et le port pour la gestion des produits*/

/**
 *
 * @author Benjamin
 */

//l'increment des commandes et produit est direcetment géré par les methodes ajouter.
public class Boutique implements Cloneable{
    
    private String nom;
    private String id;
    private int incrProduits;
    private int incrCommandes;
    private ArrayList<Produit> listeProduits=new ArrayList<Produit>();
    private ArrayList<Commande> listeCommandes=new ArrayList<Commande>();
    private ServeurThreadProduits threadServeurProduit;
    private ServeurThreadCommandes threadServeurCommandes;
    private XMLThread threadSauvegarde;
    private int IDgen;
    private ThreadGenID genID;
    private int portProduits;
    private int portCommandes;

    public Boutique(String nom, ArrayList<Produit> listeProduits){        
        this.nom = nom;
        this.listeProduits = listeProduits;
        GestionBoutique.chargerXML(this);      
        this.runSauvegardeXML();
    }

    public Boutique(String id, String nom, int portProduits, int portCommandes){
        this.nom = nom;
        this.id=id;
        GestionBoutique.chargerXML(this);
        this.runSauvegardeXML();
        //this.runGenID();
        this.portCommandes=portCommandes;
        this.portProduits=portProduits;
        
        this.runServeurProduits(portProduits);
        this.runServeurCommandes(portCommandes);
        
    }

    public String getNom() {
        return nom;
    }

    public String getId() {
        return id;
    }

    public ArrayList<Commande> getListeCommandes() {
        return listeCommandes;
    }
    
    public ArrayList<Produit> getListeProduits() {
        return listeProduits;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }    

    public void setListeProduits(ArrayList<Produit> listeProduits) {
        this.listeProduits = listeProduits;
    }
    
    public void ajouterProduit(Produit prdt){
        if(Integer.parseInt(prdt.getId())==0)
            prdt.setId(String.valueOf(this.maxIncrProduits()+1));
        this.listeProduits.add(prdt);
    }
    
     public void ajouterCommande(Commande cmd){
        if(Integer.parseInt(cmd.getId())==0)
            cmd.setId(String.valueOf(this.maxIncrCommandes()+1));
        this.listeCommandes.add(cmd);
    }

    public int getIncrProduits() {
        return incrProduits;
    }

    public int getIncrCommandes() {
        return incrCommandes;
    }

    public void setIncrProduits(int incrProduits) {
        this.incrProduits = incrProduits;
    }

    public void setIncrCommandes(int incrCommandes) {
        this.incrCommandes = incrCommandes;
    }

    public void setListeCommandes(ArrayList<Commande> listeCommandes) {
        this.listeCommandes = listeCommandes;
    }

    public int getIDgen() {
        return IDgen;
    }

    public void setIDgen(int IDgen) {
        this.IDgen = IDgen;
    }
     
     public Produit rechercherProduit(String id){
         
         Iterator prdt = this.listeProduits.iterator();
            
            while(prdt.hasNext()){
                
                Produit o = (Produit) prdt.next();
                
                if(id.equals(o.getId()))
                    return o;    
            }
        return null;
     }
     
          public Commande rechercherCommande(String id){
         
         Iterator cmd = this.listeCommandes.iterator();
            
            while(cmd.hasNext()){
                
                Commande o = (Commande) cmd.next();
                
                if(id.equals(o.getId()))
                    return o;    
            }
        return null;
     }

    public int maxIncrProduits(){
        int res=0;

        for(Produit p:this.listeProduits){
            if(Integer.parseInt(p.getId())>res)
                res=Integer.parseInt(p.getId());                   
        }
        return res;
    } 
         
    public int maxIncrCommandes(){
        int res=0;
        
        for(Commande cmd:this.listeCommandes){
            if(Integer.parseInt(cmd.getId())>res)
                res=Integer.parseInt(cmd.getId());                   
        }
        return res;
    }
    
    public void runServeurProduits(int port){
        this.threadServeurProduit=new ServeurThreadProduits(port,this);
        Thread t = new Thread(this.threadServeurProduit);
        t.start();
    }
    
    public void runServeurCommandes(int port){
        this.threadServeurCommandes=new ServeurThreadCommandes(port,this);
        Thread t = new Thread(this.threadServeurCommandes);
        t.start();
    }
    
    public void runSauvegardeXML(){
        this.threadSauvegarde=new XMLThread(this);
        Thread t = new Thread(this.threadSauvegarde);
        t.start();
    }

    public void setId(String id) {
        this.id = id;
    }
    
    public void runGenID() throws InterruptedException{
        this.genID=new ThreadGenID(this);
        Thread t = new Thread((Runnable) this.genID);
        t.start();
    }    

    public int getPortProduits() {
        return portProduits;
    }

    public int getPortCommandes() {
        return portCommandes;
    }
    
    
}

