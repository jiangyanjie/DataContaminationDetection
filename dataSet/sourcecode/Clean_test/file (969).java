/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package italler;

import java.io.*;
import java.util.ArrayList;


/**
 *
 * @author ArclorenSarth
 */
public class ctrlDisco {
    
    private static ctrlDisco INSTANCE = null;
    private File Facs = new File("./Data/facturas");
    private File Clientes = new File("./Data/clientes");
    private File Coches = new File("./Data/coches");
    
    private ctrlDisco() {
        //Facs = new File("./Data/facturas");
        //Clientes = new File("./Data/clientes");
        //Coches = new File(":/Data/coches");
        if (!Clientes.exists()) {
            Clientes.mkdirs();
        }
        if (!Coches.exists()) {
            Coches.mkdirs();
        }
        if (!Facs.exists()) {
            Facs.mkdirs();
        }
        crearArchivo("clientes/clientes");
        crearArchivo("coches/coches");
    }
    
    private static void creaInstancia() {
        if (INSTANCE == null) {
            INSTANCE = new ctrlDisco();
        }
    }

    public static ctrlDisco getInstance() {
        if (INSTANCE == null) {
            creaInstancia();
        }
        return INSTANCE;
    }

    
    public void crearArchivo(String nombre){
        File arch = new File("./Data/" + nombre + ".o");
        if(!arch.exists()){
            try{
                arch.createNewFile();
            } catch(IOException ex){
                System.out.println("\nDATA CORRUPTED!!!");
                System.out.println(ex.getMessage() + "\n");
            }
        }
    }
    
    
    public String[] listarDirectorio(String path) {
        File f = new File(path);
        return f.list(); 
    }
    
    
    public boolean eliminarDirectorio(String path) {
        File f = new File("./Data/" + path);
        
        
        System.out.println(path);
        if (f.exists()){
            
            File f2;
            String[] files = f.list();
            if(files != null){
                for(int i = 0; i<files.length; ++i){
                    f2 = new File("./Data/" + path + files[i]);
                    f2.delete();
                }
            }
            f.delete();
            return true;
        }
        else return false;
    }
    
    
    public ArrayList<String> leerArchivo(String nombre) {
        ArrayList<String> datos = new ArrayList();
        try {
            FileReader fr = new FileReader("./Data/" + nombre + ".o");
            BufferedReader br = new BufferedReader(fr);
            String line;
            while ((line = br.readLine()) != null){  
                datos.add(line);
            }
            fr.close();          
        } catch (FileNotFoundException ex) {
            crearArchivo(nombre);
        } catch (IOException ex) {
            System.out.println("\nDATA CORRUPTED!!!");
            System.out.println(ex.getMessage() + "\n");
        }
        return datos;
    }
    
    
    public void escribirArchivo(String nombre, ArrayList<String> datos) {
        try {
            FileWriter fw = new FileWriter("./Data/" + nombre + ".o");
            PrintWriter pw = new PrintWriter(fw);
            for(int i=0; i<datos.size(); ++i){ 
                pw.println(datos.get(i));
            }
            fw.close();           
        } catch (FileNotFoundException ex) {
            crearArchivo(nombre);
        } catch (IOException ex) {
            System.out.println("\nDATA CORRUPTED!!!");
            System.out.println(ex.getMessage() + "\n");
        }
        
    }
    
    
    public void escribirObjeto (String path, String nom, Object ob) /*throws FileNotFoundException, IOException*/ {
        File f = new File("./Data/Barrios/" + path);
        if (!f.exists()) {
            f.mkdirs();
        }
        f = new File("./Data/Barrios/" + path + nom + ".o");
        try{
            FileOutputStream fos = new FileOutputStream(f);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(ob);
            oos.close();
        }catch(Exception e){
            System.out.println("\nDATA CORRUPTED!!!");
            System.out.println(e.getMessage() + "\n");
        }
    }
    
    
    public Object leerObjeto (String path, String nom) /*throws FileNotFoundException, IOException*/ {
        Object ob = null;
        File f = new File("./Data/Barrios/" + path + nom + ".o");
        try{//FileWriter fw = new FileWriter(f);
            FileInputStream fis = new FileInputStream(f);
            ObjectInputStream ois = new ObjectInputStream(fis);
            ob = ois.readObject();
	    ois.close();
        } catch(Exception e){
            System.out.println("\nDATA CORRUPTED!!!");
            System.out.println(e.getMessage() + "\n");
        }
        return ob;
	}
}