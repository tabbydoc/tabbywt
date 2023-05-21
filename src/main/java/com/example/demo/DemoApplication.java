package com.example.demo;


import TableThings.ClassifierErebius;
import TableThings.TableClassifier;
import TableThings.Discriminator;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import webreduce.data.TableType;
import webreduce.extraction.mh.tools.TableConvert;

import java.util.List;
import java.util.Vector;


@SpringBootApplication
@RestController
public class DemoApplication {
	public final String TEST_URL = "https://en.wikipedia.org/wiki/List_of_countries_by_GDP_(nominal)";

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

	@GetMapping("/mytest")
	public String mytest() throws Exception{
		String url = "https://en.wikipedia.org/wiki/List_of_countries_by_GDP_(nominal)";
		//String url = "file:///home/ilya/Downloads/convertcsv.htm";
		String htmlResource = GetHTMLCode.getHtmlResourceByURL(url, "UTF-8");
		Document doc = Jsoup.parse(htmlResource);

		Elements tables = doc.getElementsByTag("table");

		StringBuilder sb = new StringBuilder();

		sb.append("<h1>").append(url).append("</h1>\n\n");

		Discriminator discr = new DiscriminatorErebius();
		TableConvert tconv = new TableConvert(2,2);

		MIPSAlgorithm mips = new MIPSAlgorithm();

		for(Element table : tables){
			TableType type = discr.classify(table);
			if(type == TableType.LAYOUT) {
				continue;
			}

			Element[][] tb = tconv.toTable(table).get();
			TableCoordinates cc2 = new TableCoordinates(999,999);
			try {
				cc2 = mips.search2arr(tb);
			} catch (Exception e) {}

			sb.append("\n\n[[table]]\n");
			sb.append(String.format("cc2: %d %d\n", cc2.i, cc2.j));
			for(int i = 0; i < tb.length; ++i){
				sb.append("\t[ROW").append(i).append("]\n");
				for(int j = 0; j < tb[i].length; ++j){
					sb.append("\t\t[").append(j).append("> ");
					if(tb[i][j] != null)
						sb.append(tb[i][j].toString());

					else
						sb.append("[NULL]");
					sb.append("\n");
				}
				sb.append("\n");
			}

			sb.append("[[/table]]");
		}

		return sb.toString();
	}

	@GetMapping("/getjson")
	public ResponseInfo getJson(@RequestParam(value = "url", defaultValue = TEST_URL) String url) throws Exception {
		ResponseInfo json = new ResponseInfo();
		json.url = url;
		json.vector = new Vector<TableInfo>();

		String htmlResource = GetHTMLCode.getHtmlResourceByURL(url,"UTF-8"); //Получаем код со страницы
		Document document = Jsoup.parse(htmlResource);

		Elements tables = document.getElementsByTag("table");

		Discriminator discr = new DiscriminatorErebius();
		TableConvert tconv = new TableConvert(2,2);
		MIPSAlgorithm mips = new MIPSAlgorithm();

		for(Element table : tables){
			TableInfo newTable = new TableInfo();

			TableType type = discr.classify(table);
			newTable.type = type;
			if(type == TableType.LAYOUT) {
				json.vector.add(newTable);
				continue;
			}

			Element[][] tb = tconv.toTable(table).get();
			TableCoordinates cc2 = new TableCoordinates(999,999);
			try {
				cc2 = mips.search2arr(tb);
			} catch (Exception e) {}

			newTable.criticalCornerI = cc2.i;
			newTable.criticalCornerJ = cc2.j;

			json.vector.add(newTable);
		}

		return json;
	}
	@GetMapping("/type")
	public String typeTest(@RequestParam(value = "url", defaultValue = "https://example.com") String url) throws Exception {


		Document doc = Jsoup.connect(url).get(); //document – объект документа представляет HTML DOM.
		                                         //Jsoup – основной класс для подключения URL- адреса и получения строки HTML.
		                                         //метод get () возвращает html запрошенного URL.


		String htmlResource = GetHTMLCode.getHtmlResourceByURL(url,"UTF-8"); //Получаем код со страницы

		Document document = Jsoup.parse(htmlResource);
//Элементс главнй класс jsoup

	//	System.out.println(htmlResource); //Вывод HTML кода в консоль

		Elements tables = document.getElementsByTag("table");
//Elements Наследует ArrayList так шо здесь у нас список тэйблов
		System.out.println("\nРАЗДЕЛЕНИЕ");
		//getAllElements выбирает все дочерние элементы и дочерние элементы дочерних элементов
//		System.out.println(tables.get(0));

//		Table tableExample = ElementToTable.transfer(tables.get(0),url);
	//	System.out.println(tables.get(0));


//		Gson gson = new GsonBuilder()
//				.setPrettyPrinting()
//				.create();
//
//		try(FileWriter writer = new FileWriter("TableToJson.json", false))
//		{
//			writer.write(gson.toJson(tableExample));
//		}
//		catch(IOException ex){
//			System.out.println(ex.getMessage());
//		}

		StringBuilder sb = new StringBuilder();
		sb.append("<h1>");
		sb.append(url);
		sb.append("</h1>\n\n");

		Discriminator discr = new DiscriminatorErebius();
		TableClassifier tclass = new ClassifierErebius();

		for(Element table : tables){
			TableType type = discr.classify(table);
			sb.append("\n\n\t<h2>");
			if(type == TableType.LAYOUT) {
				sb.append("Layout table");
			} else {
				type = tclass.classify(table);
				sb.append("Type: ");
				sb.append(type);
			}
			sb.append("</h2>\n");

			sb.append(table);
		}

		return sb.toString();

//		int count = doc.getElementsByTag("table").size(); //подсчет колличества тегов <table>
//		return String.format("Кол - во тегов " + count+ "\n" );

	}
}

class ResponseInfo{
	public String url;
	public Vector<TableInfo> vector;
}

class TableInfo{
	public TableType type;
	public int criticalCornerI;
	public int criticalCornerJ;
}
