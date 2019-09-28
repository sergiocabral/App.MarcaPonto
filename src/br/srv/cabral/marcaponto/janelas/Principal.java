package br.srv.cabral.marcapontolite.janelas;

import java.util.HashMap;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import br.srv.cabral.marcapontolite.R;
import br.srv.cabral.marcapontolite.dados.HorariosColumns;
import br.srv.cabral.marcapontolite.util.Definicoes;
import br.srv.cabral.marcapontolite.util.Funcoes;
import br.srv.cabral.marcapontolite.util.Horarios;

/* Janela principal. 
 */
public class Principal extends ListActivity {
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

	private Button btnRegistrar = null;

	/*
	 * Controle da tela: btnRegistrar.
	 */
	protected Button getBtnRegistrar() {
		if (btnRegistrar == null) {
			btnRegistrar = (Button) findViewById(R.id.btnRegistrar);
		}
		return btnRegistrar;
	}

	private TextView lblHoras = null;

	/*
	 * Controle da tela: lblHoras.
	 */
	protected TextView getLblHoras() {
		if (lblHoras == null) {
			lblHoras = (TextView) findViewById(R.id.lblHoras);
		}
		return lblHoras;
	}

	private TextView lblPeriodo = null;

	/*
	 * Controle da tela: lblPeriodo.
	 */
	protected TextView getLblPeriodo() {
		if (lblPeriodo == null) {
			lblPeriodo = (TextView) findViewById(R.id.lblPeriodo);
		}
		return lblPeriodo;
	}

	private ListView lstHorarios = null;

	/*
	 * Controle da tela: lstHorarios.
	 */
	protected ListView getLstHorarios() {
		if (lstHorarios == null) {
			lstHorarios = (ListView) findViewById(android.R.id.list);
		}
		return lstHorarios;
	}

	/*
	 * Called when the activity is first created.
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Definicoes.INSTANCIA.janelaBase = this;
		if (Definicoes.INSTANCIA.RegistrarDefinicoesPadrao(false)) {
			ExibirBoasVindas();
		}

		setContentView(R.layout.janela_principal);
		AtribuirEventos();
	}

	private void ExibirBoasVindas() {
		new AlertDialog.Builder(this).setTitle(R.string.JanelaPrincipal)
				.setMessage(R.string.JanelaPrincipal_msgBoasVindas)
				.setPositiveButton(android.R.string.ok, null).show();
	}

	/*
	 * Called when the activity is first created.
	 */
	@Override
	public void onResume() {
		super.onResume();
		AtualizarInformacoes();
		CarregarControles();
	}

	/**
	 * Inicializa o menu da janela.
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add(getResources().getString(R.string.JanelaConfiguracaoDasHoras));
		menu.add(getResources().getString(R.string.JanelaConfiguracaoDoPeriodo));
		return true;
	}

	/**
	 * Disparado quando um item de menu é acionado.
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getTitle().equals(
				getResources().getString(R.string.JanelaConfiguracaoDasHoras))) {
			ExibirJanelaDeConfiguracaoDasHoras();
		} else if (item.getTitle().equals(
				getResources().getString(R.string.JanelaConfiguracaoDoPeriodo))) {
			ExibirJanelaDeConfiguracaoDoPeriodo();
		}
		return true;
	}

	/*
	 * Atribui os eventos.
	 */
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
		getBtnRegistrar().setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				RegistrarHorarios();
			}
		});
		getLblHoras().setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				ExibirJanelaDeConfiguracaoDasHoras();
			}
		});
		getLblPeriodo().setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				ExibirJanelaDeConfiguracaoDoPeriodo();
			}
		});
		getLstHorarios().setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> adapter, View view,
					int index, long l) {
				ProcessarClickNaLista(view, index);
			}
		});

		getBtnHora1().setOnLongClickListener(new View.OnLongClickListener() {
			public boolean onLongClick(View v) {
				ExibirBoasVindas();
				return false;
			}
		});
		getBtnHora2().setOnLongClickListener(new View.OnLongClickListener() {
			public boolean onLongClick(View v) {
				ExibirBoasVindas();
				return false;
			}
		});
		getBtnHora3().setOnLongClickListener(new View.OnLongClickListener() {
			public boolean onLongClick(View v) {
				ExibirBoasVindas();
				return false;
			}
		});
		getBtnHora4().setOnLongClickListener(new View.OnLongClickListener() {
			public boolean onLongClick(View v) {
				ExibirBoasVindas();
				return false;
			}
		});
		getBtnRegistrar().setOnLongClickListener(
				new View.OnLongClickListener() {
					public boolean onLongClick(View v) {
						ExibirBoasVindas();
						return false;
					}
				});
		getLblHoras().setOnLongClickListener(new View.OnLongClickListener() {
			public boolean onLongClick(View v) {
				ExibirBoasVindas();
				return false;
			}
		});
		getLblPeriodo().setOnLongClickListener(new View.OnLongClickListener() {
			public boolean onLongClick(View v) {
				ExibirBoasVindas();
				return false;
			}
		});
	}

	/*
	 * Processar clique num horario da lista.
	 */
	private void ProcessarClickNaLista(View view, int index) {
		String data = ((TextView) view.findViewById(R.id.lblData)).getText()
				.toString();
		if (data.indexOf("\n") >= 0) {
			data = data.substring(0, data.indexOf("\n"));
		}
		Intent intent = new Intent(this, RegistrarHoraDoDia.class);
		intent.putExtra("data", data);
		startActivityFromChild(this, intent, -1);
	}

	/*
	 * Registra os quatro horários do dia.
	 */
	private void RegistrarHorarios() {
		Intent intent = new Intent(this, RegistrarHoraDoDia.class);
		startActivityFromChild(this, intent, -1);
	}

	/*
	 * Executa marcação de um horário
	 */
	private void MarcarHorario(Button btnHora) {
		int horario = btnHora == getBtnHora1() ? 1
				: btnHora == getBtnHora2() ? 2 : btnHora == getBtnHora3() ? 3
						: btnHora == getBtnHora4() ? 4 : -1;
		Intent intent = new Intent(this, RegistrarHora.class);
		intent.putExtra("horario", horario);
		startActivityFromChild(this, intent, -1);
	}

	/*
	 * Abre a janela de configuração das horas.
	 */
	private void ExibirJanelaDeConfiguracaoDasHoras() {
		Intent intent = new Intent(this, ConfiguracaoDasHoras.class);
		startActivityFromChild(this, intent, -1);
	}

	/*
	 * Abre a janela de configuração do período.
	 */
	private void ExibirJanelaDeConfiguracaoDoPeriodo() {
		Intent intent = new Intent(this, ConfiguracaoDoPeriodo.class);
		startActivityFromChild(this, intent, -1);
	}

	public class HorariosAdapter extends SimpleCursorAdapter {

		public HorariosAdapter(Context context, int layout, Cursor c,
				String[] from, int[] to) {
			super(context, layout, c, from, to);
		}

		public View getView(int position, View convertView, ViewGroup parent) {
			View view = super.getView(position, convertView, parent);
			TextView lblSaldo = (TextView) view.findViewById(R.id.lblSaldo);
			if (lblSaldo.getText().toString().contains("-")) {
				lblSaldo.setTextColor(Color.parseColor("#FF8080"));
			} else if (lblSaldo.getText().toString().contains("?")) {
				lblSaldo.setTextColor(Color.parseColor("#7CA4FF"));
			} else {
				lblSaldo.setTextColor(Color.parseColor("#00FF80"));
			}
			return view;
		}
	}

	/*
	 * Carrega os controles.
	 */
	protected void CarregarControles() {
		HashMap<Integer, String> parametros = new HashMap<Integer, String>();
		ListAdapter adapter = new HorariosAdapter(this, R.layout.lista_horario,
				Horarios.INSTANCIA.Consultar(parametros), new String[] {
						HorariosColumns.CAMPO_DATA,
						HorariosColumns.CAMPO_HORA1,
						HorariosColumns.CAMPO_HORA2,
						HorariosColumns.CAMPO_HORA3,
						HorariosColumns.CAMPO_HORA4,
						HorariosColumns.CAMPO_SALDO }, new int[] {
						R.id.lblData, R.id.lblHora1, R.id.lblHora2,
						R.id.lblHora3, R.id.lblHora4, R.id.lblSaldo });
		setListAdapter(adapter);

		getLblHoras()
				.setText(
						String.format(Funcoes
								.LerRecurso(R.string.JanelaPrincipal_lblHoras),
								parametros.get(1), parametros.get(2),
								parametros.get(3)));

		if (getLblHoras().getText().toString().contains("-")) {
			getLblHoras().setTextColor(Color.parseColor("#FF8080"));
			getLblPeriodo().setTextColor(Color.parseColor("#FF8080"));
		} else {
			getLblHoras().setTextColor(Color.parseColor("#00FF80"));
			getLblPeriodo().setTextColor(Color.parseColor("#00FF80"));
		}
	}

	/*
	 * Atualiza as informações dos labels da tela.
	 */
	private void AtualizarInformacoes() {
		String horasTrabalhadas = "?";
		String horasTrabalhadasValidas = "?";
		String saldoDeHoras = "?";
		String periodoInicial = Definicoes.INSTANCIA
				.Ler(Definicoes.DEF_PERIODO_INICIAL);
		String periodoFinal = Definicoes.INSTANCIA
				.Ler(Definicoes.DEF_PERIODO_FINAL);
		String periodo;

		if (periodoInicial.length() > 0 && periodoFinal.length() > 0) {
			periodo = String
					.format(Funcoes
							.LerRecurso(R.string.JanelaPrincipal_msgLblPeriodoInicialEFinal),
							periodoInicial, periodoFinal);
		} else if (periodoInicial.length() > 0 && periodoFinal.length() == 0) {
			periodo = String.format(Funcoes
					.LerRecurso(R.string.JanelaPrincipal_msgLblPeriodoInicial),
					periodoInicial);
		} else if (periodoInicial.length() == 0 && periodoFinal.length() > 0) {
			periodo = String.format(Funcoes
					.LerRecurso(R.string.JanelaPrincipal_msgLblPeriodoFinal),
					periodoFinal);
		} else {
			periodo = Funcoes
					.LerRecurso(R.string.JanelaPrincipal_msgLblPeriodoCompleto);
		}

		getLblHoras()
				.setText(
						String.format(Funcoes
								.LerRecurso(R.string.JanelaPrincipal_lblHoras),
								horasTrabalhadas, horasTrabalhadasValidas,
								saldoDeHoras));
		getLblPeriodo()
				.setText(
						String.format(
								Funcoes.LerRecurso(R.string.JanelaPrincipal_lblPeriodo),
								periodo));

		AtualizarExibicaoDaHora();
	}

	/*
	 * Carrega o valor atual da hora e exibe.
	 */
	protected void AtualizarExibicaoDaHora() {
		Funcoes.AtualizarExibicaoDaHora(getBtnHora1(), 1,
				R.string.JanelaPrincipal_btnHora1, null);
		Funcoes.AtualizarExibicaoDaHora(getBtnHora2(), 2,
				R.string.JanelaPrincipal_btnHora2, null);
		Funcoes.AtualizarExibicaoDaHora(getBtnHora3(), 3,
				R.string.JanelaPrincipal_btnHora3, null);
		Funcoes.AtualizarExibicaoDaHora(getBtnHora4(), 4,
				R.string.JanelaPrincipal_btnHora4, null);
	}

}