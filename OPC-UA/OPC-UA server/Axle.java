import java.util.Locale;

import org.opcfoundation.ua.builtintypes.DataValue;
import org.opcfoundation.ua.builtintypes.DateTime;
import org.opcfoundation.ua.builtintypes.LocalizedText;
import org.opcfoundation.ua.builtintypes.NodeId;
import org.opcfoundation.ua.builtintypes.QualifiedName;
import org.opcfoundation.ua.builtintypes.StatusCode;
import org.opcfoundation.ua.builtintypes.Variant;
import org.opcfoundation.ua.core.Identifiers;

import com.prosysopc.ua.StatusException;
import com.prosysopc.ua.nodes.UaNode;
import com.prosysopc.ua.nodes.UaType;
import com.prosysopc.ua.server.nodes.CacheVariable;
import com.prosysopc.ua.server.nodes.UaObjectNode;
import com.prosysopc.ua.server.nodes.UaObjectTypeNode;

public class Axle extends UaObjectNode {
	
	static String TYPE_NAME                    = "Axle";	
	static String TYPE_CHINESE_NAME            = "Öá";	
	static String VALUE_VARIABLE_NAME          = "Speed";
	static String VALUE_VARIABLE_CHINESE_NAME  = "×ªËÙ";
	
	static UaObjectTypeNode s_typeNode;
	
	CacheVariable m_s;

	public Axle(NodeManager nodeManager,String name,String displayName) throws StatusException {
		super(nodeManager, 
			  new NodeId(nodeManager.getNamespaceIndex(), name), 
			  new QualifiedName(nodeManager.getNamespaceIndex(), name),
			  new LocalizedText(displayName,Locale.CHINESE));

		createObject(nodeManager, name);
	}
	
	static void createType(NodeManager nodeManager) throws StatusException
	{
		final NodeId analogInputTypeNodeId = new NodeId(nodeManager.getNamespaceIndex(), TYPE_NAME);
		
		s_typeNode = new UaObjectTypeNode(nodeManager, 
				                          analogInputTypeNodeId, 
				                          new QualifiedName(nodeManager.getNamespaceIndex(), TYPE_NAME), 
				                          new LocalizedText(TYPE_CHINESE_NAME,Locale.CHINESE));

		UaType baseObjectType = nodeManager.getType(Identifiers.BaseObjectType);
		
		nodeManager.addNodeAndReference(baseObjectType, s_typeNode, Identifiers.HasSubtype);
		
		createMember(nodeManager, s_typeNode, TYPE_NAME);		
	}
	
	void createObject(NodeManager nodeManager, String name) throws StatusException
	{		
		this.setTypeDefinition(s_typeNode);				
		createMember(nodeManager, this, name);
		loadMember();
	}
	
	static void createMember(NodeManager nodeManager, UaNode rootNode, String objectName) throws StatusException
	{
		createValue(nodeManager, rootNode, objectName);
	}
	
	static void createValue(NodeManager nodeManager, UaNode rootNode, String name) throws StatusException
	{	
		Variant defaultValue = new Variant(new Float(0.0));		

		DateTime now = DateTime.currentTime();
		
		DataValue defaultDataValue = new DataValue(defaultValue, StatusCode.GOOD,now,now);			
		
		final NodeId valueVariableId = new NodeId(nodeManager.getNamespaceIndex(),name+"."+VALUE_VARIABLE_NAME);
		CacheVariable valueVariable = new CacheVariable(nodeManager, 
				                                        valueVariableId, 
				                                        new QualifiedName(nodeManager.getNamespaceIndex(),VALUE_VARIABLE_NAME),
				                                        new LocalizedText(VALUE_VARIABLE_CHINESE_NAME,Locale.CHINESE));
		
		valueVariable.setDataTypeId(Identifiers.Float);
		valueVariable.setValue(defaultDataValue);	
		valueVariable.setAccessLevel(NodeManager.READ_HISTORYREAD);
		
		nodeManager.registrateHistoryNode(valueVariable);
		
		nodeManager.addNodeAndReference(rootNode, valueVariable, Identifiers.HasComponent);
	}	
	
	private void loadMember()
	{
		UaNode[] variables = this.getComponents();
	
		for (int i=0; i<variables.length; i++)
		{
			if (variables[i].getBrowseName().getName() == VALUE_VARIABLE_NAME)
			{
				m_s = (CacheVariable)variables[i];
			}
		}
	}
	
	public void datachange(float a) {
		Variant values = new Variant(a);
		StatusCode status = StatusCode.GOOD;
		DateTime now = DateTime.currentTime();
		DataValue datavalues = new DataValue(values, status, now, now);
		try {
			m_s.setValue(datavalues);
		} catch (StatusException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
