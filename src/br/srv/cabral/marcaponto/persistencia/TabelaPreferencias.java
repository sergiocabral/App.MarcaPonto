package br.srv.cabral.marcaponto.persistencia;

import java.util.LinkedHashMap;
import java.util.Map;

import android.database.sqlite.SQLiteDatabase;

/**
 * Permite configurar (criar, alterar, etc) a tabela do banco de dados:
 * Preferencias
 * 
 * @author Sergio Cabral (sergio@cabral.srv.br) *
 */
public class TabelaPreferencias extends TabelaDeBancoDeDados {

	/**
	 * Índice do campo NOME no resultado do método getCampos().
	 */
	public static final Integer CAMPO_NOME = 0;

	/**
	 * Índice do campo VALOR no resultado do método getCampos().
	 */
	public static final Integer CAMPO_VALOR = 1;

	public String getNomeDaTabela() {
		return "preferencias";
	}

	public Map<String, String> getCampos() {
		LinkedHashMap<String, String> campos = new LinkedHashMap<String, String>();
		campos.put("nome", "TEXT PRIMARY KEY");
		campos.put("valor", "TEXT");
		return campos;
	}

	public void onCreate(SQLiteDatabase db) {
		db.execSQL(ObterSQLParaCriarTabela());
	}

	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		return;
	}
}
