import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import com.prosysopc.ua.SecureIdentityException;
import com.prosysopc.ua.StatusException;
import com.prosysopc.ua.server.UaServerException;

//Ua Server开发2-实时数据管理
public class MainProgram {

	public static void main(String[] args) throws UaServerException, SecureIdentityException, IOException, StatusException, InterruptedException {
		OpcUaServer server = new OpcUaServer();		
		
		server.start();		
		
		char userCommand = ' ';
		
		do 
		{			
			userCommand = PrintMemu();
			
			if (userCommand == '1')
			{
				server.datachange();
			}
		} while (userCommand != 'x'); 	
		
		server.stop();
	}
	
	public static char PrintMemu() throws IOException
	{
		System.out.println();
		System.out.println("1. 开始读取");
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
	
	private static BufferedReader s_readBuffer;
	private static InputStreamReader s_readStream;
	
}
