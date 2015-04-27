package org.bermillion;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Funciones {
	
	public static String[][] MostrarRes(ResultSet res, String type)
	{
		String[][] data=null;
		try 
		{
			res.last();
			int rows=res.getRow();
			int columns=res.getMetaData().getColumnCount();
			res.beforeFirst();
			
			if(rows!=0) {
				
				try
				{
					data=new String[rows+1][];
					data[0]=new String[2];
					data[0][0]=type;
					for(int i=1;i<=rows;++i)
					{
						data[i]=new String[columns];
					}
					
					int j=1;
					while(res.next())
					{
						for(int i=0;i<columns;++i)
						{
							data[j][i]=res.getString(i+1);
						}
						++j;
					}
				}catch(Exception e)
				{System.out.println("Error al ResultSet->String[][]");
				}
			}
			else
			{
				data= null;
			}
		} 
		catch (SQLException e1) 
		{
			System.out.println(e1.getMessage().toString());
		}
		//System.out.println(data[0].length);
		return data;
	}
}
