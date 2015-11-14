package hu.akoel.template.spring;

import hu.akoel.template.spring2.MessagePrinter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.*;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.*;

public class Application {
    
	public void exampleByXML() {

		//Megmondjuk, hogy hol a Bean leirase
		ApplicationContext context = new ClassPathXmlApplicationContext("Bean.xml");
		
		//Beazonositjuk a bean-t a parameter alapjan, letrehozzuk, feltoltjuk
		HelloWorld obj = (HelloWorld) context.getBean("helloWorld");
		obj.getMessage();
	}
	
	public void exampleByClass(){
					
		//Megmondjuk, hogy hol a Bean leirase
		ApplicationContext ctx = new AnnotationConfigApplicationContext(HelloWorldConfig.class);
				   
		//Beazonositjuk a bean-t a parameter alapjan(az a metodus, ami a parameternek megfelelo erteket ad vissza), letrehozzuk, feltoltjuk		
		HelloWorld helloWorld = ctx.getBean(HelloWorld.class);

		helloWorld.setMessage("Hello World! HW:" );
		helloWorld.getMessage();
	   
	}
	
	public void exampleComplex(){
		ApplicationContext context = new AnnotationConfigApplicationContext(Mock.class);
		MessagePrinter printer = context.getBean(MessagePrinter.class);
		printer.printMessage();
	}

  public static void main(String[] args) {	  
      (new Application()).exampleComplex();
  }
}