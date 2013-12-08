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
public class Boutique implements Cloneable {

    private String nom;
    private String id;
    private int incrProduits;
    private int incrCommandes;
    private ArrayList<Produit> listeProduits = new ArrayList<Produit>();
    private ArrayList<Commande> listeCommandes = new ArrayList<Commande>();
    private final ServeurThreadProduits threadServeurProduit;
    private final ServeurThreadCommandes threadServeurCommandes;
    private final XMLThread threadSauvegarde;
    private int IDgen;
    private ThreadGenID genID;
    private final int portProduits;
    private final int portCommandes;

    public Boutique(String id, String nom, int portProduits, int portCommandes) {
        this.nom = nom;
        this.id = id;
        GestionBoutique.chargerXML(this);
        //this.runGenID();
        this.portCommandes = portCommandes;
        this.portProduits = portProduits;

        threadServeurProduit = new ServeurThreadProduits(portProduits, this);
        threadServeurCommandes = new ServeurThreadCommandes(portCommandes, this);
        threadSauvegarde = new XMLThread(this);
        //threadSauvegarde.run();
    }

    public synchronized String getNom() {
        return nom;
    }

    public synchronized String getId() {
        return id;
    }

    public synchronized ArrayList<Commande> getListeCommandes() {
        return listeCommandes;
    }

    public synchronized ArrayList<Produit> getListeProduits() {
        return listeProduits;
    }

    public synchronized void setNom(String nom) {
        this.nom = nom;
    }

    public synchronized void setListeProduits(ArrayList<Produit> listeProduits) {
        this.listeProduits = listeProduits;
    }

    public synchronized void ajouterProduit(Produit prdt) {
        if (Integer.parseInt(prdt.getId()) == 0) {
            prdt.setId(String.valueOf(this.maxIncrProduits() + 1));
        }
        this.listeProduits.add(prdt);
    }

    public synchronized void ajouterCommande(Commande cmd) {
        if (Integer.parseInt(cmd.getId()) == 0) {
            cmd.setId(String.valueOf(this.maxIncrCommandes() + 1));
        }
        this.listeCommandes.add(cmd);
    }

    public synchronized int getIncrProduits() {
        return incrProduits;
    }

    public synchronized int getIncrCommandes() {
        return incrCommandes;
    }

    public synchronized void setIncrProduits(int incrProduits) {
        this.incrProduits = incrProduits;
    }

    public synchronized void setIncrCommandes(int incrCommandes) {
        this.incrCommandes = incrCommandes;
    }

    public synchronized void setListeCommandes(ArrayList<Commande> listeCommandes) {
        this.listeCommandes = listeCommandes;
    }

    public synchronized int getIDgen() {
        return IDgen;
    }

    public void setIDgen(int IDgen) {
        this.IDgen = IDgen;
    }

    public synchronized Produit rechercherProduit(String id) {

        Iterator prdt = this.listeProduits.iterator();

        while (prdt.hasNext()) {

            Produit o = (Produit) prdt.next();

            if (id.equals(o.getId())) {
                return o;
            }
        }
        return null;
    }

    public synchronized Commande rechercherCommande(String id) {
        for (Commande o : this.listeCommandes) {
            if (id.equals(o.getId())) {
                return o;
            }
        }
        return null;
    }

    public int maxIncrProduits() {
        int res = 0;

        for (Produit p : this.listeProduits) {
            if (Integer.parseInt(p.getId()) > res) {
                res = Integer.parseInt(p.getId());
            }
        }
        return res;
    }

    public int maxIncrCommandes() {
        int res = 0;

        for (Commande cmd : this.listeCommandes) {
            if (Integer.parseInt(cmd.getId()) > res) {
                res = Integer.parseInt(cmd.getId());
            }
        }
        return res;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void runGenID() throws InterruptedException {
        this.genID = new ThreadGenID(this);
        Thread t = new Thread((Runnable) this.genID);
        t.start();
    }

    public synchronized int getPortProduits() {
        return portProduits;
    }

    public synchronized int getPortCommandes() {
        return portCommandes;
    }

    @Override
    public String toString() {
        return "Boutique{" + "nom=" + nom + ", id=" + id + ", portProduits=" + portProduits + ", portCommandes=" + portCommandes + '}';
    }
}
