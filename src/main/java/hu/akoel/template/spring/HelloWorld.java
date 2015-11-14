package hu.akoel.template.spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class HelloWorld {
	private String message;

	public HelloWorld(){
		System.err.println("most jon letre a HelloWorld() + MessagePrinter: " );
	}
	
	public void setMessage(String message) {
		this.message = message;
	}

	public void getMessage() {
		System.out.println("Your Message : " + message);
	}
}
