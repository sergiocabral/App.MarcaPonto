package br.srv.cabral.marcapontolite.util;

import java.util.Date;
import java.util.HashMap;

import android.content.ContentValues;
import android.database.Cursor;
import br.srv.cabral.marcapontolite.dados.HorariosColumns;

/* Gerencia os horários registrados.
 */
public final class Horarios {
	/*
	 * Construtor privado. Padrão de projeto: Singleton.
	 */
	private Horarios() {
	}

	/*
	 * Instancia.
	 */
	public static final Horarios INSTANCIA = new Horarios();

	/*
	 * Lê o conjunto de horarios pela data.
	 */
	public HashMap<String, String> Ler(String data) {
		HashMap<String, String> valores = null;
		String dataNum = Funcoes.FormatarDataParaNumero(data);
		Cursor cursor = Definicoes.INSTANCIA.janelaBase
				.getContentResolver()
				.query(HorariosColumns.INSTANCIA.getCONTENT_URI(),
						new String[] { HorariosColumns.CAMPO_ID,
								HorariosColumns.CAMPO_DATA,
								HorariosColumns.CAMPO_DATA_NUM,
								HorariosColumns.CAMPO_HORA1,
								HorariosColumns.CAMPO_HORA2,
								HorariosColumns.CAMPO_HORA3,
								HorariosColumns.CAMPO_HORA4,
								HorariosColumns.CAMPO_EXTRA,
								HorariosColumns.CAMPO_SALDO },
						String.format("%s = ?", HorariosColumns.CAMPO_DATA_NUM),
						new String[] { dataNum }, null);
		if (cursor.getCount() > 0) {
			cursor.moveToFirst();
			valores = new HashMap<String, String>();
			valores.put(HorariosColumns.CAMPO_ID, cursor.getString(0));
			valores.put(HorariosColumns.CAMPO_DATA, cursor.getString(1));
			valores.put(HorariosColumns.CAMPO_DATA_NUM, cursor.getString(2));
			valores.put(HorariosColumns.CAMPO_HORA1, cursor.getString(3));
			valores.put(HorariosColumns.CAMPO_HORA2, cursor.getString(4));
			valores.put(HorariosColumns.CAMPO_HORA3, cursor.getString(5));
			valores.put(HorariosColumns.CAMPO_HORA4, cursor.getString(6));
			valores.put(HorariosColumns.CAMPO_EXTRA, cursor.getString(7));
			valores.put(HorariosColumns.CAMPO_SALDO, cursor.getString(8));
		}
		cursor.close();
		return valores;
	}

	/*
	 * Registra os horários de um dia
	 */
	public void RegistrarHorarios(String data, String hora1, String hora2,
			String hora3, String hora4) {
		if (data.indexOf("\n") > 0) {
			data = data.substring(0, data.indexOf("\n"));
		}
		ApagarHorarios(data);

		Date data_ = Funcoes.ConverterParaDate(data);
		String dataNum = Funcoes.FormataDoisDigitos(data_.getYear() + 1900)
				+ Funcoes.FormataDoisDigitos(data_.getMonth() + 1)
				+ Funcoes.FormataDoisDigitos(data_.getDate());

		Float trabalhadoNum;
		Float trabalhadoValidoNum;
		Float saldoNum;
		String saldo;
		if (Funcoes.ValidarHorarios(hora1, hora2, hora3, hora4)) {
			HashMap<String, Float> definicoes = Funcoes
					.ObterDefinicoesParaSaldoDeHoras();
			trabalhadoNum = Funcoes.CalcularTempoTrabalhado(definicoes, hora1,
					hora2, hora3, hora4);
			trabalhadoValidoNum = Funcoes.CalcularTempoTrabalhadoValido(
					definicoes, hora1, hora2, hora3, hora4);
			saldoNum = Funcoes.CalcularSaldoDeHoras(definicoes, hora1, hora2,
					hora3, hora4);
			saldo = Funcoes.FormataHora(saldoNum);
		} else {
			trabalhadoNum = new Float(0);
			trabalhadoValidoNum = new Float(0);
			saldoNum = new Float(0);
			saldo = "?";
		}
		String extra = String.format("%s|%s|%s", trabalhadoNum,
				trabalhadoValidoNum, saldoNum);

		if (hora1 == null || hora1.length() == 0) {
			hora1 = "-";
		}
		if (hora2 == null || hora2.length() == 0) {
			hora2 = "-";
		}
		if (hora3 == null || hora3.length() == 0) {
			hora3 = "-";
		}
		if (hora4 == null || hora4.length() == 0) {
			hora4 = "-";
		}

		String diaDaSemana = Funcoes.ObterDiaDaSemana(data_);

		ContentValues values = new ContentValues();
		values.put(HorariosColumns.CAMPO_DATA, data + "\n" + diaDaSemana);
		values.put(HorariosColumns.CAMPO_DATA_NUM, dataNum);
		values.put(HorariosColumns.CAMPO_HORA1, hora1);
		values.put(HorariosColumns.CAMPO_HORA2, hora2);
		values.put(HorariosColumns.CAMPO_HORA3, hora3);
		values.put(HorariosColumns.CAMPO_HORA4, hora4);
		values.put(HorariosColumns.CAMPO_SALDO, saldo);
		values.put(HorariosColumns.CAMPO_EXTRA, extra);
		Definicoes.INSTANCIA.janelaBase.getContentResolver().insert(
				HorariosColumns.INSTANCIA.getCONTENT_URI(), values);
	}

	/*
	 * Apagar os horários de uma data.
	 */
	public void ApagarHorarios(String data) {
		Date data_ = Funcoes.ConverterParaDate(data);
		String horario = Funcoes.FormataDoisDigitos(data_.getYear() + 1900)
				+ Funcoes.FormataDoisDigitos(data_.getMonth() + 1)
				+ Funcoes.FormataDoisDigitos(data_.getDate()) + "%";
		Definicoes.INSTANCIA.janelaBase.getContentResolver().delete(
				HorariosColumns.INSTANCIA.getCONTENT_URI(),
				String.format("%s like ?", HorariosColumns.CAMPO_DATA_NUM),
				new String[] { horario });
	}

	public Cursor Consultar(HashMap<Integer, String> parametros) {
		String periodoIncial = Funcoes
				.FormatarDataParaNumero(Definicoes.INSTANCIA
						.Ler(Definicoes.DEF_PERIODO_INICIAL));
		String periodoFinal = Funcoes
				.FormatarDataParaNumero(Definicoes.INSTANCIA
						.Ler(Definicoes.DEF_PERIODO_FINAL));

		String sqlWhere = "";
		String[] sqlArgs = new String[periodoIncial.length() == 0
				&& periodoFinal.length() == 0 ? 0 : periodoIncial.length() != 0
				&& periodoFinal.length() != 0 ? 2 : 1];

		if (periodoIncial.length() > 0) {
			sqlArgs[0] = periodoIncial;
			sqlWhere += String.format(" AND (%s >= ?)",
					HorariosColumns.CAMPO_DATA_NUM);
		}
		if (periodoFinal.length() > 0) {
			sqlArgs[sqlArgs[0] == null ? 0 : 1] = periodoFinal;
			sqlWhere += String.format(" AND (%s <= ?)",
					HorariosColumns.CAMPO_DATA_NUM);
		}
		if (sqlWhere.length() > 0) {
			sqlWhere = sqlWhere.substring(" AND ".length());
		}

		Cursor cursor = Definicoes.INSTANCIA.janelaBase.getContentResolver()
				.query(HorariosColumns.INSTANCIA.getCONTENT_URI(),
						new String[] { HorariosColumns.CAMPO_ID,
								HorariosColumns.CAMPO_DATA,
								HorariosColumns.CAMPO_HORA1,
								HorariosColumns.CAMPO_HORA2,
								HorariosColumns.CAMPO_HORA3,
								HorariosColumns.CAMPO_HORA4,
								HorariosColumns.CAMPO_SALDO,
								HorariosColumns.CAMPO_EXTRA }, sqlWhere,
						sqlArgs, HorariosColumns.CAMPO_DATA_NUM + " DESC");

		if (parametros != null) {
			Float trabalhadoNum = new Float(0);
			Float trabalhadoValidoNum = new Float(0);
			Float saldoNum = new Float(0);
			cursor.moveToFirst();
			while (cursor.getPosition() < cursor.getCount()) {
				String extra = cursor.getString(7);
				String[] valores = new String[3];
				valores[0] = extra.replaceAll("\\|.*", "");
				valores[1] = extra.replaceAll("(^[^\\|]*\\||\\|.*$)", "");
				valores[2] = extra.replaceAll(".*\\|", "");

				try {
					trabalhadoNum += Float.parseFloat(valores[0]);
				} catch (Exception ex) {
				}

				try {
					trabalhadoValidoNum += Float.parseFloat(valores[1]);
				} catch (Exception ex) {
				}

				try {
					saldoNum += Float.parseFloat(valores[2]);
				} catch (Exception ex) {
				}

				cursor.moveToNext();
			}
			cursor.moveToFirst();

			parametros.put(1, Funcoes.FormataHora(trabalhadoNum));
			parametros.put(2, Funcoes.FormataHora(trabalhadoValidoNum));
			parametros.put(3, Funcoes.FormataHora(saldoNum));
		}
		return cursor;
	}

	/*
	 * Recalcula todos os horários.
	 */
	public void RecalcularHorarios() {
		Cursor cursor = Definicoes.INSTANCIA.janelaBase
				.getContentResolver()
				.query(HorariosColumns.INSTANCIA.getCONTENT_URI(),
						new String[] { HorariosColumns.CAMPO_DATA,
								HorariosColumns.CAMPO_HORA1,
								HorariosColumns.CAMPO_HORA2,
								HorariosColumns.CAMPO_HORA3,
								HorariosColumns.CAMPO_HORA4 }, null, null, null);
		cursor.moveToFirst();
		while (cursor.getPosition() < cursor.getCount()) {
			String hora1 = cursor.getString(1);
			String hora2 = cursor.getString(2);
			String hora3 = cursor.getString(3);
			String hora4 = cursor.getString(4);
			if (hora1 == null || hora1.equals("-")) {
				hora1 = "";
			}
			if (hora2 == null || hora2.equals("-")) {
				hora2 = "";
			}
			if (hora3 == null || hora3.equals("-")) {
				hora3 = "";
			}
			if (hora4 == null || hora4.equals("-")) {
				hora4 = "";
			}
			RegistrarHorarios(cursor.getString(0), hora1, hora2, hora3, hora4);
			cursor.moveToNext();
		}
		cursor.close();
	}

}
