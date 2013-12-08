/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clients;

import boutique.Produit;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

/**
 *
 * @author Benjamin
 */
public class ConnexionThreadClientCommandes extends Thread {

    private static int port = 5011;
    private static String ipaddress = "localhost";
    private static ArrayList<String> listeIdProduits = new ArrayList<String>();

    public ConnexionThreadClientCommandes(int port, String ipaddress) {
        this.port = port;
        this.ipaddress = ipaddress;
    }

    public static void envoyerCommande() throws IOException {

        Element racine = new Element("connexion");
        org.jdom2.Document doc = new Document(racine);

        racine.setAttribute("action", "envoyerCommande");

        Element commande = new Element("commande");
        racine.addContent(commande);

        Element date = new Element("date");
        commande.addContent(date);
        date.setText(String.valueOf(new Date().getTime()));

        Element produits = new Element("produits");
        commande.addContent(produits);

        for (String i : ConnexionThreadClientCommandes.listeIdProduits) {

            Element produit = new Element("produit");
            produits.addContent(produit);
            produit.setAttribute("id", i);
        }

        //envoie XML
        XMLOutputter sortie = new XMLOutputter(Format.getCompactFormat());
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        sortie.output(doc, baos);

        byte[] tampon = baos.toByteArray();
        DatagramSocket socket = null;

        // Creation du socket
        try {
            socket = new DatagramSocket();
        } catch (Exception e) {
            System.err.println("Erreur lors de la creation du socket");
            System.exit(-1);
        }

        // Creation du message
        DatagramPacket msg = null;
        try {
            InetAddress adresse = InetAddress.getByName(ipaddress);
            msg = new DatagramPacket(tampon, tampon.length, adresse, port);

        } catch (Exception e) {
            System.err.println("Erreur lors de la creation du message");
            System.exit(-1);
        }

        // Envoi du message
        try {
            socket.send(msg);
        } catch (Exception e) {
            System.err.println("Erreur lors de l'envoi du message");
            System.exit(-1);
        }

        // Fermeture du socket
        try {
            socket.close();
        } catch (Exception e) {
            System.err.println("Erreur lors de la fermeture du socket");
            System.exit(-1);
        }
    }

    public static void validerCommande(int i) {

        Element racine = new Element("connexion");
        org.jdom2.Document doc = new Document(racine);

        racine.setAttribute("action", "validerCommande");

        Element commande = new Element("commande");
        racine.addContent(commande);
        commande.setAttribute("id", String.valueOf(i));

        //envoie XML
        XMLOutputter sortie = new XMLOutputter(Format.getCompactFormat());
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        try {
            sortie.output(doc, baos);
        } catch (IOException ex) {
            Logger.getLogger(ConnexionThreadClientCommandes.class.getName()).log(Level.SEVERE, null, ex);
        }

        byte[] tampon = baos.toByteArray();
        DatagramSocket socket = null;

        // Creation du socket
        try {
            socket = new DatagramSocket();
        } catch (Exception e) {
            System.err.println("Erreur lors de la creation du socket");
            System.exit(-1);
        }

        // Creation du message
        DatagramPacket msg = null;
        try {
            InetAddress adresse = InetAddress.getByName(ipaddress);
            msg = new DatagramPacket(tampon, tampon.length, adresse, port);

        } catch (Exception e) {
            System.err.println("Erreur lors de la creation du message");
            System.exit(-1);
        }

        // Envoi du message
        try {
            socket.send(msg);
        } catch (Exception e) {
            System.err.println("Erreur lors de l'envoi du message");
            System.exit(-1);
        }

        // Fermeture du socket
        try {
            socket.close();
        } catch (Exception e) {
            System.err.println("Erreur lors de la fermeture du socket");
            System.exit(-1);
        }
    }

    public void run() {

    }

    public static void main(String[] args) throws IOException {

        listeIdProduits.add("1");
        listeIdProduits.add("40");
        listeIdProduits.add("30");
        listeIdProduits.add("15");
        listeIdProduits.add("81");
        listeIdProduits.add("20");
       //ConnexionThreadClientCommandes.listeIdProduits.add("1");
        // ConnexionThreadClientCommandes.listeIdProduits.add("2");
        //ConnexionThreadClientCommandes.listeIdProduits.add("3");
        ConnexionThreadClientCommandes.envoyerCommande();
        /**
         * ConnexionThreadClientCommandes.validerCommande(1);
         * ConnexionThreadClientCommandes.validerCommande(3);
         * ConnexionThreadClientCommandes.validerCommande(4);
         * ConnexionThreadClientCommandes.validerCommande(5);
         * ConnexionThreadClientCommandes.validerCommande(6);
        ConnexionThreadClientCommandes.validerCommande(7);
         */
    }
}
