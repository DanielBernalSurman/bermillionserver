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
	public static String[] Login(String user, String pass)
	{
		Connection conexion=getConnection();
		String[] data_res = new String[3];
		try
		{
			Statement query=conexion.createStatement();
			ResultSet result=query.executeQuery("select password,cod_usuario from usuarios where nombre_usuario='"+user+"'");
			
			result.next();
			
			String bdpass=result.getString(1);
			String cod_user=result.getString(2);
			
			if(pass.equals(bdpass))
			{
				data_res[0]="3";
				data_res[1]="true";
				data_res[2]=cod_user.toString();
			}
			else 
			{
				data_res[0]="3";
				data_res[1]="false";
			}
			
			result.close();
			query.close();
		}
		catch(Exception e)
		{
			System.out.println("Error al Loguear (Login/Connections)"+e.getMessage().toString());
		}
		
		try
		{
			conexion.close();
		}
		catch(Exception e)
		{
			System.out.println("Error al cerrar la conexion (Login/Connections)");
		}
		
		return data_res;
	}
	
	public static void InsertarMovimientoHogar(String[] data) {
		Connection conexion=getConnection();
		
		try {
			Statement cod=conexion.createStatement();
			ResultSet res=cod.executeQuery("select MAX(cod_gasto) from gastos");

			res.next();
			int cod_u=res.getInt(1);
			String sentencia=("insert into gastos values("+(cod_u+1)+","+data[1]);
			
			Statement orden=conexion.createStatement();
			orden.execute(sentencia);

			res.close();
		}
		catch(Exception e) {
			System.out.println("Error al insertar movimiento (InsertarMovimientoHogar/Connections)"+e.getMessage().toString());
		}
		
		try{
			
			
			conexion.close();
		} catch(Exception e) {
			System.out.println("Error al cerrar conexion BBDD");
		}
		
	}
	
	public static String[][] solicitarMovimientos (String[] data) {
		
		Connection conexion = getConnection();
		String[][] data_res = null;		
		try
		{
			Statement query = conexion.createStatement();
			ResultSet res = query.executeQuery("select * from gastos where usuarios_cod_usuario="+data[1]);
			

			data_res = Funciones.MostrarRes(res, data[0]);			
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage().toString());
		}
		
		return data_res;
	}
}
