package org.bermillion;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Hilo extends Thread
{
	//yoquese
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
		CMen_Object recibido=null,enviar=null;
		Object recibidotrad=null;
		
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
						ObjectOutputStream oos=new ObjectOutputStream(clientehilo.getOutputStream());
						ObjectInputStream ois=new ObjectInputStream(clientehilo.getInputStream());
						
							try
							{
								
								recibidotrad=ois.readObject();
							}
							catch(Exception e)
							{
								System.out.println("Error al recibir el mensaje");
							}
							
							switch(recibido.getType())
							{
								case 1:
									enviar=Exist(recibido);
								default:
									break;
							}
							
							try
							{
								
								oos.writeObject(enviar);
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
	
	public CMen_Object Exist(CMen_Object obj)
	{
		CMen_Object objresp;
		if(Connections.ComprobarUsuario(obj.getMes()))
		{
			objresp=new CMen_Object(true,"",1);
		}
		else
		{
			objresp=new CMen_Object(false,"",1);
		}
		return objresp;
	}
}
