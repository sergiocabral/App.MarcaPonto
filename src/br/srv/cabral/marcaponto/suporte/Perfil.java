/**
 * 
 */
package br.srv.cabral.marcaponto.suporte;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import br.srv.cabral.marcaponto.R;
import br.srv.cabral.marcaponto.janelas.JanelaGenerica;
import br.srv.cabral.marcaponto.persistencia.HorariosEmSQLite;

/**
 * Gerencia os perfis do usuários
 * 
 * @author Sergio Cabral (sergio@cabral.srv.br)
 */
public class Perfil {

	public Perfil(JanelaGenerica janelaBase) {
		JanelaBase = janelaBase;
	}

	/**
	 * Janela base.
	 */
	protected JanelaGenerica JanelaBase;

	/**
	 * Retorna o nome do perfil padrão do aplicativo quando nenhum existe.
	 * 
	 * @return
	 */
	public String getNomePadraoParaPerfil() {
		return JanelaBase.getResources().getString(R.string.comum_perfilPadrao);
	}

	/**
	 * Retorna o perfil atualmente selecionado.
	 * 
	 * @return Nome do perfil
	 */
	public String getPerfilSelecionado() {
		String perfil = JanelaBase.Preferencias.getPerfilSelecionado();
		if (perfil == null || !Existe(perfil)) {
			perfil = "";
		}
		return perfil;
	}

	/**
	 * Retorna o caminho de gravação dos perfis.
	 * 
	 * @return
	 */
	public String getCaminhoDeGravacao() {
		try {
			return JanelaBase.Preferencias
					.getCaminhoDosDadosEConfiguracaoReal();
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * Seleciona o perfil padrao caso não haja nenhumperfil selecionado.
	 * 
	 * @return Retorna true quando um novo perfil teve de ser criado, pois não
	 *         existia outro.
	 * @throws Exception
	 */
	public boolean SelecionarPadrao() throws Exception {
		if (getPerfilSelecionado() == "") {
			try {
				Selecionar(getListarDePerfis().get(0));
			} catch (Exception e) {
				Adicionar(getNomePadraoParaPerfil());
				Selecionar(getListarDePerfis().get(0));
				return true;
			}
		}
		return false;
	}

	/**
	 * Obtem a lista de perfis do usuários.
	 * 
	 * @return Lista
	 */
	public List<String> getListarDePerfis() {
		ArrayList<String> lista = new ArrayList<String>();

		File caminho = new File(getCaminhoDeGravacao());
		File[] arquivos = caminho.listFiles(new FilenameFilter() {
			public boolean accept(File dir, String filename) {
				return filename.endsWith("."
						+ Constantes.EXTENSAO_PARA_ARQUIVO_DE_PERFIL);
			}
		});
		for (File arquivo : arquivos) {
			lista.add(FormatarNomeDoPerfil(arquivo));
		}

		return lista;
	}

	/**
	 * Obtem o arquivo (File) do perfil.
	 * 
	 * @param perfil
	 * @return
	 */
	public File ObterArquivo(String perfil) {
		return new File(getCaminhoDeGravacao() + perfil.trim() + "." + Constantes.EXTENSAO_PARA_ARQUIVO_DE_PERFIL);
	}

	/**
	 * Converte um nome de perfil no caminho do arquivo que armazena os dados.
	 * 
	 * @param perfil
	 * @return
	 */
	public String FormatarNomeDoArquivo(String perfil) {
		String caminho = getCaminhoDeGravacao();
		return (caminho.equals(IO.CaminhoDatabases()) ? "" : caminho)
				+ perfil.trim() + "."
				+ Constantes.EXTENSAO_PARA_ARQUIVO_DE_PERFIL;
	}

	/**
	 * Converte um caminho de arquivo de perfil no nome do perfil.
	 * 
	 * @param caminho
	 * @return
	 */
	public String FormatarNomeDoPerfil(File caminho) {
		String sufixo = "." + Constantes.EXTENSAO_PARA_ARQUIVO_DE_PERFIL;
		StringBuilder nome = new StringBuilder(caminho.getName());
		while (nome.indexOf("\\") >= 0) {
			nome.delete(0, nome.indexOf("\\") + 1);
		}
		nome.delete(nome.length() - sufixo.length(), nome.length() + 1);
		return nome.toString();
	}

	/**
	 * Verifica se um perfil existe.
	 * 
	 * @param perfil
	 * @return
	 */
	public boolean Existe(String perfil) {
		perfil = perfil.trim();
		try {
			return ObterArquivo(perfil).getCanonicalFile().exists();
		} catch (IOException e) {
			return false;
		}
	}

	/**
	 * Seleciona um perfil.
	 * 
	 * @param nome
	 * @throws Exception
	 */
	public void Selecionar(String perfil) throws Exception {
		perfil = perfil.trim();
		if (Existe(perfil)) {
			JanelaBase.Preferencias.setPerfilSelecionado(perfil);
		} else {
			throw new Exception("O perfil informado não existe.");
		}
	}

	/**
	 * Adiciona um novo perfil.
	 * 
	 * @param perfil
	 */
	public void Adicionar(String perfil) {
		perfil = perfil.trim();
		new HorariosEmSQLite(JanelaBase, FormatarNomeDoArquivo(perfil));
	}

	/**
	 * Renomeia um perfil.
	 * 
	 * @param perfil
	 * @param novoNome
	 */
	public void Renomear(String perfil, String novoNome) {
		perfil = perfil.trim();
		novoNome = novoNome.trim();
		File arquivo = ObterArquivo(perfil);
		arquivo.renameTo(ObterArquivo(novoNome));
		if (JanelaBase.Preferencias.getPerfilSelecionado().equals(perfil)) {
			JanelaBase.Preferencias.setPerfilSelecionado(novoNome);
		}
	}

	/**
	 * Exclui um perfil.
	 * 
	 * @param perfil
	 */
	public void Excluir(String perfil) {
		perfil = perfil.trim();
		File arquivo = ObterArquivo(perfil);
		arquivo.delete();
	}

	/**
	 * Copiar um perfil duplicando ele.
	 * 
	 * @param perfil
	 * @param novoNome
	 * @throws IOException
	 */
	public void Copiar(String perfil, String novoNome) throws IOException {
		String caminho = getCaminhoDeGravacao();
		if (caminho == IO.CaminhoDatabases())
		{
			caminho = "";
		}
		Exportar(perfil, novoNome, caminho);
	}

	/**
	 * Exporta um perfil para um caminho de arquivo.
	 * 
	 * @param perfil
	 * @param caminho
	 */
	public void Exportar(String perfil, String novoNome, String caminho) throws IOException {
		perfil = perfil.trim();
		novoNome = novoNome.trim();
		caminho = caminho.trim();

		File arquivoEntrada = ObterArquivo(perfil);
		InputStream streamEntrada = new BufferedInputStream(
				new FileInputStream(arquivoEntrada));

		if (caminho != "")
		{
			caminho = (caminho + "/").replace("//", "/");
		}
		String caminhoSaida = (caminho + novoNome + "." + Constantes.EXTENSAO_PARA_ARQUIVO_DE_PERFIL);
		OutputStream streamSaida = new FileOutputStream(caminhoSaida);

		byte[] buffer = new byte[(int) arquivoEntrada.length()];
		streamEntrada.read(buffer, 0, buffer.length);
		streamSaida.write(buffer);

		streamSaida.flush();
		streamSaida.close();
		streamEntrada.close();
	}
}
