package br.srv.cabral.marcapontolite.janelas;

import java.util.Date;
import java.util.HashMap;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import br.srv.cabral.marcapontolite.R;
import br.srv.cabral.marcapontolite.dados.HorariosColumns;
import br.srv.cabral.marcapontolite.util.Definicoes;
import br.srv.cabral.marcapontolite.util.Funcoes;
import br.srv.cabral.marcapontolite.util.Horarios;

/* Janela principal. 
 */
public class RegistrarHoraDoDia extends JanelaBase {
	private Button btnHora1 = null;

	/*
	 * Controle da tela: btnHora1.
	 */
	protected Button getBtnHora1() {
		if (btnHora1 == null) {
			btnHora1 = (Button) findViewById(R.id.btnHora1);
		}
		return btnHora1;
	}

	private Button btnHora2 = null;

	/*
	 * Controle da tela: btnHora2.
	 */
	protected Button getBtnHora2() {
		if (btnHora2 == null) {
			btnHora2 = (Button) findViewById(R.id.btnHora2);
		}
		return btnHora2;
	}

	private Button btnHora3 = null;

	/*
	 * Controle da tela: btnHora3.
	 */
	protected Button getBtnHora3() {
		if (btnHora3 == null) {
			btnHora3 = (Button) findViewById(R.id.btnHora3);
		}
		return btnHora3;
	}

	private Button btnHora4 = null;

	/*
	 * Controle da tela: btnHora4.
	 */
	protected Button getBtnHora4() {
		if (btnHora4 == null) {
			btnHora4 = (Button) findViewById(R.id.btnHora4);
		}
		return btnHora4;
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

	private Button btnApagar = null;

	/*
	 * Controle da tela: btnApagar.
	 */
	protected Button getBtnApagar() {
		if (btnApagar == null) {
			btnApagar = (Button) findViewById(R.id.btnApagar);
		}
		return btnApagar;
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

	private DatePicker txtData = null;

	/*
	 * Controle da tela: txtData.
	 */
	protected DatePicker getTxtData() {
		if (txtData == null) {
			txtData = (DatePicker) findViewById(R.id.txtData);
		}
		return txtData;
	}

	/*
	 * Horário sendo gerenciado por esta instância.
	 */
	protected String getDataParaEdicao() {
		try {
			String data = (String) this.getIntent().getExtras().get("data");
			if (data == null) {
				data = "";
			}
			return data;
		} catch (Exception ex) {
			return "";
		}
	}

	private HashMap<String, String> horariosParaData = null;

	protected HashMap<String, String> getHorariosParaData() {
		if (horariosParaData == null && getDataParaEdicao().length() > 0) {
			horariosParaData = Horarios.INSTANCIA.Ler(getDataParaEdicao());
		}
		return horariosParaData;
	}

	/*
	 * Horário sendo gerenciado por esta instância.
	 */
	protected boolean getApenasEdicao() {
		return getDataParaEdicao().length() > 0;
	}

	/*
	 * Called when the activity is focused.
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		boolean apenasEdicao = getApenasEdicao();
		getTxtData().setEnabled(!apenasEdicao);
		getBtnApagar().setVisibility(apenasEdicao ? View.VISIBLE : View.GONE);
		if (apenasEdicao) {
			Funcoes.DefinirData(getTxtData(), getDataParaEdicao());
		}
	}

	/*
	 * Called when the activity is first created.
	 */
	@Override
	public void onResume() {
		super.onResume();
		AtualizarExibicaoDaHora();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see sjcsj.marcaponto.janelas.JanelaBase#getLayoutResource()
	 */
	@Override
	protected int getLayoutResource() {
		return R.layout.janela_registrarhoradodia;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see sjcsj.marcaponto.janelas.JanelaBase#AtribuirEventos()
	 */
	@Override
	protected void AtribuirEventos() {
		getBtnHora1().setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				MarcarHorario(getBtnHora1());
			}
		});
		getBtnHora2().setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				MarcarHorario(getBtnHora2());
			}
		});
		getBtnHora3().setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				MarcarHorario(getBtnHora3());
			}
		});
		getBtnHora4().setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				MarcarHorario(getBtnHora4());
			}
		});
		getBtnSalvar().setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Salvar();
			}
		});
		getBtnApagar().setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Apagar();
			}
		});
		getBtnCancelar().setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				FecharJanela();
			}
		});
	}

	/*
	 * Executa marcação de um horário
	 */
	private void MarcarHorario(Button btnHora) {
		int horario = btnHora == getBtnHora1() ? 1
				: btnHora == getBtnHora2() ? 2 : btnHora == getBtnHora3() ? 3
						: btnHora == getBtnHora4() ? 4 : -1;
		Intent intent = new Intent(this, RegistrarHora.class);
		intent.putExtra("data", getDataParaEdicao());
		intent.putExtra("horario", horario);
		startActivityFromChild(this, intent, -1);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see sjcsj.marcaponto.janelas.JanelaBase#CarregarControles()
	 */
	@Override
	protected void CarregarControles() {
	}

	/*
	 * Salvar as informações.
	 */
	private void Salvar() {
		if (ValidarCampos()) {
			String data = Funcoes.ObterDataEmTexto(getTxtData());
			String hora1 = Definicoes.INSTANCIA.Ler(getDefinicaoNome(1));
			String hora2 = Definicoes.INSTANCIA.Ler(getDefinicaoNome(2));
			String hora3 = Definicoes.INSTANCIA.Ler(getDefinicaoNome(3));
			String hora4 = Definicoes.INSTANCIA.Ler(getDefinicaoNome(4));
			if (hora1 == null || hora1.equals("-")) {
				hora1 = "";
			}
			if (hora2 == null || hora2.equals("-")) {
				hora2 = "";
			}
			if (hora3 == null || hora3.equals("-")) {
				hora3 = "";
			}
			if (hora4 == null || hora4.equals("-")) {
				hora4 = "";
			}
			Horarios.INSTANCIA.RegistrarHorarios(data, hora1, hora2, hora3,
					hora4);
			Definicoes.INSTANCIA.Gravar(getDefinicaoNome(1), "");
			Definicoes.INSTANCIA.Gravar(getDefinicaoNome(2), "");
			Definicoes.INSTANCIA.Gravar(getDefinicaoNome(3), "");
			Definicoes.INSTANCIA.Gravar(getDefinicaoNome(4), "");
			FecharJanela();
		}
	}

	/*
	 * Apagar as informações.
	 */
	private void Apagar() {
		String data = Funcoes.ObterDataEmTexto(getTxtData());
		new AlertDialog.Builder(this)
				.setIcon(android.R.drawable.ic_dialog_alert)
				.setTitle(R.string.JanelaPrincipal)
				.setMessage(
						String.format(
								Funcoes.LerRecurso(R.string.JanelaRegistrarHoraDoDia_msgExcluirConfirmar),
								data))
				.setPositiveButton(R.string.Geral_btnSim,
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int which) {
								String data = Funcoes
										.ObterDataEmTexto(getTxtData());
								Horarios.INSTANCIA.ApagarHorarios(data);
								FecharJanela();
							}
						}).setNegativeButton(R.string.Geral_btnNao, null)
				.show();
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
		if (!isHorariosEmOrdemCronologica()) {
			ExibirMensagem(R.string.JanelaRegistrarHoraDoDia_msgPrecisaOrdemCronologica);
			return false;
		} else if (!getApenasEdicao()) {
			String data = Funcoes.ObterDataEmTexto(getTxtData());
			if (!getIntent().getBooleanExtra("forcar", false)
					&& Horarios.INSTANCIA.Ler(data) != null) {
				new AlertDialog.Builder(this)
						.setIcon(android.R.drawable.ic_dialog_alert)
						.setTitle(R.string.JanelaPrincipal)
						.setMessage(
								String.format(
										Funcoes.LerRecurso(R.string.JanelaRegistrarHoraDoDia_msgDataJaExiste),
										data))
						.setPositiveButton(R.string.Geral_btnSim,
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog,
											int which) {
										getIntent().putExtra("forcar", true);
										Salvar();
									}
								})
						.setNegativeButton(R.string.Geral_btnNao, null).show();
				return false;
			}
		}
		return true;
	}

	/*
	 * Retorna o nome da definição onde será salvo o dado.
	 */
	private String getDefinicaoNome(int index) {
		return Definicoes.DEF_HORARIO + (new Integer(index)).toString()
				+ (getDataParaEdicao().length() > 0 ? "_" : "");
	}

	/*
	 * Informa se os horários estão em ordem cronológica
	 */
	private boolean isHorariosEmOrdemCronologica() {
		String defHora1 = Definicoes.INSTANCIA.Ler(getDefinicaoNome(1));
		String defHora2 = Definicoes.INSTANCIA.Ler(getDefinicaoNome(2));
		String defHora3 = Definicoes.INSTANCIA.Ler(getDefinicaoNome(3));
		String defHora4 = Definicoes.INSTANCIA.Ler(getDefinicaoNome(4));

		if (defHora1 == null || defHora1.equals("-")) {
			defHora1 = "";
		}
		if (defHora2 == null || defHora2.equals("-")) {
			defHora2 = "";
		}
		if (defHora3 == null || defHora3.equals("-")) {
			defHora3 = "";
		}
		if (defHora4 == null || defHora4.equals("-")) {
			defHora4 = "";
		}

		Date dataModelo = new Date();
		Date data1 = null;
		Date data2 = null;
		Date data3 = null;
		Date data4 = null;

		if (defHora1.length() > 0) {
			data1 = (Date) dataModelo.clone();
			Funcoes.AplicarHoraEmDate(data1, defHora1);
		}
		if (defHora2.length() > 0) {
			data2 = (Date) dataModelo.clone();
			Funcoes.AplicarHoraEmDate(data2, defHora2);
		}
		if (defHora3.length() > 0) {
			data3 = (Date) dataModelo.clone();
			Funcoes.AplicarHoraEmDate(data3, defHora3);
		}
		if (defHora4.length() > 0) {
			data4 = (Date) dataModelo.clone();
			Funcoes.AplicarHoraEmDate(data4, defHora4);
		}

		if (data1 != null && data2 != null && data1.after(data2)) {
			return false;
		}
		if (data1 != null && data3 != null && data1.after(data3)) {
			return false;
		}
		if (data1 != null && data4 != null && data1.after(data4)) {
			return false;
		}

		if (data2 != null && data3 != null && data2.after(data3)) {
			return false;
		}
		if (data2 != null && data4 != null && data2.after(data4)) {
			return false;
		}

		if (data3 != null && data4 != null && data3.after(data4)) {
			return false;
		}

		return true;
	}

	private boolean carregadoNaPrimeiraVez = false;

	/*
	 * Carrega o valor atual da hora e exibe.
	 */
	protected void AtualizarExibicaoDaHora() {
		String posFixo = "";
		if (getDataParaEdicao().length() > 0) {
			posFixo = "_";
			if (!carregadoNaPrimeiraVez) {
				carregadoNaPrimeiraVez = true;
				Definicoes.INSTANCIA.Gravar(Definicoes.DEF_HORARIO + "1"
						+ posFixo,
						getHorariosParaData().get(HorariosColumns.CAMPO_HORA1)
								.replace("-", ""));
				Definicoes.INSTANCIA.Gravar(Definicoes.DEF_HORARIO + "2"
						+ posFixo,
						getHorariosParaData().get(HorariosColumns.CAMPO_HORA2)
								.replace("-", ""));
				Definicoes.INSTANCIA.Gravar(Definicoes.DEF_HORARIO + "3"
						+ posFixo,
						getHorariosParaData().get(HorariosColumns.CAMPO_HORA3)
								.replace("-", ""));
				Definicoes.INSTANCIA.Gravar(Definicoes.DEF_HORARIO + "4"
						+ posFixo,
						getHorariosParaData().get(HorariosColumns.CAMPO_HORA4)
								.replace("-", ""));
			}
		}
		Funcoes.AtualizarExibicaoDaHora(getBtnHora1(), 1,
				R.string.JanelaPrincipal_btnHora1, posFixo);
		Funcoes.AtualizarExibicaoDaHora(getBtnHora2(), 2,
				R.string.JanelaPrincipal_btnHora2, posFixo);
		Funcoes.AtualizarExibicaoDaHora(getBtnHora3(), 3,
				R.string.JanelaPrincipal_btnHora3, posFixo);
		Funcoes.AtualizarExibicaoDaHora(getBtnHora4(), 4,
				R.string.JanelaPrincipal_btnHora4, posFixo);
	}

}