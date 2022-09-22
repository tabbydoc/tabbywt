package com.example.demo.tmp_stuff;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public abstract class GetHTMLCode {
    public static String getHtmlResourceByURL(String url,String encoding){
        StringBuffer sb = new StringBuffer();
        URL urlObj = null;
        URLConnection uc = null;
        BufferedReader br = null;

        try{
            // Установить сетевое соединение
            urlObj = new URL(url);
            // Открываем сетевое соединение
            uc = urlObj.openConnection();
            br = new BufferedReader(new InputStreamReader(uc.getInputStream(),encoding));
            String tempLine = null; // Временная переменная (то есть временный файл)
            while((tempLine=br.readLine())!=null){
                sb.append (tempLine + "\n"); // Автоматически переносится после чтения строки
            }
        }catch(Exception e){
            e.getStackTrace();
            System.out.println("Connection timeOut......");
        }finally{
            if(null != br){
                try
                {
                    br.close();
                } catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
        }
        return sb.toString();
    }

}
