package kellyTallerUno;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import processing.core.PApplet;
import processing.core.PImage;


public class Logica implements Observer, Runnable {
	private PApplet app;
	Conexion cnx;
	ServerSocket servSocket;
	private final int PUERTO = 7070;
	
	// ips
	private byte[] ip;
	private GetIp oip;
	private ArrayList<String> ips;
		
	// Declacion de imagenes
	private PImage inicio, instruUno, instruDos, fondo, contaUno, contaDos; 
	private PImage puntoUno, puntoDos, menosUno, menosDos;
	
	// Variables
	private int pantallas, mensj, puntos, bad, good;
	private boolean connect;
	
	private ArrayList<Buenos> buen;
	private ArrayList<Malos> mal;
	private ArrayList<Jugadores> jug;
	
    //Jugadores jugadorUno, jugadorDos;
	
	public Logica(PApplet app) {
		// TODO Auto-generated constructor stub
		this.app = app;
		
		buen = new ArrayList<Buenos>();
		mal = new ArrayList<Malos>();
		jug = new ArrayList<Jugadores>();
		
		//inicializando los jugadores
		//jugadorUno = new Jugadores(p);
		//jugadorDos = new Jugadores(p);
		
		//inicializando los variables
		pantallas=2;
		mensj=0;
		puntos=0;
		
		//imagenes
		inicio=app.loadImage("./imagenes/inicio.png");
		instruUno=app.loadImage("./imagenes/instru1.png");
		//instruDos=p.loadImage("../imagenes/instru2.png");
		fondo=app.loadImage("./imagenes/fondo-juego.png");
		contaUno=app.loadImage("./imagenes/conta-uno.png");
		contaDos=app.loadImage("./imagenes/conta-dos.png");
		
		// Conexión con android
		//server=new Servidor(this);
		//server.setObserver(this);
		//server.start();
		
		try {
			servSocket = new ServerSocket(PUERTO);
			connect = true;
			System.out.println("[Servidor]: Atendiendo en " + InetAddress.getLocalHost().getHostAddress().toString()
					+ ":" + this.PUERTO);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void pintar() {
		// TODO Auto-generated method stub
		pantallas();
		puntos();
	}

	private void puntos() {
		// TODO Auto-generated method stub
		
	}

	private void pantallas() {
		// TODO Auto-generated method stub
		
		switch (pantallas) {
		case 0:
			app.image(inicio, 0, 0);
			break;

		case 1:
			app.image(instruUno, 0, 0);
			break;
			
		case 2:
			app.image(fondo, 0, 0);
			app.image(contaUno, 246, 0);
			app.image(contaDos, 849, 0);
			//metodos de la clase jugadores
			//jugadorUno.pintarUno();
			//jugadorDos.pintarDos();
			
			break;
		case 3:
			app.image(inicio, 0, 0);
			break;				
		}
	}
		
		// metodo para cambiar de pantalla
		
	public void cambiarPantallas() {
		// TODO Auto-generated method stub
		System.out.println(app.mouseX + "," + app.mouseY);
		
		if(app.mouseX > 489 && app.mouseX< 713 && app.mouseY > 426 && app.mouseY< 493) {
			pantallas = 1;
		}
		if(app.mouseX > 511 && app.mouseX< 703 && app.mouseY > 243 && app.mouseY< 301) {
			pantallas = 2;
		}

	}
	
	//Aqui llega el mensaje que envia android
	
	/*
	public void mensajes(String msj, String usuario) {
		if(usuario.equals("jUno")) {
			moverJugador(msj, jugadorUno);
		}else if(usuario.equals("jDos")){
			moverJugador(msj, jugadorDos);
		}
	}*/

	
	public void run() {

		while (connect) {
			try {

				// Esperar a que un cliente se conecte
				Socket s = servSocket.accept();

				Conexion cnx = new Conexion(s, jug.size());

				// Agregar el gestor como observador
				cnx.addObserver(this);

				// Comenzar el hilo de ejecución del controlador
				new Thread(cnx).start();

				// Agregar a la colección de clientes
				jug.add(new Jugadores(app, jug.size()));

				System.out.println("SERVIDOR - hay: " + jug.size() + " clientes");
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}
	
	public void generarJugador() {
		if (buen.size() < 30) {
			good = (int) app.random(6);
		}
		if (mal.size() < 30) {
			bad = (int) app.random(4);
		}
		mal.add(new Malos(app, 20, bad));
		buen.add(new Buenos(app, 20, good));
	}
	
	public void obtenerIps() {
		try {
			ip = InetAddress.getLocalHost().getAddress();

			oip = new GetIp(ip, this);
			oip.start();

		} catch (UnknownHostException e) {
			return;
		}
	}
	
	
	/*
	private void moverJugador(String msj, Jugadores ju) {
		// TODO Auto-generated method stub
		mensj= Integer.parseInt(msj);
		
		new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				
				// derecha
				if (mensj == 1) {
					ju.moverDer();
					mensj = 0;
				}

				// izquierda
				if (mensj == 2) {
					ju.moverIzq();
					mensj = 0;
				}

				// instruccion
				else if (mensj == 3) {
					ju.instruccion();
				}

			}

		}).start();
		
	}
	*/
	
	// Leer Mensajes
		public void update(Observable o, Object msn) {

			Conexion controlDevice = (Conexion) o;

			if (msn instanceof String) {
				String mensaje = (String) msn;

				System.out.println(mensaje);

				if (mensaje.equalsIgnoreCase("cliente desconectado")) {
					jug.remove(controlDevice);
					System.out.println("[Servidor] Tenemos: " + jug.size() + " clientes");
				}

				if (mensaje.contains("quien soy")) {
					controlDevice.enviarMensajes("id:" + jug.size());
				}

				String[] partes = mensaje.split(":");

				if (partes[0].contains("left") && partes[1].contains("2")) {
					Jugadores u = jug.get(1);
					u.moverIzq();
				}

				if (partes[0].contains("right") && partes[1].contains("2")) {
					Jugadores u = jug.get(1);
					u.moverDer();
				}

				if (partes[0].contains("left") && partes[1].contains("1")) {
					Jugadores u = jug.get(0);
					u.moverIzq();
				}

				if (partes[0].contains("right") && partes[1].contains("1")) {
					Jugadores u = jug.get(0);
					u.moverDer();
				}
			}
		}
	

}