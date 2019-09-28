/**
 * 
 */
package br.srv.cabral.marcaponto.janelas;

import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import br.srv.cabral.marcaponto.R;
import br.srv.cabral.marcaponto.suporte.Constantes;

/**
 * Classe que configura o layout da janela: Erro.
 * 
 * @author Sergio Cabral (sergio@cabral.srv.br) *
 */
public class LayoutErro extends LayoutDeJanela {

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
		return R.layout.erro;
	}

	@Override
	protected void ConfigurarControles() {
		Bundle extras = JanelaBase.getIntent().getExtras();
		if (extras == null) {
			FecharJanela();
		} else {
			TextView lblMensagem = ((TextView) JanelaBase
					.findViewById(R.id.lblMensagem));
			String msgErro = extras.getString(Constantes.INTENT_MSG_ERRO);
			lblMensagem.setText(msgErro);
		}
		
		Button btnVoltar = ((Button) JanelaBase.findViewById(R.id.btnVoltar));

		btnVoltar.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				FecharJanela();
			}
		});
	}
	
}
