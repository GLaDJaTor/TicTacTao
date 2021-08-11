import java.net.Socket;
import java.io.IOException;
import java.net.ServerSocket;
public class TicTacToeServer extends SocketKommunikation  {
	
	private static char[][] board = new char[3][3];
	private static char Player;
	
	public static void setBoard() {
		char ch = '1';
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				board[i][j] = ch++;
			}
		}
		displayBoard(board); //Tic-Tac-Toe-Board anzeigen
	}
	/**
	 * Eine Methode zum Abfragen des Spielfelds als serialisierter String, so dass es über
	 * die in Aufgabenteil (a) implementierte Methode versandt und empfangen werden kann.
	 * @param s
	 * @param player
	 * @return coordinat
	 * @throws NumberFormatException
	 * @throws IOException
	 */
	public static String[] socketQuery(Socket s, char player) throws NumberFormatException, IOException {
		String[] coordinat = {"0","0"}; 
		//Nachricht schiecken
		sendToSocket(s, "Enter a row and column (0, 1, or 2); for player " + player + ":");
		//Nachricht erhalten
		coordinat[0] = receiveFromSocket(s);
		coordinat[1] = receiveFromSocket(s);
		return coordinat;
	}
	/**
	 * Eine Methode zum Setzen eines Felds im Spielfeld. Neben der Position im Spielfeld soll diese Methode auch als Parameter übergeben bekommen,
	 * welcher Spieler das Feld setzen mochte (X oder O), sowie per Ruckgabewert anzeigen, ob das Setzen
	 * erfolgreich oder unzulässig (bspw. wenn das Feld bereits besetzt ist) war
	 * @param board
	 * @param coordinat
	 * @param player
	 * @return
	 */
	public static String setData(char[][] board, String[] coordinat, char player) {
		char Player =player ;
		int row= Integer.parseInt(coordinat [0]); 
		int column= Integer.parseInt(coordinat [1]); 
		
		// Falls einen falschen Raum gewählt wird gib fehlermeldung
		while (board[row][column] == 'X' || board[row][column] == 'O') {
			System.out.println("This spot is occupied. Please try again");
		}
		// Schreibe in den Raum und Wechsele den Spieler
		if (Player == 'O') {
			board[row][column] = Player;
			Player = 'X';
			return "you've succesfully set your spot";
			
		} else {
			board[row][column] = Player ;
			Player = 'O';
			return "you've succesfully set your spot";
			
		}
		
	}
	/**
	 * Spielfeld auf dem Console anzeigen
	 * @param board
	 */
	private static void displayBoard(char[][] board) {
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[i].length; j++) {
				if (j == board[i].length - 1)
					System.out.print(board[i][j]);
				else
					System.out.print(board[i][j] + " | ");
			}
			System.out.println();
		}

	}
	/**
	 * Bestimmt ob der Spieler gewonnen hat
	 * @param board
	 * @return True oder False 
	 */
	public static Boolean winner(char[][] board) {
		if ((board[0][0] == board[0][1] && board[0][0] == board[0][2])
				|| (board[0][0] == board[1][1] && board[0][0] == board[2][2])
				|| (board[0][0] == board[1][0] && board[0][0] == board[2][0])
				|| (board[2][0] == board[2][1] && board[2][0] == board[2][2])
				|| (board[2][0] == board[1][1] && board[0][0] == board[0][2])
				|| (board[0][2] == board[1][2] && board[0][2] == board[2][2])
				|| (board[0][1] == board[1][1] && board[0][1] == board[2][1])
				|| (board[1][0] == board[1][1] && board[1][0] == board[1][2])) {
			return true;
		}
		else
		{
			return false;
		}
	}
	/**
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException { 
		System.out.println("server is accessable trough IP-Adresse:127.0.0.1 and Prot 6066  ");
        ServerSocket ss = new ServerSocket(6066);
        Socket socket = ss.accept(); 
        //Spieler X Verbunden
        Thread playerX = new Thread() {
        	public void run() {
        		try {
					sendToSocket(socket, "You are as Player X connected");
					Player ='X';
				} catch (IOException e) {
					e.printStackTrace();
				}
        	}
        };
        playerX.start();
      //Spieler O Verbunden
        Thread playerO = new Thread() {
        	public void run() {
        		try {
					sendToSocket(socket, "You are as Player O connected");
					Player ='O';
				} catch (IOException e) {
					e.printStackTrace();
				}
        	}
        };
        playerO.start();
        
        //Starte das Spiel
        if(playerX.isAlive() & playerO.isAlive()) {
        	setBoard();
        	while (!winner(board) == true) {
        		String [] coordinat= socketQuery(socket, Player);
        		setData(board, coordinat, Player);
        		if (winner(board)) {
            		sendToSocket(socket, "Player " + Player + " is the winner!");
    			}
            	else if(winner(board)==false) {
            		sendToSocket(socket, "The game is a draw. Please try again.");
            	}
        	}
        }
        else {
        	ss.close();
            socket.close();
        }
    }

}
