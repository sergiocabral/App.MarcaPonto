package br.srv.cabral.marcapontolite.dados;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import br.srv.cabral.marcapontolite.util.Definicoes;

/* Manipulador do banco de dados. 
 */
public class Banco extends SQLiteOpenHelper {
	/*
	 * Instância do banco de dados.
	 */
	public static Banco INSTANCIA = null;

	/*
	 * Identificador da versão do banco de dados.
	 */
	public static final int VERSAO = 1;

	/*
	 * Identificador do banco de dados da aplicação.
	 */
	public static final String ARQUIVO = "dados.db";

	/*
	 * Construtor
	 */
	Banco(Context context) {
		super(context, Definicoes.APLICACAO_BANCO, null,
				Definicoes.APLICACAO_BANCO_VERSAO);
	}

	/*
	 * Disparado quando necessita criar o banco de dados. (non-Javadoc)
	 * 
	 * @see
	 * android.database.sqlite.SQLiteOpenHelper#onCreate(android.database.sqlite
	 * .SQLiteDatabase)
	 */
	@Override
	public void onCreate(SQLiteDatabase db) {
		CriarTabelaDefinicoes(db);
		CriarTabelaHorarios(db);
	}

	/*
	 * Disparado quando necessita atualizar o banco de dados. (non-Javadoc)
	 * 
	 * @see
	 * android.database.sqlite.SQLiteOpenHelper#onUpgrade(android.database.sqlite
	 * .SQLiteDatabase, int, int)
	 */
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		return;
	}

	/*
	 * Cria a tabela que armazena as definições.
	 */
	private void CriarTabelaDefinicoes(SQLiteDatabase db) {
		db.execSQL(String
				.format("CREATE TABLE %s (%s VARCHAR(50) PRIMARY KEY, %s VARCHAR(100));",
						DefinicoesColumns.INSTANCIA.getTABELA(),
						DefinicoesColumns.CAMPO_NOME,
						DefinicoesColumns.CAMPO_VALOR));
	}

	/*
	 * Cria a tabela que armazena os horarios.
	 */
	private void CriarTabelaHorarios(SQLiteDatabase db) {
		db.execSQL(String.format("CREATE TABLE %s ("
				+ "%s INTEGER PRIMARY KEY, " + "%s VARCHAR(10), "
				+ "%s NUMBER(7,2), " + "%s VARCHAR(5), " + "%s VARCHAR(5), "
				+ "%s VARCHAR(5), " + "%s VARCHAR(5), " + "%s VARCHAR(7), "
				+ "%s VARCHAR(100));", HorariosColumns.INSTANCIA.getTABELA(),
				HorariosColumns.CAMPO_ID, HorariosColumns.CAMPO_DATA,
				HorariosColumns.CAMPO_DATA_NUM, HorariosColumns.CAMPO_HORA1,
				HorariosColumns.CAMPO_HORA2, HorariosColumns.CAMPO_HORA3,
				HorariosColumns.CAMPO_HORA4, HorariosColumns.CAMPO_SALDO,
				HorariosColumns.CAMPO_EXTRA));
	}
}
