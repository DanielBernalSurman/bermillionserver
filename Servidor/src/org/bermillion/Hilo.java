package org.bermillion;

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
	ObjectInputStream ois=null;
	ObjectOutputStream oos=null;
	

	
	
	
	public Hilo(int puerto)
	{
		this.puerto=puerto;
	}
	

	
	public void run()
	{
		String data_req[] = null;
		String data_resp[] = null;
		String data_respD[][] = null;
		
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
									data_resp=Case1(data_req);
									break;
								case "2":
									data_resp=Case2(data_req);
									break;
								case "3":
									data_resp=Case3(data_req);
									break;
								case "4":
									data_resp=Case4(data_req);
									break;
								case "5":
									data_respD=Case5(data_req);
									break;
								default:
									break;
							}
							
							try
							{
								if(data_resp != null) {
								oos.writeObject(data_resp);
								} else
								{
									oos.writeObject(data_respD);
								}
								
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
	
	public String[] Case1(String[] data)
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
	
	public String[] Case2(String[] data)
	{
		String[] data_res=new String[3];
		Connections.RegistrarUsuario(data[1]);
		
		data_res[0]="2";
		data_res[1]="";
		data_res[2]="true";
		
		return data_res;
	}
	private String[] Case3(String[] data)
	{
		String[] data_res=new String[3];
		
		data_res = Connections.Login(data[1], data[2]);
		
		return data_res;
	}
	
	public String[] Case4(String[] data)
	{
		String[] data_res= new String[3];
		Connections.InsertarMovimientoHogar(data);
		
		data_res[0]="4";
		data_res[1]="";
		data_res[2]="true";
		
		return data_res;
	}
	
	public String[][] Case5(String[] data)
	{
		String[][] data_res;
		data_res = Connections.solicitarMovimientos(data);
		
		return data_res;
	}
}
