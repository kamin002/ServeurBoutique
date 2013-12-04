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

    public Commande(Date dateCmd, ArrayList<Produit> listeProduits) {
        this.dateCmd = dateCmd;
        this.listeProduits = listeProduits;
        this.id="0";
        this.validation=false;
    }
    
     public Commande(Date dateCmd, ArrayList<Produit> listeProduits, Boolean valide) {
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
    
    public void setListeProduits(ArrayList<Produit> listeProduits) {
        this.listeProduits = listeProduits;
    }

    public void setId(String id) {
        this.id = id;
    }
   
    public String getId() {
        return id;
    }

    public Date getDateCmd() {
        return dateCmd;
    }

    public ArrayList<Produit> getListeProduits() {
        return listeProduits;
    }
    
    public void validerCommande(){
        this.validation=true;
    }

    public boolean isValidation() {
        return validation;
    }
    
    public Object clone() throws CloneNotSupportedException {  
        Commande copy = (Commande) super.clone();
        
        if (copy.listeProduits != null)  
                copy.listeProduits = (ArrayList<Produit>) copy.listeProduits.clone();  

        return copy;  
    }  

    
    //cette méthide permet de dupliquer un produit de la boutique dans la liste des produits d'une commande. on créer ici une nouvelle instance de produit.
    public void ajouterProduit(Produit prdt) throws CloneNotSupportedException{
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
