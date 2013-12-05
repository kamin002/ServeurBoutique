/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thread;

import boutique.Boutique;
import boutique.Produit;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;
import org.xml.sax.InputSource;

/**
 *
 * @author Benjamin
 */
public class ConnexionThreadProduits extends Thread {

    private static Socket sock;
    private InputStream is;
    private ObjectInputStream ois;
    private static Boutique bout;
    BufferedReader input;
    PrintWriter output;

    public ConnexionThreadProduits(Socket s, Boutique boutique) throws IOException {
        this.sock = s;
        this.bout = boutique;
    }

    public void run() {
        try {
            InputStream is = this.sock.getInputStream();
            ObjectInputStream ois = new ObjectInputStream(is);
            org.jdom2.Document document = (org.jdom2.Document) ois.readObject();
            lireMessage(document);
            sock.close();
        } catch (IOException ex) {
            Logger.getLogger(ConnexionThreadProduits.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ConnexionThreadProduits.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public static void lireMessage(org.jdom2.Document document) {

        Element message = document.getRootElement();

        if (message.getAttributeValue("action").equals("ajoutProduit")) {
            bout.ajouterProduit(new Produit(message.getChildText("nom"), Long.parseLong(message.getChildText("prix"))));
        }
        if (message.getAttributeValue("action").equals("recevoirProduits")) {
            envoyerProduits();
        }

        System.out.println("La boutique " + bout.getNom() + " a reçu " + message.getAttributeValue("action"));
        affiche(document);
                //System.out.println(bout.getListeProduits());

    }

    public static void envoyerProduits() {

        Element racine = new Element("produits");
        org.jdom2.Document doc = new Document(racine);

        for (Produit pdrt : ConnexionThreadProduits.bout.getListeProduits()) {

            Element pro = new Element("produit");

            Element id = new Element("id");
            id.setText(pdrt.getId());
            pro.addContent(id);
            Element nom = new Element("nom");
            nom.setText(pdrt.getNom());
            pro.addContent(nom);
            Element prix = new Element("prix");
            prix.setText(String.valueOf(pdrt.getPrix()));
            pro.addContent(prix);

            racine.addContent(pro);
        }

        try {
            OutputStream os = sock.getOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(os);
            oos.writeObject(doc);
            oos.flush();
        } catch (IOException ex) {
            Logger.getLogger(ConnexionThreadProduits.class.getName()).log(Level.SEVERE, null, ex);
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
