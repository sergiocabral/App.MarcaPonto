package br.srv.cabral.marcaponto.persistencia;

import java.util.LinkedHashMap;
import java.util.Map;

import android.database.sqlite.SQLiteDatabase;

/**
 * Permite configurar (criar, alterar, etc) a tabela do banco de dados: Horários
 * 
 * @author Sergio Cabral (sergio@cabral.srv.br) *
 */
public class TabelaHorarios extends TabelaDeBancoDeDados {

	/**
	 * Índice do campo ID no resultado do método getCampos().
	 */
	public static final Integer CAMPO_ID = 0;

	/**
	 * Índice do campo MODO no resultado do método getCampos().
	 */
	public static final Integer CAMPO_MODO = 1;

	/**
	 * Índice do campo ANO no resultado do método getCampos().
	 */
	public static final Integer CAMPO_ANO = 2;

	/**
	 * Índice do campo MES no resultado do método getCampos().
	 */
	public static final Integer CAMPO_MES = 3;

	/**
	 * Índice do campo DIA no resultado do método getCampos().
	 */
	public static final Integer CAMPO_DIA = 4;

	/**
	 * Índice do campo HORA no resultado do método getCampos().
	 */
	public static final Integer CAMPO_HORA = 5;

	/**
	 * Índice do campo MINUTO no resultado do método getCampos().
	 */
	public static final Integer CAMPO_MINUTO = 6;

	/**
	 * Índice do campo SEGUNDO no resultado do método getCampos().
	 */
	public static final Integer CAMPO_SEGUNDO = 7;

	/**
	 * Índice do campo COMENTARIO no resultado do método getCampos().
	 */
	public static final Integer CAMPO_COMENTARIO = 8;

	/**
	 * Índice do campo COR no resultado do método getCampos().
	 */
	public static final Integer CAMPO_COR = 9;

	public String getNomeDaTabela() {
		return "horarios";
	}

	public Map<String, String> getCampos() {
		LinkedHashMap<String, String> campos = new LinkedHashMap<String, String>();
		campos.put("id", "INTEGER PRIMARY KEY");
		campos.put("modo", "INTEGER");
		campos.put("ano", "INTEGER");
		campos.put("mes", "INTEGER");
		campos.put("dia", "INTEGER");
		campos.put("hora", "INTEGER");
		campos.put("minuto", "INTEGER");
		campos.put("segundo", "INTEGER");
		campos.put("comentario", "TEXT");
		campos.put("cor", "INTEGER");
		return campos;
	}

	public void onCreate(SQLiteDatabase db) {
		db.execSQL(ObterSQLParaCriarTabela());
	}

	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		return;
	}
}
