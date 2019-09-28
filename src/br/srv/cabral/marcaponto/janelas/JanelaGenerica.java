package br.srv.cabral.marcaponto.janelas;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import br.srv.cabral.marcaponto.R;
import br.srv.cabral.marcaponto.janelas.DialogoSelecionarCor.OnColorChangedListener;
import br.srv.cabral.marcaponto.suporte.Constantes;
import br.srv.cabral.marcaponto.suporte.GerenciadorDeHorarios;
import br.srv.cabral.marcaponto.suporte.Perfil;
import br.srv.cabral.marcaponto.suporte.Preferencias;

/**
 * Classe ancestral de toda janela do aplicativo.
 * 
 * @author Sergio Cabral (sergio@cabral.srv.br)
 */
public class JanelaGenerica extends Activity implements ILayoutDeJanelaListener {

	/**
	 * Disparado quando a janela é criada pela primeira vez.
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Preferencias = new Preferencias(this);
		Perfil = new Perfil(this);
		GerenciadorDeHorarios = new GerenciadorDeHorarios(this);
		ExibirLayout(ObterLayout(), false);
	}

	/**
	 * Disparado quando a janela é exibida.
	 */
	@Override
	public void onStart() {
		super.onStart();
		AtualizarTitulo();
		UltimoLayout.AoExibirJanela();
	}
	
	/**
	 * Disparado sempre que a janela é exibida ao usuário.
	 */
	protected void AoExibirJanela()
	{		
	}

	/**
	 * Atualiza a exibição do título do aplicativo.
	 */
	public void AtualizarTitulo() {
		try {
			Perfil.SelecionarPadrao();
		} catch (Exception e) {
		}
		String perfil = Perfil.getPerfilSelecionado();
		setTitle(getResources().getString(R.string.geral_NomeDoSistema)
				+ (perfil.length() == 0 ? "" : ": " + perfil));
	}

	/**
	 * Inicializa o menu da janela.
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		Integer resourceMenuId = UltimoLayout.getResourceMenuId();
		if (resourceMenuId > 0) {
			MenuInflater inflater = getMenuInflater();
			inflater.inflate(resourceMenuId, menu);
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Inicializa menu de contexto para a janela.
	 */
	@Override
	public void onCreateContextMenu(ContextMenu menu, View view,
			ContextMenu.ContextMenuInfo menuInfo) {
		if (UltimoLayout != null) {
			UltimoLayout.onCreateContextMenu(menu, view, menuInfo);
		} else {
			super.onCreateContextMenu(menu, view, menuInfo);
		}
	}

	/**
	 * Disparado quando um item de menu é acionado.
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (UltimoLayout != null) {
			return UltimoLayout.onOptionsItemSelected(item);
		} else {
			return super.onOptionsItemSelected(item);
		}
	}

	/**
	 * Disparado quando um item de menu de conexto é acionado.
	 */
	@Override
	public boolean onContextItemSelected(MenuItem item) {
		if (UltimoLayout != null) {
			return UltimoLayout.onContextItemSelected(item);
		} else {
			return super.onContextItemSelected(item);
		}
	}

	/**
	 * Gerencia as preferências do aplicativo.
	 */
	public Preferencias Preferencias;

	/**
	 * Gerencia os perfis do usuário.
	 */
	public Perfil Perfil;

	/**
	 * Gerencia os horários.
	 */
	public GerenciadorDeHorarios GerenciadorDeHorarios;

	/**
	 * Quando ==true indica que o aplicativo já está em execução.
	 */
	public static boolean AplicativoJaIniciado = false;

	/**
	 * Último layout utilizado.
	 */
	protected ILayoutDeJanela UltimoLayout;

	/**
	 * Armazena uma lista de layouts ILayoutDeJanela. Serve de comunicação entre
	 * Janelas.
	 */
	public static final HashMap<Integer, ILayoutDeJanela> layoutsDeJanelas = new HashMap<Integer, ILayoutDeJanela>();

	/**
	 * Exibe uma mensagem de erro para o usuário.
	 * 
	 * @param exception
	 *            Exceção disparada.
	 */
	public void ExibirErro(Exception exception) {
		ExibirErro(exception.getMessage());
	}

	/**
	 * Exibe uma mensagem de erro para o usuário.
	 * 
	 * @param mensagemResId
	 *            Id do recurso da mensagem
	 */
	public void ExibirErro(int mensagemResId) {
		ExibirErro(getResources().getString(mensagemResId));
	}

	/**
	 * Exibe uma mensagem de erro para o usuário.
	 * 
	 * @param mensagem
	 *            Mensagem de erro.
	 */
	public void ExibirErro(String mensagem) {
		HashMap<String, String> mensagens = new HashMap<String, String>();
		mensagens.put(Constantes.INTENT_MSG_ERRO, mensagem);
		ExibirLayout(LayoutErro.class, true, mensagens);
	}

	/**
	 * Exibe uma mensagem tipo Toast para o usuário.
	 * 
	 * @param mensagemResId
	 *            Id do recurso da mensagem
	 */
	public void ExibirMensagemToast(int mensagemResId) {
		ExibirMensagemToast(getResources().getString(mensagemResId));
	}

	/**
	 * Exibe uma mensagem tipo Toast para o usuário.
	 * 
	 * @param mensagem
	 *            Mensagem
	 */
	public void ExibirMensagemToast(String mensagem) {
		ExibirMensagemToast(mensagem, R.drawable.icone_informacao);
	}

	/**
	 * Exibe uma mensagem de erro tipo Toast para o usuário.
	 * 
	 * @param mensagemResId
	 *            Id do recurso da mensagem
	 */
	public void ExibirErroToast(int mensagemResId) {
		ExibirErroToast(getResources().getString(mensagemResId));
	}

	/**
	 * Exibe uma mensagem de erro tipo Toast para o usuário.
	 * 
	 * @param mensagem
	 *            Mensagem
	 */
	public void ExibirErroToast(String mensagem) {
		ExibirMensagemToast(mensagem, R.drawable.icone_erro);
	}

	/**
	 * Exibe uma mensagem tipo Toast para o usuário.
	 * 
	 * @param mensagem
	 *            Mensagem
	 */
	private void ExibirMensagemToast(String mensagem, int iconeResId) {
		LayoutInflater inflater = getLayoutInflater();
		View layoutRaiz = inflater.inflate(R.layout.toast,
				(ViewGroup) findViewById(R.id.layoutRaiz));
		TextView lblMensagem = (TextView) layoutRaiz
				.findViewById(R.id.lblMensagem);
		ImageView imgIcone = (ImageView) layoutRaiz.findViewById(R.id.imgIcone);
		lblMensagem.setText(mensagem);

		imgIcone.setImageResource(iconeResId);

		Toast toast = new Toast(getApplicationContext());
		toast.setDuration(Toast.LENGTH_SHORT);
		toast.setView(layoutRaiz);
		toast.show();

		return;
	}

	/**
	 * Exibe uma caixa de diálogo para seleção de cores.
	 * 
	 * @param onColorChangedListener
	 * @param corInicial
	 */
	public void ExibirSelecaoDeCor(
			OnColorChangedListener onColorChangedListener, int corInicial) {
		DialogoSelecionarCor colorDialog = new DialogoSelecionarCor(this,
				onColorChangedListener, corInicial);
		colorDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		colorDialog.show();
	}

	/**
	 * Exibe uma caixa de diálogo para receber um valor tipo texto do usuário.
	 * 
	 * @param mensagem
	 * @param onClickListener
	 */
	public void ExibirMensagemEReceberValor(String valorInicial,
			String mensagem,
			DialogoRecebeValor.OnReceberValorListener onReceberValorListener) {
		DialogoRecebeValor dialog = new DialogoRecebeValor(this, valorInicial,
				mensagem, onReceberValorListener);
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog.show();
	}

	/**
	 * Configura uma alerta para exibir um alerta.
	 * 
	 * @param mensagemResId
	 *            Id do recurso da mensagem
	 * @param botaoOk
	 *            Evento do botão quando acionado.
	 */
	public void ExibirMensagemAlerta(int mensagemResId, OnClickListener botaoOk) {
		ConfigurarMensagem(R.drawable.icone_alerta, R.string.comum_msgAlerta,
				mensagemResId, botaoOk, null).show();
	}

	/**
	 * Configura uma alerta para exibir um alerta.
	 * 
	 * @param mensagem
	 *            Mensagem
	 * @param botaoOk
	 *            Evento do botão quando acionado.
	 */
	public void ExibirMensagemAlerta(String mensagem, OnClickListener botaoOk) {
		ConfigurarMensagem(R.drawable.icone_alerta, R.string.comum_msgAlerta,
				mensagem, botaoOk, null).show();
	}

	/**
	 * Configura uma alerta para exibir uma pergunta.
	 * 
	 * @param mensagemResId
	 *            Id do recurso da mensagem
	 * @param botaoSim
	 *            Evento do botão Sim quando acionado.
	 * @param botaoNao
	 *            Evento do botão Nao quando acionado.
	 */
	public void ExibirMensagemPergunta(int mensagemResId,
			OnClickListener botaoSim, OnClickListener botaoNao) {
		ConfigurarMensagem(R.drawable.icone_pergunta,
				R.string.comum_msgPergunta, mensagemResId, botaoSim, botaoNao)
				.show();
	}

	/**
	 * Configura uma alerta para exibir uma pergunta.
	 * 
	 * @param mensagem
	 *            Mensagem
	 * @param botaoSim
	 *            Evento do botão Sim quando acionado.
	 * @param botaoNao
	 *            Evento do botão Nao quando acionado.
	 */
	public void ExibirMensagemPergunta(String mensagem,
			OnClickListener botaoSim, OnClickListener botaoNao) {
		ConfigurarMensagem(R.drawable.icone_pergunta,
				R.string.comum_msgPergunta, mensagem, botaoSim, botaoNao)
				.show();
	}

	/**
	 * Configura uma alerta para exibição.
	 * 
	 * @param iconeResId
	 *            Id do recurso do ícone
	 * @param tituloResId
	 *            Id do recurso da mensagem com o título
	 * @param mensagemResId
	 *            Id do recurso da mensagem
	 * @param botao1
	 *            Evento do botão1 quando acionado.
	 * @param botao2
	 *            Evento do botão2 quando acionado.
	 * @return
	 */
	private AlertDialog ConfigurarMensagem(int iconeResId, int tituloResId,
			int mensagemResId, OnClickListener botao1, OnClickListener botao2) {
		return ConfigurarMensagem(iconeResId, tituloResId, getResources()
				.getString(mensagemResId), botao1, botao2);
	}

	/**
	 * Configura uma alerta para exibição.
	 * 
	 * @param iconeResId
	 *            Id do recurso do ícone
	 * @param tituloResId
	 *            Id do recurso da mensagem com o título
	 * @param mensagem
	 *            Mensagem
	 * @param botao1
	 *            Evento do botão1 quando acionado.
	 * @param botao2
	 *            Evento do botão2 quando acionado.
	 * @return
	 */
	private AlertDialog ConfigurarMensagem(int iconeResId, int tituloResId,
			String mensagem, OnClickListener botao1, OnClickListener botao2) {
		Resources resources = getResources();
		AlertDialog alerta = new AlertDialog.Builder(this).create();
		alerta.setTitle(resources.getString(tituloResId));
		alerta.setIcon(iconeResId);
		alerta.setMessage(mensagem);
		if (botao2 == null) {
			alerta.setButton(resources.getString(R.string.comum_btnOk), botao1);
		} else {
			alerta.setButton(resources.getString(R.string.comum_btnSim), botao1);
			alerta.setButton2(resources.getString(R.string.comum_btnNao),
					botao2);
		}
		return alerta;
	}

	/**
	 * Exibe um layout de janela
	 * 
	 * @param classLayout
	 *            Classe do layout a ser exibido.
	 * @param novaJanela
	 *            Indica se uma nova janela deve ser criada.
	 */
	protected void ExibirLayout(Class<?> classLayout, Boolean novaJanela) {
		ExibirLayout(classLayout, novaJanela, null);
	}

	/**
	 * Exibe um layout de janela
	 * 
	 * @param classLayout
	 *            Classe do layout a ser exibido.
	 * @param novaJanela
	 *            Indica se uma nova janela deve ser criada.
	 */
	protected void ExibirLayout(Class<?> classLayout, Boolean novaJanela,
			Map<String, String> mensagens) {
		ILayoutDeJanela layout = null;
		try {
			layout = (ILayoutDeJanela) classLayout.getConstructor(new Class[0])
					.newInstance(new Object[0]);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		}
		ExibirLayout(layout, novaJanela, mensagens);
	}

	/**
	 * Exibe um layout de janela
	 * 
	 * @param layout
	 *            Layout a ser exibido.
	 * @param novaJanela
	 *            Indica se uma nova janela deve ser criada.
	 */
	protected void ExibirLayout(ILayoutDeJanela layout, Boolean novaJanela) {
		ExibirLayout(layout, novaJanela, null);
	}

	/**
	 * Exibe um layout de janela
	 * 
	 * @param layout
	 *            Layout a ser exibido.
	 * @param novaJanela
	 *            Indica se uma nova janela deve ser criada.
	 * @param mensagens
	 *            Mensagens transmitidas através do Intent.
	 */
	protected void ExibirLayout(ILayoutDeJanela layout, Boolean novaJanela,
			Map<String, String> mensagens) {
		if (layout != null) {
			if (novaJanela) {
				int key;
				do {
					key = (new Random()).nextInt();
				} while (layoutsDeJanelas.containsKey(key));

				// Visto que o ambiente não é multi-janelas podemos excluir
				// todas as janelas abertas.
				layoutsDeJanelas.clear();

				layoutsDeJanelas.put(key, layout);
				Intent intent = new Intent(this, JanelaGenerica.class);
				intent.putExtra(Constantes.INTENT_MSG_LAYOUT, key);
				if (mensagens != null) {
					for (String nome : mensagens.keySet()) {
						intent.putExtra(nome, mensagens.get(nome));
					}
				}
				startActivityFromChild(this, intent, -1);
			} else {
				UltimoLayout = layout;
				layout.addListener(this);
				layout.Configurar(this);
			}
		}
	}

	/**
	 * Obtem o layout caso tenha sido informado.
	 * 
	 * @return
	 */
	private ILayoutDeJanela ObterLayout() {
		Bundle bundle = getIntent().getExtras();
		if (bundle != null && bundle.containsKey(Constantes.INTENT_MSG_LAYOUT)) {
			int key = bundle.getInt(Constantes.INTENT_MSG_LAYOUT, -1);
			if (layoutsDeJanelas.containsKey(key))
				return layoutsDeJanelas.get(key);
		}
		return null;
	}

	public void EventoFecharJanela(LayoutDeJanelaEvento e) {
		finish();
	}
}
