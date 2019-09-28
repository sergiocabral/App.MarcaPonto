package br.srv.cabral.marcaponto.janelas;

import android.os.Bundle;
import br.srv.cabral.marcaponto.suporte.Constantes;

/**
 * Janela principal do sistema
 * 
 * @author Sergio Cabral (sergio@cabral.srv.br)
 */
public class JanelaInicial extends JanelaGenerica implements
		ILayoutDeJanelaListener {
	/**
	 * Disparado quando a janela é criada pela primeira vez.
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		Constantes.Contexto = this;
		
		if (AplicativoJaIniciado
				|| (Preferencias.getIgnorarJanelaSobreAoIniciar() && Preferencias
						.getVersaoSequencialDoAplicativo() == Constantes.VERSAO_SEQUENCIAL)) {
			ExibirLayout(LayoutPrincipal.class, false);
		} else {
			ExibirLayout(LayoutSobre.class, false);
		}
	}

	public void EventoFecharJanela(LayoutDeJanelaEvento e) {
		ExibirLayout(LayoutPrincipal.class, false);
	}
}