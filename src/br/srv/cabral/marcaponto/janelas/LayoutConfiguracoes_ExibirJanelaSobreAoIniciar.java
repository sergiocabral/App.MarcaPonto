/**
 * 
 */
package br.srv.cabral.marcaponto.janelas;

import android.widget.CheckBox;
import br.srv.cabral.marcaponto.R;

/**
 * Classe que implementa a seção ExibirJanelaSobreAoIniciar da tela
 * LayoutConfiguracoes.
 * 
 * @author Sergio Cabral (sergio@cabral.srv.br)
 */
public class LayoutConfiguracoes_ExibirJanelaSobreAoIniciar implements
		ILayoutConfiguracoes_Secao {

	/**
	 * Construtor
	 * 
	 * @param layoutDeJanela
	 *            Layout da janela.
	 */
	public LayoutConfiguracoes_ExibirJanelaSobreAoIniciar(
			JanelaGenerica janelaBase) {
		JanelaBase = janelaBase;
	}

	/**
	 * Janela
	 */
	protected JanelaGenerica JanelaBase;

	public void ConfigurarControles() {
		CheckBox chkExibirJanelaSobreAoIniciar = ((CheckBox) JanelaBase
				.findViewById(R.id.chkExibirJanelaSobreAoIniciar));
		chkExibirJanelaSobreAoIniciar.setChecked(!JanelaBase.Preferencias
				.getIgnorarJanelaSobreAoIniciar());
	}

	public boolean CamposPreenchidosCorretamente() {
		return true;
	}

	public void GravarConfiguracoes() {
		CheckBox chkExibirJanelaSobreAoIniciar = ((CheckBox) JanelaBase
				.findViewById(R.id.chkExibirJanelaSobreAoIniciar));
		JanelaBase.Preferencias
				.setIgnorarJanelaSobreAoIniciar(!chkExibirJanelaSobreAoIniciar
						.isChecked());
	}

}
