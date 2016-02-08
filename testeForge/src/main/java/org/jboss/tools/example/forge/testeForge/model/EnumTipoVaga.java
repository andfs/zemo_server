package org.jboss.tools.example.forge.testeForge.model;

public enum EnumTipoVaga 
{
	LIVRE {
		@Override
		public String getInfo() {
			return "Vaga livre, pode parar a vontade";
		}
	},
	FAIXA_AZUL {
		@Override
		public String getInfo() {
			return "Faixa Azul. Compre o seu faixa azul durante e se atente ao horário. Apenas 2h!";
		}
	},
	FAIXA_VERMELHA {
		@Override
		public String getInfo() {
			return "Faixa Vermelha. Compre o seu faixa vermelha e se atente ao horário. Apenas 1h!";
		}
	},
	PISCA_ALERA_LIGADO {
		@Override
		public String getInfo() {
			return "Você pode estacionar durante 10 min com o pisca alerta ligado";
		}
	},
	DEFICIENTE {
		@Override
		public String getInfo() {
			return "Vaga especial para grávidas ou deficientes físicos";
		}
	};
	
	public abstract String getInfo();

	public static EnumTipoVaga getEnum(int intValue) 
	{
		EnumTipoVaga result = null;
		switch (intValue) 
		{
			case 0:
				result = LIVRE;
				break;
			
			case 1:
				result = FAIXA_AZUL;
				break;
	
			case 2:
				result = FAIXA_VERMELHA;
				break;
	
			case 3:
				result = PISCA_ALERA_LIGADO;
				break;
	
			
			default:
				result = DEFICIENTE;
				break;
		}
		
		return result;
	}
}
