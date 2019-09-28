package br.srv.cabral.marcapontolite.janelas;

import java.util.Date;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.DatePicker;
import br.srv.cabral.marcapontolite.R;
import br.srv.cabral.marcapontolite.util.Definicoes;
import br.srv.cabral.marcapontolite.util.Funcoes;

/* Janela principal. 
 */
public class ConfiguracaoDoPeriodo extends JanelaBase {
	private CheckBox chkPeriodoInicial = null;

	/*
	 * Controle da tela: chkPeriodoInicial.
	 */
	protected CheckBox getChkPeriodoInicial() {
		if (chkPeriodoInicial == null) {
			chkPeriodoInicial = (CheckBox) findViewById(R.id.chkPeriodoInicial);
		}
		return chkPeriodoInicial;
	}

	private DatePicker txtPeriodoInicial = null;

	/*
	 * Controle da tela: txtPeriodoInicial.
	 */
	protected DatePicker getTxtPeriodoInicial() {
		if (txtPeriodoInicial == null) {
			txtPeriodoInicial = (DatePicker) findViewById(R.id.txtPeriodoInicial);
		}
		return txtPeriodoInicial;
	}

	private CheckBox chkPeriodoFinal = null;

	/*
	 * Controle da tela: chkPeriodoFinal.
	 */
	protected CheckBox getChkPeriodoFinal() {
		if (chkPeriodoFinal == null) {
			chkPeriodoFinal = (CheckBox) findViewById(R.id.chkPeriodoFinal);
		}
		return chkPeriodoFinal;
	}

	private DatePicker txtPeriodoFinal = null;

	/*
	 * Controle da tela: txtPeriodoFinal.
	 */
	protected DatePicker getTxtPeriodoFinal() {
		if (txtPeriodoFinal == null) {
			txtPeriodoFinal = (DatePicker) findViewById(R.id.txtPeriodoFinal);
		}
		return txtPeriodoFinal;
	}

	private Button btnSalvar = null;

	/*
	 * Controle da tela: btnSalvar.
	 */
	protected Button getBtnSalvar() {
		if (btnSalvar == null) {
			btnSalvar = (Button) findViewById(R.id.btnSalvar);
		}
		return btnSalvar;
	}

	private Button btnCancelar = null;

	/*
	 * Controle da tela: btnCancelar.
	 */
	protected Button getBtnCancelar() {
		if (btnCancelar == null) {
			btnCancelar = (Button) findViewById(R.id.btnCancelar);
		}
		return btnCancelar;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see sjcsj.marcaponto.janelas.JanelaBase#getLayoutResource()
	 */
	@Override
	protected int getLayoutResource() {
		return R.layout.janela_configuracaodoperiodo;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see sjcsj.marcaponto.janelas.JanelaBase#AtribuirEventos()
	 */
	@Override
	protected void AtribuirEventos() {
		getBtnSalvar().setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				SalvarConfiguracoes();
			}
		});
		getBtnCancelar().setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				FecharJanela();
			}
		});
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see sjcsj.marcaponto.janelas.JanelaBase#CarregarControles()
	 */
	@Override
	protected void CarregarControles() {
		String defPeriodoInicial = Definicoes.INSTANCIA
				.Ler(Definicoes.DEF_PERIODO_INICIAL);
		String defPeriodoFinal = Definicoes.INSTANCIA
				.Ler(Definicoes.DEF_PERIODO_FINAL);

		getChkPeriodoInicial().setOnCheckedChangeListener(
				new OnCheckedChangeListener() {
					public void onCheckedChanged(CompoundButton buttonView,
							boolean isChecked) {
						ProcessarEvento_CheckBox_Click(getChkPeriodoInicial(),
								getTxtPeriodoInicial());
					}
				});
		getChkPeriodoFinal().setOnCheckedChangeListener(
				new OnCheckedChangeListener() {
					public void onCheckedChanged(CompoundButton buttonView,
							boolean isChecked) {
						ProcessarEvento_CheckBox_Click(getChkPeriodoFinal(),
								getTxtPeriodoFinal());
					}
				});
		getChkPeriodoInicial().setChecked(defPeriodoInicial.length() > 0);
		getChkPeriodoFinal().setChecked(defPeriodoFinal.length() > 0);
		ProcessarEvento_CheckBox_Click(getChkPeriodoInicial(),
				getTxtPeriodoInicial());
		ProcessarEvento_CheckBox_Click(getChkPeriodoFinal(),
				getTxtPeriodoFinal());
		if (getChkPeriodoInicial().isChecked()) {
			Funcoes.DefinirData(getTxtPeriodoInicial(), defPeriodoInicial);
		}
		if (getChkPeriodoFinal().isChecked()) {
			Funcoes.DefinirData(getTxtPeriodoFinal(), defPeriodoFinal);
		}
	}

	/*
	 * Processa o evento de clique no checkbox do período.
	 */
	private void ProcessarEvento_CheckBox_Click(CheckBox checkBox,
			DatePicker datePicker) {
		datePicker.setVisibility(checkBox.isChecked() ? View.VISIBLE
				: View.GONE);
	}

	/*
	 * Salvar as informações.
	 */
	private void SalvarConfiguracoes() {
		if (ValidarCampos()) {
			String defPeriodoInicial = getChkPeriodoInicial().isChecked() ? Funcoes
					.ObterDataEmTexto(getTxtPeriodoInicial()) : "";
			Definicoes.INSTANCIA.Gravar(Definicoes.DEF_PERIODO_INICIAL,
					defPeriodoInicial);

			String defPeriodoFinal = getChkPeriodoFinal().isChecked() ? Funcoes
					.ObterDataEmTexto(getTxtPeriodoFinal()) : "";
			Definicoes.INSTANCIA.Gravar(Definicoes.DEF_PERIODO_FINAL,
					defPeriodoFinal);

			FecharJanela();
		}
	}

	/*
	 * Fecha esta janela.
	 */
	private void FecharJanela() {
		finish();
	}

	/*
	 * Verifica se os campos estão preenchidos corretamente.
	 */
	private boolean ValidarCampos() {
		if (getChkPeriodoInicial().isChecked()
				&& getChkPeriodoFinal().isChecked()) {
			Date dataInicial = Funcoes
					.ConverterParaDate(getTxtPeriodoInicial());
			Date dataFinal = Funcoes.ConverterParaDate(getTxtPeriodoFinal());
			if (dataInicial.after(dataFinal)) {
				ExibirMensagem(R.string.JanelaConfiguracaoDoPeriodo_msgDataInvalida);
				return false;
			}
		}
		return true;
	}
}