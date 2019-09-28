/**
 * 
 */
package br.srv.cabral.marcaponto.persistencia;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;

/**
 * Classe ancestral de classes que configuram (criar, alterar, etc) uma tabela
 * no banco de dados.
 * 
 * @author Sergio Cabral (sergio@cabral.srv.br)
 */
public abstract class TabelaDeBancoDeDados implements ITabelaDeBancoDeDados,
		BaseColumns {

	public abstract String getNomeDaTabela();

	public abstract Map<String, String> getCampos();

	public List<String> getNomeDosCampos() {
		ArrayList<String> lista = new ArrayList<String>();
		for (String key : getCampos().keySet()) {
			lista.add(key);
		}
		return lista;
	}

	public abstract void onCreate(SQLiteDatabase db);

	public abstract void onUpgrade(SQLiteDatabase db, int oldVersion,
			int newVersion);

	/**
	 * Montar um código SQL para criar a tabela no banco de dados.
	 * 
	 * @return String
	 */
	protected String ObterSQLParaCriarTabela() {
		Map<String, String> campos = getCampos();
		StringBuilder sqlCampos = new StringBuilder();
		for (String key : campos.keySet()) {
			if (sqlCampos.length() > 0) {
				sqlCampos.append(", ");
			}
			sqlCampos.append(key + " " + campos.get(key));
		}
		return String.format("CREATE TABLE %s (%s);", getNomeDaTabela(),
				sqlCampos);
	}

	/**
	 * Monta um ContentValue com os valores informados
	 * 
	 * @param campos
	 *            Lista com os campos
	 * @param valores
	 *            Lista com os valores para os respectivos campos
	 * @return ContentValue montado, pronto para uso.
	 * @throws Exception
	 *             Ambos os parâmetros devem ter a mesma quantidade de itens.
	 */
	public ContentValues PreencherValores(int campos[], Object valores[]) {
		List<String> nomeDosCampos = getNomeDosCampos();
		ContentValues cv = new ContentValues();
		for (int i = 0; i < valores.length; i++) {
			cv.put(nomeDosCampos.get(campos[i]), String.valueOf(valores[i]));
		}
		return cv;
	}
}
