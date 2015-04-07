package org.bermillion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class Connections 
{
	private static Connection getConnection()
	{
		Connection conexion=null;
		try
		{
			Class.forName("com.mysql.jdbc.Driver");
			
			try
			{
				conexion=DriverManager.getConnection("jdbc:mysql://localhost/bermillion","root","");
			}
			catch(Exception e)
			{
				System.out.println("Error al abrir conexion con base de datos (Connections)");
			}
		}
		catch(Exception e)
		{
			System.out.println("Error al cargar el driver (Connections)");
		}
		
		return conexion;
	}
	
	/*Ingresar nuevo usuario*/
	public static void NuevoUser(String reg)
	{
		Connection conexion=getConnection();
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
		try
		{
			conexion.close();
		}
		catch(Exception e)
		{
			System.out.println("Error al cerrar la conexion (Connections)");
		}
	}
	
	/*Comprpbar si existe nombre_usuario*/
	public static boolean ComprobarUsuario(String sentencia)
	{
		Connection conexion=getConnection();
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
		}
		catch(Exception e)
		{
			System.out.println("Error al insertar nuevo usuario(Connections)");
			existe=true;
		}
		try
		{
			conexion.close();
		}
		catch(Exception e)
		{
			System.out.println("Error al cerrar la conexion (Connections)");
			existe=true;
		}
		
		return existe;
	}
}
