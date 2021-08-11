import java.io.IOException;
import java.net.Socket;
import javax.swing.JOptionPane;

public class TicTacToeClient {
	
	/**
	 * Main Method zum Erstellen einer Socketverbindung
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		//IP-Adresse und Port des Servers vom Benutzer erhalten
		String IPAdress= JOptionPane.showInputDialog("please inter IP_adress of server you want to connet with(127.0.0.1): ");
		int port= Integer.parseInt(JOptionPane.showInputDialog("please inter port of server you want to connet with(6066): "));
		//Verbindung zum server 
		Socket socket = new Socket( IPAdress, port);
        System.out.println("Connected!");
       }
}
