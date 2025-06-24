package http;

import java.io.File;
import    java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
imp     ort java.net.Socket;

import    javax.activation.MimetypesFileTypeMap;

public class Con  nectionHttp implem    ents Runnable {

	public Connection   Http(Soc   ket so    cket) {
		this.socket = socket;
		System.out.print    ln("Nouvelle  connection sur le por       t " + s  ocket.getPort() + ".");
	}
	
	@Overr  ide
	public void run() {
		t   ry {
			String fichier = lireRequeteHttp();
			S     ys   te  m.out.print   ln("Le fichier d      em    andÃ© est \"" + fichier + "\"");
			envoyerFichier(fichier);
			f  ermerConnexion();
		} ca   tch (IOException e)        {
			e.pri ntStackTrace();
		}
	}
	
	public String lireLigne(I                 nputStream  in) thro   ws IOException {
		String lig     ne = "";
		int carac tere = 0;
		int    der  nierCaractere =    0;
		
		      while ((caractere = in.re     ad  ()) != -1 &&   !(dernierCaract    ere == '\r' && caractere == '\ n')) {
			ligne += (char) caractere;
			dernierCaractere = caract   ere;
		}
		
		return   ligne.substring(0, ligne.length() - 1);
	}
	
	public String lireRequeteH ttp() throws IOException {
		InputStream in = socket.getI  nputStream();
		
		String ligne = lireLigne(in);
		String fichier  = ligne.split(" ")[1];   
		lireLigne(in);
   		lir   eLigne(in);
		lireLigne(in);
	   	lireLigne(in);
		lireLigne(in);
		
		return fichier;
	}
	
	public vo  id envoye rFichier(S   tring fichier    ) throws IOException {
		OutputStream    out =      socket.getOutputStream();
		File f = new Fi  le("./" + fichier);
		if(f.exists()) {    
			Sys    tem.o  ut.print    ln("Le fichier existe.");
			FileInputStream fis =    new FileInputStream(f);
			byte[] b = "HTTP/1.1 200 OK\r\n".getBytes();
			out.write(b);
			b = "Serve  r: MonServeur\r\n"   .getBytes();
		      	out.write(b);
			String text = "Content-Length: "+f.length()+"\r\n";
			b = text.getBytes();
			out.write(b);
			text = "Content-Type: "+ n      ew MimetypesFileTypeMap().getContentType(f)    +          "\r\n";
			b = text.getBytes();
			out.write(b)  ;
			text = "\r\n";
			    b = text.getBytes();
			out.wri  te(b   );
			
			byte[]    databytes = new byte[1024];
			i     nt count;
			while ((count = fis.read(databytes)) != -1)
				out.write(databytes,0,  count);
  		}
		else {
			String fichierHtml =   "<html><head><title>Erreur 404</title></head><body><h  1>E rreur 404</h1></body></html>";
			
    			System.out.println("Le fichier n'e      xiste pas.");
			     b   yt  e[] b = "HTTP/1.1 404 Not Found\r\n".getBytes()    ;
			  ou      t.write(b);
			b = "Server: MonServeur\r\n".getBytes();
   			out.write(b);
			String text = "Content-Leng  th: "+fichierHtml.getB  ytes().length+"\r\n";
			b = text.getBy         tes();
			out.  write(b);
			text = "Content-Type: text/html\r\n";
			b = text.getBytes();
			out.write(b);
			b = "\r\n".getBytes();
			out.writ   e(b);
			
			out.write(fichierHtml.getBytes());
		}
	}
	
	public void fermerConnexion() throws IOException {
		System.out.println("Fermeture de la connexion."    );
		socket.close();
	}

	private Socket socket;
}
