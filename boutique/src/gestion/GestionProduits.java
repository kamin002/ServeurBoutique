/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package gestion;

import boutique.Boutique;
import boutique.Produit;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Iterator;
import java.util.List;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

/**
 *
 * @author Benjamin
 */
public class GestionProduits {
    
   private static Element racine;
   private static org.jdom2.Document document;
   
   public static void reset(){
       GestionProduits.racine= new Element("produits");
       GestionProduits.document = new Document(racine);
   }
        
    public static void sauvegarderXML(Boutique boutique){
        reset();
        //incr.setText(String.valueOf(Produit.getIncr()));
        
        //parcourt des commandes de la boutique passée en paramétre
        for(Produit pdrt:boutique.getListeProduits()){
            
            //on ajoute l'élément commande à l'élément commandes
            Element produit= new Element("produit");
            racine.addContent(produit);
            
            //on ajoute l'élément id à l'élément commande
            Element id= new Element("id");
            produit.addContent(id);
            id.setText(pdrt.getId());

            //on ajoute l'élément date à l'élément commande
            Element nom= new Element("nom");
            produit.addContent(nom);
            nom.setText(pdrt.getNom());
            
              //on ajoute l'élément date à l'élément commande
            Element prix= new Element("prix");
            produit.addContent(prix);
            prix.setText(String.valueOf(pdrt.getPrix()));
        }
        
        //on demande l'écriture du fichier .xml
        writeFile(boutique.getId());

    }
    
    public static void chargerXML(Boutique boutique){
        
        if(ParserXML(boutique.getId())!=null){
            Element racine2=ParserXML(boutique.getId());

            //On commence par mettre à jour l'incrément des commandes
           // boutique.setIncrProduits(Integer.parseInt(racine2.getChild("incr").getText()));

            //On créer une liste des Element Jdom de type produit
            List produits = racine2.getChildren("produit");

             //On la parcourt afin de recréer nos objets
            Iterator prdt = produits.iterator();

            while(prdt.hasNext()){
                Element produit = (Element)prdt.next();

                boutique.ajouterProduit(new Produit(produit.getChild("nom").getText(),produit.getChild("id").getText(),Long.parseLong(produit.getChild("prix").getText())));
            }
            System.out.println("ajout de "+produits.size()+" produits pour la boutique "+boutique.getNom());
        }
    }
    
    public static Element ParserXML(String name)
   {
      //On crée une instance de SAXBuilder
      SAXBuilder sxb = new SAXBuilder();
      try
      {
         //On crée un nouveau document JDOM avec en argument le fichier XML
         //Le parsing est terminé ;)
        /*File global = new File("XML-Boutiques");
        boolean isGlobalCreated = global.mkdirs();

        File dir=new File(global, "Boutique"+name);
        boolean isDirCreated = dir.mkdirs();

        File fichier=new File(dir, "produits.xml");*/
          
         document = sxb.build("XML-Boutiques/Boutique"+name+"/produits.xml");
      }
      catch(Exception e){
          System.out.println(e);
          return null;
      }

      //On initialise un nouvel élément racine avec l'élément racine du document.
      return document.getRootElement();

   }
    
    public static void writeFile(String name)
    {
            try
            {
                    XMLOutputter sortie = new XMLOutputter(Format.getPrettyFormat());

                    File global = new File("XML-Boutiques");
                    boolean isGlobalCreated = global.mkdirs();
                    
                    File dir=new File(global, "Boutique"+name);
                    boolean isDirCreated = dir.mkdirs();
                    
                    File fichier=new File(dir, "produits.xml");
                    sortie.output(document, new FileOutputStream(fichier));
            }
            catch (java.io.IOException e){
                System.out.println("erreur fichier produits.xml pour la boutique "+name+" "+e);
            }
    }
    
}