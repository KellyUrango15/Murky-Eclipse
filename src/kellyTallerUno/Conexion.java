package kellyTallerUno;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Observable;

public class Conexion extends Observable implements Runnable {

	private int ide;
	private boolean conectado;
	
	//ServerSocket ss;
	private Socket s;
	//Conectar observer;
	
	public Conexion(Socket s, int ide) {
		this.s = s;
		this.ide = ide;
		conectado = true;
		
		/*
		try{
		ss= new ServerSocket(5000);
		System.out.println("Esperando conexión...");
		}catch (IOException e) {
			e.printStackTrace();
		}*/
	}

	@Override
	public void run() {
		enviarMensajes("Hola jugador");
		
		while(conectado) {
			recibirMensajes();
			
			try {
				Thread.sleep(100);
			}catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
			
	}
	
	private void recibirMensajes() {
		DataInputStream dis = null;
		try {
			dis = new DataInputStream(s.getInputStream());
			String mensaje = dis.readUTF();

			setChanged();
			notifyObservers(mensaje);
		} catch (IOException e) {
			System.err.println("Cliente" + ide + "Desconectado");
			try {
				if (dis != null) {
					dis.close();
				}
				s.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			s = null;
			conectado = false;
			setChanged();
			notifyObservers("Desconectado");
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void enviarMensajes(String mensaje) {
		DataOutputStream dos = null;
		try {
			dos = new DataOutputStream(s.getOutputStream());
			dos.writeUTF(mensaje);
			System.out.println("Cliente" + ide + "Se envia el mensaje al cliente: " + mensaje);
		} catch (IOException e) {
			System.err.println("Cliente" + ide + "Desconectado");
			try {
				if (dos != null) {
					dos.close();
				}

				s.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			s = null;
			conectado = false;
			setChanged();
			notifyObservers("Desconectado");
		}
	}
	
	public int getIde() {
		return ide;
	}

	public void setIde(int ide) {
		this.ide = ide;
	}

	public boolean isConectado() {
		return conectado;
	}
	
	
	public void setConectado (boolean conectado) {
		this.conectado = conectado;
	}
	
}
