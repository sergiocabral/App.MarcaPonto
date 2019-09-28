/**
 * 
 */
package br.srv.cabral.marcaponto.janelas;

import java.util.Date;
import java.util.HashMap;
import android.database.Cursor;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import br.srv.cabral.marcaponto.R;
import br.srv.cabral.marcaponto.persistencia.TabelaHorarios;
import br.srv.cabral.marcaponto.suporte.Constantes;
import br.srv.cabral.marcaponto.suporte.Preferencias;

/**
 * Classe que configura o layout da janela: Sobre.
 * 
 * @author Sergio Cabral (sergio@cabral.srv.br) *
 */
public class LayoutPrincipal extends LayoutDeJanela {

	public Integer getResourceMenuId() {
		return R.menu.principal;
	}

	/**
	 * Disparado quando um item de menu é acionado.
	 */
	public boolean onOptionsItemSelected(MenuItem item) {
		return ExecutarAcaoDoMenu(item.getItemId());
	}

	public boolean onContextItemSelected(MenuItem item) {
		return false;
	}

	public void onCreateContextMenu(ContextMenu menu, View view,
			ContextMenuInfo menuInfo) {
		return;
	}
	
	@Override
	public void AoExibirJanela() {
		ConfigurarPeriodo();
	}

	/**
	 * Executa a ação de um menu
	 * 
	 * @param menuResId
	 *            Id do recurso do menu.
	 * @return
	 */
	public Boolean ExecutarAcaoDoMenu(int menuResId) {
		switch (menuResId) {
		case R.id.menuPrincipalPerfil:
			JanelaBase.ExibirLayout(LayoutPerfil.class, true);
			return true;
		case R.id.menuPrincipalDefinirPeriodo:
			JanelaBase.ExibirLayout(LayoutDefinirPeriodo.class, true);
			return true;
		case R.id.menuPrincipalConfiguracoes:
			JanelaBase.ExibirLayout(LayoutConfiguracoes.class, true);
			return true;
		case R.id.menuPrincipalConfiguracoesReset:
			JanelaBase.ExibirLayout(LayoutConfiguracoesReset.class, true);
			return true;
		case R.id.menuPrincipalSobre:
			JanelaBase.ExibirLayout(LayoutSobre.class, true);
			return true;
		default:
			return false;
		}
	}

	@Override
	protected int getLayoutResourceId() {
		return R.layout.principal;
	}

	@Override
	protected void ConfigurarControles() {
		JanelaBase.Preferencias
				.setVersaoSequencialDoAplicativo(Constantes.VERSAO_SEQUENCIAL);

		Button btnMarcarEntrada = ((Button) JanelaBase
				.findViewById(R.id.btnMarcarEntrada));
		Button btnMarcarSaida = ((Button) JanelaBase
				.findViewById(R.id.btnMarcarSaida));
		Button btnPeriodo = ((Button) JanelaBase
				.findViewById(R.id.btnPeriodo));

		OnClickListener onClickListener = new OnClickListener() {
			public void onClick(View v) {
				HashMap<String, String> mensagens = new HashMap<String, String>();
				mensagens.put(Constantes.INTENT_MSG_MARCAR_HORARIO,
						((Button) v).getText().toString());
				JanelaBase.ExibirLayout(LayoutMarcarHorario.class, true,
						mensagens);
			}
		};

		btnMarcarEntrada.setOnClickListener(onClickListener);
		btnMarcarSaida.setOnClickListener(onClickListener);

		btnPeriodo.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				JanelaBase.ExibirLayout(LayoutDefinirPeriodo.class, true);
			}
		});
		ConfigurarPeriodo();
		
		ConfigurarControles_Lista();
	}
	
	/**
	 * Configura o Periodo em exibicao nesta janela. 
	 */
	private void ConfigurarPeriodo() {
		Button btnPeriodo = ((Button) JanelaBase.findViewById(R.id.btnPeriodo));
		btnPeriodo.setText(String.format(this.JanelaBase.getText(R.string.layoutPrincipal_btnPeriodo).toString(), ConfiguraTextoDoPeriodo()));
	}

	private String ConfiguraTextoDoPeriodo()
	{
		String retorno;
		
		Date[] periodo = this.JanelaBase.Preferencias.getPeriodoDeExibicaoDoGridDeHorarios();
		Date periodoInicio = periodo[0];
		Date periodoFim = periodo[1];
		
		
		if (periodoInicio != null && periodoFim != null)
		{
			retorno = String.format(
					this.JanelaBase.getText(R.string.layoutPrincipal_btnPeriodo_Definido).toString(), 
					Preferencias.FormatarData(periodoInicio), 
					Preferencias.FormatarData(periodoFim));
		}
		else if (periodoInicio != null && periodoFim == null)
		{
			retorno = String.format(
					this.JanelaBase.getText(R.string.layoutPrincipal_btnPeriodo_APartir).toString(), 
					Preferencias.FormatarData(periodoInicio));
		}
		else if (periodoInicio == null && periodoFim != null)
		{
			retorno = String.format(
					this.JanelaBase.getText(R.string.layoutPrincipal_btnPeriodo_Ate).toString(), 
					Preferencias.FormatarData(periodoFim));
		}
		else
		{
			retorno = this.JanelaBase.getText(R.string.layoutPrincipal_btnPeriodo_Indefinido).toString();
		}
		
		return retorno;
	}

	private void ConfigurarControles_Lista() {
		ListView lstHorarios = ((ListView)JanelaBase.findViewById(R.id.lstHorarios));
		
		TabelaHorarios tblHorarios = new TabelaHorarios();
		String coluna = (String)tblHorarios.getNomeDosCampos().get(TabelaHorarios.CAMPO_COMENTARIO);

		String[] campos = { coluna };
		int[] controles = { R.id.txtValor };
		Cursor cursor = JanelaBase.GerenciadorDeHorarios
				.Consultar(JanelaBase.Perfil.getPerfilSelecionado());

		SimpleCursorAdapter adapter = new SimpleCursorAdapter(JanelaBase, 
				R.layout.principal_listagem, cursor, campos, controles);
		lstHorarios.setAdapter(adapter);
	}
}
