/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package thread;

import boutique.Boutique;
import java.io.ByteArrayOutputStream;
import static java.lang.Math.random;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Random;
import java.util.Timer;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Benjamin
 */
public class ThreadGenID extends Thread{
    
    private String ipAddress="192.168.1.21";
    private int port = 5050;
    private int ID;
    private Random rand = new Random(97987465);
    
    public ThreadGenID(Boutique bout){
        bout.setIDgen(ID);
    }
    
    @Override
    public void run(){
        while(true){
            try {
                this.ID=rand.nextInt();
                envoyerID();
                this.sleep(50000);
            } catch (InterruptedException ex) {
                Logger.getLogger(ThreadGenID.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public void envoyerID(){
        
        try {
                DatagramSocket datagramSocket = new DatagramSocket();
                byte[] buffer = String.valueOf(ID).getBytes();
                InetAddress receiverAddress = InetAddress.getByName(ipAddress);
                DatagramPacket packet = new DatagramPacket(buffer,
                buffer.length,
                receiverAddress,
                port);
                datagramSocket.send(packet);
                System.out.println("ID send "+this.ID);
                } catch (Exception e) {
                System.out.println("Erreur " + e);
            }
  
    }
}
