import java.util.EnumSet;
import java.util.Locale;

import org.opcfoundation.ua.builtintypes.DataValue;
import org.opcfoundation.ua.builtintypes.DateTime;
import org.opcfoundation.ua.builtintypes.LocalizedText;
import org.opcfoundation.ua.builtintypes.NodeId;
import org.opcfoundation.ua.builtintypes.QualifiedName;
import org.opcfoundation.ua.builtintypes.StatusCode;
import org.opcfoundation.ua.builtintypes.Variant;
import org.opcfoundation.ua.core.AccessLevel;
import org.opcfoundation.ua.core.Identifiers;

import com.prosysopc.ua.StatusException;
import com.prosysopc.ua.nodes.UaObject;
import com.prosysopc.ua.server.NodeManagerUaNode;
import com.prosysopc.ua.server.UaServer;
import com.prosysopc.ua.server.nodes.CacheVariable;
import com.prosysopc.ua.server.nodes.opcua.FolderType;

public class NodeManager extends NodeManagerUaNode {
	
	public static EnumSet<AccessLevel> READ_ONLY = 
            EnumSet.of(AccessLevel.CurrentRead);

public static EnumSet<AccessLevel> READ_WRITE = 
            EnumSet.of(AccessLevel.CurrentRead, 
                       AccessLevel.CurrentWrite);

static EnumSet<AccessLevel> READ_HISTORYREAD = 
            EnumSet.of(AccessLevel.CurrentRead, 
                       AccessLevel.HistoryRead);	
	
	private static String NODE_MANAGER_URI = "urn:localhost:UA:ADDRESSSPACE";
	
	Coordinates mc;
	Axle ma;
	CacheVariable mf;
	
	private HistoryManager m_historyManager;

	public NodeManager(UaServer server) {
		super(server,NODE_MANAGER_URI);
		
		m_historyManager = new HistoryManager();
		
		this.getHistoryManager().setListener(m_historyManager);
	}
	
	public void createAddressSpace() throws StatusException
	{
		createType();
		
		FolderType deviceFolder = createDeviceFolder();

		createObject(deviceFolder);
	}
	
	private void createType() throws StatusException
	{
		Axle.createType(this);	
		Coordinates.createType(this);
	}

	private FolderType createDeviceFolder()
	{
		NodeId deviceFolderId = new NodeId(this.getNamespaceIndex(), "Machine Tool");
		FolderType deviceFolder = new FolderType(this, deviceFolderId, 
				                                 new QualifiedName(this.getNamespaceIndex(),"Machine Tool"), 
				                                 new LocalizedText("机床",Locale.CHINESE));
		
		UaObject objectFolder = this.getServer().getNodeManagerRoot().getObjectsFolder();
		objectFolder.addReference(deviceFolder, Identifiers.Organizes, false);
		return deviceFolder;
	}
	
	private void createObject(FolderType folder) throws StatusException
	{
		Axle M = new Axle(this, "Maxle", "主轴");
		folder.addReference(M, Identifiers.Organizes, false);
		ma=M;
		
		Coordinates C = new Coordinates(this, "Mcoordinates", "主轴坐标");
		this.addNodeAndReference(M, C, Identifiers.HasComponent);
		mc=C;
		
		createValue(folder);		
	}	
	
	private void createValue(FolderType folder) {
		Variant defaultValue = new Variant(new Float(0.0));		

		DateTime now = DateTime.currentTime();
		
		DataValue defaultDataValue = new DataValue(defaultValue, StatusCode.GOOD,now,now);			
		
		final NodeId valueVariableId = new NodeId(this.getNamespaceIndex(),"Machine Tool"+"."+"FedRate");
		CacheVariable valueVariable = new CacheVariable(this, 
				                                        valueVariableId, 
				                                        new QualifiedName(this.getNamespaceIndex(),"FedRate"),
				                                        new LocalizedText("进给速率",Locale.CHINESE));
		
		valueVariable.setDataTypeId(Identifiers.Float);
		try {
			valueVariable.setValue(defaultDataValue);
		} catch (StatusException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		valueVariable.setAccessLevel(NodeManager.READ_HISTORYREAD);
		
		folder.addReference(valueVariable, Identifiers.Organizes, false);
		mf = valueVariable;
		
		this.registrateHistoryNode(valueVariable);
		
	}
	
	public void datechange() {
		Read r = new Read();
		r.read();
		for(int i=0;i<r.n;i++) {
			mc.datachange(r.x[i],r.y[i],r.z[i]);
			ma.datachange(r.s[i]+(float)(-0.5+Math.random()));
			setvalue(r.f[i]+(float)(-2.5+Math.random()*5));
			Thread.currentThread();
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	private void setvalue(float a) {
		Variant valuef = new Variant(a);
		StatusCode status = StatusCode.GOOD;
		DateTime now = DateTime.currentTime();
		DataValue datavaluef = new DataValue(valuef, status, now, now);
		try {
			mf.setValue(datavaluef);
		} catch (StatusException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void registrateHistoryNode(CacheVariable variable)
	{
		variable.addDataChangeListener(m_historyManager);
	}
	
}
