/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package boutique;

import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author Benjamin
 */
public class Commande implements Cloneable{
    
    private String id;
    private Date dateCmd;
    private boolean validation;
    private ArrayList<Produit> listeProduits = new ArrayList<Produit>();
    private String idCli;

    public Commande(String idCli, Date dateCmd, ArrayList<Produit> listeProduits) {
        this.dateCmd = dateCmd;
        this.listeProduits = listeProduits;
        this.id="0";
        this.validation=false;
    }
    
     public Commande(String idCli, Date dateCmd, ArrayList<Produit> listeProduits, Boolean valide) {
        this.dateCmd = dateCmd;
        this.listeProduits = listeProduits;
        this.id="0";
        this.validation=valide;
    }
    
    public Commande(Date dateCmd) {
        this.dateCmd = dateCmd;
        this.listeProduits = listeProduits;
        this.id="0";
    }
    
    public synchronized void setListeProduits(ArrayList<Produit> listeProduits) {
        this.listeProduits = listeProduits;
    }

    public synchronized void setId(String id) {
        this.id = id;
    }
   
    public synchronized String getId() {
        return id;
    }

    public String getIdCli() {
        return idCli;
    }
    
    

    public synchronized Date getDateCmd() {
        return dateCmd;
    }

    public synchronized ArrayList<Produit> getListeProduits() {
        return listeProduits;
    }
    
    public synchronized void validerCommande(){
        this.validation=true;
    }

    public synchronized boolean isValidation() {
        return validation;
    }
    
    public synchronized Object clone() throws CloneNotSupportedException {  
        Commande copy = (Commande) super.clone();
        
        if (copy.listeProduits != null)  
                copy.listeProduits = (ArrayList<Produit>) copy.listeProduits.clone();  

        return copy;  
    }  

    
    //cette méthide permet de dupliquer un produit de la boutique dans la liste des produits d'une commande. on créer ici une nouvelle instance de produit.
    public synchronized void ajouterProduit(Produit prdt) throws CloneNotSupportedException{
        //this.listeProduits.add((Produit)prdt.clone());
    }
    /*
    public int maxIncrProduits(){
        int res=0;
        
        for(Produit p:this.listeProduits){
            if(Integer.parseInt(p.getId())>res)
                res=Integer.parseInt(p.getId());                   
        }
        return res;
    } */
}
