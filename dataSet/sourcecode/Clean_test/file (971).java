/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.ub.prog2.VidalMartinezAlvaro2.controlador;

import edu.ub.prog2.VidalMartinezAlvaro2.model.DadesVisor;
import edu.ub.prog2.VidalMartinezAlvaro2.vista.VisorUB2;
import edu.ub.prog2.utils.BasicCtrl;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Alvykun
 */
public class CtrlVisor extends BasicCtrl{
    //Atributos
    public DadesVisor dadesVisor;
    
    //Constructor
    public CtrlVisor(){
        this.dadesVisor = new DadesVisor();
    }
    
    
    
    //METODOS BIBLIOTECA ----------------------------------------------------- 
    public void addImagenBiblioteca(File f) {
        this.dadesVisor.addImagenBiblioteca(f);
    }

    public String mostrarBiblioteca() {
        return this.dadesVisor.mostrarBiblioteca();
    }

    public void removeImagenBiblioteca(int opt2Delete) {
        this.dadesVisor.removeImagenBiblioteca(opt2Delete);
    }

    public void viewImagenBiblioteca(int option) {
        try {
            this.dadesVisor.viewImagenBiblioteca(option);
        } catch (IOException ex) {
            System.out.println("CONTROLADOR -> Some I/O problem has occurred when writing the object into the file!");
            Logger.getLogger(CtrlVisor.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            System.out.println("CONTROLADOR -> Some Exception has occurred!");
            Logger.getLogger(CtrlVisor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

  
    
    
    //METODOS ALBUM ->SUBMENU 1<- ---------------------------------------------------------
    public void afegirAlbum(String newAlbumName) {
        this.dadesVisor.afegirAlbum(newAlbumName);
    }
    
    public String mostrarAlbums() {
        return this.dadesVisor.mostrarAlbums();
    }
    
    public void removeAlbum(int opt1) {
        this.dadesVisor.removeAlbum(opt1);
    }
    
    
    //METODOS ALBUM ->SUBMENU 2<- ---------------------------------------------------------
    
    public void addImagenAlbum(File f, int choosenAlbum) {
        this.dadesVisor.addImagenAlbum(f,choosenAlbum);
    }

    public String mostrarAlbumImagenes(int choosenAlbum) {
        return this.dadesVisor.mostrarAlbumImagenes(choosenAlbum);
    }
    
    public void removeAlbumImage(int choosenAlbum, int image2Remove) {
        this.dadesVisor.removeAlbumImage(choosenAlbum,image2Remove);
    }
    
    public String mostrarDadesModificar(int choosenAlbum) {
        return this.dadesVisor.mostrarDadesModificar(choosenAlbum);
    } 
    public void modificarDadesAlbum(int choosenAlbum, int image2Modify) {
        this.dadesVisor.modificarDadesAlbum(choosenAlbum, image2Modify);
    }
    
 
    
    public void viewImagenAlbum(int choosenAlbum, int opt2View) {
        try {
            this.dadesVisor.viewImagenAlbum(choosenAlbum,opt2View);
        } catch (IOException ex) {
            System.out.println("CONTROLADOR -> Some I/O problem has occurred when writing the object into the file!");
            Logger.getLogger(CtrlVisor.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            System.out.println("CONTROLADOR -> Some Exception has occurred!");
            Logger.getLogger(CtrlVisor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    
    
    //METODO GUARDAR DADES -----------------------------------------------------
    public void guardarDades(String fNameSave) {
        try{
            File ftoSave = new File(fNameSave);
            // Obrir un Stream de sortida cap al fitxer _OutputStream_
            FileOutputStream fout= new FileOutputStream(ftoSave);
            //Creates an ObjectOutputStream that writes to the specified OutputStream.
            ObjectOutputStream oos = new ObjectOutputStream(fout);
            // Write the specified object to the ObjectOutputStream.
            oos.writeObject(this.dadesVisor);//writing in the file
            //Closes the stream.
            fout.close();
            System.out.println("CONTROLLER -> FILE has been SAVED!");

        } catch (FileNotFoundException ex) {
        System.out.println("CONTROLLER -> The file could not be found!");
        Logger.getLogger(VisorUB2.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            System.out.println("CONTROLLER -> Some I/O problem has occurred when writing the object into the file!");
            Logger.getLogger(VisorUB2.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    
    //METODO RECUPERAR DADES ---------------------------------------------------
    public void recuperarDades(String fNameRecover) {
        try {
            //Obrim un Stream dentrada cap al fitxer fNameRecover 
            FileInputStream fin = new FileInputStream(fNameRecover);
            // Creates an ObjectInputStream that reads from the specified InputStream.
            ObjectInputStream ois = new ObjectInputStream(fin);
            //Read an object from the ObjectInputStream. Lo guardo en mi anterior variable this.myImageTableList casteandolo para que lo entienda.
            //comprobamos que era y casteamos para poder "entenderlo" posteriormente
            this.dadesVisor = (DadesVisor)ois.readObject();  
            //Closes the stream.
            fin.close();
            System.out.println("CONTROLLER -> FILE has been RECOVERED!");

        } catch (FileNotFoundException ex) {
            System.out.println("CONTROLLER -> The file could not be found!");
            Logger.getLogger(VisorUB2.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            System.out.println("CONTROLLER -> Some I/O problem has occurred when writing the object into the file!");
            Logger.getLogger(VisorUB2.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            System.out.println("CONTROLLER -> No definition for the class with the specified name could be found!");
            Logger.getLogger(VisorUB2.class.getName()).log(Level.SEVERE, null, ex);
        }
    }  
}
