package hu.akoel.template.spring2;

import hu.akoel.template.spring.MessageService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MessagePrinter {

    final private MessageService service;

    @Autowired    
    public MessagePrinter(MessageService service) {
    	System.out.println("Most jon letre a MessagePrinter");
        this.service = service;
    }

    public MessagePrinter(){
    	service = new MessageService() {
            public String getMessage() {            	
              return "Hello World! Method without paramater";
            }
        };
    }
    
    public void printMessage() {
        System.out.println(this.service.getMessage());
    }
}