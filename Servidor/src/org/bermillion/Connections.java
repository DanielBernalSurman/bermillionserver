package org.bermillion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class Connections 
{
	
	/*Ingresar nuevo usuario*/
	public static void RegistrarUsuario(String reg)
	{
		Connection conexion=Hilo.conexion;
		try
		{
			Statement orden=conexion.createStatement();
			orden.execute(reg);
			
			orden.close();
		}
		catch(Exception e)
		{
			System.out.println("Error al insertar nuevo usuario(Connections)");
		}
	}
	
	/*Comprpbar si existe nombre_usuario*/
	public static boolean ComprobarUsuario(String sentencia)
	{
		Connection conexion=Hilo.conexion;
		boolean existe;
		try
		{
			Statement orden=conexion.createStatement();
			ResultSet resp=orden.executeQuery(sentencia);
			
			resp.beforeFirst();
			resp.next();
			
			if(resp.getRow()==1)
			{
				existe=false;
			}
			else existe=true;
			
			
			orden.close();
			resp.close();
		}
		catch(Exception e)
		{
			System.out.println("Error al insertar nuevo usuario(Connections)");
			existe=true;
		}
		
		return existe;
	}
}
