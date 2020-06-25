import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.text.ParseException;

import org.opcfoundation.ua.encoding.DecodingException;

import com.prosysopc.ua.SecureIdentityException;
import com.prosysopc.ua.ServiceException;
import com.prosysopc.ua.StatusException;
import com.prosysopc.ua.client.ConnectException;
import com.prosysopc.ua.client.InvalidServerEndpointException;
import com.prosysopc.ua.client.ServerConnectionException;
import com.prosysopc.ua.client.ServerListException;

//Ua Client开发3-订阅
public class MainProgram {
	
	
	private static BufferedReader s_readBuffer;
	private static InputStreamReader s_readStream;

	public static void main(String[] args) throws IOException, ServerListException, InvalidServerEndpointException, ConnectException, SecureIdentityException, ServiceException, URISyntaxException, StatusException, ServerConnectionException, DecodingException {
		
		OpcUaClient client = new OpcUaClient();
		
		if (client.connect())
		{
			System.out.println("连接到 " + client.getServerUrl());
		} else {
			System.out.println("无法连接到 " + client.getServerUrl());	
			return;
		}
		
		Menu(client);
	}

	private static void Menu(OpcUaClient client) throws IOException, ServiceException, StatusException, ServerConnectionException, DecodingException
	{	
		char userCommand = ' ';
	
		do 
		{			
			userCommand = PrintMemu();
			
			if (userCommand == '1')
			{
				System.out.println("浏览列表");
				client.browse();
			}
			
			if (userCommand == '2')
			{
				client.startSubscription();
			}
				
			if (userCommand == '3')
			{
				client.stopSubscription();
			}
			
			if (userCommand == '4')
			{
				System.out.println("历史数据");
				try {
					client.readHistory();
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		} while (userCommand != 'x'); 		
		
		client.disconnect();
		
		System.out.println("断开服务器 " + client.getServerUrl());	
	}
	
	public static char PrintMemu() throws IOException
	{
		System.out.println();
		System.out.println("1. 浏览地址空间");
		System.out.println("2. 开始订阅");
		System.out.println("3. 结束订阅");
		System.out.println("4. 读取历史");
		System.out.println("x. 断开服务器");
		
		String userInput = readLine();
		
		char userCommand = ' ';
		
		if (userInput.length() > 0)
		{
			userCommand = userInput.charAt(0);
		}
		
		return userCommand;
	}
	
	public static String readLine() throws IOException
	{
		if (null == s_readStream)
		{
			s_readStream = new InputStreamReader(System.in);
		}
		
		if (null == s_readBuffer)
		{
			s_readBuffer =  new BufferedReader(s_readStream);
		}
		
		return s_readBuffer.readLine();
	}
	
}
