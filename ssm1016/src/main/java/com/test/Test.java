package com.test;

import cn.com.webxml.ArrayOfString;
import cn.com.webxml.WeatherWSSoap;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class Test {
    public static void main(String[] args) {
        ArrayOfString arrayOfString= null;
        try {
            URL url=new URL("http://ws.webxml.com.cn/WebServices/WeatherWS.asmx?wsdl");
           QName qName=new QName("http://WebXml.com.cn","WeatherWS");
            Service service=Service.create(url,qName);
            WeatherWSSoap soap=service.getPort(WeatherWSSoap.class);
            arrayOfString = soap.getWeather("上海",null);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        List<String> list=arrayOfString.getString();
        System.out.println(".......................................");
        for (String s : list) {
            System.out.println(s);
        }
        System.out.println(".......................................");
    }
}
