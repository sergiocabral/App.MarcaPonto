/**
 * 
 */
package br.srv.cabral.marcaponto.persistencia;

import java.util.List;
import java.util.Map;

import android.database.sqlite.SQLiteDatabase;

/**
 * Interface para classe que representa as configurações de uma tabela do banco
 * de dados. Permite configurar (criar, alterar, etc) a tabela no banco de
 * dados.
 * 
 * @author Sergio Cabral (sergio@cabral.srv.br)
 */
public interface ITabelaDeBancoDeDados {
	/**
	 * Obtem o nome da tabela.
	 * 
	 * @return String
	 */
	String getNomeDaTabela();

	/**
	 * Coleção dos nomes dos campos e suas respectivas definições.
	 * 
	 * @return Dicionário de dados.
	 */
	Map<String, String> getCampos();

	/**
	 * Lista com os nomes dos campos. 
	 * @return Lista
	 */
	List<String> getNomeDosCampos();
	
	/**
	 * Disparado quando o banco de dados é criado (fisicamente falando) pela
	 * primeira vez.
	 */
	void onCreate(SQLiteDatabase db);

	/**
	 * Disparado quando o banco de dados precisa ser atualizado (fisicamente
	 * falando) pela primeira vez.
	 */
	void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion);
}
