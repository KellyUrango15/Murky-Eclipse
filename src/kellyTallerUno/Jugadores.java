package kellyTallerUno;

import processing.core.PApplet;
import processing.core.PImage;

public class Jugadores {
	
	private PApplet app;
	private PImage[] juga = new PImage[2];
	private int posX, posY, jug;
	//private PImage ju;

	public Jugadores(PApplet app, int jug) {
		this.app = app;
		this.jug = jug;
		
		posX = (int) app.random(80, 1000);
		posY = 600;
		
		juga[0] = app.loadImage("./imagenes/per-uno.png");
		juga[1] = app.loadImage("./imagenes/per-dos.png");
		//ju=p.loadImage("./imagenes/per-uno.png");
	}

	public void pintar() {
		app.imageMode(app.CENTER);
		// Usuario 1
		if (jug == 0) {
			app.image(juga[jug], posX, posY);
		}
		// Usuario 2
		if (jug == 1) {
			app.image(juga[jug], posX, posY);
		}
		app.imageMode(app.CORNER);
	}


	public void moverIzq() {
		// TODO Auto-generated method stub
		posX += 50;
	}

	public void moverDer() {
		// TODO Auto-generated method stub
		posX -= 50;
	}

	// Getters y Setters
		public int getPosX() {
			return posX;
		}

		public void setPosX(int posX) {
			this.posX = posX;
		}

		public int getPosY() {
			return posY;
		}

		public void setPosY(int posY) {
			this.posY = posY;
		}

		public int getJug() {
			return jug;
		}

		public void setJug(int jug) {
			this.jug = jug;
		}

}
