import java.sql.SQLException;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;
import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

class SecondThreadMessageSender extends Thread {
    
	SecondThreadMessageSender(String name){
        super(name);
    }
      
	public void run() {
	    String url = ActiveMQConnection.DEFAULT_BROKER_URL;
	    String subject = "queue1"; 
		try {		
			ExampleDB exampledb = new ExampleDB();
			String result = exampledb.numberOfPeople();
	        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(url);
	        Connection connection = connectionFactory.createConnection();
	        connection.start();        
	        Session session = connection.createSession(false,Session.AUTO_ACKNOWLEDGE);          
	        Destination destination = session.createQueue(subject);          
	        MessageProducer producer = session.createProducer(destination);        
	        TextMessage message = session.createTextMessage(result);       
	        producer.send(message);         
	        System.out.println("Second message send");
	        connection.close();
		} catch (SQLException | JMSException e) {
			e.printStackTrace();
			System.out.println(e.toString());	
		}		
	}

}

 class ThirdThreadMessageSender extends Thread {
    
	ThirdThreadMessageSender(String name){
        super(name);
    }
      
	public void run() {
	    String url = ActiveMQConnection.DEFAULT_BROKER_URL;
	    String subject = "queue2"; 
		try {		
			ExampleDB exampledb = new ExampleDB();
			String result = exampledb.numberOfCities();
	        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(url);
	        Connection connection = connectionFactory.createConnection();
	        connection.start();        
	        Session session = connection.createSession(false,Session.AUTO_ACKNOWLEDGE);          
	        Destination destination = session.createQueue(subject);          
	        MessageProducer producer = session.createProducer(destination);        
	        TextMessage message = session.createTextMessage(result);       
	        producer.send(message);         
	        System.out.println("Third message send");
	        connection.close();
		} catch (SQLException | JMSException e) {
			e.printStackTrace();
			System.out.println(e.toString());	
		}		
	}

 }
public class MessageSender {
    private static String url = ActiveMQConnection.DEFAULT_BROKER_URL;
    private static String subject = "queue";     
    public static void main(String[] args) throws JMSException { 
        new SecondThreadMessageSender("SecondThreadMessageSender").start();
        new ThirdThreadMessageSender("ThirdThreadMessageSender").start();
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(url);
        Connection connection = connectionFactory.createConnection();
        connection.start();        
        Session session = connection.createSession(false,Session.AUTO_ACKNOWLEDGE);          
        Destination destination = session.createQueue(subject);          
        MessageProducer producer = session.createProducer(destination);        
        TextMessage message = session.createTextMessage(getAllData());       
        producer.send(message);         
        System.out.println("First message send");
        connection.close();
    }

    public static String getAllData() {
    	try {
    		ExampleDB exampledb = new ExampleDB();
    		String result = exampledb.check();
    		return result;
    	} catch (SQLException e) {
    		e.printStackTrace();
    		return e.toString();	
    	}		
    }}

