package org.bermillion;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Hilo extends Thread
{
	int puerto=0;
	ServerSocket servidorhilo=null;
	Socket clientehilo=null;
	DataInputStream dis=null;
	DataOutputStream dos=null;
	
	public Hilo(int puerto)
	{
		this.puerto=puerto;
	}
	
	public void run()
	{
		//abrimos un servidor con puerto dedicado
		try
		{
			servidorhilo=new ServerSocket(puerto);
			System.out.println("Escuchando en puerto --> "+puerto);
			
			//aceptamos la conexion
			try
			{
				clientehilo=servidorhilo.accept();
				dis=new DataInputStream(clientehilo.getInputStream());
				String reg=dis.readUTF();
				Connections.NuevoUser(reg);
			}
			catch(Exception e)
			{
				System.out.println("Error al aceptar conexionHilo");
			}
			
		}
		catch(Exception e)
		{
			System.out.println("Error al crear el servidor en el hilo");
		}
		
		//cerramos la conexion y el servidor
		try
		{
			servidorhilo.close();
			clientehilo.close();
			
			MainServidor.puertos[puerto-30000][1]=0;
		}
		catch(Exception e)
		{
			System.out.println("Error al cerrar el hilo");
		}
	}
}
