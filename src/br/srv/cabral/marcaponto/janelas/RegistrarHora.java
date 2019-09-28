package br.srv.cabral.marcapontolite.janelas;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;
import br.srv.cabral.marcapontolite.R;
import br.srv.cabral.marcapontolite.util.Definicoes;
import br.srv.cabral.marcapontolite.util.Funcoes;

/* Janela principal. 
 */
public class RegistrarHora extends JanelaBase {
	private TextView lblHora = null;

	/*
	 * Controle da tela: lblHora.
	 */
	protected TextView getLblHora() {
		if (lblHora == null) {
			lblHora = (TextView) findViewById(R.id.lblHora);
		}
		return lblHora;
	}

	private TextView lblHoraEx = null;

	/*
	 * Controle da tela: lblHoraEx.
	 */
	protected TextView getLblHoraEx() {
		if (lblHoraEx == null) {
			lblHoraEx = (TextView) findViewById(R.id.lblHoraEx);
		}
		return lblHoraEx;
	}

	private TimePicker txtHora = null;

	/*
	 * Controle da tela: txtHora.
	 */
	protected TimePicker getTxtHora() {
		if (txtHora == null) {
			txtHora = (TimePicker) findViewById(R.id.txtHora);
			txtHora.setIs24HourView(true);
		}
		return txtHora;
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

	/*
	 * Horário sendo gerenciado por esta instância.
	 */
	protected Integer getHorario() {
		return (Integer) this.getIntent().getExtras().get("horario");
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
		return R.layout.janela_registrarhora;
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
	 * (non-Javadoc)
	 * 
	 * @see sjcsj.marcaponto.janelas.JanelaBase#CarregarControles()
	 */
	@Override
	protected void CarregarControles() {
		switch (getHorario()) {
		case 1:
			getLblHoraEx().setText(
					Funcoes.LerRecurso(R.string.JanelaPrincipal_btnHora1));
			break;
		case 2:
			getLblHoraEx().setText(
					Funcoes.LerRecurso(R.string.JanelaPrincipal_btnHora2));
			break;
		case 3:
			getLblHoraEx().setText(
					Funcoes.LerRecurso(R.string.JanelaPrincipal_btnHora3));
			break;
		case 4:
			getLblHoraEx().setText(
					Funcoes.LerRecurso(R.string.JanelaPrincipal_btnHora4));
			break;
		}
	}

	/*
	 * Retorna o nome da definição onde será salvo o dado.
	 */
	private String getDefinicaoNome() {
		return Definicoes.DEF_HORARIO + getHorario().toString()
				+ (getDataParaEdicao().length() > 0 ? "_" : "");
	}

	/*
	 * Carrega o valor atual da hora e exibe.
	 */
	protected void AtualizarExibicaoDaHora() {
		String horario = Definicoes.INSTANCIA.Ler(getDefinicaoNome());
		if (horario == null || horario.equals("-")) {
			horario = "";
		}
		Funcoes.DefinirHora(getTxtHora(), horario);
	}

	/*
	 * Salvar as informações.
	 */
	private void Salvar() {
		if (ValidarCampos()) {
			String hora = Funcoes.ObterHoraEmTexto(getTxtHora());
			Definicoes.INSTANCIA.Gravar(getDefinicaoNome(), hora);

			FecharJanela();
		}
	}

	/*
	 * Salvar as informações.
	 */
	private void Apagar() {
		Definicoes.INSTANCIA.Gravar(getDefinicaoNome(), "");
		FecharJanela();
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
		return true;
	}

}