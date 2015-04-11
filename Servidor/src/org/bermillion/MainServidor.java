package org.bermillion;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class MainServidor 
{
	static ServerSocket servidor=null;
	static Socket cliente=null;
	static int puertoauxiliar=29999, puertobase=30000;
	static int puertos[][];
	static DataInputStream dis=null;
	static DataOutputStream dos=null;
	
	public static void main (String[]arg)
	{
		try
		{
			//creamos e inicializamos un array en el que contendra un numero y un indicador de si esta ocupado o no
			//el numero de puerto se le sumara a puerto base, que es 30000, o sea, los puertos disponibles
			//van del rango 30 000 al 30 024
			puertos=new int[25][2];
			
			for(int i=0;i<25;++i)
			{
				puertos[i][0]=i;
				puertos[i][1]=0;
			}
			//creamos el servidor que estara siempre encendido con un bucle infinito
			servidor=new ServerSocket(puertoauxiliar);
			
			while(true)
			{
				System.out.println("Escuchando en el puerto -->"+puertoauxiliar);
				try
				{
					//aceptamos la peticion de cliente, y si esta conectado, ejecutamos la asignacion
					cliente=servidor.accept();
					if(cliente.isConnected())
					{
						System.out.println("Se detecto la ip --> "+cliente.getInetAddress().toString());
						
						//buscamos a ver que puertos estan libres
						boolean semaf=false;
						int iport=0;
						
						do
						{
							//si el puerto esta libre
							if(puertos[iport][1]==0)
							{
								try
								{
									//le mandamos el puerto nuevo
									dos=new DataOutputStream(cliente.getOutputStream());
									dos.writeInt(puertobase+puertos[iport][0]);
									
									//cremos el hilo que iniciara un servidor con el puerto nuevo
									Hilo hilo=new Hilo(puertobase+puertos[iport][0]);
									Thread th=new Thread(hilo);
									th.start();
									
									puertos[iport][1]=1;
								}
								catch(Exception e)
								{
									System.out.println("Error al enviar el puerto nuevo");
								}
								
								semaf=true;
							}
						++iport;
						}while(semaf==false);
					}
					
					//cerramos la conexion con el servidor auxiliar
					try
					{
						cliente.close();
					}
					catch(Exception e)
					{
						System.out.println("Error al cerrar la conexion auxiliar en el main del servidor");
					}
				}
				catch(Exception e)
				{
					System.out.println("Error al aceptaral cliente");
				}
			}
		}
		catch(Exception e)
		{
			System.out.println("Error al iniciar el servidor");
		}
		
	}

}