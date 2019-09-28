/**
 * 
 */
package br.srv.cabral.marcaponto.janelas;

import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.TextView;
import br.srv.cabral.marcaponto.R;
import br.srv.cabral.marcaponto.suporte.Constantes;

/**
 * Classe que configura o layout da janela: Sobre.
 * 
 * @author Sergio Cabral (sergio@cabral.srv.br)
 */
public class LayoutSobre extends LayoutDeJanela {

	public Integer getResourceMenuId() {
		return 0;
	}

	/**
	 * Disparado quando um item de menu é acionado.
	 */
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
		return R.layout.sobre;
	}

	@Override
	protected void ConfigurarControles() {
		Button btnProsseguir = ((Button) JanelaBase
				.findViewById(R.id.btnProsseguir));
		
		TextView lblVersao = ((TextView) JanelaBase
				.findViewById(R.id.lblVersao));

		lblVersao.setText(((Integer) Constantes.VERSAO_NOMINAL).toString()
				+ "."
				+ ((Integer) (Constantes.VERSAO_SEQUENCIAL - 1)).toString());

		CheckBox chkExibirAoIniciar = ((CheckBox) JanelaBase
				.findViewById(R.id.chkExibirAoIniciar));

		if (JanelaBase.getIntent().getExtras() == null) {
			btnProsseguir.setText(R.string.layoutSobre_btnIniciar);
		} else {
			btnProsseguir.setText(R.string.comum_btnFechar);
		}

		btnProsseguir.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				JanelaGenerica.AplicativoJaIniciado = true;
				FecharJanela();
			}
		});

		chkExibirAoIniciar
				.setOnCheckedChangeListener(new OnCheckedChangeListener() {

					public void onCheckedChanged(CompoundButton buttonView,
							boolean isChecked) {
						JanelaBase.Preferencias
								.setIgnorarJanelaSobreAoIniciar(!isChecked);
					}
				});

		chkExibirAoIniciar.setChecked(!JanelaBase.Preferencias
				.getIgnorarJanelaSobreAoIniciar());
	}

}
