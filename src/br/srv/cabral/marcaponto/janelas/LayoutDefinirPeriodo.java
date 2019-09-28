/**
 * 
 */
package br.srv.cabral.marcaponto.janelas;

import java.util.Calendar;
import java.util.Date;

import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.DatePicker;
import br.srv.cabral.marcaponto.R;

/**
 * Classe que configura o layout da janela: Definir Período.
 * 
 * @author Sergio Cabral (sergio@cabral.srv.br)
 */
public class LayoutDefinirPeriodo extends LayoutDeJanela {

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
		return R.layout.definir_periodo;
	}

	@Override
	protected void ConfigurarControles() {
		Button btnGravar = ((Button) JanelaBase.findViewById(R.id.btnGravar));
		Button btnVoltar = ((Button) JanelaBase.findViewById(R.id.btnVoltar));
		CheckBox chkPeriodoInicio = ((CheckBox) JanelaBase.findViewById(R.id.chkPeriodoInicio));
		CheckBox chkPeriodoFim = ((CheckBox) JanelaBase.findViewById(R.id.chkPeriodoFim));
		
		btnGravar.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				GravarDados();
			}
		});

		btnVoltar.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				FecharJanela();
			}
		});

		chkPeriodoInicio.setOnCheckedChangeListener(
				new OnCheckedChangeListener() {
					public void onCheckedChanged(CompoundButton buttonView,
							boolean isChecked) {
						DatePicker picker = ((DatePicker) JanelaBase.findViewById(R.id.pikPeriodoInicio));
						picker.setVisibility(isChecked ? View.VISIBLE : View.GONE);
					}
				});
		chkPeriodoFim.setOnCheckedChangeListener(
				new OnCheckedChangeListener() {
					public void onCheckedChanged(CompoundButton buttonView,
							boolean isChecked) {
						DatePicker picker = ((DatePicker) JanelaBase.findViewById(R.id.pikPeriodoFim));
						picker.setVisibility(isChecked ? View.VISIBLE : View.GONE);
					}
				});
		
		CarregarDados();
	}

	/**
	 * Grava os dados do usuário e fecha a janela.
	 */
	private void GravarDados() {
		Calendar calendar = Calendar.getInstance();
		
		CheckBox chkPeriodoInicio = ((CheckBox) JanelaBase.findViewById(R.id.chkPeriodoInicio));
		DatePicker pikPeriodoInicio = ((DatePicker) JanelaBase.findViewById(R.id.pikPeriodoInicio));
		CheckBox chkPeriodoFim = ((CheckBox) JanelaBase.findViewById(R.id.chkPeriodoFim));
		DatePicker pikPeriodoFim = ((DatePicker) JanelaBase.findViewById(R.id.pikPeriodoFim));
		
		Date periodoInicio;
		if (chkPeriodoInicio.isChecked())
		{
			calendar.set(Calendar.DATE, pikPeriodoInicio.getDayOfMonth());
			calendar.set(Calendar.MONTH, pikPeriodoInicio.getMonth());
			calendar.set(Calendar.YEAR, pikPeriodoInicio.getYear());
			periodoInicio = calendar.getTime();
		}
		else
		{
			periodoInicio = null;
		}
		
		Date periodoFim;
		if (chkPeriodoFim.isChecked())
		{
			calendar.set(Calendar.DATE, pikPeriodoFim.getDayOfMonth());
			calendar.set(Calendar.MONTH, pikPeriodoFim.getMonth());
			calendar.set(Calendar.YEAR, pikPeriodoFim.getYear());
			periodoFim = calendar.getTime();
		}
		else
		{
			periodoFim = null;
		}
		
		String perfil = JanelaBase.Perfil.getPerfilSelecionado();
		this.JanelaBase.Preferencias.setPeriodoDeExibicaoDoGridDeHorarios(perfil, periodoInicio, periodoFim);
		this.JanelaBase.ExibirMensagemToast(R.string.comum_msgDefinicaoGravada);
		
		FecharJanela();
	}
	
	/**
	 * Carrega os dados gravados para os controles da tela.
	 */
	private void CarregarDados()
	{
		Calendar calendar = Calendar.getInstance();
		
		CheckBox chkPeriodoInicio = ((CheckBox) JanelaBase.findViewById(R.id.chkPeriodoInicio));
		DatePicker pikPeriodoInicio = ((DatePicker) JanelaBase.findViewById(R.id.pikPeriodoInicio));
		CheckBox chkPeriodoFim = ((CheckBox) JanelaBase.findViewById(R.id.chkPeriodoFim));
		DatePicker pikPeriodoFim = ((DatePicker) JanelaBase.findViewById(R.id.pikPeriodoFim));
		
		Date[] periodo = this.JanelaBase.Preferencias.getPeriodoDeExibicaoDoGridDeHorarios();
		Date periodoInicio = periodo[0];
		Date periodoFim = periodo[1];
		
		chkPeriodoInicio.setChecked(periodoInicio != null);
		pikPeriodoInicio.setVisibility(periodoInicio != null ? View.VISIBLE : View.GONE);
		if (periodoInicio == null)
		{
			calendar.set(Calendar.DATE, 1);
			periodoInicio = calendar.getTime();
		}
		calendar.setTime(periodoInicio);
		pikPeriodoInicio.init(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), null);
		
		chkPeriodoFim.setChecked(periodoFim != null);
		pikPeriodoFim.setVisibility(periodoFim != null ? View.VISIBLE : View.GONE);
		if (periodoFim == null)
		{
			calendar.add(Calendar.DATE, 32);
			calendar.set(Calendar.DATE, 1);
			calendar.add(Calendar.DATE, -1);
			periodoFim = calendar.getTime();
		}
		calendar.setTime(periodoFim);
		pikPeriodoFim.init(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), null);

	}
	
}
