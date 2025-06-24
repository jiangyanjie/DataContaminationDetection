/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.ub.prog2.VidalMartinezAlvaro2.model;

import edu.ub.prog2.utils.ImageFile;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;

public class DadesVisor implements Serializable{
    //ATRIBUTOS
    private BibliotecaImatges bibliotecaIm;
    private ArrayList<AlbumImatges> albumList; 
    
    //CONSTRUCTOR
    public DadesVisor(){
        this.bibliotecaIm = new BibliotecaImatges();
        this.albumList = new ArrayList();
    }
    
    
    
    //METODOS BIBLIOTECA -----------------------------------------------------------------------------
    public void addImagenBiblioteca(File f) {
        //Checking if the FILE to create the image does exists
        if(f.exists()){ //exists() -> Test whether the file or directory denoted by this abstract pathname exists.
            Image im = new Image(f);
            
            //Checking if the image does already been entered before
            if(!checkIfExist(im)){ //ifNotFound
                this.bibliotecaIm.addImage(im);
            }else{//ifFound
                System.out.println("DADES_VISOR-> The image that you want to add DOES ALREADY EXISTS.");
            }
            
        }else{ //If FILE does not exists
            System.out.println("DADES_VISOR -> The FILE of the image to create DOES NOT EXISTS");
        }
    }
    
    public String mostrarBiblioteca() {
        String contenido = this.bibliotecaIm.toString();
        if(contenido == null){
            return "There are NO IMAGES on the list.";
        }
        else{
            return contenido;
        }
    }
    
    public void removeImagenBiblioteca(int opt2Delete) {
        ImageFile im2remove = this.bibliotecaIm.getAt(opt2Delete);
        this.bibliotecaIm.removeImage(im2remove);
    }
    
    public void viewImagenBiblioteca(int option) throws IOException, Exception {
        this.bibliotecaIm.getAt(option).show(true);
    }

     

    /*Comprueba si ya existe el fichero (imagen) que se quiere introducir en la biblioteca*/
    private boolean checkIfExist(Image im) {
        ArrayList imageList = this.bibliotecaIm.getLista();
        boolean ifFound = false;
        
        for(int i = 0;i < imageList.size();i++){
            Image imBiblio = (Image)imageList.get(i);
            if(im.fullpath.equalsIgnoreCase(imBiblio.fullpath)){
                ifFound = true;
                break;
            }
        }
        return ifFound;
    }

      
    
    
    
    //METODOS ALBUM ->SUBMENU 1<- --------------------------------------------------------
    public void afegirAlbum(String newAlbumName) {
        AlbumImatges newAlbum = new AlbumImatges(newAlbumName);
        this.albumList.add(newAlbum);
    }

    public String mostrarAlbums() {
        String listaAlbum ="";
        int count = 1;
        if(!this.albumList.isEmpty()){
            for(int i=0;i<this.albumList.size();i++){
                AlbumImatges album = (AlbumImatges)this.albumList.get(i);
                //album.getLista().get(0);
                listaAlbum = listaAlbum+"File: "+count+" -> NAME: "+album.getName()+"\n";
                count++;
            }
            return listaAlbum;
        }else{
            return "There are NO ALBUMS on the list.";
        }
    }

    public void removeAlbum(int opt1) {
        this.albumList.remove(opt1);
    }
    
    
    //METODOS ALBUM ->SUBMENU 2<- ---------------------------------------------------------

    public void addImagenAlbum(File f, int choosenAlbum) {
        Image imAlbum = new Image(f);
        this.albumList.get(choosenAlbum).addImage(imAlbum);
    }

    public String mostrarAlbumImagenes(int choosenAlbum) {
        return this.albumList.get(choosenAlbum).toString();      
    }

    public void removeAlbumImage(int choosenAlbum, int image2Remove) {
        AlbumImatges albumIm = (AlbumImatges)this.albumList.get(choosenAlbum);
        ImageFile im = albumIm.getAt(image2Remove);
        albumIm.removeImage(im);
    }
    
    public String mostrarDadesModificar(int choosenAlbum) {
        String dadesModificar ="";
        AlbumImatges album = this.albumList.get(choosenAlbum);
        return dadesModificar = "-> NAME: "+album.getName()+"\n-> POSITION: "+album.getLatitud()+","+album.getLongitud()+"\n-> IMAGEN_PORTADA: ???????";
    }
    public void modificarDadesAlbum(int choosenAlbum, int image2Modify) {
        
    }
    
    public void viewImagenAlbum(int choosenAlbum, int opt2View) throws IOException, Exception {
        this.albumList.get(choosenAlbum).getAt(opt2View).show(true);
    }
}
