/**
 * 
 */
package br.srv.cabral.marcaponto.janelas;

import android.content.DialogInterface;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import br.srv.cabral.marcaponto.R;

/**
 * Classe que configura o layout da janela: Configuracoes.
 * 
 * @author Sergio Cabral (sergio@cabral.srv.br)
 */
public class LayoutConfiguracoes extends LayoutDeJanela {

	/**
	 * Seção ExibirJanelaSobreAoIniciar
	 */
	private ILayoutConfiguracoes_Secao secaoExibirJanelaSobreAoIniciar;

	/**
	 * Seção CaminhoDosDadosEConfiguracoes
	 */
	private ILayoutConfiguracoes_Secao secaoCaminhoDosDadosEConfiguracoes;

	/**
	 * Inicializa variáveis.
	 */
	private void InicializarVariaveis() {
		secaoExibirJanelaSobreAoIniciar = new LayoutConfiguracoes_ExibirJanelaSobreAoIniciar(
				this.JanelaBase);
		secaoCaminhoDosDadosEConfiguracoes = new LayoutConfiguracoes_CaminhoDosDadosEConfiguracoes(
				this.JanelaBase);
	}

	public Integer getResourceMenuId() {
		return 0;
	}

	public boolean onOptionsItemSelected(MenuItem item) {
		return false;
	}

	public boolean onContextItemSelected(MenuItem item) {
		return false;
	}

	public void onCreateContextMenu(ContextMenu menu, View view,
			ContextMenuInfo menuInfo) {
		return;
	}

	@Override
	protected int getLayoutResourceId() {
		return R.layout.configuracoes;
	}

	@Override
	protected void ConfigurarControles() {
		InicializarVariaveis();

		Button btnGravar = ((Button) JanelaBase.findViewById(R.id.btnGravar));
		Button btnVoltar = ((Button) JanelaBase.findViewById(R.id.btnVoltar));

		btnGravar.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {

				JanelaBase.ExibirMensagemPergunta(
						R.string.layoutConfiguracoes_msgGravar,
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface arg0, int arg1) {
								if (CamposPreenchidosCorretamente()) {
									GravarConfiguracoes();
									JanelaBase
											.ExibirMensagemToast(R.string.layoutConfiguracoes_msgGravarSucesso);
									FecharJanela();
								}
							}
						}, new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface arg0, int arg1) {
								return;
							}
						});

			}
		});

		btnVoltar.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				FecharJanela();
			}
		});

		secaoExibirJanelaSobreAoIniciar.ConfigurarControles();
		secaoCaminhoDosDadosEConfiguracoes.ConfigurarControles();
	}
	
	/**
	 * Verifica se os campos estão preenchidos corretamente.
	 * 
	 * @return boolean. Se retorna false exibe automaticamente a mensagem de
	 *         erro.
	 */
	private boolean CamposPreenchidosCorretamente() {
		if (!secaoExibirJanelaSobreAoIniciar.CamposPreenchidosCorretamente()) {
			return false;
		}
		if (!secaoCaminhoDosDadosEConfiguracoes.CamposPreenchidosCorretamente()) {
			return false;
		}
		return true;
	}

	/**
	 * Grava as configurações na tela.
	 */
	private void GravarConfiguracoes() {
		secaoExibirJanelaSobreAoIniciar.GravarConfiguracoes();
		secaoCaminhoDosDadosEConfiguracoes.GravarConfiguracoes();
	}
}
