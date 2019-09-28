package br.srv.cabral.marcapontolite.dados;

import java.util.HashMap;

public class DefinicoesColumns extends ColumnsBase {
	public static final DefinicoesColumns INSTANCIA = new DefinicoesColumns();

	/*
	 * (non-Javadoc)
	 * 
	 * @see sjcsj.marcaponto_.dados.ColumnsBase#getPARAM_URI()
	 */
	@Override
	public String getPARAM_URI() {
		return "definicoes";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see sjcsj.marcaponto_.dados.ColumnsBase#getID()
	 */
	@Override
	public int getID() {
		return 1;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see sjcsj.marcaponto_.dados.ColumnsBase#getTABELA()
	 */
	@Override
	public String getTABELA() {
		return "TB_DEFINICAO";
	}

	private HashMap<String, String> campos = null;

	/*
	 * (non-Javadoc)
	 * 
	 * @see sjcsj.marcaponto_.dados.ColumnsBase#getCampos()
	 */
	@Override
	public HashMap<String, String> getCampos() {
		if (campos == null) {
			campos = new HashMap<String, String>();
			campos.put(CAMPO_NOME, CAMPO_NOME);
			campos.put(CAMPO_VALOR, CAMPO_VALOR);
		}
		return campos;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see sjcsj.marcaponto_.dados.ColumnsBase#getCAMPO_CHAVE_PRIMARIA()
	 */
	@Override
	public String getCAMPO_CHAVE_PRIMARIA() {
		return CAMPO_NOME;
	}

	/*
	 * Nome do campo: Nome da definição.
	 */
	public static final String CAMPO_NOME = "nome";

	/*
	 * Nome do campo: Valor da definição.
	 */
	public static final String CAMPO_VALOR = "valor";

}
