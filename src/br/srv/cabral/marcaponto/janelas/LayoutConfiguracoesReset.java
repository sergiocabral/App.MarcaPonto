/**
 * 
 */
package br.srv.cabral.marcaponto.janelas;

import android.content.DialogInterface;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.View.OnClickListener;
import android.widget.Button;
import br.srv.cabral.marcaponto.R;

/**
 * Classe que configura o layout da janela: Restaurar Configuracoes.
 * 
 * @author Sergio Cabral (sergio@cabral.srv.br) *
 */
public class LayoutConfiguracoesReset extends LayoutDeJanela {

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
		return R.layout.configuracoes_reset;
	}

	@Override
	protected void ConfigurarControles() {
		Button btnReset = ((Button) JanelaBase.findViewById(R.id.btnReset));
		Button btnVoltar = ((Button) JanelaBase.findViewById(R.id.btnVoltar));

		btnReset.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				JanelaBase.ExibirMensagemPergunta(
						R.string.layoutConfiguracoesReset_msgReset,
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface arg0, int arg1) {
								JanelaBase.Preferencias.Limpar();
								JanelaBase
										.ExibirMensagemToast(R.string.layoutConfiguracoesReset_msgResetSucesso);
								FecharJanela();
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
	}
}
