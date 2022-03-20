package Server;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class NetworkTrokosServer {
	
    TrokosSkel tskel = null; 

    public ServerSocket connect(int port) {
        ServerSocket serverSocket = null;

        try {
            serverSocket = new ServerSocket(port);
        } catch(IOException e) {
            System.err.println(e.getMessage());
            System.exit(-1);
        }

        return serverSocket;
    }

    public void mainLoop(ServerSocket serverSocket) {
        System.out.println("Server is running in port " + serverSocket.getLocalPort());
        
        while(true) {
            
            try {
                Socket s = serverSocket.accept();
                ServerThread newServerThread = new ServerThread(s);
				tskel = new TrokosSkel(); // Criamos o Skeleton no inicio do programa para comecar a guardas as coisas no Hash e .Txt
                newServerThread.start();
            } catch(IOException e) {
                e.printStackTrace();
            }
        }
    }

    class ServerThread extends Thread {

    	private ObjectOutputStream outStream;
    	private ObjectInputStream inStream;
		private Socket socket = null;

		ServerThread(Socket s) throws IOException {
			socket = s;
			outStream = new ObjectOutputStream(socket.getOutputStream());
            inStream = new ObjectInputStream(socket.getInputStream());
		}

        public void run() {
            
            try {
            	boolean autenticacao = verificaAutenticacao();
                
				outStream.writeObject(autenticacao);
				
				//interpretar operacao a realizar
				if(autenticacao) {
					String op = (String) inStream.readObject();
					
					if(op.equals("balance")) {
	                    String idOfUser = (String) inStream.readObject();
						outStream.writeObject(tskel.getBalance(idOfUser));
	
					} else if (op.equals("makepayment")) {
	                    String clientID = (String) inStream.readObject();
	                    String amount = (String) inStream.readObject();
	                    tskel.makePayment(clientID, amount);
	                }
					//mandar para o skel para ver que operacao executar
					
					outStream.close();
	                inStream.close();

	                socket.close();
				}
					

            } catch (IOException | ClassNotFoundException e) {
                System.err.println(e.getMessage());
                System.exit(-1);
            }

        }
        
        public boolean verificaAutenticacao () throws ClassNotFoundException, IOException {
        	boolean autenticado = false;
        	String userID = (String)inStream.readObject();
			String password = (String)inStream.readObject();
			
			File users = new File("users.txt");
			users.createNewFile();
			Scanner sc = new Scanner(users);
			
			boolean clienteEncontrado = false;
			String[] userData = new String[2];
			
            while(sc.hasNextLine() && !clienteEncontrado) {
				String linha = sc.nextLine();
				userData = linha.split(":");
				if(userData[0].equals(userID)) {
					clienteEncontrado = true;
				}
			}

            if(!clienteEncontrado) {
                FileWriter fw = new FileWriter(users.getName(), true);
                BufferedWriter bw = new BufferedWriter(fw);
                bw.write(userID + ":" + password);
                bw.newLine();
                bw.close();
				
				autenticado = true;

                tskel.createNewClient(userID, password);
			} else {
				if(userData[1].equals(password)) {
					autenticado = true;
					tskel.initializeClient(userID);
				}
			}
            sc.close();
			return autenticado;
        }

    }

    public void disconnect(ServerSocket s) {
        try {
            s.close();
        } catch (IOException e) {
            System.err.println(e.getMessage());
            System.exit(-1);
        }
    }
}