/**
 * 
 */
package br.srv.cabral.marcaponto.janelas;

/**
 * Interface para classe que implementa as seções de configuração da tela
 * LayoutConfiguracoes.
 * 
 * @author Sergio Cabral (sergio@cabral.srv.br)
 */
public interface ILayoutConfiguracoes_Secao {

	/**
	 * Configura os controles da seção.
	 */
	void ConfigurarControles();

	/**
	 * Verifica se os campos estão preenchidos corretamente.
	 * 
	 * @return boolean. Se retorna false exibe automaticamente a mensagem de
	 *         erro.
	 */
	boolean CamposPreenchidosCorretamente();

	/**
	 * Grava as configurações na tela.
	 */
	void GravarConfiguracoes();
}
