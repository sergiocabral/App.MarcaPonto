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
 * Gerencia as prefer�ncias do aplicativo.
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
	 * Prefer�ncias do usu�rio armazenadas no sistema operacional.
	 */
	protected IPreferencias getPreferenciasVolatil()
	{
		return new PreferenciasSalvasNoSistema(JanelaBase, Constantes.DEFINICAO_NO_SISTEMA);
	}
	
	/**
	 * Prefer�ncias do usu�rio armazenadas em arquivo.
	 */
	protected IPreferencias getPreferenciasFixa()
	{
		return new PreferenciasSalvasEmSQLite(JanelaBase, Constantes.DEFINICAO_EM_ARQUIVO);
	}
	
	/**
	 * Prefer�ncias do usu�rio armazenadas em arquivo do perfil selecionado.
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
	 * Limpa todas as prefer�ncias do aplicativo.
	 */
	public void Limpar() {
		getPreferenciasFixa().LimparTodas();
		getPreferenciasVolatil().LimparTodas();
	}

	/**
	 * Nome para prefer�ncia do aplicativo: IgnorarJanelaSobreAoIniciar
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
	 * Nome para prefer�ncia do aplicativo: VersaoSequencialDoAplicativo
	 */
	private static final String VersaoSequencialDoAplicativo = "VersaoSequencialDoAplicativo";

	/**
	 * Indica a vers�o sequencial do aplicativo.
	 * 
	 * @return int
	 */
	public int getVersaoSequencialDoAplicativo() {
		IPreferencias preferencias = getPreferenciasVolatil();
		return preferencias.Ler(VersaoSequencialDoAplicativo, 0);
	}

	/**
	 * Indica a vers�o sequencial do aplicativo.
	 */
	public void setVersaoSequencialDoAplicativo(int valor) {
		IPreferencias preferencias = getPreferenciasVolatil();
		preferencias.Salvar(VersaoSequencialDoAplicativo, valor);
	}

	/**
	 * Nome para prefer�ncia do aplicativo: CaminhoDosDadosEConfiguracao
	 */
	private static final String CaminhoDosDadosEConfiguracao = "CaminhoDosDadosEConfiguracao";

	/**
	 * Indica o caminho onde s�o gravados os dados e configura��es. Somente
	 * retorna um valor se houver permiss�o de escrita. Caso contr�rio dispara
	 * uma exce��o. Mesmo para o caminho do sistema retorna o endere�o correto.
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
	 * Indica o caminho onde s�o gravados os dados e configura��es.
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
	 * Caminho onde s�o gravados os perfis no cart�o de mem�ria.
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
	 * Indica o caminho onde s�o gravados os dados e configura��es.
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
	 * Nome para prefer�ncia do aplicativo: PerfilSelecionado
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
	 * Nome para prefer�ncia do aplicativo: PeriodoInicio
	 */
	private static final String PeriodoInicio = "PeriodoInicio";

	/**
	 * Nome para prefer�ncia do aplicativo: PeriodoFim
	 */
	private static final String PeriodoFim = "PeriodoFim";

	/**
	 * Retorna o per�odo que filtra o grid com os hor�rios registrados.
	 * @return Date[2] Dois elementos para data in�cio e fim, respectivamente
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
	 * Define o per�odo que filtra o grid com os hor�rios registrados.
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
