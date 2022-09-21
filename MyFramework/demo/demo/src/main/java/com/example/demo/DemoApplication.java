package com.example.demo;

import com.aspose.cells.SaveFormat;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@SpringBootApplication
@RestController
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@GetMapping("/hello")
	public String hello(@RequestParam(value = "name", defaultValue = "World") String name) {
		return String.format("Hello %s!", name);
	}



	@GetMapping("/CounttableJsoup")
	public String counttables(@RequestParam(value = "url", defaultValue = "https://example.com") String url) throws Exception {


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
		System.out.println(tables);


		int count = doc.getElementsByTag("table").size(); //подсчет колличества тегов <table>
		return String.format("Кол - во тегов " + count+ "\n" );

	}
}