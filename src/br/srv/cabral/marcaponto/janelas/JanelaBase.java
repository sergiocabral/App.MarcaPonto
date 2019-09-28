package br.srv.cabral.marcapontolite.janelas;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import br.srv.cabral.marcapontolite.R;

/* Classe base para todas as janelas (Activity). 
 */
public abstract class JanelaBase extends Activity {
	/*
	 * Called when the activity is first created.
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(getLayoutResource());
		CarregarControles();
		AtribuirEventos();
	}

	/*
	 * Exibe uma mensagem modal.
	 */
	protected void ExibirMensagem(String mensagem) {
		new AlertDialog.Builder(this).setTitle(R.string.JanelaPrincipal)
				.setMessage(mensagem)
				.setPositiveButton(android.R.string.ok, null).show();
	}

	/*
	 * Exibe uma mensagem modal.
	 */
	protected void ExibirMensagem(int mensagem) {
		new AlertDialog.Builder(this).setTitle(R.string.JanelaPrincipal)
				.setMessage(mensagem)
				.setPositiveButton(android.R.string.ok, null).show();
	}

	/*
	 * Referência para o recurso do layout da janela.
	 */
	protected abstract int getLayoutResource();

	/*
	 * Carrega os dados dos controles da tela.
	 */
	protected abstract void CarregarControles();

	/*
	 * Atribui os tratamentos dos eventos para os controles da tela.
	 */
	protected abstract void AtribuirEventos();

}
