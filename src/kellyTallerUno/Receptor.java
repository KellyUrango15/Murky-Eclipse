package kellyTallerUno;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;


public class Receptor extends Thread {
	Socket s;
	OnMessage observer;
	String usuario;
	
	public Receptor (Socket s, String usuario) {
		this.s=s;
		this.usuario= usuario;
	}

	@Override
	public void run() {
		try {
			InputStream inp = s.getInputStream();
			BufferedReader lector = new BufferedReader (new InputStreamReader (inp));
			
			while (true) {
				String msj = lector.readLine();
				System.out.println(msj);
				observer.recibirMen(msj, usuario);
				}
		}catch (IOException e) {
			e.printStackTrace();
		}
	}
		
		public interface OnMessage {
			public void recibirMen ( String msj, String usuario );
		}
		public void setObserver ( OnMessage mensaje ) {		
			this.observer = mensaje;
		}

}
