/**
 * 
 */
package br.srv.cabral.marcaponto.janelas;

import java.io.File;

import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import br.srv.cabral.marcaponto.R;
import br.srv.cabral.marcaponto.suporte.IO;
import br.srv.cabral.marcaponto.suporte.Preferencias;

/**
 * Classe que implementa a seção CaminhoDosDadosEConfiguracoes da tela
 * LayoutConfiguracoes.
 * 
 * @author Sergio Cabral (sergio@cabral.srv.br)
 */
public class LayoutConfiguracoes_CaminhoDosDadosEConfiguracoes implements
		ILayoutConfiguracoes_Secao {

	/**
	 * Construtor
	 * 
	 * @param layoutDeJanela
	 *            Layout da janela.
	 */
	public LayoutConfiguracoes_CaminhoDosDadosEConfiguracoes(
			JanelaGenerica janelaBase) {
		JanelaBase = janelaBase;
	}

	/**
	 * Janela
	 */
	protected JanelaGenerica JanelaBase;

	public void ConfigurarControles() {
		EditText txtCaminhoDosDadosEConfiguracoes = ((EditText) JanelaBase
				.findViewById(R.id.txtCaminhoDosDadosEConfiguracoes));
		CheckBox chkCaminhoDosDadosEConfiguracoesPadrao = ((CheckBox) JanelaBase
				.findViewById(R.id.chkCaminhoDosDadosEConfiguracoesPadrao));

		String CaminhoDosDadosEConfiguracoes;
		CaminhoDosDadosEConfiguracoes = JanelaBase.Preferencias
				.getCaminhoDosDadosEConfiguracao();
		chkCaminhoDosDadosEConfiguracoesPadrao
				.setChecked(CaminhoDosDadosEConfiguracoes == "");
		txtCaminhoDosDadosEConfiguracoes
				.setEnabled(CaminhoDosDadosEConfiguracoes != "");
		try {
			txtCaminhoDosDadosEConfiguracoes.setText(JanelaBase.Preferencias
					.getCaminhoDosDadosEConfiguracaoReal());
		} catch (Exception e1) {
			txtCaminhoDosDadosEConfiguracoes.setText("");
		}

		chkCaminhoDosDadosEConfiguracoesPadrao
				.setOnCheckedChangeListener(new OnCheckedChangeListener() {
					public void onCheckedChanged(CompoundButton buttonView,
							boolean isChecked) {
						EditText txtCaminhoDosDadosEConfiguracoes = ((EditText) JanelaBase
								.findViewById(R.id.txtCaminhoDosDadosEConfiguracoes));
						if (isChecked) {
							try {
								txtCaminhoDosDadosEConfiguracoes.setText(IO.CaminhoDatabases());
							} catch (Exception e) {
								txtCaminhoDosDadosEConfiguracoes.setText("");
							}
							txtCaminhoDosDadosEConfiguracoes.setEnabled(false);
						} else {
							String caminho = JanelaBase.Preferencias
									.getCaminhoDosDadosEConfiguracao();
							if (caminho == "") {
								caminho = Preferencias.getCaminhoDosDadosEConfiguracaoNoCartaoDeMemoria();
							}
							txtCaminhoDosDadosEConfiguracoes.setText(caminho);
							txtCaminhoDosDadosEConfiguracoes.setEnabled(true);
						}
					}
				});
	}

	public boolean CamposPreenchidosCorretamente() {
		EditText txtCaminhoDosDadosEConfiguracoes = ((EditText) JanelaBase
				.findViewById(R.id.txtCaminhoDosDadosEConfiguracoes));
		CheckBox chkCaminhoDosDadosEConfiguracoesPadrao = ((CheckBox) JanelaBase
				.findViewById(R.id.chkCaminhoDosDadosEConfiguracoesPadrao));
		String caminho = txtCaminhoDosDadosEConfiguracoes.getText().toString();
		if (chkCaminhoDosDadosEConfiguracoesPadrao.isChecked() || caminho == "") {
			return true;
		} else {
			File file = new File(caminho);
			if (file.isDirectory() && file.exists()) {
				return true;
			} else {
				try {
					file.mkdirs();
					if (!file.exists()) {
						throw new Exception();
					}
					return true;
				} catch (Exception e) {
					JanelaBase
							.ExibirErroToast(R.string.layoutConfiguracoes_CaminhoDosDadosEConfiguracoes_msgInvalido);
					txtCaminhoDosDadosEConfiguracoes.requestFocus();
					return false;
				}
			}
		}
	}

	public void GravarConfiguracoes() {
		EditText txtCaminhoDosDadosEConfiguracoes = ((EditText) JanelaBase
				.findViewById(R.id.txtCaminhoDosDadosEConfiguracoes));
		CheckBox chkCaminhoDosDadosEConfiguracoesPadrao = ((CheckBox) JanelaBase
				.findViewById(R.id.chkCaminhoDosDadosEConfiguracoesPadrao));

		String caminho;
		if (chkCaminhoDosDadosEConfiguracoesPadrao.isChecked()) {
			caminho = "";
		} else {
			caminho = txtCaminhoDosDadosEConfiguracoes.getText().toString();
		}
		JanelaBase.Preferencias.setCaminhoDosDadosEConfiguracao(caminho);
	}

}
