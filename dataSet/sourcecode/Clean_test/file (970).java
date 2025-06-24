/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package italler;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.TreeMap;

/**
 *
 * @author ArclorenSarth
 */
public class ctrlTaller {
    
    private static ctrlTaller INSTANCE = null;
    ctrlDisco disco;
    TreeMap<String,Cliente> cjtClientes;
    TreeMap<String,Coche> cjtCoches;
    
    
    private ctrlTaller() {
        disco = ctrlDisco.getInstance();
        cjtClientes = new TreeMap();
        cjtCoches = new TreeMap();
        leerClientes();
        leerCoches();
    }
    
    private static void creaInstancia() {
        if (INSTANCE == null) {
            INSTANCE = new ctrlTaller();
        }
    }

    public static ctrlTaller getInstance() {
        if (INSTANCE == null) {
            creaInstancia();
        }
        return INSTANCE;
    }
    
    
    
    
    //FUNCIONES DE FACTURAS
    public ArrayList<String> searchFacturas(String mat){
        String dataPath = "./data/facturas";
	String[] years = disco.listarDirectorio(dataPath);
	String[] months, fac;
	ArrayList <String> result = new ArrayList();
	for(int i=0; i< years.length; ++i){
		months = disco.listarDirectorio(dataPath +"/"+ years[i]);
		for(int j=0; j<months.length; ++j){
			fac = disco.listarDirectorio(dataPath +"/"+ years[i] +"/"+ months[j]);
			for(int k=0; k<fac.length; ++k){
				if(fac[k].contains(mat)) 
                                    result.add(dataPath +"/"+ years[i] +"/"+ months[j] +"/"+ fac[k]);
	
                        }
                }
        }
        return result;
    }
    
    
    public void abrirFactura(String path){
        try {
            if (Desktop.isDesktopSupported()) {
                Desktop.getDesktop().open(new File(path));
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }
    
    
    
    
    //FUNCIONES DE CLIENTES
    public void crearCliente(String nom, String dni, String dir, String tlf, 
                             String mvl, String mail, String obs) throws Exception{
        if(nom == null){
            throw new Exception("El campo nombre no puede estar vacio!");
        }
        if(nom.length() < 1){
            throw new Exception("El campo nombre no puede estar vacio!");
        }
        if(dni == null) dni = "-NA-";
        if(dir == null) dir = "-NA-";
        if(tlf == null) tlf = "-NA-";
        if(mvl == null) mvl = "-NA-";
        if(mail == null) mail = "-NA-";
        if(obs == null) obs = "-NA-";
        
        if(dni.length() == 0) dni = "-NA-";
        if(dir.length() == 0) dir = "-NA-";
        if(tlf.length() == 0) tlf = "-NA-";
        if(mvl.length() == 0) mvl = "-NA-";
        if(mail.length() == 0) mail = "-NA-";
        if(obs.length() == 0) obs = "-NA-";
        
        
        if(cjtClientes.containsKey(nom)){
            throw new Exception("Ya existe un cliente con este nombre!");
        }
        if(nom.contains("\t") || dni.contains("\t") || dir.contains("\t") ||
           tlf.contains("\t") || mvl.contains("\t") || mail.contains("\t") ||
           obs.contains("\t")){
            throw new Exception("Los parametros del cliente no pueden contener tabulaciones");
        }
        
        if(nom.contains("/") || nom.contains(":") || nom.contains("\\") ||
           nom.contains("*") || nom.contains("<") || nom.contains(">") ||
           nom.contains("\"") || nom.contains("?") || nom.contains("|")){
            throw new Exception("El nombre no puede contener / \\ < > * ? | :");
        }
        
        Cliente cl = new Cliente(nom,dni,dir,tlf,mvl,mail,obs);
        cjtClientes.put(nom,cl);
        
        //GUARDAR EN EL ARCHIVO!
        ArrayList<String> list = disco.leerArchivo("clientes/clientes");
        ArrayList<String> o = new ArrayList();
        o.add(obs);
        list.add(cl.saveCLiente());
        disco.escribirArchivo("clientes/clientes",list);
        disco.crearArchivo("clientes/" + nom);
        disco.escribirArchivo("clientes/" + nom, o);
        
    }
    
    
    public void eliminarCliente(String name) throws Exception{
        if(!cjtClientes.containsKey(name)){
            throw new Exception("Este cliente no existe!");
        }
        
        //ACTUALIZAR LOS COCHES QUE TENIA EL CLIENTE Y DEJARLOS SIN PROPIETARIO
        ArrayList<String> coch = cjtClientes.get(name).getCoches();
        for(int i=0; i<coch.size(); ++i){
            if(cjtCoches.containsKey(coch.get(i))){
                Coche c = cjtCoches.get(coch.get(i));
                c.setPropietario("-NA-");
                //ACTUALIZAR EL ARCHIVO DE COCHES
                ArrayList<String> laux = disco.leerArchivo("coches/coches");
                boolean find = false;
                String[] aux;
                for(int j=0; j<laux.size() && !find; ++j){
                    if(c.getMatricula().equals(laux.get(j).split("\t")[0])){
                        laux.remove(j);
                        laux.add(j,c.saveCoche());
                        find = true;
                    }
                }
                disco.escribirArchivo("coches/coches",laux);
            }
        }
        
        cjtClientes.remove(name);
                
        ArrayList<String> list = disco.leerArchivo("clientes/clientes");
        boolean find = false;
        String[] aux;        
        for(int i=0; i<list.size() && !find; ++i){
            if(name.equals(list.get(i).split("\t")[0])){
                list.remove(i);
                find = true;
            }
        }
        disco.escribirArchivo("clientes/clientes",list);
        disco.eliminarDirectorio("clientes/" + name + ".o");
    }
    
    
    public void modificarCliente(String old, String nom, String dni, String dir, String tlf, 
                             String mvl, String mail, String obs) throws Exception{
        if(nom == null){
            throw new Exception("El campo nombre no puede estar vacio!");
        }
        if(nom.length() < 1){
            throw new Exception("El campo nombre no puede estar vacio!");
        }
        if(dni == null) dni = "-NA-";
        if(dir == null) dir = "-NA-";
        if(tlf == null) tlf = "-NA-";
        if(mvl == null) mvl = "-NA-";
        if(mail == null) mail = "-NA-";
        if(obs == null) obs = "-NA-";
        
        if(dni.length() == 0) dni = "-NA-";
        if(dir.length() == 0) dir = "-NA-";
        if(tlf.length() == 0) tlf = "-NA-";
        if(mvl.length() == 0) mvl = "-NA-";
        if(mail.length() == 0) mail = "-NA-";
        if(obs.length() == 0) obs = "-NA-";
        
        
        if(cjtClientes.containsKey(nom) && !nom.equals(old)){
            throw new Exception("El nombre esta siendo usado por otro cliente!");
        }
        if(nom.contains("\t") || dni.contains("\t") || dir.contains("\t") ||
           tlf.contains("\t") || mvl.contains("\t") || mail.contains("\t") ||
           obs.contains("\t")){
            throw new Exception("Los parametros del cliente no pueden contener tabulaciones");
        }
        
        if(nom.contains("/") || nom.contains(":") || nom.contains("\\") ||
           nom.contains("*") || nom.contains("<") || nom.contains(">") ||
           nom.contains("\"") || nom.contains("?") || nom.contains("|")){
            throw new Exception("El nombre no puede contener / \\ < > * ? | :");
        }
        
        
        
        Cliente cl = cjtClientes.get(old);
        //ACTUALIZAR LOS COCHES QUE TIENE EL PROPIETARIO
        ArrayList<String> coch = cl.getCoches();
        for(int i=0; i<coch.size(); ++i){
            if(cjtCoches.containsKey(coch.get(i))){
                Coche c = cjtCoches.get(coch.get(i));
                c.setPropietario(nom);
                
                //ACTUALIZAR EL ARCHIVO DE COCHES
                ArrayList<String> laux = disco.leerArchivo("coches/coches");
                boolean find = false;
                String[] aux;
                for(int j=0; j<laux.size() && !find; ++j){
                    if(c.getMatricula().equals(laux.get(j).split("\t")[0])){
                        laux.remove(j);
                        laux.add(j,c.saveCoche());
                        find = true;
                    }
                }
                disco.escribirArchivo("coches/coches",laux);
                
            }
        }
        
        cl.setNombre(nom);
        cl.setDNI(dni);
        cl.setTlf(tlf);
        cl.setMvl(mvl);
        cl.setMail(mail);
        cl.setDir(dir);
        cl.setObs(obs);
        if(!nom.equals(old)){
            cjtClientes.put(nom, cl);
            cjtClientes.remove(old);
        }
        
        ArrayList<String> list = disco.leerArchivo("clientes/clientes");
        boolean find = false;
        String[] aux;        
        for(int i=0; i<list.size() && !find; ++i){
            if(old.equals(list.get(i).split("\t")[0])){
                list.remove(i);
                list.add(i,cl.saveCLiente());
                find = true;
            }
        }
        disco.escribirArchivo("clientes/clientes",list);
        
        
        ArrayList<String> o = new ArrayList();
        o.add(obs);
        disco.eliminarDirectorio("clientes/" + old + ".o");
        disco.crearArchivo("clientes/" + nom);
        disco.escribirArchivo("clientes/" + nom, o);
        
        
    }
    
    
    public ArrayList<Cliente> searchClientes(String nom){
        ArrayList<Cliente> cList = new ArrayList();
        ArrayList<Cliente> res = new ArrayList();
        cList.addAll(cjtClientes.values());
        for(int i=0; i<cList.size(); ++i){
            if(cList.get(i).getNombre().contains(nom))
                res.add(cList.get(i));
        }
        return res;
    }
    
    
    private void leerClientes(){
        ArrayList<String> list = disco.leerArchivo("clientes/clientes");
        String[] aux;
        for(int i=0; i<list.size(); ++i){
            aux = list.get(i).split("\t");
            String o = new String();
            ArrayList<String> ob = disco.leerArchivo("clientes/" + aux[0]);
            for(int j=0; j<ob.size(); ++j){
                o = o.concat(ob.get(j));
                if(j+1<ob.size())
                    o = o.concat("\n");
            }
            cjtClientes.put(aux[0],new Cliente(aux[0],aux[1],aux[5],aux[2],
                                               aux[3],aux[4],o));
            
        }
    }
    
    
    //FUNCIONES DE COCHES
    public ArrayList<Coche> searchCoches(String mat){
        ArrayList<Coche> cList = new ArrayList();
        ArrayList<Coche> res = new ArrayList();
        cList.addAll(cjtCoches.values());
        for(int i=0; i<cList.size(); ++i){
            if(cList.get(i).getMatricula().contains(mat))
                res.add(cList.get(i));
        }
        return res;
    }
    
    
    private void leerCoches(){
        ArrayList<String> list = disco.leerArchivo("coches/coches");
        String[] aux;
        for(int i=0; i<list.size(); ++i){
            aux = list.get(i).split("\t");
            if(!cjtClientes.containsKey(aux[5])) aux[5]="-NA-";
            else cjtClientes.get(aux[5]).addCoche(aux[0]);
            cjtCoches.put(aux[0],new Coche(aux[0],aux[1],aux[2],aux[3],
                                               aux[4],aux[5]));
            
        }
    }

    
    public void eliminarCoche(String matricula) throws Exception {
        if(!cjtCoches.containsKey(matricula)){
            throw new Exception("Este vehiculo no existe!");
        }

        //ACTUALIZAR PROPIETARIO
        if(cjtClientes.containsKey(cjtCoches.get(matricula).getPropietario())){
            cjtClientes.get(cjtCoches.get(matricula).getPropietario()).removeCoche(matricula);
        }
        
        cjtCoches.remove(matricula);
             
        ArrayList<String> list = disco.leerArchivo("coches/coches");
        boolean find = false;
        String[] aux;        
        for(int i=0; i<list.size() && !find; ++i){
            if(matricula.equals(list.get(i).split("\t")[0])){
                list.remove(i);
                find = true;
            }
        }
        disco.escribirArchivo("coches/coches",list);
        
        
        
                
    }

    
    public void crearCoche(String mat, String marca, String modelo, 
                           String km, String bast, String prop) throws Exception {
        if(mat == null){
            throw new Exception("El campo matricula no puede estar vacio!");
        }
        if(mat.length() < 1){
            throw new Exception("El campo matricula no puede estar vacio!");
        }
        if(marca == null) marca = "-NA-";
        if(modelo == null) modelo = "-NA-";
        if(km == null) km = "-NA-";
        if(bast == null) bast = "-NA-";
        if(prop == null) prop = "-NA-";
        
        if(marca.length() == 0) marca = "-NA-";
        if(modelo.length() == 0) modelo = "-NA-";
        if(km.length() == 0) km = "-NA-";
        if(bast.length() == 0) bast = "-NA-";
        if(prop.length() == 0) prop = "-NA-";
        
                
        if(cjtCoches.containsKey(mat)){
            throw new Exception("Ya existe un vehiculo con esta matricula!");
        }
        if(mat.contains("\t") || marca.contains("\t") || modelo.contains("\t") ||
           km.contains("\t") || bast.contains("\t") || prop.contains("\t")){
            throw new Exception("Los parametros del vehiculo no pueden contener tabulaciones");
        }
        
        if(mat.contains("/") || mat.contains(":") || mat.contains("\\") ||
           mat.contains("*") || mat.contains("<") || mat.contains(">") ||
           mat.contains("\"") || mat.contains("?") || mat.contains("|")){
            throw new Exception("La matricula no puede contener / \\ < > * ? | :");
        }
        
        Coche c = new Coche(mat,marca,modelo,km,bast,prop);
        cjtCoches.put(mat,c);
        
        //GUARDAR EN EL ARCHIVO!
        ArrayList<String> list = disco.leerArchivo("coches/coches");
        list.add(c.saveCoche());
        disco.escribirArchivo("coches/coches",list);
        
        //ACTUALIZAR PROPIETARIO
        if(cjtClientes.containsKey(cjtCoches.get(mat).getPropietario())){
            cjtClientes.get(cjtCoches.get(mat).getPropietario()).addCoche(mat);
        }
        
    }

    
    public void modificarCoche(String mat, String old, String marca, String modelo, 
                           String km, String bast, String prop) throws Exception {
        if(mat == null){
            throw new Exception("El campo matricula no puede estar vacio!");
        }
        if(mat.length() < 1){
            throw new Exception("El campo matricula no puede estar vacio!");
        }
        if(marca == null) marca = "-NA-";
        if(modelo == null) modelo = "-NA-";
        if(km == null) km = "-NA-";
        if(bast == null) bast = "-NA-";
        if(prop == null) prop = "-NA-";
        
        if(marca.length() == 0) marca = "-NA-";
        if(modelo.length() == 0) modelo = "-NA-";
        if(km.length() == 0) km = "-NA-";
        if(bast.length() == 0) bast = "-NA-";
        if(prop.length() == 0) prop = "-NA-";
        
                
        if(cjtCoches.containsKey(mat) && !mat.equals(old)){
            throw new Exception("Ya existe un vehiculo con esta matricula!");
        }
        
        
        if(mat.contains("\t") || marca.contains("\t") || modelo.contains("\t") ||
           km.contains("\t") || bast.contains("\t") || prop.contains("\t")){
            throw new Exception("Los parametros del vehiculo no pueden contener tabulaciones");
        }
        
        if(mat.contains("/") || mat.contains(":") || mat.contains("\\") ||
           mat.contains("*") || mat.contains("<") || mat.contains(">") ||
           mat.contains("\"") || mat.contains("?") || mat.contains("|")){
            throw new Exception("La matricula no puede contener / \\ < > * ? | :");
        }
        
        Coche c = cjtCoches.get(old);
        //BORRAR LA MATRICULA ANTERIOR DEL ANTERIOR PROPIETARIO PROPIETARIO
        if(cjtClientes.containsKey(c.getPropietario())){
            cjtClientes.get(c.getPropietario()).removeCoche(old);
        }
        c.setMatricula(mat);
        c.setMarca(marca);
        c.setModelo(modelo);
        c.setNumKm(km);
        c.setNumBast(bast);
        c.setPropietario(prop);
        if(!mat.equals(old)){
            cjtCoches.put(mat, c);
            cjtCoches.remove(old);
        }
        //ACTUALIZAR PROPIETARIO
        if(cjtClientes.containsKey(c.getPropietario())){
            cjtClientes.get(c.getPropietario()).addCoche(mat);
        }
        
        //GUARDAR EN EL ARCHIVO!
        ArrayList<String> list = disco.leerArchivo("coches/coches");
        boolean find = false;
        String[] aux;
        for(int i=0; i<list.size() && !find; ++i){
            if(old.equals(list.get(i).split("\t")[0])){
                list.remove(i);
                list.add(i,c.saveCoche());
                find = true;
            }
        }
        disco.escribirArchivo("coches/coches",list);
                
    }
    
}
