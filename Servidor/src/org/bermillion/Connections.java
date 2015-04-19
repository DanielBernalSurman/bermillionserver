package org.bermillion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class Connections 
{
	
	
	//obtenemos la conexion 
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
				System.out.println("Error al abrir conexion con base de datos (Connectiones)");
			}
		}
		catch(Exception e)
		{
			System.out.println("Error al cargar el driver (Connections)");
		}
		return conexion;
		
	}
	
	/*Ingresar nuevo usuario*/
	public static void RegistrarUsuario(String reg)
	{
		Connection conexion=getConnection();
		try
		{
			Statement cod=conexion.createStatement();
			ResultSet res=cod.executeQuery("select MAX(cod_usuario) from usuarios");
			res.next();
			int cod_u=res.getInt(1);
			
			reg="insert into usuarios values('"+(cod_u+1)+"',"+reg;
			
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
			resp.close();
			
			
		}
		catch(Exception e)
		{
			System.out.println("Error al comprobar usuario(Connections)");
			existe=true;
		}
		
		try
		{
			conexion.close();
		}
		catch(Exception e)
		{
			System.out.println("Error al cerrar la conexion (ComprobarUsuario/Connections)");
		}
		
		return existe;
	}
	public static boolean Login(String sentencia, String pass)
	{
		Connection conexion=getConnection();
		boolean isOk=false;
		//holahola
		try
		{
			Statement query=conexion.createStatement();
			ResultSet result=query.executeQuery(sentencia);
			
			result.next();
			
			String bdpass=result.getString(1);
			System.out.println(bdpass);
			System.out.println(pass);
			
			if(pass.equals(bdpass)){
				isOk=true;
			}
			else isOk=false;
			
			result.close();
			query.close();
		}
		catch(Exception e)
		{
			System.out.println("Error al Loguear (Login/Connections)"+e.getMessage().toString());
			isOk=false;
		}
		
		try
		{
			conexion.close();
		}
		catch(Exception e)
		{
			System.out.println("Error al cerrar la conexion (Login/Connections)");
		}
		
		return isOk;
	}
}
