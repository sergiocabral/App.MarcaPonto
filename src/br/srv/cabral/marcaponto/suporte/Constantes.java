/**
 * 
 */
package br.srv.cabral.marcaponto.suporte;

import android.content.Context;

/**
 * Constantes para uso geral.
 * 
 * @author Sergio Cabral (sergio@cabral.srv.br)
 */
public final class Constantes {
	/**
	 * Contexto que ser� utilizado como base para o aplicativo.
	 */
	public static Context Contexto;

	/**
	 * N�mero nominal da vers�o do aplicativo.
	 */
	public static final int VERSAO_NOMINAL = 2;

	/**
	 * N�mero sequencial da vers�o do aplicativo. Cada nova vers�o deve
	 * incrementar 1 no valor desta constante.
	 */
	public static final int VERSAO_SEQUENCIAL = 1;

	/**
	 * Nome identificador do sistema.
	 */
	public static final String NOME_IDENTIFICADOR_DO_SISTEMA = "MarcaPonto";

	/**
	 * Nome do arquivo que armazena o banco de dados.
	 */
	public static final String DEFINICAO_EM_ARQUIVO = "marcaponto.db";

	/**
	 * Nome do conjunto de prefer�ncia do aplicativo armazenadas no sistema
	 * operacional.
	 */
	public static final String DEFINICAO_NO_SISTEMA = "marcaponto";

	/**
	 * Nome da informa��o extra passado para o Intent para que um layout seja
	 * aplicado na abertura de uma janela.
	 */
	public static final String INTENT_MSG_LAYOUT = "INTENT_MSG_LAYOUT";

	/**
	 * Nome da informa��o extra passado para o Intent para que o layout de Erro
	 * exiba a mensagem do erro ocorrido.
	 */
	public static final String INTENT_MSG_ERRO = "INTENT_MSG_ERRO";

	/**
	 * Nome da informa��o extra passado para o Intent para que o layout de
	 * Marcar Hor�rios saiba se � uma inclus�o de entrada ou sa�da.
	 */
	public static final String INTENT_MSG_MARCAR_HORARIO = "INTENT_MSG_MARCAR_HORARIO";

	/**
	 * Nome da informa��o extra passado para o Intent para que o layout de
	 * Marcar Hor�rios saiba se dee habilitar o modo de ed��o
	 */
	public static final String INTENT_MSG_MARCAR_HORARIO_EDICAO = "INTENT_MSG_MARCAR_HORARIO_EDICAO";

	/**
	 * Extens�o dos arquivos que armazenam os dados de um perfil.
	 */
	public static final String EXTENSAO_PARA_ARQUIVO_DE_PERFIL = "pfl";

}
