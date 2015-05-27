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
								for(int i=0;i==data_req.length;i++){
									System.out.println(data_req[i]);
								}
								
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
									data_respD=Case3(data_req);
									break;
								case "4":
									data_respD=Case4(data_req);
									break;
								case "5":
									data_respD=Case5(data_req);
									break;								
								case "6":
									data_respD=Case6(data_req);
									break;
								case "7":
									data_respD=Case7(data_req);
									break;
								case "8":
									data_respD=Case8(data_req);
									break;
								case "9":
									data_respD=Case9(data_req);
									break;
								case "10":
									data_respD=Case10(data_req);
									break;
								case "11":
									data_respD=Case11(data_req);
								case "12":
									data_respD=Case12(data_req);
									break;
								case "13":
									data_respD=Case13(data_req);
									break;
								case "14":
									data_respD=Case14(data_req);
									break;
								case "15":
									data_respD=Case15(data_req);
									break;
								case "16":
									data_respD=Case16(data_req);
									break;
								case "17":
									data_respD=Case17(data_req);
									break;
								case "18":
									data_respD=Case18(data_req);
									break;
								case "19":
									data_respD=Case19(data_req);
									break;
								case "20":
									data_respD=Case20(data_req);
									break;
								case "21":
									data_respD=Case21(data_req);
									break;
								case "22":
									data_respD=Case22(data_req);
									break;
								case "23":
									data_respD=Case23(data_req);
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
		Connections.RegistrarUsuario(data);
		
		data_res[0]="2";
		data_res[1]="";
		data_res[2]="true";
		
		return data_res;
	}
	private String[][] Case3(String[] data)
	{
		String[][] data_res= null;
		
		data_res = Connections.Login(data[1], data[2]);
		
		return data_res;
	}
	
	public String[][] Case4(String[] data)
	{
		String[][] data_res;
		Connections.InsertarMovimientoHogar(data);
		data_res=Connections.solicitarMovimientos(data);
		
		data_res[0][0]="5";
		data_res[0][1]=data[1];
		
		return data_res;
	}
	
	public String[][] Case5(String[] data)
	{
		String[][] data_res;
		data_res = Connections.solicitarMovimientos(data);
		
		return data_res;
	}
	
	public String[][] Case6(String[] data)
	{
		String[][] data_res;
		data_res = Connections.solicitarContactos(data);
		
		return data_res;
	}
	
	public String[][] Case7(String[] data)
	{
		String[][] data_res;
		data_res=Connections.solicitarUnMovimiento(data);
		
		return data_res;
	}
	
	public String[][] Case8(String[] data) {
		
		
		Connections.InsertarContacto(data);
		String[][] data_res = null;
		
		data[0] = "6";
		data_res = Connections.solicitarContactos(data);
		
		return data_res;
	}
	
	public String[][] Case9(String[] data)
	{
		String[][] data_res;
		data_res=Connections.solicitarUnMovimiento(data);
		
		return data_res;
	}
	
	public String[][] Case10(String[] data) {
		String[][] data_res;
		data_res = Connections.buscarContacto(data);
		
		return data_res;
		
	}
	
	public String[][] Case11(String[] data){
		
		String [][] data_respD;
		Connections.eliminarMovimiento(data);
		
		data[0] = "5";
		
		data_respD=Connections.solicitarMovimientos(data);
		
		return data_respD;
	}
	
	public String[][] Case12(String[] data){
		String[][] data_res;
		data_res=Connections.solicitarUnUsuario(data);
		
		return data_res;
	}
	
	public String[][] Case13(String[] data) {
		String[][] data_res;
		data_res = Connections.buscarMovimiento(data);
		
		System.out.println(data_res[0][0]);
		return data_res;
	}
	
	public String[][] Case14(String[] data){
		String[][] data_res;
		data_res = Connections.recogerDatosGrafica(data);
		return data_res;
	}
	
	public String[][] Case15(String[] data) {
		
		String[][] data_res;
		data_res = Connections.solicitarUnContacto(data);
		return data_res;
	}
	
	public String[][] Case16(String[] data) {
		
		Connections.actualizarContacto(data);
		String[][] data_res;
		
		data[0] = "6";
		data_res = Connections.solicitarContactos(data);
		
		return data_res;
	}
	
	public String[][] Case17(String[] data) {
		
		Connections.eliminarContacto(data);
		String[][] data_res;
		data[0] = "6";

		data_res = Connections.solicitarContactos(data);
		return data_res;
	}
	public String[][] Case18(String[] data){
		String[][] data_res;
			
		Connections.InsertarAviso(data);
		data_res = Connections.solicitarAvisos(data);
		data_res [0][0] = "16";
		return data_res;
		
	}
	
	public String[][] Case19(String[] data){
		
		String[][] data_res;
		data[0] ="16";
		data_res = Connections.solicitarAvisos(data);
		
		
		return data_res;
		
	}
	
	public String [][] Case20(String[] data){
		String [][] data_res;
		
		data_res = Connections.eliminarAviso(data);
		
		
		return data_res;
	}
	public String [][] Case21(String[] data){
		String [][] data_res;
		data_res = Connections.actualizarAviso(data);		
		return data_res;
	}
	
	public String [][] Case22(String[] data){
		String[][] data_res;
		data_res = Connections.solicitarUnAviso(data);
		return data_res;
	}
	
	public String [][] Case23(String[] data){
		String[][]data_res;
		data_res = Connections.buscarAviso(data);
		data_res[0][0]="16";
		return data_res;
				
	}
	
}
