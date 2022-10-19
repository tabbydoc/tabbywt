package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.*;
import java.net.URL;
import java.net.MalformedURLException;
import java.util.Scanner;
import java.util.regex.Pattern;

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

	@GetMapping("/read-website")
	public String readwebsite(@RequestParam(value = "url", defaultValue = "https://example.com") String url){
		try {
			//TableSearcher ts = new TableSearcher(url);
			int count = 1; //ts.countTables();
			return String.format("Tables = %d", count);
		}
		catch(Exception e){
			return "Oops! Something went wrong...\n ----- " + e.getMessage();
		}

	}

}
            