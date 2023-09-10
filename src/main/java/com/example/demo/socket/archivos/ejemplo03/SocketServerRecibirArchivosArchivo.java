package com.example.demo.socket.archivos.ejemplo03;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class SocketServerRecibirArchivosArchivo {

	private final Integer PUERTO = 3456;

	private Socket cliente;

	@SuppressWarnings("unused")
	public SocketServerRecibirArchivosArchivo() {
		System.out.println("FileServer: esperando peticiones TCP/IP");
		System.out.println("_______________________________________");

		try {
			ServerSocket servidor = new ServerSocket(PUERTO);
			while (true) {

				cliente = servidor.accept();

				// 1 RECIBE DEL NOMBRE DEL ARCHIVO
				// ====================================
				// Permite el envio de cualquier objeto
				ObjectOutputStream ous = new ObjectOutputStream(cliente.getOutputStream());
				ObjectInputStream ois = new ObjectInputStream(cliente.getInputStream());

				String nombreSeparadoPorComas = (String)ois.readObject();
				System.out.println("fileName ==> " + nombreSeparadoPorComas);
				
				
				// 2 RECIBE LOS BYTES DEL ARCHIVO
				// ====================================
				String[] nameArray = nombreSeparadoPorComas.split(",");
				DataInputStream entrada = new DataInputStream(cliente.getInputStream());
				
				for (int i = 0; i < nameArray.length; i++) {
					FileOutputStream fos = new FileOutputStream("D:/server/"+nameArray[i]);
				
					int byteLeidos;
					while( (byteLeidos = entrada.read()) != -1) {
						fos.write(byteLeidos);
					}
					
					fos.close();
					
				}
				entrada.close();
				cliente.close();
			}
		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		new SocketServerRecibirArchivosArchivo();
	}

}
