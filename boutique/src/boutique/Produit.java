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

    public String getNom() {
        return nom;
    }

    public String getId() {
        return id;
    }

    public long getPrix() {
        return prix;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setPrix(long prix) {
        this.prix = prix;
    }

    public void setId(String id) {
        this.id = id;
    }    

    @Override
    public String toString() {
        return "Produit{" + "nom=" + nom + ", id=" + id + ", prix=" + prix + '}';
    }
}
