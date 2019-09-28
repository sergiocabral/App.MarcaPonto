/**
 * 
 */
package br.srv.cabral.marcaponto.janelas;

import java.util.ArrayList;

/**
 * Classe ancestral de classes que configuram um layout de uma janela.
 * 
 * @author Sergio Cabral (sergio@cabral.srv.br)
 */
public abstract class LayoutDeJanela implements ILayoutDeJanela {
	/**
	 * Janela que implementa o layout.
	 */
	protected JanelaGenerica JanelaBase;

	public void Configurar(JanelaGenerica janelaBase) {		
		JanelaBase = janelaBase;
		JanelaBase.setContentView(getLayoutResourceId());
		ConfigurarControles();
	}

	/**
	 * Listeners para evento.
	 */
	private ArrayList<ILayoutDeJanelaListener> eventoListeners = new ArrayList<ILayoutDeJanelaListener>();

	/**
	 * Adiciona um listener de evento.
	 * 
	 * @param listener
	 */
	public synchronized void addListener(ILayoutDeJanelaListener listener) {
		if (!eventoListeners.contains(listener)) {
			eventoListeners.add(listener);
		}
	}

	/**
	 * Remove um listener de evento.
	 * 
	 * @param listener
	 */
	public synchronized void removeListener(ILayoutDeJanelaListener listener) {
		eventoListeners.remove(listener);
	}

	/**
	 * Disparar evento: Fechar Janela
	 */
	@SuppressWarnings("unchecked")
	protected void FecharJanela() {
		ArrayList<ILayoutDeJanelaListener> listeners;
		synchronized (this) {
			listeners = (ArrayList<ILayoutDeJanelaListener>) eventoListeners
					.clone();
		}
		LayoutDeJanelaEvento evento = new LayoutDeJanelaEvento(this);

		for (ILayoutDeJanelaListener listener : listeners) {
			listener.EventoFecharJanela(evento);
		}
	}

	/**
	 * Retorna o id do recurso do layout.
	 * 
	 * @return int
	 */
	protected abstract int getLayoutResourceId();

	/**
	 * Configura os controles do layout.
	 */
	protected abstract void ConfigurarControles();
	
	public void AoExibirJanela() { } 
}
