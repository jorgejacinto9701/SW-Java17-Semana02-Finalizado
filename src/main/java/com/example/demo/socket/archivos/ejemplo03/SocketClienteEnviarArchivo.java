package com.example.demo.socket.archivos.ejemplo03;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Iterator;

public class SocketClienteEnviarArchivo {

	private final String HOST = "localhost";
	private final Integer PUERTO = 3456;

	@SuppressWarnings("unused")
	public SocketClienteEnviarArchivo(String rutas) {
		System.out.println(rutas);

		try {

			Socket cliente = new Socket(HOST, PUERTO);

			// 1 ENVO DEL NOMBRE DEL ARCHIVO
			// ====================================
			// Permite el envio de cualquier objeto
			ObjectOutputStream ous = new ObjectOutputStream(cliente.getOutputStream());
			ObjectInputStream ois = new ObjectInputStream(cliente.getInputStream());
			
			//Las rutas se convierten en un arreglo
			String[] rutaArray = rutas.split(",");
			
			//Se obtiene la lista de nombres de los archivos separados por comas
			String nombreSeparadoPorComas = ""; 
			for (int i = 0; i < rutaArray.length; i++) {
				File fileInicio = new File(rutaArray[i]);
				String fileName = fileInicio.getName();
				System.out.println("fileName => " + fileName);
				nombreSeparadoPorComas += fileName ;
				if (i < rutaArray.length-1) nombreSeparadoPorComas += ",";
			}
			System.out.println("nombreSeparadoPorComas >> " + nombreSeparadoPorComas);
			
			//Envia el nombre al server
			ous.writeObject(nombreSeparadoPorComas);
			

			// 2 ENVIO DE LOS PAQUETES DE BYTES DEL ARCHIVO
			// ============================================
			DataOutputStream salida = new DataOutputStream(cliente.getOutputStream());
			
			for (int i = 0; i < rutaArray.length; i++) {
				File file = new File(rutaArray[i]);
				FileInputStream fis = new FileInputStream(file);
			
				int byteLeidos;
				while( (byteLeidos = fis.read()) != -1) {
					salida.write(byteLeidos);
				}
				fis.close();
			}
			salida.close();
			cliente.close();

		} catch (Exception e) {

			e.printStackTrace();
		}
	}

}
