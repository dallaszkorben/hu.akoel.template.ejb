package hu.akoel.template.spring;

import org.springframework.context.annotation.*;

@Configuration
@ComponentScan("hu.akoel.template.spring2")
public class Mock {
	
	@Bean
    String haho() {
		System.out.println("elindult a haho");
        return new String("haho");
	}
	
	@Bean
    MessageService mockMessageService() {
		System.out.println("elindult a MessageService() method");
        return new MessageService() {
            public String getMessage() {            	
              return "Hello World!";
            }
        };
    }

	
	
/*	@Bean
	MessagePrinter messP(){
		return new MessagePrinter(new MessageService() {
			
			@Override
			public String getMessage() {
				
				return "ez itt a messageService implementacio";
			}
		});
	}
*/	
	
}
