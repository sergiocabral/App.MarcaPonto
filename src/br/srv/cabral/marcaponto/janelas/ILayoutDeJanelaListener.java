package br.srv.cabral.marcaponto.janelas;

import java.util.EventListener;

/**
 * Interface para classe que implemente os eventos para LayoutDeJanela
 * 
 * @author Sergio Cabral (sergio@cabral.srv.br)
 */
public interface ILayoutDeJanelaListener extends EventListener {
	/**
	 * Disparado quando a janela recebe sinal do usuário para ser fechada.
	 * 
	 * @param e
	 */
	void EventoFecharJanela(LayoutDeJanelaEvento e);
}