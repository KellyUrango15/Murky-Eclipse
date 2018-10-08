package kellyTallerUno;

// Importo el PApplet de processing
import processing.core.PApplet;

public class Main extends PApplet{
	
	private Logica logica;
	
	public static void main(String[] args) {
		PApplet.main("kellyTallerUno.Main");
	}
	
	// Defino las dimensiones de la pantalla
	public void settings() {
		size(1200,700);
	}
	
	@Override
	public void setup() {
		logica= new Logica(this);
	}
	
	@Override
	public void draw() {
		logica.pintar();
	}
	
	@Override
	public void mousePressed() {
		logica.cambiarPantallas();
	}
}
