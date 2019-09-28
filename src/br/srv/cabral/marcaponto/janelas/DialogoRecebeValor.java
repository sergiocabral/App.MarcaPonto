package br.srv.cabral.marcaponto.janelas;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import br.srv.cabral.marcaponto.R;

/**
 * Caixa de diálogo que recebe um valor informado pelo usuário.
 * 
 * @author Sergio Cabral (sergio@cabral.srv.br)
 */
public class DialogoRecebeValor extends Dialog {

	/**
	 * Interface para listerner desta caixa de diálogo.
	 * 
	 * @author Sergio Cabral (sergio@cabral.srv.br)
	 */
	public interface OnReceberValorListener {
		public void Resposta(String string);
	}

	private String ValorInicial;
	private String Mensagem;
	private OnReceberValorListener ReadyListener;

	public DialogoRecebeValor(Context context, String valorInicial,
			String mensagem, OnReceberValorListener readyListener) {
		super(context);
		ValorInicial = valorInicial;
		Mensagem = mensagem;
		ReadyListener = readyListener;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dialogo_recebe_valor);
		EditText txtValor = (EditText) findViewById(R.id.txtValor);
		TextView lblMensagem = (TextView) findViewById(R.id.lblMensagem);
		Button btnOk = (Button) findViewById(R.id.btnOk);
		Button btnCancelar = (Button) findViewById(R.id.btnCancelar);
		
		txtValor.setText(ValorInicial);
		lblMensagem.setText(Mensagem);
		btnOk.setOnClickListener(new android.view.View.OnClickListener() {
			public void onClick(View v) {
				ReadyListener.Resposta(((EditText) DialogoRecebeValor.this
						.findViewById(R.id.txtValor)).getText().toString());
				DialogoRecebeValor.this.dismiss();
			}
		});
		btnCancelar.setOnClickListener(new android.view.View.OnClickListener() {
			public void onClick(View v) {
				DialogoRecebeValor.this.dismiss();
			}
		});
	}
}
