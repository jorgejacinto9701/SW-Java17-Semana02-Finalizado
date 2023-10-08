package com.example.demo.socket.archivos.ejemplo03;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.net.Socket;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class SocketClienteEnviarArchivo {

	private final String HOST = "localhost";
	private final Integer PUERTO = 3456;

	public SocketClienteEnviarArchivo(String rutas) {
		System.out.println(rutas);
		try {
			Socket cliente = new Socket(HOST, PUERTO);
			// 1 CREAR EL ZIP
			// ============================================
			ZipOutputStream zipOut = new ZipOutputStream(new FileOutputStream("D:/cliente/consolidado.zip"));
			
			// Agregar archivo al archivo ZIP
			String[] rutasArray = rutas.split(",");
			for (int i = 0; i < rutasArray.length; i++) {
				System.out.println(">> " + (i+1) + " >> " + rutasArray[i]);
				
				 //Crea el archivo 
				 File archivoParaComprimir = new File(rutasArray[i]);
				 FileInputStream fis = new FileInputStream(archivoParaComprimir);
				 
				 //Crea la entrada
				 ZipEntry zipEntry = new ZipEntry(archivoParaComprimir.getName());
				 zipOut.putNextEntry(zipEntry);
				 
				 //Agregar lo bytes
				 int bytes;
				 while ((bytes = fis.read()) != -1) {
				        zipOut.write(bytes);
				 }
				 fis.close();
				 zipOut.closeEntry();
			}
			zipOut.close();		

			// 2 ENVIAR EL ZIP
			// ============================================		
			File file = new File("D:/cliente/consolidado.zip");
			FileInputStream fis = new FileInputStream(file);
			DataOutputStream salida = new DataOutputStream(cliente.getOutputStream());

			int bytesLeidos;
			while ((bytesLeidos = fis.read()) != -1) {
				 salida.write(bytesLeidos);
			}
			 
			fis.close();
			salida.close();
			cliente.close();

		} catch (Exception e) {

			e.printStackTrace();
		}
	}

}
