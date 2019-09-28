package br.srv.cabral.marcaponto.suporte;

import java.util.List;

import android.database.Cursor;
import br.srv.cabral.marcaponto.entidades.Horario;
import br.srv.cabral.marcaponto.janelas.JanelaGenerica;
import br.srv.cabral.marcaponto.persistencia.HorariosEmSQLite;
import br.srv.cabral.marcaponto.persistencia.TabelaHorarios;

/**
 * Gerencia os horários cadastrados.
 * 
 * @author Sergio Cabral (sergio@cabral.srv.br)
 */
public class GerenciadorDeHorarios {
	public GerenciadorDeHorarios(JanelaGenerica janelaBase) {
		JanelaBase = janelaBase;
	}

	/**
	 * Janela base.
	 */
	protected JanelaGenerica JanelaBase;

	protected HorariosEmSQLite getBanco(String perfil) {
		return new HorariosEmSQLite(JanelaBase, JanelaBase.Perfil
				.FormatarNomeDoArquivo(perfil));
	}

	/**
	 * Registra um horário
	 */
	public void Registrar(String perfil, Horario horario) {
		HorariosEmSQLite banco = getBanco(perfil);
		banco.Registrar(horario);
	}

	/**
	 * Consulta horários.
	 */
	public Cursor Consultar(String perfil) {
		TabelaHorarios tblHorarios = new TabelaHorarios();
		List<String> campos = (new TabelaHorarios()).getNomeDosCampos();

		StringBuilder sql = new StringBuilder();
		
		sql.append("select ");
		sql.append(campos.get(TabelaHorarios.CAMPO_ID));
		sql.append(" as _id, ");
		sql.append(campos.get(TabelaHorarios.CAMPO_COMENTARIO));
		sql.append(" from ");
		sql.append(tblHorarios.getNomeDaTabela());
		sql.append(" order by ");
		sql.append(campos.get(TabelaHorarios.CAMPO_COMENTARIO));
		         
		return getBanco(perfil).ExcutarSQL(sql.toString(), new String[] {});		
	}

}
