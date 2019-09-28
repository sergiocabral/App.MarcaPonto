/**
 * 
 */
package br.srv.cabral.marcaponto.suporte;

/**
 * Interface para classe que armazena e manipula definições de preferências do
 * usuário ou do aplicativo.
 * 
 * @author Sergio Cabral (sergio@cabral.srv.br)
 */
public interface IPreferencias {

	/**
	 * Limpar todas as definições.
	 */
	void LimparTodas();
	
	/**
	 * Verifica se uma definição existe.
	 * 
	 * @param nome
	 *            Nome da definição.
	 * @return boolean.
	 */
	boolean Existe(String nome);

	/**
	 * Lê um valor tipo: String
	 * 
	 * @param nome
	 *            Nome da definição.
	 * @param valorPadrao
	 *            Valor padrão caso não exista definido ainda.
	 * @return Valor da definição.
	 */
	String Ler(String nome, String valorPadrao);

	/**
	 * Salva um valor tipo: String
	 * 
	 * @param nome
	 *            Nome da definição.
	 * @param valor
	 *            Valor da definição.
	 */
	void Salvar(String nome, String valor);

	/**
	 * Lê um valor tipo: int
	 * 
	 * @param nome
	 *            Nome da definição.
	 * @param valorPadrao
	 *            Valor padrão caso não exista definido ainda.
	 * @return Valor da definição.
	 */
	int Ler(String nome, int valorPadrao);

	/**
	 * Salva um valor tipo: int
	 * 
	 * @param nome
	 *            Nome da definição.
	 * @param valor
	 *            Valor da definição.
	 */
	void Salvar(String nome, int valor);

	/**
	 * Lê um valor tipo: float
	 * 
	 * @param nome
	 *            Nome da definição.
	 * @param valorPadrao
	 *            Valor padrão caso não exista definido ainda.
	 * @return Valor da definição.
	 */
	float Ler(String nome, float valorPadrao);

	/**
	 * Salva um valor tipo: int
	 * 
	 * @param nome
	 *            Nome da definição.
	 * @param valor
	 *            Valor da definição.
	 */
	void Salvar(String nome, float valor);

	/**
	 * Lê um valor tipo: boolean
	 * 
	 * @param nome
	 *            Nome da definição.
	 * @param valorPadrao
	 *            Valor padrão caso não exista definido ainda.
	 * @return Valor da definição.
	 */
	boolean Ler(String nome, boolean valorPadrao);

	/**
	 * Salva um valor tipo: boolean
	 * 
	 * @param nome
	 *            Nome da definição.
	 * @param valor
	 *            Valor da definição.
	 */
	void Salvar(String nome, boolean valor);

}
