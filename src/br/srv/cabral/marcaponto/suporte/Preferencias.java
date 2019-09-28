/**
 * 
 */
package br.srv.cabral.marcaponto.suporte;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.os.Environment;
import br.srv.cabral.marcaponto.R;
import br.srv.cabral.marcaponto.janelas.JanelaGenerica;
import br.srv.cabral.marcaponto.persistencia.PreferenciasSalvasEmSQLite;
import br.srv.cabral.marcaponto.persistencia.PreferenciasSalvasNoSistema;

/**
 * Gerencia as preferências do aplicativo.
 * 
 * @author Sergio Cabral (sergio@cabral.srv.br)
 */
public class Preferencias {

	/**
	 * Construtor. 
	 */
	public Preferencias(JanelaGenerica janelaBase) {
		JanelaBase = janelaBase;
	}
	
	/**
	 * Preferências do usuário armazenadas no sistema operacional.
	 */
	protected IPreferencias getPreferenciasVolatil()
	{
		return new PreferenciasSalvasNoSistema(JanelaBase, Constantes.DEFINICAO_NO_SISTEMA);
	}
	
	/**
	 * Preferências do usuário armazenadas em arquivo.
	 */
	protected IPreferencias getPreferenciasFixa()
	{
		return new PreferenciasSalvasEmSQLite(JanelaBase, Constantes.DEFINICAO_EM_ARQUIVO);
	}
	
	/**
	 * Preferências do usuário armazenadas em arquivo do perfil selecionado.
	 */
	protected IPreferencias getPreferenciasDoPerfil()
	{
		try {
			JanelaBase.Perfil.SelecionarPadrao();
		} catch (Exception e) {
		}
		return JanelaBase.GerenciadorDeHorarios.getBanco(JanelaBase.Perfil.getPerfilSelecionado());
	}

	/**
	 * Janela base.
	 */
	protected JanelaGenerica JanelaBase;

	/**
	 * Limpa todas as preferências do aplicativo.
	 */
	public void Limpar() {
		getPreferenciasFixa().LimparTodas();
		getPreferenciasVolatil().LimparTodas();
	}

	/**
	 * Nome para preferência do aplicativo: IgnorarJanelaSobreAoIniciar
	 */
	private static final String IgnorarJanelaSobreAoIniciar = "IgnorarJanelaSobreAoIniciar";

	/**
	 * Indica se a janela Sobre (tela de boas vindas) deve ser ignorada ao
	 * iniciar o sistema.
	 * 
	 * @return boolean
	 */
	public boolean getIgnorarJanelaSobreAoIniciar() {
		IPreferencias preferencias = getPreferenciasVolatil();
		return preferencias.Ler(IgnorarJanelaSobreAoIniciar, true);
	}

	/**
	 * Indica se a janela Sobre (tela de boas vindas) deve ser ignorada ao
	 * iniciar o sistema.
	 */
	public void setIgnorarJanelaSobreAoIniciar(boolean valor) {
		IPreferencias preferencias = getPreferenciasVolatil();
		preferencias.Salvar(IgnorarJanelaSobreAoIniciar, valor);
	}

	/**
	 * Nome para preferência do aplicativo: VersaoSequencialDoAplicativo
	 */
	private static final String VersaoSequencialDoAplicativo = "VersaoSequencialDoAplicativo";

	/**
	 * Indica a versão sequencial do aplicativo.
	 * 
	 * @return int
	 */
	public int getVersaoSequencialDoAplicativo() {
		IPreferencias preferencias = getPreferenciasVolatil();
		return preferencias.Ler(VersaoSequencialDoAplicativo, 0);
	}

	/**
	 * Indica a versão sequencial do aplicativo.
	 */
	public void setVersaoSequencialDoAplicativo(int valor) {
		IPreferencias preferencias = getPreferenciasVolatil();
		preferencias.Salvar(VersaoSequencialDoAplicativo, valor);
	}

	/**
	 * Nome para preferência do aplicativo: CaminhoDosDadosEConfiguracao
	 */
	private static final String CaminhoDosDadosEConfiguracao = "CaminhoDosDadosEConfiguracao";

	/**
	 * Indica o caminho onde são gravados os dados e configurações. Somente
	 * retorna um valor se houver permissão de escrita. Caso contrário dispara
	 * uma exceção. Mesmo para o caminho do sistema retorna o endereço correto.
	 * 
	 * @return String
	 * @throws Exception
	 */
	public String getCaminhoDosDadosEConfiguracaoReal() throws Exception {
		String caminho = getCaminhoDosDadosEConfiguracao();
		if (caminho == null || caminho == "") {
			caminho = IO.CaminhoDatabases();
		}
		if (!IO.CaminhoPermiteGravacao(caminho)) {
			String mensagem = JanelaBase.getResources().getString(
					R.string.erro_CaminhoDosDadosEConfiguracaoNaoEstaAcessivel);
			throw new Exception(String.format(mensagem, caminho));
		}
		return (caminho + "/").replace("//", "/");
	}

	/**
	 * Indica o caminho onde são gravados os dados e configurações.
	 * 
	 * @return String
	 */
	public String getCaminhoDosDadosEConfiguracao() {
		IPreferencias preferencias = getPreferenciasVolatil();
		String caminho = preferencias.Ler(CaminhoDosDadosEConfiguracao, null);
		if (caminho == null)
		{
			caminho = getCaminhoDosDadosEConfiguracaoNoCartaoDeMemoria();
			if (!IO.CaminhoPermiteGravacao(caminho))
			{
				caminho = "";
			}
			preferencias.Salvar(CaminhoDosDadosEConfiguracao, caminho);
		}
		return caminho;
	}
	
	/**
	 * Caminho onde são gravados os perfis no cartão de memória.
	 * @return
	 */
	public static String getCaminhoDosDadosEConfiguracaoNoCartaoDeMemoria()
	{
		return (Environment
				.getExternalStorageDirectory()
				.getAbsolutePath()
				+ "/"
				+ Constantes.NOME_IDENTIFICADOR_DO_SISTEMA + "/")
				.replace("//", "/");
	}

	/**
	 * Indica o caminho onde são gravados os dados e configurações.
	 */
	public void setCaminhoDosDadosEConfiguracao(String valor) {
		if (valor != "") {
			File file = new File((valor + "/").replace("//", "/"));
			try {
				file.mkdirs();
				if (!file.exists()) {
					throw new Exception();
				}
				valor = (file.getAbsolutePath() + "/").replace("//", "/");
			} catch (Exception e) {
				valor = "";
			}
		}

		IPreferencias preferencias = getPreferenciasVolatil();
		preferencias.Salvar(CaminhoDosDadosEConfiguracao, valor);
	}

	/**
	 * Nome para preferência do aplicativo: PerfilSelecionado
	 */
	private static final String PerfilSelecionado = "PerfilSelecionado";

	/**
	 * Indica se a janela Sobre (tela de boas vindas) deve ser ignorada ao
	 * iniciar o sistema.
	 * 
	 * @return boolean
	 */
	public String getPerfilSelecionado() {
		IPreferencias preferencias = getPreferenciasVolatil();
		return preferencias.Ler(PerfilSelecionado, "");
	}

	/**
	 * Indica se a janela Sobre (tela de boas vindas) deve ser ignorada ao
	 * iniciar o sistema.
	 */
	public void setPerfilSelecionado(String valor) {
		IPreferencias preferencias = getPreferenciasVolatil();
		preferencias.Salvar(PerfilSelecionado, valor);
	}

	/**
	 * Nome para preferência do aplicativo: PeriodoInicio
	 */
	private static final String PeriodoInicio = "PeriodoInicio";

	/**
	 * Nome para preferência do aplicativo: PeriodoFim
	 */
	private static final String PeriodoFim = "PeriodoFim";

	/**
	 * Retorna o período que filtra o grid com os horários registrados.
	 * @return Date[2] Dois elementos para data início e fim, respectivamente
	 */
	public Date[] getPeriodoDeExibicaoDoGridDeHorarios() {
		IPreferencias preferencias = getPreferenciasDoPerfil();
		
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
		
		String strPeriodoInicio = preferencias.Ler(PeriodoInicio, "");
		String strPeriodoFim = preferencias.Ler(PeriodoFim, "");		

		Date[] result = new Date[2];
		try {
			result[0] = format.parse(strPeriodoInicio);
		} catch (ParseException e) {
			result[0] = null;
		}
		try {
			result[1] = format.parse(strPeriodoFim);
		} catch (ParseException e) {
			result[1] = null;
		}
		return result;
	}
	
	/**
	 * Define o período que filtra o grid com os horários registrados.
	 */
	public void setPeriodoDeExibicaoDoGridDeHorarios(String perfil, Date periodoInicio, Date periodoFim) {
		IPreferencias preferencias = getPreferenciasDoPerfil();
		
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");		
		
		String strPeriodoInicio = periodoInicio == null ? "" : format.format(periodoInicio);
		preferencias.Salvar(PeriodoInicio, strPeriodoInicio);

		String strPeriodoFim = periodoFim == null ? "" : format.format(periodoFim);
		preferencias.Salvar(PeriodoFim, strPeriodoFim);
	}
	
	/**
	 * Fornata uma data para String
	 */
	public static String FormatarData(Date data)
	{
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		if (data == null)
		{
			return "";			
		}
		else
		{
			return dateFormat.format(data);
		}
	}
	
}
