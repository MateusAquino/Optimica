package me.mateusakino.optimica;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ChemSpider {
	public String search(String smiles) throws IOException {
		request = new File("C:\\Users\\Home\\Desktop\\Developer Folders\\Java\\Trabalhos\\Optimica\\request.http");
		rSearch = new File("C:\\Users\\Home\\Desktop\\Developer Folders\\Java\\Trabalhos\\Optimica\\request_search.http");
		rInit = new File("C:\\Users\\Home\\Desktop\\Developer Folders\\Java\\Trabalhos\\Optimica\\request_init.http");
		
		String uuid = init();
		String res = search(uuid, smiles).trim();
		System.out.println("RES: " + res);
		if (!res.equals("Search ChemSpider")){
			Matcher m2 = Pattern.compile("www.chemspider.com\\/Chemical-Structure.([^.]*?).html").matcher(htmlRes);
			m2.find();
			m2.group(1);
			String[] r = res.split("\\|");
			Principal.instance.info.setImg("http://www.chemspider.com/ImagesHandler.ashx?id="+m2.group(1)+"&w=250&h=250");
			Principal.instance.info.setText("Fórmula molecular: "+r[1]+"\n"
									  	+ "SMILES: " + smiles);
			Principal.instance.info.setTitulo(r[0]);
		}
		return res;
	}
	
	private String htmlRes = "";
	private File request, rSearch, rInit;
	private String search(String uuid, String smiles) throws IOException {
		String dados = read(rSearch);
		dados = dados.replaceAll("C1CCCCC1C", smiles);
		write(request, dados);
		String resultadoHTML = request("http://www.chemspider.com/Search.aspx?rid="+uuid);
		System.out.println("http://www.chemspider.com/Search.aspx?rid="+uuid);
		if (resultadoHTML.contains("Object moved")){
			System.out.println("Redirecionando...");
			Matcher m = Pattern.compile("<a href=\"(.*?)\"").matcher(resultadoHTML);
			m.find();
			resultadoHTML = request("http://www.chemspider.com" + m.group(1)); 
		}
		Matcher m = Pattern.compile("<title>([\\s\\S]+?)<\\/title>").matcher(resultadoHTML);
		m.find();
		String res = m.group(1);
		if (res.trim().equals("Search ChemSpider")) {
			System.out.println("Mais de um elemento encontrado -> Fazer janela de opções");
			Principal.instance.info.setTitulo("erro temporario!\n(confira o console)");
		} else {
			htmlRes = resultadoHTML;
		}		
		return res;
	}
	
	private String init() throws IOException {
		String dados = read(rInit);
		write(request, dados);
		
		return request("http://www.chemspider.com/controls/search/SearchResultHandler.ashx").substring(10, 46);
	}

	private String request(String url) throws IOException {
		return HttpRequests.req(url, read(request));
	}
	
    private void write(File file, String data) throws IOException {
		BufferedWriter writer = new BufferedWriter(new FileWriter(file));
	    writer.write(data);
	     
	    writer.close();
	}
    
    private String read(File file) throws IOException {
    	StringBuilder txt = new StringBuilder();
    	Files.readAllLines(file.toPath()).forEach(e->txt.append(e+"\n"));
    	return txt.toString();
	}
}