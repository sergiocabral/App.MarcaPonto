/**
 * 
 */
package br.srv.cabral.marcaponto.persistencia;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import br.srv.cabral.marcaponto.suporte.IPreferencias;

/**
 * Armazena e manipula definições de preferências do usuário ou do aplicativo.
 * salvas no próprio sistema operacional.
 * 
 * @author Sergio Cabral (sergio@cabral.srv.br)
 */
public class PreferenciasSalvasNoSistema implements IPreferencias {

	/**
	 * Construtor
	 * 
	 * @param contexto
	 *            Contexto do aplicativo.
	 * @param identificador
	 *            Nome que identifica o conjunto das preferências garenciadas
	 *            pela instância desta classe.
	 */
	public PreferenciasSalvasNoSistema(Context contexto, String identificador) {
		Contexto = contexto;
		SharedPreferences = Contexto.getSharedPreferences(identificador,
				Context.MODE_PRIVATE);
	}

	/**
	 * Contexto do aplicativo.
	 */
	protected Context Contexto;

	/**
	 * Preferências compartilhadas
	 */
	protected SharedPreferences SharedPreferences;

	public void LimparTodas() {
		Editor edicao = SharedPreferences.edit();
		edicao.clear();
		edicao.commit();
	}
	
	public boolean Existe(String nome) {
		return SharedPreferences.contains(nome);
	}

	public String Ler(String nome, String valorPadrao) {
		return SharedPreferences.getString(nome, valorPadrao);
	}

	public void Salvar(String nome, String valor) {
		Editor edicao = SharedPreferences.edit();
		edicao.putString(nome, valor);
		edicao.commit();
	}

	public int Ler(String nome, int valorPadrao) {
		return SharedPreferences.getInt(nome, valorPadrao);
	}

	public void Salvar(String nome, int valor) {
		Editor edicao = SharedPreferences.edit();
		edicao.putInt(nome, valor);
		edicao.commit();
	}

	public float Ler(String nome, float valorPadrao) {
		return SharedPreferences.getFloat(nome, valorPadrao);
	}

	public void Salvar(String nome, float valor) {
		Editor edicao = SharedPreferences.edit();
		edicao.putFloat(nome, valor);
		edicao.commit();
	}

	public boolean Ler(String nome, boolean valorPadrao) {
		return SharedPreferences.getBoolean(nome, valorPadrao);
	}

	public void Salvar(String nome, boolean valor) {
		Editor edicao = SharedPreferences.edit();
		edicao.putBoolean(nome, valor);
		edicao.commit();
	}
}
