package kellyTallerUno;

import processing.core.PApplet;
import processing.core.PImage;

public class Buenos {

	private PImage[] buen = new PImage[1];
	PApplet app;
	public float x, y, a;
	public int bu;

	public Buenos(PApplet app, float y, int bu) {

		this.app = app;
		this.y = y;
		this.bu = bu;

		x = (int) app.random(80, 1000);
		a = (int) app.random(4, 8);

		buen[0] = app.loadImage("./imagenes/punt-uno.png");

	}

	public void pintar(PApplet app) {
		app.imageMode(app.CENTER);
		// Buena
		if (bu == 0) {
			app.image(buen[bu], x, y);
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
