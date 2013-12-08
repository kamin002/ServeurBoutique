/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package boutique;

/**
 *
 * @author Benjamin
 */
public class Produit implements Cloneable{
    
    private String nom;
    private String id;
    private long prix;

    public Produit(String nom, long prix) {
        this.nom = nom;
        this.prix = prix;
        this.id="0";
    }

    public Produit(String nom, String id, long prix) {
        this.nom = nom;
        this.id = id;
        this.prix = prix;
    }
    
     public Produit(Produit prdt) {
        this.nom = new String(prdt.getNom());
        this.id = new String(prdt.getId());
        this.prix=new Long(prdt.getPrix());
     }

    public synchronized String getNom() {
        return nom;
    }

    public synchronized String getId() {
        return id;
    }

    public synchronized long getPrix() {
        return prix;
    }

    public synchronized void setNom(String nom) {
        this.nom = nom;
    }

    public synchronized void setPrix(long prix) {
        this.prix = prix;
    }

    public synchronized void setId(String id) {
        this.id = id;
    }    

    @Override
    public String toString() {
        return "Produit{" + "nom=" + nom + ", id=" + id + ", prix=" + prix + '}';
    }
}
