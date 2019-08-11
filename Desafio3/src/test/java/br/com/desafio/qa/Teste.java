package br.com.desafio.qa;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

import org.apache.poi.EncryptedDocumentException;
import org.junit.Test;

/**
 * Como lojista<br>
   Quero saber quanto de desconto posso conceder no carrinho<br>
   Para fidelizar um cliente
 *
 */
public class Teste {

	DecimalFormat numberFormat = new DecimalFormat("#.00");
	
	/**
	 * Acima de R$ 100 e abaixo de R$ 200 => 10%
	 * @throws EncryptedDocumentException
	 * @throws IOException
	 */
	@Test
	public void teste1() throws EncryptedDocumentException, IOException {
		
		int[] carrinho = {getIdFilme("Senhor dos Anéis"), getIdFilme("As Branquelas"), getIdFilme("Velozes e Furiosos 6")};
		
		System.out.println("Total com desconto de 10%: " + numberFormat.format(calcularTotal(carrinho)));
	}
	
	/**
	 * Acima de R$ 200 e abaixo de R$ 300 => 20%
	 * @throws EncryptedDocumentException
	 * @throws IOException
	 */
	@Test
	public void teste2() throws EncryptedDocumentException, IOException {
		
		int[] carrinho = {getIdFilme("Senhor dos Anéis"), getIdFilme("As Branquelas"), getIdFilme("Velozes e Furiosos 6"),
						  getIdFilme("Velozes e Furiosos 7")};
		
		System.out.println("Total com desconto de 20% + 5% do gênero de Ação: " + numberFormat.format(calcularTotal(carrinho)));
	}
	
	/**
	 * Acima de R$ 300 e abaixo de R$ 400 => 25%
	 * @throws EncryptedDocumentException
	 * @throws IOException
	 */
	@Test
	public void teste3() throws EncryptedDocumentException, IOException {
		
		int[] carrinho = {getIdFilme("Meu Malvado Favorito"), getIdFilme("The Scapegoat"), getIdFilme("Senhor dos Anéis")};
		
		System.out.println("Total com desconto de 25%: " + numberFormat.format(calcularTotal(carrinho)));
	}
	
	/**
	 * Acima de R$ 400 => 30%
	 * @throws EncryptedDocumentException
	 * @throws IOException
	 */
	@Test
	public void teste4() throws EncryptedDocumentException, IOException {
		
		int[] carrinho = {getIdFilme("Meu Malvado Favorito"), getIdFilme("The Scapegoat"), getIdFilme("Velozes e Furiosos 6"),
						  getIdFilme("Velozes e Furiosos 7")};
		
		System.out.println("Total com desconto de 30% + 5% do gênero de Ação: " + numberFormat.format(calcularTotal(carrinho)));
	}
	
	/**
	 * Calcula o valor total dos itens do carrinho.
	 * @param carrinho
	 * @return
	 * @throws NumberFormatException
	 * @throws IOException
	 */
	public static Double calcularTotal(int carrinho[]) throws NumberFormatException, IOException {
		
		double total = 0.0;
		
		for (int i = 0; i < carrinho.length; i++) {
			
			total += Integer.parseInt(getValorFilmes(Integer.valueOf(carrinho[i])).trim());
		}
		
		if(total >= 100 && total < 200) {
			
			total -= (total * 10)/100;
		
		}else if(total >= 200 && total < 300) {
			
			total -= (total * 20)/100;
		
		}else if(total >= 300 && total < 400) {
			
			total -= (total * 25)/100;
		
		}else if(total >= 400) {
			
			total -= (total * 30)/100;
		}
		
		for (int i = 0; i < carrinho.length; i++) {
			
		  if(getGeneroFilme(carrinho[i]).equals("Ação")) {
			
			  total -= (total * 5)/100;
		  }
		}
		
		return total;
	}
	
	public static String getValorFilmes(int id) throws IOException {
		
		Map<Integer, String> map = new HashMap<Integer, String>();
		
		String fileName = "./filmes/filmes.txt";
		File file = new File(fileName);
		FileReader fr = new FileReader(file);
		BufferedReader br = new BufferedReader(fr);
		String line;
		int count = 1;
		
		while((line = br.readLine()) != null){
		
			map.put(count, line);
		    count++;
		}
		
		String s = map.get(id);
		String valor[] = s.split("-");
		
		return valor[3];
	}
	
	public static int getIdFilme(String filme) throws IOException {
		
		Map<Integer, String> map = new HashMap<Integer, String>();
		
		String fileName = "./filmes/filmes.txt";
		File file = new File(fileName);
		FileReader fr = new FileReader(file);
		BufferedReader br = new BufferedReader(fr);
		String line;
		int count = 1;
		
		while((line = br.readLine()) != null){
		
			map.put(count, line);
		    count++;
		}
		
		int id = 0;
		
		for (int i = 1; i <= count; i++) {
			
			if (map.get(i).contains(filme)) {
				
				String s = map.get(i);
				String valor[] = s.split("-");
				
				id = Integer.parseInt(valor[0].trim());
				
				break;
			}
		}
		
		return id;
	}
	
	public static String getGeneroFilme(int id) throws IOException {
		
		Map<Integer, String> map = new HashMap<Integer, String>();
		
		String fileName = "./filmes/filmes.txt";
		File file = new File(fileName);
		FileReader fr = new FileReader(file);
		BufferedReader br = new BufferedReader(fr);
		String line;
		int count = 1;
		
		while((line = br.readLine()) != null){
		
			map.put(count, line);
		    count++;
		}
		
		String s = map.get(id);
		String valor[] = s.split("-");
		
		return valor[2].trim();
	}
}
