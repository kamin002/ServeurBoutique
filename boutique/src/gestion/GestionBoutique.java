/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package gestion;

import boutique.Boutique;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
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
public class GestionBoutique {
    
    private static ArrayList<Boutique> listeBoutiques = new ArrayList<Boutique>();

    public GestionBoutique() {
    }
    
    public static void sauvegarderXML(Boutique boutique){
        
        GestionProduits.sauvegarderXML(boutique);
        GestionCommandes.sauvegarderXML(boutique); 
    }
    
    public static void chargerXML(Boutique boutique){
        GestionProduits.chargerXML(boutique);
        GestionCommandes.chargerXML(boutique);
    }
    
    public void sauvegarderBoutiqueXML(ArrayList<Boutique> listeBoutiques){
        
       Element boutiques=new Element("boutiques");
       org.jdom2.Document document=new Document(boutiques);
       
       for(Boutique b:GestionBoutique.listeBoutiques){
           Element boutique=new Element("boutique");
           boutiques.addContent(boutique);
           
           Element id=new Element("id");
           boutique.addContent(id);
           id.setText(b.getId());
           
           Element portProduits=new Element("portProduits");
           boutique.addContent(portProduits);
           portProduits.setText(String.valueOf(b.getPortProduits()));
           
           Element portCommandes=new Element("portCommandes");
           boutique.addContent(portCommandes);
           portCommandes.setText(String.valueOf(b.getPortProduits()));  
       }       
    }
    
    public static void chargerBoutiqueXML(){
        
        org.jdom2.Document document=ParserXML();
        Element racine = document.getRootElement();
        
        List boutiques = racine.getChildren("boutique");
        
        Iterator bout = boutiques.iterator();
            
        while(bout.hasNext()){
            Element boutique = (Element)bout.next();
            GestionBoutique.ajouterBoutique(new Boutique(boutique.getChild("id").getText(),boutique.getChild("nom").getText(),Integer.parseInt(boutique.getChild("portProduits").getText()),Integer.parseInt(boutique.getChild("portCommandes").getText())));
            System.out.println("Chargement de la Boutique "+boutique.getChild("nom").getText());
        }
    }
    
    public static void writeFile(String name, org.jdom2.Document document)
    {
            try
            {
                    //On utilise ici un affichage classique avec getPrettyFormat()
                    XMLOutputter sortie = new XMLOutputter(Format.getPrettyFormat());
                    //Remarquez qu'il suffit simplement de créer une instance de FileOutputStream
                    //avec en argument le nom du fichier pour effectuer la sérialisation.
                    File global = new File("XML-Boutiques");
                    boolean isGlobalCreated = global.mkdirs();
                    
                    File fichier=new File(global, "boutiques.xml");
                    sortie.output(document, new FileOutputStream(fichier));
            }
            catch (java.io.IOException e){}
    }
    
        public static org.jdom2.Document ParserXML(){
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

        File fichier=new File(dir, "commandes.xml");*/
          
         org.jdom2.Document document = sxb.build("XML-Boutiques/boutiques.xml");
         return document;
      }
      catch(Exception e){
        System.out.println(e);
        return null;
      }

   }
        
    public static void ajouterBoutique(Boutique b){
        listeBoutiques.add(b);
    }
    
     public static void ajouterBoutique(String nom, int portProduit, int portCommandes){
        listeBoutiques.add(new Boutique(String.valueOf(maxIncrBoutiques()+1),nom,portProduit,portCommandes));
    }
            
    public static int maxIncrBoutiques(){
        int res=0;
        
        if(listeBoutiques.size()==0)
            return 0;
        
        for(Boutique b:listeBoutiques){
            if(Integer.parseInt(b.getId())>res)
                res=Integer.parseInt(b.getId());                   
        }
        return res;
    }

    public static ArrayList<Boutique> getListeBoutiques() {
        return listeBoutiques;
    }
    
    
}
