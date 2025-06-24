






/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */








package parser_correo;

import java.util.LinkedList;

/**
 *


 * @author sebastian
 */
public class correo {







    
    public int no;
    public String usuario;



    public String nombre_usuario;  






    public String asunto;




    public String fecha;
    public String de;
    public String nombre_de;





    public String contenido;
    public LinkedList<archivo> archivo;

    public correo(int no, String usuario, String nombre_usuario, String asunto, String fecha, String de, String nombre_de, String contenido, LinkedList<archivo> archivo) {
        this.no = no;
        this.usuario = usuario;


        this.nombre_usuario = nombre_usuario;






        this.asunto = asunto;












        this.fecha = fecha;
        this.de = de;





        this.nombre_de = nombre_de;
        this.contenido = contenido;
        this.archivo = archivo;
    }

    public int getNo() {







        return no;
    }














    public void setNo(int no) {
        this.no = no;
    }




    public String getUsuario() {
        return usuario;
    }



    public void setUsuario(String usuario) {



        this.usuario = usuario;
    }





    public String getNombre_usuario() {














        return nombre_usuario;
    }

    public void setNombre_usuario(String nombre_usuario) {


        this.nombre_usuario = nombre_usuario;
    }


    public String getAsunto() {




        return asunto;
    }









    public void setAsunto(String asunto) {
        this.asunto = asunto;
    }






    public String getFecha() {





        return fecha;
    }









    public void setFecha(String fecha) {






        this.fecha = fecha;
    }

    public String getDe() {


        return de;
    }




    public void setDe(String de) {
        this.de = de;
    }

    public String getNombre_de() {


        return nombre_de;
    }

    public void setNombre_de(String nombre_de) {
        this.nombre_de = nombre_de;



    }

    public String getContenido() {
        return contenido;
    }


    public void setContenido(String contenido) {





        this.contenido = contenido;
    }

    public LinkedList<archivo> getArchivo() {
        return archivo;
    }

    public void setArchivo(LinkedList<archivo> archivo) {
        this.archivo = archivo;
    }
    
    
   
    
    
}
