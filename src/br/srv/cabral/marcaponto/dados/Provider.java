package br.srv.cabral.marcapontolite.dados;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import br.srv.cabral.marcapontolite.util.Definicoes;

/* ContentProvider para a as definições salvas no banco de dados.
 */
public class Provider extends ContentProvider {
	/*
	 * Extrai informações sobre Uri.
	 */
	private static final UriMatcher uriMatcher;
	static {
		uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
		uriMatcher.addURI(Definicoes.AUTHORITY,
				DefinicoesColumns.INSTANCIA.getPARAM_URI(),
				DefinicoesColumns.INSTANCIA.getID());
		uriMatcher.addURI(Definicoes.AUTHORITY,
				HorariosColumns.INSTANCIA.getPARAM_URI(),
				HorariosColumns.INSTANCIA.getID());
	}

	/*
	 * Manipulado do banco de dados.
	 */
	private Banco banco;

	/*
	 * Obtem o ContentType a partir da Uri (non-Javadoc)
	 * 
	 * @see android.content.ContentProvider#getType(android.net.Uri)
	 */
	@Override
	public String getType(Uri uri) {
		ColumnsBase tabelaInfo = ObterTabelaInfo(uri);
		return tabelaInfo.getCONTENT_TYPE();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.content.ContentProvider#onCreate()
	 */
	@Override
	public boolean onCreate() {
		banco = new Banco(getContext());
		Banco.INSTANCIA = banco;
		return true;
	}

	@Override
	public Cursor query(Uri uri, String[] projection, String selection,
			String[] selectionArgs, String sortOrder) {
		ColumnsBase tabelaInfo = ObterTabelaInfo(uri);
		SQLiteQueryBuilder builder = new SQLiteQueryBuilder();
		SQLiteDatabase database = banco.getReadableDatabase();
		Cursor cursor;
		builder.setTables(tabelaInfo.getTABELA());
		builder.setProjectionMap(tabelaInfo.getCampos());
		cursor = builder.query(database, projection, selection, selectionArgs,
				null, null, sortOrder);
		cursor.setNotificationUri(getContext().getContentResolver(), uri);
		return cursor;
	}

	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {
		ColumnsBase tabelaInfo = ObterTabelaInfo(uri);
		SQLiteDatabase db = banco.getWritableDatabase();
		int count = db.delete(tabelaInfo.getTABELA(), selection, selectionArgs);
		getContext().getContentResolver().notifyChange(uri, null);
		return count;
	}

	@Override
	public Uri insert(Uri uri, ContentValues values) {
		ColumnsBase tabelaInfo = ObterTabelaInfo(uri);
		SQLiteDatabase db = banco.getWritableDatabase();
		long rowId = db.insert(tabelaInfo.getTABELA(),
				tabelaInfo.getCAMPO_CHAVE_PRIMARIA(), values);
		if (rowId > 0) {
			Uri uri_ = ContentUris.withAppendedId(tabelaInfo.getCONTENT_URI(),
					rowId);
			getContext().getContentResolver().notifyChange(uri_, null);
			return uri_;
		}
		throw new IllegalArgumentException("URI desconhecida " + uri);
	}

	@Override
	public int update(Uri uri, ContentValues values, String selection,
			String[] selectionArgs) {
		ColumnsBase tabelaInfo = ObterTabelaInfo(uri);
		SQLiteDatabase db = banco.getWritableDatabase();
		int count = db.update(tabelaInfo.getTABELA(), values, selection,
				selectionArgs);
		getContext().getContentResolver().notifyChange(uri, null);
		return count;
	}

	/*
	 * Obtem a classe com informações sobre a tabela correspondente a Uri
	 * informada.
	 */
	public ColumnsBase ObterTabelaInfo(Uri uri) {
		ColumnsBase tabelaInfo;
		int uriId = uriMatcher.match(uri);
		if (uriId == DefinicoesColumns.INSTANCIA.getID()) {
			tabelaInfo = DefinicoesColumns.INSTANCIA;
		} else if (uriId == HorariosColumns.INSTANCIA.getID()) {
			tabelaInfo = HorariosColumns.INSTANCIA;
		} else {
			throw new IllegalArgumentException("URI desconhecida " + uri);
		}
		return tabelaInfo;
	}

}
