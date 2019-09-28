package br.srv.cabral.marcaponto.persistencia;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import br.srv.cabral.marcaponto.entidades.Horario;

/**
 * Gerencia os horários de um perfil do aplicativo.
 * 
 * @author Sergio Cabral (sergio@cabral.srv.br)
 */
public class HorariosEmSQLite extends PreferenciasSalvasEmSQLite {

	// TODO: Implementar esta classe.

	/**
	 * Construtor
	 * 
	 * @param contexto
	 *            Contexto do aplicativo.
	 * @param nomeDoBancoDeDados
	 *            Nome do banco de dados do SQLite.
	 */
	public HorariosEmSQLite(Context contexto, String nomeDoBancoDeDados) {
		super(contexto, nomeDoBancoDeDados);
		Contexto = contexto;
		getReadableDatabase();
	}

	/**
	 * Contexto do aplicativo.
	 */
	protected Context Contexto;

	private TabelaHorarios tbHorarios;
	/**
	 * Configurações da tabela: Preferencias
	 */
	public TabelaHorarios getTbHorarios()
	{
		if (tbHorarios == null)
		{
			tbHorarios = new TabelaHorarios();
		}
		return tbHorarios;
	}

	/**
	 * Disparado quando o banco de dados é criado (fisicamente falando) pela
	 * primeira vez.
	 */
	@Override
	public void onCreate(SQLiteDatabase db) {
		super.onCreate(db);
		getTbHorarios().onCreate(db);
	}

	/**
	 * Disparado quando o banco de dados precisa ser atualizado (fisicamente
	 * falando) pela primeira vez.
	 */
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		super.onUpgrade(db, oldVersion, newVersion);
		getTbHorarios().onUpgrade(db, oldVersion, newVersion);
	}

	/**
	 * Registrar no banco de dados um entidade de Horário
	 */
	public void Registrar(Horario horario) {
		SQLiteDatabase db = getWritableDatabase();
		ContentValues valores = getTbHorarios().PreencherValores(new int[] {
				// TabelaHorarios.CAMPO_ID,
				TabelaHorarios.CAMPO_MODO, TabelaHorarios.CAMPO_ANO,
				TabelaHorarios.CAMPO_MES, TabelaHorarios.CAMPO_DIA,
				TabelaHorarios.CAMPO_HORA, TabelaHorarios.CAMPO_MINUTO,
				TabelaHorarios.CAMPO_SEGUNDO, TabelaHorarios.CAMPO_COMENTARIO,
				TabelaHorarios.CAMPO_COR }, new Object[] {
				// null,
				horario.Modo, horario.Ano, horario.Mes, horario.Dia,
				horario.Hora, horario.Minuto, horario.Segundo,
				horario.Comentario, horario.Cor });
		db.insert(getTbHorarios().getNomeDaTabela(), null, valores);
	}

	/**
	 * Executar um SQL
	 * @param sql
	 * @param selectionArgs
	 * @return
	 */
	public Cursor ExcutarSQL(String sql, String[] selectionArgs) {
		SQLiteDatabase db = getReadableDatabase();
		Cursor cursor = db.rawQuery(sql, selectionArgs);
		return cursor;
	}
}
