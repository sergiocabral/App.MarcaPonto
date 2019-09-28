package br.srv.cabral.marcapontolite.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import android.graphics.Color;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;

/* Agrupa funcionalidades de uso comum.
 */
public final class Funcoes {
	/*
	 * Obtem a data de um DatePicker em formato texto
	 */
	public static String ObterDataEmTexto(DatePicker datePicker) {
		String data = String.format("%s/%s/%s",
				Funcoes.FormataDoisDigitos(datePicker.getDayOfMonth()),
				Funcoes.FormataDoisDigitos(datePicker.getMonth() + 1),
				Funcoes.FormataDoisDigitos(datePicker.getYear()));
		return data;
	}

	/*
	 * Obtem a hora de um TimePicker em formato texto
	 */
	public static String ObterHoraEmTexto(TimePicker timePicker) {
		String data = String.format("%s:%s",
				FormataDoisDigitos(timePicker.getCurrentHour()),
				FormataDoisDigitos(timePicker.getCurrentMinute()));
		return data;
	}

	/*
	 * Converte um valor de data em formato texto para o tipo primitivo Date.
	 */
	public static Date ConverterParaDate(String dataEmTexto) {
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		Date date = new Date();
		try {
			date = dateFormat.parse(dataEmTexto);
		} catch (ParseException e) {
		}
		return date;
	}

	/*
	 * Converte um valor de data em formato texto para o tipo primitivo Date.
	 */
	public static Date ConverterParaDate(DatePicker datePicker) {
		return ConverterParaDate(ObterDataEmTexto(datePicker));
	}

	/*
	 * Converte um valor de hora em formato texto para o tipo primitivo Date.
	 */
	public static void AplicarHoraEmDate(Date dateBase, String horaEmTexto) {
		Integer hora = Integer.parseInt(horaEmTexto.replaceAll(":[0-9]*$", ""));
		Integer minuto = Integer.parseInt(horaEmTexto
				.replaceAll("^[0-9]*:", ""));
		dateBase.setHours(hora);
		dateBase.setMinutes(minuto);
	}

	/*
	 * Atribui um valor de data a um DatePicker
	 */
	public static void DefinirData(DatePicker datePicker, String dataEmTexto) {
		Date date = ConverterParaDate(dataEmTexto);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		datePicker.init(calendar.get(Calendar.YEAR),
				calendar.get(Calendar.MONTH),
				calendar.get(Calendar.DAY_OF_MONTH), null);
	}

	/*
	 * Atribui um valor de hora a um TimePicker
	 */
	public static void DefinirHora(TimePicker timePicker, String horaEmTexto) {
		Integer hora;
		Integer minuto;
		if (horaEmTexto != null && horaEmTexto.length() > 0) {
			Date date = new Date();
			AplicarHoraEmDate(date, horaEmTexto);
			hora = date.getHours();
			minuto = date.getMinutes();
		} else {
			Date dataCorrente = new Date();
			hora = dataCorrente.getHours();
			minuto = dataCorrente.getMinutes();
		}
		timePicker.setCurrentHour(hora);
		timePicker.setCurrentMinute(minuto);
	}

	/*
	 * Obtem o texto de uma string de recurso.
	 */
	public static String LerRecurso(int resid) {
		TextView textView = new TextView(Definicoes.INSTANCIA.janelaBase);
		textView.setText(resid);
		return textView.getText().toString();
	}

	/*
	 * Formata um numero para pelo menos dois digitos.
	 */
	public static String FormataDoisDigitos(int numero) {
		String num = new Integer(numero).toString();
		if (numero < 10) {
			num = "0" + num;
		}
		return num;
	}

	/*
	 * Formata um data para ser lida como numero. Ex: 20101210 corresponde a
	 * 10/12/2010
	 */
	public static String FormatarDataParaNumero(Date data) {
		try {
			return Funcoes.FormataDoisDigitos(data.getYear() + 1900)
					+ Funcoes.FormataDoisDigitos(data.getMonth() + 1)
					+ Funcoes.FormataDoisDigitos(data.getDate());
		} catch (Exception ex) {
			return "";
		}
	}

	/*
	 * Formata um data para ser lida como numero. Ex: 20101210 corresponde a
	 * 10/12/2010
	 */
	public static String FormatarDataParaNumero(String dataEmTexto) {
		if (dataEmTexto != null && dataEmTexto.length() > 0) {
			return FormatarDataParaNumero(ConverterParaDate(dataEmTexto));
		} else {
			return "";
		}
	}

	/*
	 * Carrega o valor atual da hora e exibe.
	 */
	public static void AtualizarExibicaoDaHora(Button botao, int idDaHora,
			int textoDoBotao, String posFixo) {
		if (posFixo == null) {
			posFixo = "";
		}

		String definicao = Definicoes.DEF_HORARIO
				+ (new Integer(idDaHora)).toString() + posFixo;
		String horario = Definicoes.INSTANCIA.Ler(definicao);
		int cor;
		if (horario != null && horario.length() > 0) {
			cor = Color.BLACK;
		} else {
			horario = Funcoes.LerRecurso(textoDoBotao);
			cor = Color.GRAY;
		}
		botao.setText(horario);
		botao.setTextColor(cor);
	}

	/*
	 * Montar um conjunto com as definições atuais para calcular o saldo de
	 * horas.
	 */
	public static HashMap<String, Float> ObterDefinicoesParaSaldoDeHoras() {
		HashMap<String, Float> definicoes = new HashMap<String, Float>();
		definicoes.put(Definicoes.DEF_HORA_MENOR_PERMITIDA, Float
				.parseFloat(Definicoes.INSTANCIA
						.Ler(Definicoes.DEF_HORA_MENOR_PERMITIDA)));
		definicoes.put(Definicoes.DEF_HORA_MAIOR_PERMITIDA, Float
				.parseFloat(Definicoes.INSTANCIA
						.Ler(Definicoes.DEF_HORA_MAIOR_PERMITIDA)));
		definicoes.put(Definicoes.DEF_HORA_ALMOCO, Float
				.parseFloat(Definicoes.INSTANCIA
						.Ler(Definicoes.DEF_HORA_ALMOCO)));
		definicoes.put(Definicoes.DEF_HORA_JORNADA, Float
				.parseFloat(Definicoes.INSTANCIA
						.Ler(Definicoes.DEF_HORA_JORNADA)));
		return definicoes;
	}

	/*
	 * Obtem o total de horas em formato decimal.
	 */
	public static Float ObterHoras(String horaEmTexto) {
		Date data = new Date();
		AplicarHoraEmDate(data, horaEmTexto);
		return ObterHoras(data);
	}

	/*
	 * Obtem o total de horas em formato decimal.
	 */
	public static Float ObterHoras(Date hora) {
		return new Float(hora.getHours())
				+ (new Float(hora.getMinutes()) / new Float(60));
	}

	/*
	 * Calcula a diferença de horas entre duas datas.
	 */
	public static Float CalcularDiferencaDeHoras(Date hora2, Date hora1) {
		Float horas2 = ObterHoras(hora2);
		Float horas1 = ObterHoras(hora1);
		return horas2 - horas1;
	}

	/*
	 * Calcula a diferença de horas entre duas datas.
	 */
	public static Float CalcularDiferencaDeHoras(String horaEmTexto2,
			String horaEmTexto1) {
		Date data2 = new Date();
		Date data1 = (Date) data2.clone();
		AplicarHoraEmDate(data2, horaEmTexto2);
		AplicarHoraEmDate(data1, horaEmTexto1);
		return CalcularDiferencaDeHoras(data2, data1);
	}

	/*
	 * Verifica se os horários permitem cálculos.
	 */
	public static boolean ValidarHorarios(String hora1, String hora2,
			String hora3, String hora4) {
		if ((hora1.length() == 0 && hora2.length() == 0 && hora3.length() == 0 && hora4
				.length() == 0)
				|| (hora1.length() > 0 && hora2.length() > 0
						&& hora3.length() > 0 && hora4.length() > 0)
				|| (hora1.length() > 0 && hora2.length() > 0
						&& hora3.length() == 0 && hora4.length() == 0)
				|| (hora1.length() == 0 && hora2.length() == 0
						&& hora3.length() > 0 && hora4.length() > 0)
				|| (hora1.length() > 0 && hora2.length() == 0
						&& hora3.length() == 0 && hora4.length() > 0)) {
			return true;
		} else {
			return false;
		}
	}

	/*
	 * Calcula o saldo de horas
	 */
	public static Float CalcularTempoTrabalhado(
			HashMap<String, Float> definicoes, String hora1, String hora2,
			String hora3, String hora4) {
		Float trabalhoManha = new Float(0);
		Float trabalhoTarde = new Float(0);

		if (hora1.length() > 0 && hora2.length() > 0) {
			trabalhoManha = CalcularDiferencaDeHoras(hora2, hora1);
		}

		if (hora3.length() > 0 && hora4.length() > 0) {
			trabalhoTarde = CalcularDiferencaDeHoras(hora4, hora3);
		}

		if (trabalhoManha == 0 && trabalhoTarde == 0 && hora1.length() > 0
				&& hora4.length() > 0) {
			trabalhoManha = CalcularDiferencaDeHoras(hora4, hora1);
			trabalhoTarde = trabalhoManha / 2;
			trabalhoManha = trabalhoTarde;
		}

		return trabalhoManha + trabalhoTarde;
	}

	/*
	 * Calcula o saldo de horas
	 */
	public static Float CalcularTempoTrabalhadoValido(
			HashMap<String, Float> definicoes, String hora1, String hora2,
			String hora3, String hora4) {
		Date horaInicial = null;
		if (hora1.length() > 0) {
			horaInicial = new Date();
			AplicarHoraEmDate(horaInicial, hora1);
			if (horaInicial.getHours() < definicoes
					.get(Definicoes.DEF_HORA_MENOR_PERMITIDA)) {
				hora1 = FormataDoisDigitos(definicoes.get(
						Definicoes.DEF_HORA_MENOR_PERMITIDA).intValue())
						+ ":00";
				AplicarHoraEmDate(horaInicial, hora1);
			}
		}

		Date horaFinal = null;
		if (hora4.length() > 0) {
			horaFinal = horaInicial == null ? new Date() : (Date) horaInicial
					.clone();
			AplicarHoraEmDate(horaFinal, hora4);
			if (horaFinal.getHours() >= definicoes
					.get(Definicoes.DEF_HORA_MAIOR_PERMITIDA)) {
				hora4 = FormataDoisDigitos(definicoes.get(
						Definicoes.DEF_HORA_MAIOR_PERMITIDA).intValue())
						+ ":00";
				AplicarHoraEmDate(horaFinal, hora1);
			}
		}

		if (horaInicial != null && horaFinal != null
				&& horaInicial.after(horaFinal)) {
			return new Float(0);
		}

		Float almoco = definicoes.get(Definicoes.DEF_HORA_ALMOCO);
		Float diferencaAlmoco = new Float(0);
		if (hora2.length() > 0 && hora3.length() > 0) {
			Float diferenca = ObterHoras(hora3) - ObterHoras(hora2);
			if (diferenca < almoco) {
				hora2 = "";
				hora3 = "";
				diferencaAlmoco = almoco;
			}
		} else if (hora1.length() > 0 && hora4.length() > 0) {
			hora2 = "";
			hora3 = "";
			diferencaAlmoco = almoco;
		}
		Float tempo = CalcularTempoTrabalhado(definicoes, hora1, hora2, hora3,
				hora4) - diferencaAlmoco;
		if (tempo < 0) {
			tempo = new Float(0);
		}
		return tempo;
	}

	/*
	 * Calcula o saldo de horas
	 */
	public static Float CalcularSaldoDeHoras(HashMap<String, Float> definicoes,
			String hora1, String hora2, String hora3, String hora4) {
		return CalcularTempoTrabalhadoValido(definicoes, hora1, hora2, hora3,
				hora4) - definicoes.get(Definicoes.DEF_HORA_JORNADA);
	}

	/*
	 * Formata o saldo de horas para exibição.
	 */
	public static String FormataHora(Float saldoNum) {
		String sinal = saldoNum < 0 ? "- " : "+ ";
		saldoNum = new Float(Math.abs(saldoNum));
		Integer hora = saldoNum.intValue();
		Integer minuto = new Integer(Math.round((saldoNum - new Float(saldoNum
				.intValue())) * new Float(60)));
		if (minuto == 60) {
			minuto = 0;
			hora += 1;
		}
		return sinal + FormataDoisDigitos(hora) + ":"
				+ FormataDoisDigitos(minuto);
	}

	/*
	 * Obter nome do dia da semana.
	 */
	public static String ObterDiaDaSemana(Date data) {
		switch (data.getDay()) {
		case 0:
			return "Domingo";
		case 1:
			return "Segunda";
		case 2:
			return "Terça";
		case 3:
			return "Quarta";
		case 4:
			return "Quinta";
		case 5:
			return "Sexta";
		case 6:
			return "Sábado";
		}
		return "";
	}
}
