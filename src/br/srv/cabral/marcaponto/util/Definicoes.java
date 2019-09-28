package br.srv.cabral.marcapontolite.util;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import br.srv.cabral.marcapontolite.dados.Banco;
import br.srv.cabral.marcapontolite.dados.DefinicoesColumns;

/* Agrupa valores de constantes ou defini��es salvas no banco de dados.
 */
public final class Definicoes {
	/*
	 * Construtor privado. Padr�o de projeto: Singleton.
	 */
	private Definicoes() {
	}

	/*
	 * Instancia.
	 */
	public static final Definicoes INSTANCIA = new Definicoes();

	/*
	 * Refer�ncia para uma janela quaquer.
	 */
	public Activity janelaBase = null;

	/*
	 * Identificado da aplica��o.
	 */
	public static final String APLICACAO_ID = "MPONTO";

	/*
	 * Identificado do banco de dados da aplica��o.
	 */
	public static final String APLICACAO_BANCO = Banco.ARQUIVO;

	/*
	 * Identificado da vers�o do banco de dados.
	 */
	public static final int APLICACAO_BANCO_VERSAO = Banco.VERSAO;

	/*
	 * Authority da aplica��o.
	 */
	public static final String AUTHORITY = "br.srv.cabral.marcapontolite";

	/*
	 * Nome da defini��o no banco de dados. Hor�rio de entrada ou sa�da.
	 */
	public static final String DEF_HORARIO = "DEF_HORARIO_";

	/*
	 * Nome da defini��o no banco de dados. Menor hor�rio de entrada permitido.
	 */
	public static final String DEF_HORA_MENOR_PERMITIDA = "DEF_HORA_MENOR_PERMITIDA";

	/*
	 * Nome da defini��o no banco de dados. Maior hor�rio de entrada permitido.
	 */
	public static final String DEF_HORA_MAIOR_PERMITIDA = "DEF_HORA_MAIOR_PERMITIDA";

	/*
	 * Nome da defini��o no banco de dados. M�nimo de horas destinadas ao
	 * almo�o.
	 */
	public static final String DEF_HORA_ALMOCO = "DEF_HORA_ALMOCO";

	/*
	 * Nome da defini��o no banco de dados. Total de horas da jornada de
	 * trabalho de um dia.
	 */
	public static final String DEF_HORA_JORNADA = "DEF_HORA_JORNADA";

	/*
	 * Nome da defini��o no banco de dados. Per�odo inicial do filtro.
	 */
	public static final String DEF_PERIODO_INICIAL = "DEF_PERIODO_INICIAL";

	/*
	 * Nome da defini��o no banco de dados. Per�odo final do filtro.
	 */
	public static final String DEF_PERIODO_FINAL = "DEF_PERIODO_FINAL";

	/*
	 * L� o valor de uma configura��o.
	 */
	public String Ler(String nome) {
		String valor = null;

		Cursor cursor = janelaBase.getContentResolver().query(
				DefinicoesColumns.INSTANCIA.getCONTENT_URI(),
				new String[] { DefinicoesColumns.CAMPO_VALOR },
				String.format("%s = ?", DefinicoesColumns.CAMPO_NOME),
				new String[] { nome }, null);
		if (cursor.getCount() > 0) {
			cursor.moveToFirst();
			valor = cursor.getString(0);
		}
		cursor.close();
		return valor;
	}

	/*
	 * Grava o valor de uma configura��o.
	 */
	public void Gravar(String nome, String valor) {
		if (Existe(nome)) {
			Atualizar(nome, valor);
		} else {
			Incluir(nome, valor);
		}
	}

	/*
	 * Verifica se uma defini��o existe.
	 */
	private boolean Existe(String nome) {
		return Ler(nome) != null;
	}

	/*
	 * Incluir um valor de uma configura��o.
	 */
	private void Incluir(String nome, String valor) {
		ContentValues values = new ContentValues();
		values.put(DefinicoesColumns.CAMPO_NOME, nome);
		values.put(DefinicoesColumns.CAMPO_VALOR, valor);
		janelaBase.getContentResolver().insert(
				DefinicoesColumns.INSTANCIA.getCONTENT_URI(), values);
	}

	/*
	 * Atualiza um valor de uma configura��o.
	 */
	private void Atualizar(String nome, String valor) {
		ContentValues values = new ContentValues();
		values.put(DefinicoesColumns.CAMPO_VALOR, valor);
		janelaBase.getContentResolver().update(
				DefinicoesColumns.INSTANCIA.getCONTENT_URI(), values,
				String.format("%s = ?", DefinicoesColumns.CAMPO_NOME),
				new String[] { nome });
	}

	/*
	 * Registrar os valores das definicoes padr�o caso n�o existam.
	 */
	public boolean RegistrarDefinicoesPadrao(boolean forcar) {
		String definicao = "DEF_DEFINIDAS";
		if (forcar || Ler(definicao) == null || Ler(definicao) == "Carregado") {
			Gravar(definicao, "def_20110508_1");
			Gravar(DEF_HORA_MENOR_PERMITIDA, "7");
			Gravar(DEF_HORA_MAIOR_PERMITIDA, "18");
			Gravar(DEF_HORA_ALMOCO, "1");
			Gravar(DEF_HORA_JORNADA, "8");
			Gravar(DEF_PERIODO_INICIAL, "");
			Gravar(DEF_PERIODO_FINAL, "");
			return true;
		}
		return false;
	}
}
