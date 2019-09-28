/**
 * 
 */
package br.srv.cabral.marcaponto.janelas;

import java.util.Calendar;

import android.graphics.Color;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import br.srv.cabral.marcaponto.R;
import br.srv.cabral.marcaponto.entidades.Horario;
import br.srv.cabral.marcaponto.janelas.DialogoSelecionarCor.OnColorChangedListener;
import br.srv.cabral.marcaponto.suporte.Constantes;

/**
 * Classe que configura o layout da janela: MarcarHorario.
 * 
 * @author Sergio Cabral (sergio@cabral.srv.br)
 */
public class LayoutMarcarHorario extends LayoutDeJanela {

	/**
	 * Indica o modo de funcionamento desta janela: Marca horário de entrada.
	 */
	private static final int MODO_ENTRADA = 1;

	/**
	 * Indica o modo de funcionamento desta janela: Marca horário de saída.
	 */
	private static final int MODO_SAIDA = 2;

	/**
	 * Edica se a janela foi aberta para edição de dados já cadastrados.
	 * 
	 * @return
	 */
	private boolean isEdicao() {
		String modo = JanelaBase.getIntent().getExtras()
				.getString(Constantes.INTENT_MSG_MARCAR_HORARIO_EDICAO);
		return modo == Constantes.INTENT_MSG_MARCAR_HORARIO_EDICAO;
	}

	/**
	 * Retorna o moo de funcionamento da tela: Marca horário de entrada ou
	 * saída.
	 * 
	 * @return Retorna uma das constantes: MODO_SAIDA ou MODO_ENTRADA.
	 */
	private int getModoDeFuncionamento() {
		String modo = JanelaBase.getIntent().getExtras()
				.getString(Constantes.INTENT_MSG_MARCAR_HORARIO);
		String modoSaida = JanelaBase.getResources().getString(
				R.string.layoutPrincipal_btnMarcarSaida);
		if (modo.equals(modoSaida)) {
			return MODO_SAIDA;
		} else {
			return MODO_ENTRADA;
		}
	}

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
		return R.layout.marcar_horario;
	}

	@Override
	protected void ConfigurarControles() {
		int modo = getModoDeFuncionamento();
		if (modo == MODO_ENTRADA) {
			ConfigurarControlesComoEntrada();
		} else if (modo == MODO_SAIDA) {
			ConfigurarControlesComoSaida();
		}

		ConfigurarControlesButton();
		ConfigurarControlesDataEHora();
	}

	/**
	 * Configura os controles tipo Button
	 */
	private void ConfigurarControlesButton() {
		if (!isEdicao()) {
			Button imgApagar1 = (Button) JanelaBase
					.findViewById(R.id.btnApagar1);
			Button imgApagar2 = (Button) JanelaBase
					.findViewById(R.id.btnApagar2);
			imgApagar1.setVisibility(View.GONE);
			imgApagar2.setVisibility(View.GONE);
		}

		Button btnCancelar1 = ((Button) JanelaBase
				.findViewById(R.id.btnCancelar1));
		Button btnCancelar2 = ((Button) JanelaBase
				.findViewById(R.id.btnCancelar2));
		OnClickListener btnCancelar_OnClickListener = new OnClickListener() {
			public void onClick(View v) {
				FecharJanela();
			}
		};
		btnCancelar1.setOnClickListener(btnCancelar_OnClickListener);
		btnCancelar2.setOnClickListener(btnCancelar_OnClickListener);

		Button btnGravar1 = ((Button) JanelaBase.findViewById(R.id.btnGravar1));
		Button btnGravar2 = ((Button) JanelaBase.findViewById(R.id.btnGravar2));
		OnClickListener btnGravar_OnClickListener = new OnClickListener() {
			public void onClick(View v) {
				JanelaBase.GerenciadorDeHorarios.Registrar(
						JanelaBase.Perfil.getPerfilSelecionado(),
						ObterHorarioDosCamposDaTela());
				JanelaBase
						.ExibirMensagemToast(R.string.layoutMarcarHorario_msgHorarioRegistrado);
				FecharJanela();
			}
		};
		btnGravar1.setOnClickListener(btnGravar_OnClickListener);
		btnGravar2.setOnClickListener(btnGravar_OnClickListener);
	}

	/**
	 * Obtem uma entidade preenchida com os campos da tela.
	 */
	private Horario ObterHorarioDosCamposDaTela() {
		TimePicker timePicker = (TimePicker) JanelaBase
				.findViewById(R.id.timePicker);
		DatePicker datePicker = (DatePicker) JanelaBase
				.findViewById(R.id.datePicker);
		TextView txtComentarios = (TextView) JanelaBase
				.findViewById(R.id.txtComentarios);
		Button btnSelecionarCor = (Button) JanelaBase
				.findViewById(R.id.btnSelecionarCor);

		Horario horario = new Horario();
		horario.Modo = getModoDeFuncionamento();
		horario.Ano = datePicker.getYear();
		horario.Mes = datePicker.getMonth();
		horario.Dia = datePicker.getDayOfMonth();
		horario.Hora = timePicker.getCurrentHour();
		horario.Minuto = timePicker.getCurrentMinute();
		horario.Segundo = 0;
		horario.Comentario = txtComentarios.getText().toString();
		horario.Cor = Integer
				.parseInt(String.valueOf(btnSelecionarCor.getTag()));

		return horario;
	}

	/**
	 * Configura os controles que recebem a data e hora.
	 */
	private void ConfigurarControlesDataEHora() {
		Calendar calendar = Calendar.getInstance();

		TimePicker timePicker = (TimePicker) JanelaBase
				.findViewById(R.id.timePicker);
		timePicker.setIs24HourView(true);
		timePicker.setCurrentHour(calendar.get(Calendar.HOUR_OF_DAY));
		timePicker.setCurrentMinute(calendar.get(Calendar.MINUTE));

		DatePicker datePicker = (DatePicker) JanelaBase
				.findViewById(R.id.datePicker);
		datePicker.init(calendar.get(Calendar.YEAR),
				calendar.get(Calendar.MONTH),
				calendar.get(Calendar.DAY_OF_MONTH), null);
		Button btnSelecionarCor = (Button) JanelaBase
				.findViewById(R.id.btnSelecionarCor);
		btnSelecionarCor.setBackgroundColor(0);
		btnSelecionarCor.setTag(0);
		btnSelecionarCor.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				JanelaBase.ExibirSelecaoDeCor(new OnColorChangedListener() {
					public void colorChanged(int color) {
						Button btnSelecionarCor = (Button) JanelaBase
								.findViewById(R.id.btnSelecionarCor);
						btnSelecionarCor.setBackgroundColor(color);
						btnSelecionarCor.setTag(color);
						if (color == 0) {
							btnSelecionarCor.setTextColor(Color.WHITE);
						} else {
							btnSelecionarCor.setTextColor(Color.BLACK);
						}
					}
				}, 0);
			}
		});
	}

	/**
	 * Configura os cotroles da janela para Marcar Horário de Entrada.
	 */
	private void ConfigurarControlesComoEntrada() {
		ImageView imgIcone = (ImageView) JanelaBase.findViewById(R.id.imgIcone);
		TextView lblTitulo = (TextView) JanelaBase.findViewById(R.id.lblTitulo);
		imgIcone.setImageResource(R.drawable.icone_marcar_entrada);
		lblTitulo.setText(R.string.layoutPrincipal_btnMarcarEntrada);
	}

	/**
	 * Configura os cotroles da janela para Marcar Horário de Saida.
	 */
	private void ConfigurarControlesComoSaida() {
		ImageView imgIcone = (ImageView) JanelaBase.findViewById(R.id.imgIcone);
		TextView lblTitulo = (TextView) JanelaBase.findViewById(R.id.lblTitulo);
		imgIcone.setImageResource(R.drawable.icone_marcar_saida);
		lblTitulo.setText(R.string.layoutPrincipal_btnMarcarSaida);
	}

}
