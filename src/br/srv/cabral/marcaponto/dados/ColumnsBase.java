package br.srv.cabral.marcapontolite.dados;

import java.util.HashMap;

import android.net.Uri;
import android.provider.BaseColumns;
import br.srv.cabral.marcapontolite.util.Definicoes;

public abstract class ColumnsBase implements BaseColumns {
	/*
	 * Uri para as definições..
	 */
	public abstract String getPARAM_URI();

	/*
	 * Uri para as definições..
	 */
	public Uri getCONTENT_URI() {
		return Uri.parse("content://" + Definicoes.AUTHORITY + "/"
				+ getPARAM_URI());
	}

	/*
	 * Content Type
	 */
	public String getCONTENT_TYPE() {
		return "vnd.android.cursor.dir/" + Definicoes.AUTHORITY;
	}

	/*
	 * Id da Uri.
	 */
	public abstract int getID();

	/*
	 * Nome da tabela
	 */
	public abstract String getTABELA();

	/*
	 * Nome do campo usado como chave primária.
	 */
	public abstract String getCAMPO_CHAVE_PRIMARIA();

	/*
	 * Conjunto de campos
	 */
	public abstract HashMap<String, String> getCampos();
}
