package kellyTallerUno;

import processing.core.PApplet;
import processing.core.PImage;

public class Malos {

	private PImage[] ma = new PImage[4];
	PApplet app;
	public float x, y, a;
	public int malo;

	public Malos(PApplet app, float y, int malo) {

		this.app = app;
		this.y = y;
		this.malo = malo;

		x = (int) app.random(80, 1000);
		a = (int) app.random(4, 8);

		ma[0] = app.loadImage("./imagenes/menos-uno");

	}

	public void pintar(PApplet app) {
		app.imageMode(app.CENTER);
		// Rodillo
		if (malo == 0) {
			app.image(ma[malo], x, y);
		}
		app.imageMode(app.CORNER);
	}

	public void mover() {
		if (y < 900) {
			y += a;
		}
		if (y == 900) {
			y = 0;
			x = (int) app.random(80, 1000);
		}
	}

	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}

}
