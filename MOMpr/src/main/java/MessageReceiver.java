import java.sql.SQLException;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;
import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;



class ThirdThreadMessageReceiver extends Thread {

	ThirdThreadMessageReceiver(String name) {
		super(name);
	}

	public void run() {
		String url = ActiveMQConnection.DEFAULT_BROKER_URL;
		String subject = "queue2";
		try {
			ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(url);
			Connection connection = connectionFactory.createConnection();
			connection.start();
			Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
			Destination destination = session.createQueue(subject);
			MessageConsumer consumer = session.createConsumer(destination);
			Message message = consumer.receive();
			if (message instanceof TextMessage) {
				TextMessage textMessage = (TextMessage) message;
				System.out.println("Received message (thread 3) : \n" + textMessage.getText());
				connection.close();
			}
		
		} catch (JMSException e) {
			e.printStackTrace();
			System.out.println(e.toString());
		}
	}

}

class SecondThreadMessageReceiver extends Thread {

	SecondThreadMessageReceiver(String name) {
		super(name);
	}

	public void run() {
		String url = ActiveMQConnection.DEFAULT_BROKER_URL;
		String subject = "queue1";
		try {
			ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(url);
			Connection connection = connectionFactory.createConnection();
			connection.start();
			Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
			Destination destination = session.createQueue(subject);
			MessageConsumer consumer = session.createConsumer(destination);
			Message message = consumer.receive();
			if (message instanceof TextMessage) {
				TextMessage textMessage = (TextMessage) message;
				System.out.println("Received message (thread 2) : \n" + textMessage.getText());
				connection.close();
			}
		
		} catch (JMSException e) {
			e.printStackTrace();
			System.out.println(e.toString());
		}
	}
}

public class MessageReceiver {
	private static String url = ActiveMQConnection.DEFAULT_BROKER_URL;
	private static String subject = "queue";
	public static void main(String[] args) throws JMSException, InterruptedException {
		ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(url);
		Connection connection = connectionFactory.createConnection();
		connection.start();
		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		Destination destination = session.createQueue(subject);
		MessageConsumer consumer = session.createConsumer(destination);
		Message message = consumer.receive();
        new SecondThreadMessageReceiver("SecondThreadMessageReceiver").start();
        new ThirdThreadMessageReceiver("ThirdThreadMessageReceiver").start();
		if (message instanceof TextMessage) {
			TextMessage textMessage = (TextMessage) message;
			System.out.println("Received message (main thread) : \n" + textMessage.getText());
			connection.close();
		}
	}
}
