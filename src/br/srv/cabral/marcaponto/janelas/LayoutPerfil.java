/**
 * 
 */
package br.srv.cabral.marcaponto.janelas;

import java.io.IOException;
import java.util.HashMap;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Environment;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import br.srv.cabral.marcaponto.R;
import br.srv.cabral.marcaponto.suporte.IO;

/**
 * Classe que configura o layout da janela: Erro.
 * 
 * @author Sergio Cabral (sergio@cabral.srv.br) *
 */
public class LayoutPerfil extends LayoutDeJanela {

	/**
	 * Armazena valores para uso interno desta classe.
	 */
	private HashMap<String, String> info = new HashMap<String, String>();

	public Integer getResourceMenuId() {
		return 0;
	}

	public boolean onOptionsItemSelected(MenuItem item) {
		return false;
	}

	public boolean onContextItemSelected(MenuItem item) {
		Intent intent = item.getIntent();
		if (intent.getAction() == ((Button) JanelaBase
				.findViewById(R.id.btnSelecionar)).getText()) {
			return AcaoSelecionar(item.getTitle().toString());
		} else if (intent.getAction() == ((Button) JanelaBase
				.findViewById(R.id.btnRenomear)).getText()) {
			return AcaoRenomear(item.getTitle().toString());
		} else if (intent.getAction() == ((Button) JanelaBase
				.findViewById(R.id.btnApagar)).getText()) {
			return AcaoApagar(item.getTitle().toString());
		} else if (intent.getAction() == ((Button) JanelaBase
				.findViewById(R.id.btnCopiar)).getText()) {
			return AcaoCopiar(item.getTitle().toString());
		} else if (intent.getAction() == ((Button) JanelaBase
				.findViewById(R.id.btnExportar)).getText()) {
			return AcaoExportar(item.getTitle().toString());
		}
		return false;
	}

	public void onCreateContextMenu(ContextMenu menu, View view,
			ContextMenuInfo menuInfo) {
		Intent intent = new Intent(((Button) view).getText().toString());
		TextView lblPerfilSelecionado = ((TextView) JanelaBase
				.findViewById(R.id.lblPerfilSelecionado));
		boolean menuParaSelecionar = view == JanelaBase
				.findViewById(R.id.btnSelecionar);
		menu.setHeaderTitle(((Button) view).getText());
		for (String item : JanelaBase.Perfil.getListarDePerfis()) {
			MenuItem menuItem = menu.add(item);
			if (menuParaSelecionar) {
				menuItem.setCheckable(true);
				menuItem.setChecked(lblPerfilSelecionado.getText().equals(item));
			}
			menuItem.setIntent(intent);
		}
	}

	@Override
	protected int getLayoutResourceId() {
		return R.layout.perfil;
	}

	@Override
	protected void ConfigurarControles() {
		Button btnVoltar = ((Button) JanelaBase.findViewById(R.id.btnVoltar));
		btnVoltar.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				FecharJanela();
			}
		});

		ConfigurarControles_BotoesDeGerenciamento();
		ConfigurarControles_PerfilSelecionado();
		ConfigurarControles_CaminhoDosPerfis();
	}

	/**
	 * Configura os botões que gerenciam os perfis.
	 */
	private void ConfigurarControles_BotoesDeGerenciamento() {
		Button btnSelecionar = ((Button) JanelaBase
				.findViewById(R.id.btnSelecionar));
		Button btnAdicionar = ((Button) JanelaBase
				.findViewById(R.id.btnAdicionar));
		Button btnRenomear = ((Button) JanelaBase
				.findViewById(R.id.btnRenomear));
		Button btnApagar = ((Button) JanelaBase.findViewById(R.id.btnApagar));
		Button btnCopiar = ((Button) JanelaBase.findViewById(R.id.btnCopiar));
		Button btnExportar = ((Button) JanelaBase
				.findViewById(R.id.btnExportar));

		JanelaBase.registerForContextMenu(btnSelecionar);
		JanelaBase.registerForContextMenu(btnRenomear);
		JanelaBase.registerForContextMenu(btnApagar);
		JanelaBase.registerForContextMenu(btnCopiar);
		JanelaBase.registerForContextMenu(btnExportar);
		OnClickListener btnComSelecaoDePerfil = new OnClickListener() {
			public void onClick(View v) {
				v.showContextMenu();
			}
		};
		btnSelecionar.setOnClickListener(btnComSelecaoDePerfil);
		btnRenomear.setOnClickListener(btnComSelecaoDePerfil);
		btnApagar.setOnClickListener(btnComSelecaoDePerfil);
		btnCopiar.setOnClickListener(btnComSelecaoDePerfil);
		btnExportar.setOnClickListener(btnComSelecaoDePerfil);
		btnAdicionar.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				AcaoAdicionar();
			}
		});
	}

	/**
	 * Configura os controles que exibem o perfil selecionado.
	 */
	private void ConfigurarControles_PerfilSelecionado() {
		try {
			JanelaBase.Perfil.SelecionarPadrao();
			TextView lblPerfilSelecionado = ((TextView) JanelaBase
					.findViewById(R.id.lblPerfilSelecionado));
			lblPerfilSelecionado.setText(JanelaBase.Perfil
					.getPerfilSelecionado());
		} catch (Exception e) {
			JanelaBase.ExibirErro(String.format(JanelaBase.getResources()
					.getString(R.string.erro_FalhaAoAdicionarPerfil),
					JanelaBase.Perfil.getNomePadraoParaPerfil()));
			FecharJanela();
		}
		JanelaBase.AtualizarTitulo();
	}

	/**
	 * Configura os controles que exibem o caminho onde são salvos os perfil.
	 */
	private void ConfigurarControles_CaminhoDosPerfis() {
		TextView lblCaminhoDosPerfis = ((TextView) JanelaBase
				.findViewById(R.id.lblCaminhoDosPerfis));
		lblCaminhoDosPerfis.setText(JanelaBase.Perfil.getCaminhoDeGravacao());
	}

	/**
	 * Executar a ação: Selecionar
	 * 
	 * @param perfil
	 */
	private boolean AcaoSelecionar(String perfil) {
		try {
			JanelaBase.Perfil.Selecionar(perfil);
			JanelaBase
					.ExibirMensagemToast(String
							.format(JanelaBase
									.getResources()
									.getString(
											R.string.layoutConfiguracoesPerfil_msgSelecionarSucesso),
									perfil));
			ConfigurarControles_PerfilSelecionado();
		} catch (Exception e) {
		}
		FecharJanela();
		return true;
	}

	/**
	 * Executar a ação: Adicionar
	 */
	private void AcaoAdicionar() {
		JanelaBase.ExibirMensagemEReceberValor("", JanelaBase.getResources()
				.getString(R.string.layoutConfiguracoesPerfil_msgAdicionar),
				new DialogoRecebeValor.OnReceberValorListener() {

					public void Resposta(String perfilNovo) {
						if (!IO.NomeDeArquivoEValido(perfilNovo)) {
							JanelaBase.ExibirMensagemToast(String
									.format(JanelaBase
											.getResources()
											.getString(
													R.string.layoutConfiguracoesPerfil_msgFalhaNomeInvalido),
											perfilNovo));
						} else if (!JanelaBase.Perfil.Existe(perfilNovo)) {
							JanelaBase.Perfil.Adicionar(perfilNovo);
							JanelaBase.ExibirMensagemToast(String
									.format(JanelaBase
											.getResources()
											.getString(
													R.string.layoutConfiguracoesPerfil_msgAdicionarSucesso),
											perfilNovo));
							try {
								JanelaBase.Perfil.Selecionar(perfilNovo);
								ConfigurarControles_PerfilSelecionado();
							} catch (Exception e) {
							}
						} else {
							JanelaBase.ExibirMensagemToast(String
									.format(JanelaBase
											.getResources()
											.getString(
													R.string.layoutConfiguracoesPerfil_msgFalhaNomeJaExiste),
											perfilNovo));
						}
					}
				});
	}

	/**
	 * Executar a ação: Renomear
	 * 
	 * @param perfil
	 */
	private boolean AcaoRenomear(String perfil) {
		info.remove("AcaoRenomearPerfil");
		info.put("AcaoRenomearPerfil", perfil);
		JanelaBase
				.ExibirMensagemEReceberValor(
						perfil,
						String.format(
								JanelaBase
										.getResources()
										.getString(
												R.string.layoutConfiguracoesPerfil_msgRenomear),
								perfil),
						new DialogoRecebeValor.OnReceberValorListener() {

							public void Resposta(String perfilNovo) {
								if (!IO.NomeDeArquivoEValido(perfilNovo)) {
									JanelaBase.ExibirMensagemToast(String
											.format(JanelaBase
													.getResources()
													.getString(
															R.string.layoutConfiguracoesPerfil_msgFalhaNomeInvalido),
													perfilNovo));
								} else if (perfilNovo.equals(info
										.get("AcaoRenomearPerfil"))) {
									return;
								} else if (!JanelaBase.Perfil
										.Existe(perfilNovo)) {
									JanelaBase.Perfil.Renomear(
											info.get("AcaoRenomearPerfil"),
											perfilNovo);
									JanelaBase.ExibirMensagemToast(String
											.format(JanelaBase
													.getResources()
													.getString(
															R.string.layoutConfiguracoesPerfil_msgRenomearSucesso),
													info.get("AcaoRenomearPerfil"),
													perfilNovo));
									ConfigurarControles_PerfilSelecionado();
								} else {
									JanelaBase.ExibirMensagemToast(String
											.format(JanelaBase
													.getResources()
													.getString(
															R.string.layoutConfiguracoesPerfil_msgFalhaNomeJaExiste),
													perfilNovo));
								}
							}
						});
		return true;
	}

	/**
	 * Executar a ação: Apagar
	 * 
	 * @param perfil
	 */
	private boolean AcaoApagar(String perfil) {
		info.remove("AcaoApagarPerfil");
		info.put("AcaoApagarPerfil", perfil);
		JanelaBase.ExibirMensagemPergunta(String.format(
				JanelaBase.getResources().getString(
						R.string.layoutConfiguracoesPerfil_msgApagar), perfil),
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						JanelaBase.Perfil.Excluir(info.get("AcaoApagarPerfil"));
						JanelaBase.ExibirMensagemToast(String
								.format(JanelaBase
										.getResources()
										.getString(
												R.string.layoutConfiguracoesPerfil_msgApagarSucesso),
										info.get("AcaoApagarPerfil")));
						try {
							if (JanelaBase.Perfil.SelecionarPadrao()) {
								JanelaBase.ExibirMensagemToast(String
										.format(JanelaBase
												.getResources()
												.getString(
														R.string.layoutConfiguracoesPerfil_msgPerfilPadraoRecriado),
												JanelaBase.Perfil
														.getPerfilSelecionado()));

							}
						} catch (Exception e) {
						}
						ConfigurarControles_PerfilSelecionado();
					}
				}, new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						return;
					}
				});
		return true;
	}

	/**
	 * Executar a ação: Exportar
	 * 
	 * @param perfil
	 */
	private boolean AcaoExportar(String perfil) {
		info.remove("AcaoExportarPerfil");
		info.put("AcaoExportarPerfil", perfil);
		JanelaBase
				.ExibirMensagemEReceberValor(
						Environment.getExternalStorageDirectory().getAbsolutePath(),
						String.format(
								JanelaBase
										.getResources()
										.getString(
												R.string.layoutConfiguracoesPerfil_msgExportar),
								perfil),
						new DialogoRecebeValor.OnReceberValorListener() {

							public void Resposta(String caminho) {
								try {
									JanelaBase.Perfil.Exportar(
											info.get("AcaoExportarPerfil"),
											info.get("AcaoExportarPerfil"),
											caminho);
									JanelaBase.ExibirMensagemToast(String
											.format(JanelaBase
													.getResources()
													.getString(
															R.string.layoutConfiguracoesPerfil_msgExportarSucesso),
													info.get("AcaoExportarPerfil"),
													caminho));
								} catch (Exception e) {
									e.printStackTrace();
									JanelaBase
											.ExibirMensagemToast(JanelaBase
													.getResources()
													.getString(
															R.string.layoutConfiguracoesPerfil_msgExportarFalha));
								}
							}
						});
		return true;
	}

	/**
	 * Executar a ação: Copiar
	 * 
	 * @param perfil
	 */
	private boolean AcaoCopiar(String perfil) {
		info.remove("AcaoCopiarPerfil");
		info.put("AcaoCopiarPerfil", perfil);
		JanelaBase.ExibirMensagemEReceberValor(perfil, String.format(
				JanelaBase.getResources().getString(
						R.string.layoutConfiguracoesPerfil_msgCopiar), perfil),
				new DialogoRecebeValor.OnReceberValorListener() {

					public void Resposta(String perfilNovo) {
						if (!IO.NomeDeArquivoEValido(perfilNovo)) {
							JanelaBase.ExibirMensagemToast(String
									.format(JanelaBase
											.getResources()
											.getString(
													R.string.layoutConfiguracoesPerfil_msgFalhaNomeInvalido),
											perfilNovo));
						} else if (!JanelaBase.Perfil.Existe(perfilNovo)) {
							try {
								JanelaBase.Perfil.Copiar(
										info.get("AcaoCopiarPerfil"),
										perfilNovo);
								JanelaBase.ExibirMensagemToast(String
										.format(JanelaBase
												.getResources()
												.getString(
														R.string.layoutConfiguracoesPerfil_msgCopiarSucesso),
												info.get("AcaoCopiarPerfil"),
												perfilNovo));
							} catch (IOException e) {
								e.printStackTrace();
								JanelaBase
										.ExibirMensagemToast(JanelaBase
												.getResources()
												.getString(
														R.string.layoutConfiguracoesPerfil_msgCopiarFalha));
							}
						} else {
							JanelaBase.ExibirMensagemToast(String
									.format(JanelaBase
											.getResources()
											.getString(
													R.string.layoutConfiguracoesPerfil_msgFalhaNomeJaExiste),
											perfilNovo));
						}
					}
				});
		return true;
	}
}
