


package Datos;






// Generated 11-nov-2014 19:09:06 by Hibernate Tools 3.6.0


import java.io.Serializable;

/**



 * Cultivo generated by hbm2java
 */
public class Cultivo  implements java.io.Serializable {








     private int idCultivo;



     private Propiedad propiedad;



     private Area area;




     private int id;
     private Serializable nombre;




     private Serializable distEntrePlantas;





     private Serializable distEntreSurcos;
     private Serializable superficieTotal;
     private Serializable cantidadSemillas;


     private Serializable kilogramosSemillas;
     private Serializable produccionEsperada;





     private int cerrado;
     private Serializable fechaCreacion;
     private Serializable fechaActualizacion;
     private Serializable fechaCierre;
     private Serializable tiempoTranscurrido;
     private Serializable produccionReal;
     private Serializable observaciones;
     private int idPropiedad;





     private int idArea;
     private int idTipocultivo;


     private int idTipocultivo_1;

    public Cultivo() {
    }





	











    public Cultivo(int idCultivo, Propiedad propiedad, Area area, int id, Serializable nombre, Serializable distEntrePlantas, Serializable distEntreSurcos, Serializable superficieTotal, Serializable cantidadSemillas, Serializable kilogramosSemillas, Serializable produccionEsperada, int cerrado, Serializable fechaCreacion, Serializable fechaActualizacion, int idPropiedad, int idArea, int idTipocultivo, int idTipocultivo_1) {
        this.idCultivo = idCultivo;





        this.propiedad = propiedad;



        this.area = area;





        this.id = id;
        this.nombre = nombre;
        this.distEntrePlantas = distEntrePlantas;
        this.distEntreSurcos = distEntreSurcos;
        this.superficieTotal = superficieTotal;
        this.cantidadSemillas = cantidadSemillas;




        this.kilogramosSemillas = kilogramosSemillas;
        this.produccionEsperada = produccionEsperada;



        this.cerrado = cerrado;
        this.fechaCreacion = fechaCreacion;










        this.fechaActualizacion = fechaActualizacion;
        this.idPropiedad = idPropiedad;



        this.idArea = idArea;
        this.idTipocultivo = idTipocultivo;
        this.idTipocultivo_1 = idTipocultivo_1;











    }
    public Cultivo(int idCultivo, Propiedad propiedad, Area area, int id, Serializable nombre, Serializable distEntrePlantas, Serializable distEntreSurcos, Serializable superficieTotal, Serializable cantidadSemillas, Serializable kilogramosSemillas, Serializable produccionEsperada, int cerrado, Serializable fechaCreacion, Serializable fechaActualizacion, Serializable fechaCierre, Serializable tiempoTranscurrido, Serializable produccionReal, Serializable observaciones, int idPropiedad, int idArea, int idTipocultivo, int idTipocultivo_1) {
       this.idCultivo = idCultivo;
       this.propiedad = propiedad;
       this.area = area;


       this.id = id;
       this.nombre = nombre;




       this.distEntrePlantas = distEntrePlantas;
       this.distEntreSurcos = distEntreSurcos;




       this.superficieTotal = superficieTotal;








       this.cantidadSemillas = cantidadSemillas;




       this.kilogramosSemillas = kilogramosSemillas;



       this.produccionEsperada = produccionEsperada;
       this.cerrado = cerrado;
       this.fechaCreacion = fechaCreacion;
       this.fechaActualizacion = fechaActualizacion;
       this.fechaCierre = fechaCierre;
       this.tiempoTranscurrido = tiempoTranscurrido;








       this.produccionReal = produccionReal;


       this.observaciones = observaciones;
       this.idPropiedad = idPropiedad;
       this.idArea = idArea;
       this.idTipocultivo = idTipocultivo;
       this.idTipocultivo_1 = idTipocultivo_1;
    }
   
    public int getIdCultivo() {






        return this.idCultivo;




    }


    
    public void setIdCultivo(int idCultivo) {
        this.idCultivo = idCultivo;
    }
    public Propiedad getPropiedad() {
        return this.propiedad;
    }
    








    public void setPropiedad(Propiedad propiedad) {
        this.propiedad = propiedad;



    }






    public Area getArea() {
        return this.area;
    }
    





    public void setArea(Area area) {
        this.area = area;
    }
    public int getId() {
        return this.id;
    }







    











    public void setId(int id) {


        this.id = id;
    }
    public Serializable getNombre() {
        return this.nombre;
    }
    
    public void setNombre(Serializable nombre) {
        this.nombre = nombre;


    }
    public Serializable getDistEntrePlantas() {

        return this.distEntrePlantas;
    }


    
    public void setDistEntrePlantas(Serializable distEntrePlantas) {





        this.distEntrePlantas = distEntrePlantas;
    }




    public Serializable getDistEntreSurcos() {
        return this.distEntreSurcos;









    }
    
    public void setDistEntreSurcos(Serializable distEntreSurcos) {







        this.distEntreSurcos = distEntreSurcos;
    }
    public Serializable getSuperficieTotal() {
        return this.superficieTotal;


    }

    
    public void setSuperficieTotal(Serializable superficieTotal) {



        this.superficieTotal = superficieTotal;


    }
    public Serializable getCantidadSemillas() {
        return this.cantidadSemillas;
    }


    
    public void setCantidadSemillas(Serializable cantidadSemillas) {








        this.cantidadSemillas = cantidadSemillas;
    }
    public Serializable getKilogramosSemillas() {
        return this.kilogramosSemillas;
    }





    
    public void setKilogramosSemillas(Serializable kilogramosSemillas) {
        this.kilogramosSemillas = kilogramosSemillas;
    }
    public Serializable getProduccionEsperada() {

        return this.produccionEsperada;





    }
    





    public void setProduccionEsperada(Serializable produccionEsperada) {
        this.produccionEsperada = produccionEsperada;
    }









    public int getCerrado() {


        return this.cerrado;

    }




    
    public void setCerrado(int cerrado) {
        this.cerrado = cerrado;
    }


    public Serializable getFechaCreacion() {

        return this.fechaCreacion;










    }







    
    public void setFechaCreacion(Serializable fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }
    public Serializable getFechaActualizacion() {
        return this.fechaActualizacion;
    }
    










    public void setFechaActualizacion(Serializable fechaActualizacion) {
        this.fechaActualizacion = fechaActualizacion;
    }


    public Serializable getFechaCierre() {
        return this.fechaCierre;
    }
    
    public void setFechaCierre(Serializable fechaCierre) {
        this.fechaCierre = fechaCierre;






    }
    public Serializable getTiempoTranscurrido() {


        return this.tiempoTranscurrido;
    }
    
    public void setTiempoTranscurrido(Serializable tiempoTranscurrido) {
        this.tiempoTranscurrido = tiempoTranscurrido;
    }
    public Serializable getProduccionReal() {
        return this.produccionReal;








    }
    

    public void setProduccionReal(Serializable produccionReal) {












        this.produccionReal = produccionReal;
    }
    public Serializable getObservaciones() {
        return this.observaciones;




    }
    
    public void setObservaciones(Serializable observaciones) {
        this.observaciones = observaciones;
    }
    public int getIdPropiedad() {
        return this.idPropiedad;
    }
    
    public void setIdPropiedad(int idPropiedad) {



        this.idPropiedad = idPropiedad;
    }
    public int getIdArea() {
        return this.idArea;
    }

    
    public void setIdArea(int idArea) {
        this.idArea = idArea;
    }
    public int getIdTipocultivo() {
        return this.idTipocultivo;
    }
    
    public void setIdTipocultivo(int idTipocultivo) {
        this.idTipocultivo = idTipocultivo;
    }
    public int getIdTipocultivo_1() {
        return this.idTipocultivo_1;
    }
    
    public void setIdTipocultivo_1(int idTipocultivo_1) {
        this.idTipocultivo_1 = idTipocultivo_1;


    }




}


