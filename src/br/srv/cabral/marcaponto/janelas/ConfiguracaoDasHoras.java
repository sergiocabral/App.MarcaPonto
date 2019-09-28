package br.srv.cabral.marcapontolite.janelas;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Button;
import br.srv.cabral.marcapontolite.R;
import br.srv.cabral.marcapontolite.util.Definicoes;
import br.srv.cabral.marcapontolite.util.Horarios;

/* Janela principal. 
 */
public class ConfiguracaoDasHoras extends JanelaBase {
	private TextView lblHoraEntradaMenor = null;

	/*
	 * Controle da tela: lblHoraEntradaMenor.
	 */
	protected TextView getLblHoraEntradaMenor() {
		if (lblHoraEntradaMenor == null) {
			lblHoraEntradaMenor = (TextView) findViewById(R.id.lblHoraEntradaMenor);
		}
		return lblHoraEntradaMenor;
	}

	private Spinner txtHoraEntradaMenor = null;

	/*
	 * Controle da tela: txtHoraEntradaMenor.
	 */
	protected Spinner getTxtHoraEntradaMenor() {
		if (txtHoraEntradaMenor == null) {
			txtHoraEntradaMenor = (Spinner) findViewById(R.id.txtHoraEntradaMenor);
		}
		return txtHoraEntradaMenor;
	}

	private TextView lblHoraEntradaMaior = null;

	/*
	 * Controle da tela: lblHoraEntradaMaior.
	 */
	protected TextView getLblHoraEntradaMaior() {
		if (lblHoraEntradaMaior == null) {
			lblHoraEntradaMaior = (TextView) findViewById(R.id.lblHoraEntradaMaior);
		}
		return lblHoraEntradaMaior;
	}

	private Spinner txtHoraEntradaMaior = null;

	/*
	 * Controle da tela: txtHoraEntradaMaior.
	 */
	protected Spinner getTxtHoraEntradaMaior() {
		if (txtHoraEntradaMaior == null) {
			txtHoraEntradaMaior = (Spinner) findViewById(R.id.txtHoraEntradaMaior);
		}
		return txtHoraEntradaMaior;
	}

	private TextView lblHoraAlmoco = null;

	/*
	 * Controle da tela: lblHoraAlmoco.
	 */
	protected TextView getLblHoraAlmoco() {
		if (lblHoraAlmoco == null) {
			lblHoraAlmoco = (TextView) findViewById(R.id.lblHoraAlmoco);
		}
		return lblHoraAlmoco;
	}

	private Spinner txtHoraAlmoco = null;

	/*
	 * Controle da tela: txtHoraAlmoco.
	 */
	protected Spinner getTxtHoraAlmoco() {
		if (txtHoraAlmoco == null) {
			txtHoraAlmoco = (Spinner) findViewById(R.id.txtHoraAlmoco);
		}
		return txtHoraAlmoco;
	}

	private TextView lblHoraJornada = null;

	/*
	 * Controle da tela: lblHoraJornada.
	 */
	protected TextView getLblHoraJornada() {
		if (lblHoraJornada == null) {
			lblHoraJornada = (TextView) findViewById(R.id.lblHoraJornada);
		}
		return lblHoraJornada;
	}

	private Spinner txtHoraJornada = null;

	/*
	 * Controle da tela: txtHoraJornada.
	 */
	protected Spinner getTxtHoraJornada() {
		if (txtHoraJornada == null) {
			txtHoraJornada = (Spinner) findViewById(R.id.txtHoraJornada);
		}
		return txtHoraJornada;
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
		return R.layout.janela_configuracaodashoras;
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
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
				this, R.array.Geral_ListaHorasDoDia,
				android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
		getTxtHoraEntradaMenor().setAdapter(adapter);
		getTxtHoraEntradaMaior().setAdapter(adapter);
		getTxtHoraAlmoco().setAdapter(adapter);
		getTxtHoraJornada().setAdapter(adapter);

		getTxtHoraEntradaMenor().setSelection(
				Integer.parseInt(Definicoes.INSTANCIA
						.Ler(Definicoes.DEF_HORA_MENOR_PERMITIDA)));
		getTxtHoraEntradaMaior().setSelection(
				Integer.parseInt(Definicoes.INSTANCIA
						.Ler(Definicoes.DEF_HORA_MAIOR_PERMITIDA)));
		getTxtHoraAlmoco().setSelection(
				Integer.parseInt(Definicoes.INSTANCIA
						.Ler(Definicoes.DEF_HORA_ALMOCO)));
		getTxtHoraJornada().setSelection(
				Integer.parseInt(Definicoes.INSTANCIA
						.Ler(Definicoes.DEF_HORA_JORNADA)));
	}

	/*
	 * Salvar as informações.
	 */
	private void SalvarConfiguracoes() {
		if (ValidarCampos()) {
			Definicoes.INSTANCIA.Gravar(Definicoes.DEF_HORA_MENOR_PERMITIDA,
					((Integer) getTxtHoraEntradaMenor()
							.getSelectedItemPosition()).toString());
			Definicoes.INSTANCIA.Gravar(Definicoes.DEF_HORA_MAIOR_PERMITIDA,
					((Integer) getTxtHoraEntradaMaior()
							.getSelectedItemPosition()).toString());
			Definicoes.INSTANCIA.Gravar(Definicoes.DEF_HORA_ALMOCO,
					((Integer) getTxtHoraAlmoco().getSelectedItemPosition())
							.toString());
			Definicoes.INSTANCIA.Gravar(Definicoes.DEF_HORA_JORNADA,
					((Integer) getTxtHoraJornada().getSelectedItemPosition())
							.toString());
			Horarios.INSTANCIA.RecalcularHorarios();
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
		int horaEntradaMaior = getTxtHoraEntradaMaior()
				.getSelectedItemPosition();
		int horaEntradaMenor = getTxtHoraEntradaMenor()
				.getSelectedItemPosition();
		int horaAlmoco = getTxtHoraAlmoco().getSelectedItemPosition();
		int horaJornada = getTxtHoraJornada().getSelectedItemPosition();

		if (horaEntradaMaior - horaEntradaMenor <= 0) {
			ExibirMensagem(R.string.JanelaConfiguracaoDasHoras_msgHoraMenorEMaior);
			return false;
		} else if (horaAlmoco >= horaJornada) {
			ExibirMensagem(R.string.JanelaConfiguracaoDasHoras_msgHoraAlmocoMaior);
			return false;
		} else if (horaJornada > (horaEntradaMaior - horaEntradaMenor - horaAlmoco)) {
			ExibirMensagem(R.string.JanelaConfiguracaoDasHoras_msgHoraJornadaImpossivel);
			return false;
		}
		return true;
	}

}