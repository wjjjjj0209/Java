import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.text.ParseException;
import java.util.List;
import java.util.Locale;

import org.opcfoundation.ua.builtintypes.DataValue;
import org.opcfoundation.ua.builtintypes.DateTime;
import org.opcfoundation.ua.builtintypes.LocalizedText;
import org.opcfoundation.ua.builtintypes.NodeId;
import org.opcfoundation.ua.core.ApplicationDescription;
import org.opcfoundation.ua.core.ApplicationType;
import org.opcfoundation.ua.core.Identifiers;
import org.opcfoundation.ua.core.ReferenceDescription;
import org.opcfoundation.ua.core.TimestampsToReturn;
import org.opcfoundation.ua.encoding.DecodingException;
import org.opcfoundation.ua.transport.security.SecurityMode;

import com.prosysopc.ua.ApplicationIdentity;
import com.prosysopc.ua.PkiFileBasedCertificateValidator;
import com.prosysopc.ua.SecureIdentityException;
import com.prosysopc.ua.ServiceException;
import com.prosysopc.ua.SessionActivationException;
import com.prosysopc.ua.StatusException;
import com.prosysopc.ua.client.ConnectException;
import com.prosysopc.ua.client.InvalidServerEndpointException;
import com.prosysopc.ua.client.ServerConnectionException;
import com.prosysopc.ua.client.UaClient;

public class OpcUaClient {
	
	
private static String APP_NAME    = "UaClient";
private static String APP_URI     = "urn:localhost:UA:UaClient";
private static String PRODUCT_URI = "urn:sunwayland.com:UA:UaClient";	

private static String SERVER_URL = "opc.tcp://127.0.0.1:4850/UaServer";

private UaClient m_client;
private OpcUaSubscription m_subscription;

	public OpcUaClient() {
		m_subscription = null;
	}
	
	public Boolean connect() throws SecureIdentityException, IOException, InvalidServerEndpointException, ConnectException, ServiceException, URISyntaxException
	{
		try {
			Initialize();
			m_client.connect();
		} catch (Exception e) {			
			e.printStackTrace();
			return false;
		}
		
		return true;
	}
	
	public void disconnect()
	{
		m_client.disconnect();
	}
	
	public void browse() throws ServiceException, StatusException
	{
		m_client.getAddressSpace().setReferenceTypeId(Identifiers.HierarchicalReferences);
		
		List<ReferenceDescription> objectNodes = m_client.getAddressSpace().browse(new NodeId(2,"Machine Tool"));

		for (int i=0; i<objectNodes.size(); i++)
		{
			System.out.println(objectNodes.get(i).getDisplayName().getText());	
			
			NodeId objectId = new NodeId(objectNodes.get(i).getNodeId().getNamespaceIndex(), (String)objectNodes.get(i).getNodeId().getValue());
			List<ReferenceDescription> memberNodes = m_client.getAddressSpace().browse(objectId);
				
			for (int j=0; j<memberNodes.size(); j++)
			{
				System.out.println("  "+objectNodes.get(i).getDisplayName().getText()+"成员： " + memberNodes.get(j).getDisplayName().getText());	
				
				NodeId objectId1 = new NodeId(memberNodes.get(j).getNodeId().getNamespaceIndex(), (String)memberNodes.get(j).getNodeId().getValue());
				List<ReferenceDescription> memberNodes1 = m_client.getAddressSpace().browse(objectId1);
				
				for (int k=0; k<memberNodes1.size(); k++) {
					System.out.println("    "+memberNodes.get(j).getDisplayName().getText()+"成员： " + memberNodes1.get(k).getDisplayName().getText());	
				}
				
				System.out.println();
			}
			
			System.out.println();
		} 
	}	

	
	public void startSubscription() throws ServiceException, StatusException
	{
		if (null == m_subscription)
		{		
			m_subscription = new OpcUaSubscription(m_client);
			m_subscription.createMonitoredItem();
		} 
		
		m_subscription.startSubscription();
	}
	
	public void stopSubscription() throws ServiceException, StatusException
	{
		if (null != m_subscription)
		{
			m_subscription.stopSubscription();
		}
	}

	public void readHistory() throws ServerConnectionException, DecodingException, ServiceException, StatusException, ParseException
	{	
		String variableNameX = new String("Mcoordinates.X");	
		NodeId idX = new NodeId(2, variableNameX);

		DateTime startTimeX = DateTime.parseDateTime("2018-03-27 00:00:00");
		DateTime endTimeX = DateTime.parseDateTime("2018-04-29 24:00:00");		
		
		DataValue[] valuesX = null;
		valuesX = m_client.historyReadRaw(idX, startTimeX, endTimeX, 0, false, null, TimestampsToReturn.Source);
		
		if (null == valuesX) return;
		
		String variableNameY = new String("Mcoordinates.Y");	
		NodeId idY = new NodeId(2, variableNameY);

		DateTime startTimeY = DateTime.parseDateTime("2018-03-27 00:00:00");
		DateTime endTimeY = DateTime.parseDateTime("2018-04-29 24:00:00");		
		
		DataValue[] valuesY = null;
		valuesY = m_client.historyReadRaw(idY, startTimeY, endTimeY, 0, false, null, TimestampsToReturn.Source);
		
		if (null == valuesY) return;
		
		String variableNameZ = new String("Mcoordinates.Z");	
		NodeId idZ = new NodeId(2, variableNameZ);

		DateTime startTimeZ = DateTime.parseDateTime("2018-03-27 00:00:00");
		DateTime endTimeZ = DateTime.parseDateTime("2018-04-29 24:00:00");		
		
		DataValue[] valuesZ = null;
		valuesZ = m_client.historyReadRaw(idZ, startTimeZ, endTimeZ, 0, false, null, TimestampsToReturn.Source);
		
		if (null == valuesZ) return;
		
		String variableNameF = new String("Machine Tool.FedRate");	
		NodeId idF = new NodeId(2, variableNameF);

		DateTime startTimeF = DateTime.parseDateTime("2018-03-27 00:00:00");
		DateTime endTimeF = DateTime.parseDateTime("2018-04-29 24:00:00");		
		
		DataValue[] valuesF = null;
		valuesF = m_client.historyReadRaw(idF, startTimeF, endTimeF, 0, false, null, TimestampsToReturn.Source);
		
		if (null == valuesF) return;
		
		String variableNameS = new String("Maxle.Speed");	
		NodeId idS = new NodeId(2, variableNameS);

		DateTime startTimeS = DateTime.parseDateTime("2018-03-27 00:00:00");
		DateTime endTimeS = DateTime.parseDateTime("2018-04-29 24:00:00");		
		
		DataValue[] valuesS = null;
		valuesS = m_client.historyReadRaw(idS, startTimeS, endTimeS, 0, false, null, TimestampsToReturn.Source);
		
		if (null == valuesS) return;
		
		System.out.println("History value " + variableNameX + "," +variableNameY + "," + variableNameZ + ","+variableNameF+","+variableNameS+":");

		for (int i=0; i < valuesX.length; i++)
		{
			System.out.println("X="+valuesX[i].getValue().floatValue()+" "+"Y="+valuesY[i].getValue().floatValue()+" "+"Z="+valuesZ[i].getValue().floatValue()+" "+"F="+valuesF[i].getValue().floatValue()+" "+"S="+valuesS[i].getValue().floatValue());
		}
	}	
	
	public String getServerUrl()
	{
		return SERVER_URL;
	}
	
	private void Initialize() throws SecureIdentityException, IOException, SessionActivationException, URISyntaxException
	{
		m_client = new UaClient(SERVER_URL);		
		
		initClientIdentity();
		
		initSecuritySetting();		
	}
	
	private void initClientIdentity() throws SecureIdentityException, IOException
	{
		// 初始化应用程序描述
		ApplicationDescription appDescription = new ApplicationDescription();
		appDescription.setApplicationName(new LocalizedText(APP_NAME, Locale.ENGLISH));

		appDescription.setApplicationUri(APP_URI);
		appDescription.setProductUri(PRODUCT_URI);
		appDescription.setApplicationType(ApplicationType.Client);

		// 初始化证书管理文件	
		PkiFileBasedCertificateValidator validator = new PkiFileBasedCertificateValidator();
		m_client.setCertificateValidator(validator);

		File privateFile = new File(validator.getBaseDir(), "private");			
	
		// 将客户端描述信息与证书管理信息统一放入ApplicationIdentity中管理
		final ApplicationIdentity identity = ApplicationIdentity.loadOrCreateCertificate(
				appDescription, 
				"Sunwayland",
				"Sunwayland",
				privateFile,
				true);
		
		m_client.setApplicationIdentity(identity);
	}
	
	private void initSecuritySetting() throws SessionActivationException
	{
		// 设置连接模式
		m_client.setSecurityMode(SecurityMode.NONE);
	}
	

}
