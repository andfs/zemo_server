package org.jboss.tools.example.forge.testeForge.model;

public enum EnumStatusVaga 
{
	DESOCUPADA,
	OCUPADA;

	public static EnumStatusVaga getEnum(int intValue) 
	{
		EnumStatusVaga result = null;
		switch (intValue) 
		{
			case 0:
				result = DESOCUPADA;
				break;
	
			default:
				result = OCUPADA;
				break;
		}
		return result;
	}
}
