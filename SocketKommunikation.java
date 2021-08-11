import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.OutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.net.ServerSocket;
import java.net.UnknownHostException;
import java.util.Scanner;
/*
 * Entwickeln Sie eine Klasse zur Abstraktion der Socket-Kommunikation (aus java.net.Socket), die
 *  die folgenden statischen Methoden enthalt 
 */
public class SocketKommunikation {
	
	/**
	 * Eine Funktion zum Senden der Daten über Socket
	 * @param s
	 * @param Data
	 * @throws UnknownHostException
	 * @throws IOException
	 */
	public static void sendToSocket(Socket s, String Data) throws UnknownHostException, IOException {
		
		OutputStream outputStream = s.getOutputStream();// Erstellen eines Datenausgabestream
		DataOutputStream dataOutputStream = new DataOutputStream(outputStream); 
		dataOutputStream.writeUTF(Data); // Schreiben der Nachricht
		dataOutputStream.flush(); //Nachricht schiecken

	}
	/**
	 * Eine Funktion zum Erhalten der Daten über Socket
	 * @param s
	 * @return message
	 * @throws IOException
	 */
	public static String receiveFromSocket(Socket s) throws IOException {

		InputStream inputStream = s.getInputStream();// Erstellen eines Dateneingabestream
		DataInputStream dataInputStream = new DataInputStream(inputStream);
		String message = dataInputStream.readUTF(); //Lesen von Socket
		if (message != "") {
			s.close(); //Schließen des sockets falls es keine Nachrichten gibt
		}
		return message;
	}

}