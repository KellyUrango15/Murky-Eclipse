package kellyTallerUno;

import java.net.InetAddress;

//Codigo ayudado en monitoria
public class GetIp extends Thread {

	byte ip[];
	Logica log;

	public GetIp(byte ip[], Logica log) {
		this.ip = ip;
		this.log = log;
	}

	public void run() {
		for (int i = 0; i < 255; i++) {
			final int j = i;
			try {
				ip[3] = (byte) j;

				InetAddress address = InetAddress.getByAddress(ip);
				String ipObject = address.toString().substring(1);

				if (address.isReachable(3000)) {
					log.generarJugador();
				}

				Thread.sleep(1000);

			} catch (Exception e) {
				System.out.println(e);
			}

		}
	}

}
