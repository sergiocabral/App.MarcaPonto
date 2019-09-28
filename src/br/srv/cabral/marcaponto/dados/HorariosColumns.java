package br.srv.cabral.marcapontolite.dados;

import java.util.HashMap;

public class HorariosColumns extends ColumnsBase {
	public static final HorariosColumns INSTANCIA = new HorariosColumns();

	/*
	 * (non-Javadoc)
	 * 
	 * @see sjcsj.marcaponto.dados.ColumnsBase#getPARAM_URI()
	 */
	@Override
	public String getPARAM_URI() {
		return "horarios";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see sjcsj.marcaponto.dados.ColumnsBase#getID()
	 */
	@Override
	public int getID() {
		return 2;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see sjcsj.marcaponto.dados.ColumnsBase#getTABELA()
	 */
	@Override
	public String getTABELA() {
		return "TB_HORARIO";
	}

	private HashMap<String, String> campos = null;

	/*
	 * (non-Javadoc)
	 * 
	 * @see sjcsj.marcaponto.dados.ColumnsBase#getCampos()
	 */
	@Override
	public HashMap<String, String> getCampos() {
		if (campos == null) {
			campos = new HashMap<String, String>();
			campos.put(CAMPO_ID, CAMPO_ID);
			campos.put(CAMPO_DATA, CAMPO_DATA);
			campos.put(CAMPO_DATA_NUM, CAMPO_DATA_NUM);
			campos.put(CAMPO_HORA1, CAMPO_HORA1);
			campos.put(CAMPO_HORA2, CAMPO_HORA2);
			campos.put(CAMPO_HORA3, CAMPO_HORA3);
			campos.put(CAMPO_HORA4, CAMPO_HORA4);
			campos.put(CAMPO_SALDO, CAMPO_SALDO);
			campos.put(CAMPO_EXTRA, CAMPO_EXTRA);
		}
		return campos;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see sjcsj.marcaponto.dados.ColumnsBase#getCAMPO_CHAVE_PRIMARIA()
	 */
	@Override
	public String getCAMPO_CHAVE_PRIMARIA() {
		return CAMPO_ID;
	}

	/*
	 * Nome do campo: Sequencial.
	 */
	public static final String CAMPO_ID = "_id";

	/*
	 * Nome do campo: Data
	 */
	public static final String CAMPO_DATA = "data";

	/*
	 * Nome do campo: Data
	 */
	public static final String CAMPO_DATA_NUM = "dataNum";

	/*
	 * Nome do campo: Horário 1
	 */
	public static final String CAMPO_HORA1 = "hara1";

	/*
	 * Nome do campo: Horário 2
	 */
	public static final String CAMPO_HORA2 = "hora2";

	/*
	 * Nome do campo: Horário 3
	 */
	public static final String CAMPO_HORA3 = "hora3";

	/*
	 * Nome do campo: Horário 4
	 */
	public static final String CAMPO_HORA4 = "hora4";

	/*
	 * Nome do campo: Saldo de horas
	 */
	public static final String CAMPO_SALDO = "saldo";

	/*
	 * Nome do campo: Horas trabalhadas.
	 */
	public static final String CAMPO_EXTRA = "extra";

}
