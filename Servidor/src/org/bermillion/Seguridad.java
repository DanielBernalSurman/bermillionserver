package org.bermillion;

public class Seguridad {
	
	public static String Codificar(String cadena) {
		
		int sim=(int) (Math.random()*(1-5)+5);
		String cadenaCif =Integer.toString(sim);
		
		int j=0;
		for(int i=0;i<=cadena.length()*sim;++i)
		{
			char letra=cadena.charAt(j);
			String letrasim="";
			if(i%sim==0 && i!=0)
			{
			switch(letra)
			{
			case 'a':
				letra='g';
				break;
			case 'b':
				letra='s';
				break;
			case 'c':
				letra='k';
				break;
			case 'd':
				letra='y';
				break;
			case 'e':
				letra='z';
				break;
			case 'f':
				letra='q';
				break;
			case 'g':
				letra='o';
				break;
			case 'h':
				letra='ñ';
				break;
			case 'i':
				letra='m';
				break;
			case 'j':
				letra='u';
				break;
			case 'k':
				letra='t';
				break;
			case 'l':
				letra='w';
				break;
			case 'm':
				letra='x';
				break;
			case 'n':
				letra='h';
				break;
			case 'ñ':
				letra='i';
				break;
			case 'o':
				letra='r';
				break;
			case 'p':
				letra='v';
				break;
			case 'q':
				letra='p';
				break;
			case 'r':
				letra='a';
				break;
			case 's':
				letra='e';
				break;
			case 't':
				letra='b';
				break;
			case 'u':
				letra='f';
				break;
			case 'v':
				letra='c';
				break;
			case 'w':
				letra='j';
				break;
			case 'x':
				letra='d';
				break;
			case 'y':
				letra='$';
				break;
			case 'z':
				letra='l';
				break;
			case ' ':
				letra='n';
				break;	
			}
			++j;
			cadenaCif=cadenaCif+letra;
			}
			else
			{
				int cualq=(int)(Math.random()*(1-9)+9);
				letrasim=Integer.toString(cualq);
				cadenaCif=cadenaCif+letrasim;
			}
			
			
		}
		return cadenaCif;
	}
	
	public static String Decodificar (String cadenaCif) {
		
		char simc=cadenaCif.charAt(0);
		String simt=""+simc;
		int sim=Integer.parseInt(simt);
		String cadenaF=cadenaCif.substring(1);
		String cadenaDes="";
		
		for(int i=sim;i<cadenaF.length();++i)
		{
			
		
			char letra=cadenaF.charAt(i);
			if(i%sim==0)
			{
			switch(letra)
			{
			case 'g':
				letra='a';
				break;
			case 's':
				letra='b';
				break;
			case 'k':
				letra='c';
				break;
			case 'y':
				letra='d';
				break;
			case 'z':
				letra='e';
				break;
			case 'q':
				letra='f';
				break;
			case 'o':
				letra='g';
				break;
			case 'ñ':
				letra='h';
				break;
			case 'm':
				letra='i';
				break;
			case 'u':
				letra='j';
				break;
			case 't':
				letra='k';
				break;
			case 'w':
				letra='l';
				break;
			case 'x':
				letra='m';
				break;
			case 'h':
				letra='n';
				break;
			case 'i':
				letra='ñ';
				break;
			case 'r':
				letra='o';
				break;
			case 'v':
				letra='p';
				break;
			case 'p':
				letra='q';
				break;
			case 'a':
				letra='r';
				break;
			case 'e':
				letra='s';
				break;
			case 'b':
				letra='t';
				break;
			case 'f':
				letra='u';
				break;
			case 'c':
				letra='v';
				break;
			case 'j':
				letra='w';
				break;
			case 'd':
				letra='x';
				break;
			case '$':
				letra='y';
				break;
			case 'l':
				letra='z';
				break;
			case 'n':
				letra=' ';
				break;
			}
			cadenaDes=cadenaDes+letra;
			}
		}
		return cadenaDes;
	}
}
