package org.bermillion;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;

public class Hilo extends Thread
{
	//yoquese
	int puerto=0;
	ServerSocket servidorhilo=null;
	Socket clientehilo=null;
	ObjectInputStream ois=null;
	ObjectOutputStream oos=null;
	

	
	
	
	public Hilo(int puerto)
	{
		this.puerto=puerto;
	}
	
	
	
	public void run()
	{
		String data_req[]=new String[3];
		String data_resp[]=new String[3];
		
		// 1: tipo, 2: mensaje, 3: estado
		
		//abrimos un servidor con puerto dedicado
		try
		{
			servidorhilo=new ServerSocket(puerto);
			System.out.println("Escuchando en puerto --> "+puerto);
			
			//aceptamos la conexion
			try
			{
				clientehilo=servidorhilo.accept();
				if(clientehilo.isConnected())
				{
					try
					{					
						ois=new ObjectInputStream(clientehilo.getInputStream());
						oos=new ObjectOutputStream(clientehilo.getOutputStream());
							try
							{
								
								data_req=(String[])ois.readObject();
							}
							catch(Exception e)
							{
								System.out.println("Error al recibir el mensaje");
							}
							
							switch(data_req[0])
							{
								case "1":
									data_resp=Exist(data_req);
								case "2":
									data_resp=Registrar(data_req);
								default:
									break;
							}
							
							try
							{
								System.out.println(data_resp[2]);
								oos.writeObject(data_resp);
							}
							catch(Exception e)
							{
								System.out.println("Error al enviar el mensaje");
							}
							
							try
							{
								if(oos!=null){oos.close();}
								if(ois!=null){ois.close();}
							}
							catch(Exception e)
							{
								System.out.println("Error al cerrar las comunicaciones (Hilo)");
							}
					}
					catch(Exception e)
					{
						System.out.println("Error al crear oos y ois (Hilo)");
					}
					
					
				}
				else
				{
					System.out.println("No se logro la conexion");
				}
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
	
	
	/*Metodos de gestion de tipos*/
	
	public String[] Exist(String[] data)
	{
		String[] data_res=new String[3];
		if(Connections.ComprobarUsuario(data[1]))
		{
			data_res[0]="1";
			data_res[1]="";
			data_res[2]="true";
		}
		else
		{
			data_res[0]="1";
			data_res[1]="";
			data_res[2]="false";
		}
		return data_res;
	}
	
	public String[] Registrar(String[] data)
	{
		String[] data_res=new String[3];
		Connections.RegistrarUsuario(data[1]);
		return data_res;
	}
}
