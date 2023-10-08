package com.example.demo.socket.archivos.ejemplo02;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class SocketServerRecibirArchivosArchivo {

	private final Integer PUERTO = 3456;

	private Socket cliente;

	public SocketServerRecibirArchivosArchivo() {
		System.out.println("FileServer: esperando peticiones TCP/IP");
		System.out.println("_______________________________________");

		try (ServerSocket servidor = new ServerSocket(PUERTO)) {
			while (true) {

				cliente = servidor.accept();

				// 1 RECIBE DEL NOMBRE DEL ARCHIVO
				// ====================================
				// Permite el envio de cualquier objeto
				ObjectInputStream ois = new ObjectInputStream(cliente.getInputStream());

				String fileName = (String)ois.readObject();
				System.out.println("fileName ==> " + fileName);
				
				// 2 RECIBE LOS BYTES DEL ARCHIVO
				// ====================================
				File fileDestino = new File("D:/server/"+fileName);
				FileOutputStream fos = new FileOutputStream(fileDestino);
				DataInputStream entrada = new DataInputStream(cliente.getInputStream());
				
				int byteLeidos;
				while( (byteLeidos = entrada.read()) != -1) {
					fos.write(byteLeidos);
				}
				
				fos.close();
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
