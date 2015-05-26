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
	public static void RegistrarUsuario(String[] data)
	{
		Connection conexion=getConnection();
		try
		{
			Statement cod=conexion.createStatement();
			ResultSet res=cod.executeQuery("select MAX(cod_usuario) from usuarios");
			res.next();
			int cod_u=res.getInt(1);
			data[6] = Seguridad.Codificar(data[6]);
			String reg="insert into usuarios values('"+(cod_u+1)+"','" +data[1]+ "','" +data[2]+ "','" +data[3]+ "','" +data[4]+ "',"+data[5]+",'" +data[6]+"',"+data[7]+ ")";

			
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
		String[] data_res = new String[4];
		try
		{
			Statement query=conexion.createStatement();
			ResultSet result=query.executeQuery("select password,cod_usuario,tipo from usuarios where nombre_usuario='"+user+"'");
			
			result.next();
			
			String bdpass=result.getString(1);
			String cod_user=result.getString(2);
			String tipo_user=result.getString(3);
			
			bdpass=Seguridad.Decodificar(bdpass);
			
			if(pass.equals(bdpass))
			{
				data_res[0]="3";
				data_res[1]="true";
				data_res[2]=cod_user;
				data_res[3]=tipo_user;
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
		String sentencia;
		System.out.print(data[2]);
		System.out.print(data[1]);
			if((Integer.parseInt(data[3])>0)){
				sentencia=("update gastos set "+data[2]+"where cod_gasto="+data[3]);
			
			}

			else{
			 
			Statement cod=conexion.createStatement();
			ResultSet res=cod.executeQuery("select MAX(cod_gasto) from gastos");

			res.next();
			int cod_u=res.getInt(1);
			System.out.print(cod_u);
			 sentencia=("insert into gastos values("+(cod_u+1)+","+data[2]);
			res.close();
			}
			Statement orden=conexion.createStatement();
			orden.execute(sentencia);
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
			System.out.println(data[1]);
			
			res.next();
			if (res.getRow()==0) {
				data_res= new String[1][2];
				data_res[0][0]=data[0];
				data_res[0][1]="No se encontró ningún contacto.";
			} else {
				
				res.beforeFirst();
				data_res = Funciones.MostrarRes(res, data[0]);
			}	
						
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage().toString());
		}
		
		return data_res;
	}

	public static String[][] solicitarUnMovimiento (String[] data){
		
		Connection conexion=getConnection();
		String[][] data_res=null;
		try
		{
			Statement query=conexion.createStatement();
			ResultSet res=query.executeQuery("select * from gastos where cod_gasto="+data[1]);
			data_res=Funciones.MostrarRes(res, data[0]);
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage().toString());
		}
		return data_res;
	}

	
	public static String[][] solicitarContactos (String[] data) {
		
		Connection conexion = getConnection();
		String[][] data_res = null;		
		try
		{
			Statement query = conexion.createStatement();
			ResultSet res = query.executeQuery("select * from contactos where usuarios_cod_usuario="+data[1]);
			
			res.next();
			if (res.getRow()==0) {
				data_res= new String[1][2];
				data_res[0][0]=data[0];
				data_res[0][1]="No se encontró ningún contacto.";
			} else {
				
				res.beforeFirst();
				data_res = Funciones.MostrarRes(res, data[0]);		 
			}	
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage().toString());
		}

		return data_res;
	}
	
	public static void InsertarContacto (String[] data) {
		
		Connection conexion=getConnection();
		
		try {
			Statement cod=conexion.createStatement();
			ResultSet res=cod.executeQuery("select MAX(contacto_id) from contactos");

			res.next();
			int cod_u=res.getInt(1);
			String sentencia=("insert into contactos values("+(cod_u+1)+","+Integer.parseInt(data[1])+",'"+data[2]+"','"+data[3]+"','"+data[4]+"')");
			
			Statement orden=conexion.createStatement();
			orden.execute(sentencia);

			res.close();
		}
		catch(Exception e) {
			System.out.println("Error al insertar contacto (InsertarContacto/Connections)"+e.getMessage().toString());
		}
		
		try{
			
			
			conexion.close();
		} catch(Exception e) {
			System.out.println("Error insertar contacto");
		}
	}
	
	public static String[][] buscarContacto(String[] data) {
		Connection conexion = getConnection();
		String[][] data_res = null;
		try {
			
			Statement sta = conexion.createStatement();
			ResultSet res = sta.executeQuery("select * from contactos where nombre like '%"+data[1]+"%' or"
					+ " apellido like '%"+data[1]+"%' or telefono like '%"+data[1]+"%'");
			
			res.next();
			if (res.getRow()==0) {
				data_res= new String[1][2];
				data_res[0][0]=data[0];
				data_res[0][1]="No se encontró ningún contacto.";
			} else {
				
				res.beforeFirst();
				data_res = Funciones.MostrarRes(res, data[0]);		 
			}	
			
					
		} catch (Exception e) {
			
			System.out.println("Error al buscar contacto (buscarContacto/COnnections");
		}

		return data_res;
	}
	public static void eliminarMovimiento (String[] data){
		
		Connection conexion=getConnection();
		
		try
		{
			Statement query=conexion.createStatement();
			query.executeUpdate("delete from gastos where cod_gasto="+data[2]);
			System.out.println("Movimiento con código "+data[2]+" eliminado correctamente");
	
		}
		catch(Exception e)
		{
			System.out.println("Error al borrar"+e.getMessage().toString());
		}
		
	}
public static String[][] solicitarUnUsuario (String[] data){
		
		Connection conexion=getConnection();
		String[][] data_res=null;
		try
		{
			Statement query=conexion.createStatement();
			ResultSet res=query.executeQuery("select * from usuarios where cod_usuario="+data[1]);
			data_res=Funciones.MostrarRes(res, data[0]);
			for(int i=0; i<8;i++){
				System.out.println(data_res[1][i]);
			}
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage().toString());
		}
		return data_res;
	}
		
	public static String[][] buscarMovimiento(String[] data) {
		Connection conexion = getConnection();
		String[][] data_res = null;
		try {
			
			Statement sta = conexion.createStatement();
			ResultSet res = sta.executeQuery("select * from gastos where descripcion like '%"+data[1]+"%' or"
					+ " fecha like '%"+data[1]+"%' or importe like '%"+data[1]+"%' or concepto like '%"+data[1]+"%'");
			
			res.next();
			if (res.getRow()==0) {
				data_res= new String[1][2];
				data_res[0][0]="5";
				data_res[0][1]="No se encontró ningún movimiento.";
			} else {
				
				res.beforeFirst();
				data_res = Funciones.MostrarRes(res, "5");		 
			}	
			
					
		} catch (Exception e) {
			
			System.out.println("Error al buscar movimiento (buscarMovimiento/COnnections");
		}
	
		return data_res;
	}
	
	public static String[][] recogerDatosGrafica(String[] data) {
		Connection conexion=getConnection();
		String[][] data_res=null;
		try{
			String sentencia="Select * from saldos where usuarios_cod_usuario="+data[1];
			Statement orden = conexion.createStatement();
			ResultSet res=orden.executeQuery(sentencia);
			res.next();
			if (res.getRow()==0){
				data_res= new String [1][2];
				data_res[0][0]="11";
				data_res[0][1]="No se encontraron Datos";
			}else{
			
				data_res=Funciones.MostrarRes(res, "13");
				for(int i=0;i<3;i++){
					System.out.println(data_res[1][i]);
				}
			}
		}
		catch(Exception e){
			System.out.println("Error al buscar saldos (recoger datos grafica/connections"+e.getMessage());
			
		}
		return data_res;
	}
	
public static String[][] solicitarUnContacto(String[] data) {
		
		Connection conexion=getConnection();
		String[][] data_res=null;
		try
		{
			Statement query=conexion.createStatement();
			ResultSet res=query.executeQuery("select * from contactos where contacto_id="+data[1]);
			data_res=Funciones.MostrarRes(res, data[0]);
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage().toString());
		}
		return data_res;
	}
	
	public static void actualizarContacto(String[] data) {
		
Connection conexion=getConnection();
		
		try {
			String sentencia=("update contactos set nombre='"+data[2]+"', apellido='"+data[3]+"', telefono='"+data[4]+"' where contacto_id="+data[5]);
			
			Statement orden=conexion.createStatement();
			orden.execute(sentencia);

		}
		catch(Exception e) {
			System.out.println("Error al insertar contacto (InsertarContacto/Connections)"+e.getMessage().toString());
		}
		
		try{
			
			
			conexion.close();
		} catch(Exception e) {
			System.out.println("Error insertar contacto");
		}
	}
	
	public static void eliminarContacto(String[] data) {
		
		Connection conexion=getConnection();
				
				try {
					String sentencia=("delete from contactos where contacto_id="+Integer.parseInt(data[2]));
					
					Statement orden=conexion.createStatement();
					orden.execute(sentencia);

				}
				catch(Exception e) {
					System.out.println("Error al borrar contacto (eliminarContacto/Connections)"+e.getMessage().toString());
				}
				
				try{
					
					
					conexion.close();
				} catch(Exception e) {
					System.out.println("Error borrar contacto");
				}
			}
	public static void InsertarAviso(String[] data){
		Connection conexion=getConnection();
		try {
			Statement cod=conexion.createStatement();
			

			
			String sentencia=("insert into avisos set "+data[1]);
			System.out.println(sentencia);
			
			Statement orden=conexion.createStatement();
			orden.execute(sentencia);

			
		}
		catch(Exception e) {
			System.out.println("Error al insertar aviso (InsertarAviso/Connections)"+e.getMessage().toString());
		}
		
		try{
			
			
			conexion.close();
		} catch(Exception e) {
			System.out.println("Error insertar contacto");
		}
	}
	
	public static String[][] solicitarAvisos(String[] data){
		Connection conexion = getConnection();
		String[][] data_res = null;		
		try
		{
			Statement query = conexion.createStatement();
			ResultSet res = query.executeQuery("select idavisos, usuarios_cod_usuario, nombre, descripcion, DATE_FORMAT(fecha,'%d/%m/%Y') AS fechaok from avisos where usuarios_cod_usuario="+data[2]+" order by fecha");
			
			res.next();
			if (res.getRow()==0) {
				data_res= new String[1][2];
				data_res[0][0]=data[0];
				data_res[0][1]="No se encontró ningún aviso.";
			} else {
				
				res.beforeFirst();
				data_res = Funciones.MostrarRes(res, data[0]);		 
			}	
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage().toString());
		}

		return data_res;
		
	}
public static String[][] eliminarAviso (String[] data){
		
		Connection conexion=getConnection();
		String [][] data_respD = null;
		try
		{
			Statement query=conexion.createStatement();
			query.executeUpdate("delete from avisos where idavisos="+data[2]);
			System.out.println("Aviso con código "+data[2]+" eliminado correctamente");
			
			data[0] = "19";
			data_respD=solicitarAvisos(data);
			
			
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage().toString());
		}
		return data_respD;
	}
public static String[][] actualizarAviso(String[] data) {
	
	String[][] data_respD=null;
	Connection conexion=getConnection();
		/*borrar esta linea*/
		try {
			String sentencia=("update avisos set "+data[1]+" where idavisos="+data[2]);
			
			Statement orden=conexion.createStatement();
			orden.execute(sentencia);
			
			data_respD=solicitarAvisos(data);

		}
		catch(Exception e) {
			System.out.println("Error al insertar contacto (InsertarContacto/Connections)"+e.getMessage().toString());
		}
		
		try{
			
			
			conexion.close();
		} catch(Exception e) {
			System.out.println("Error insertar contacto");
		}
		return data_respD;
	}

public static String[][] solicitarUnAviso (String[] data){
	
	Connection conexion=getConnection();
	String[][] data_res=null;
	try
	{
		Statement query=conexion.createStatement();
		ResultSet res=query.executeQuery("select * from avisos where idavisos="+data[1]);
		data_res=Funciones.MostrarRes(res, data[0]);
		data_res[0][0] = "17";
	}
	catch(Exception e)
	{
		System.out.println(e.getMessage().toString());
	}
	return data_res;
}

public static String[][] buscarAviso(String[] data) {
	Connection conexion = getConnection();
	String[][] data_res = null;
	try {
		
		Statement sta = conexion.createStatement();
		ResultSet res = sta.executeQuery("select * from avisos where nombre like '%"+data[1]+"%' or"
				+ " descripcion like '%"+data[1]+"%'");
		
		res.next();
		if (res.getRow()==0) {
			data_res= new String[1][2];
			data_res[0][0]=data[0];
			data_res[0][1]="No se encontró ningún aviso.";
		} else {
			
			res.beforeFirst();
			data_res = Funciones.MostrarRes(res, data[0]);		 
		}	
		
				
	} catch (Exception e) {
		
		System.out.println("Error al buscar Aviso (buscar/AvisoCOnnections");
	}

	return data_res;
}
	
	

}
