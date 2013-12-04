package clients;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import org.jdom2.Attribute;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

public class ConnexionThreadClientProduits extends Thread{
	
private static String address = "benesis002.no-ip.biz";
private static int port = 5012;

	public static void main(String args[]) throws ClassNotFoundException, UnknownHostException, IOException{
		
		ajouterProduit(address,port,"Kinder",5);
		demanderProduits("benesis002.no-ip.biz", 5009);
	}
	
	public static void ajouterProduit(String address, int port, String nomProduit, long prix){
		
            Element racine= new Element("produit");
            org.jdom2.Document doc = new Document(racine);
		
            Element nom= new Element("nom");
            racine.addContent(nom);
            nom.setText(nomProduit);
        
            Element prixp= new Element("prix");
            racine.addContent(prixp);
            prixp.setText(String.valueOf(prix));
        
            racine.setAttribute(new Attribute("action","ajoutProduit"));
		
        try{
    		Socket s = new Socket(address,port);
    		java.io.OutputStream os = s.getOutputStream();
    		ObjectOutputStream oos = new ObjectOutputStream(os);
    		oos.writeObject(doc);
    		oos.flush();
    		s.close();
    		
    		}catch(Exception e){
    		System.err.println("Erreur : " + e);
    		}
		
	}
	
	public static void demanderProduits(String address, int port) throws ClassNotFoundException, UnknownHostException, IOException{
		
		Element racine= new Element("demande");
		org.jdom2.Document doc = new Document(racine);
		Socket s = new Socket(address,port);
        
                racine.setAttribute(new Attribute("action","recevoirProduits"));
		
        //envoyer la demande
        try{
    		java.io.OutputStream os = s.getOutputStream();
    		ObjectOutputStream oos = new ObjectOutputStream(os);
    		oos.writeObject(doc);
    		oos.flush();
    		
    		
    		}catch(Exception e){
    		System.err.println("Erreur : " + e);
    		}
        
        //reception de la demande
        
            InputStream is;
			try {
				is = s.getInputStream();
				ObjectInputStream ois = new ObjectInputStream(is);
				org.jdom2.Document document = (org.jdom2.Document) ois.readObject();
				affiche(document);
				s.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}     
        }
	
	static void affiche(org.jdom2.Document document){
	       try
	       {
	          //On utilise ici un affichage classique avec getPrettyFormat()
	          XMLOutputter sortie = new XMLOutputter(Format.getPrettyFormat());
	          sortie.output(document, System.out);
	       }
	       catch (java.io.IOException e){}
	    }
}