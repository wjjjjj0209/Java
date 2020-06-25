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

public class Coordinates extends UaObjectNode {
	
	static String TYPE_NAME                    = "Coordinates";	
	static String TYPE_CHINESE_NAME            = "зјБъ";	
	static String VALUE_VARIABLE_NAME1          = "X";
	static String VALUE_VARIABLE_CHINESE_NAME1  = "X";
	
	static String VALUE_VARIABLE_NAME2          = "Y";
	static String VALUE_VARIABLE_CHINESE_NAME2  = "Y";
	
	static String VALUE_VARIABLE_NAME3          = "Z";
	static String VALUE_VARIABLE_CHINESE_NAME3  = "Z";
	
	static UaObjectTypeNode s_typeNode;
	CacheVariable m_x;
	CacheVariable m_y;
	CacheVariable m_z;

	public Coordinates(NodeManager nodeManager,String name,String displayName) throws StatusException {
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
		createValueX(nodeManager, rootNode, objectName);
		createValueY(nodeManager, rootNode, objectName);
		createValueZ(nodeManager, rootNode, objectName);
	}
	
	static void createValueX(NodeManager nodeManager, UaNode rootNode, String name) throws StatusException
	{	
		Variant defaultValue = new Variant(new Float(0.0));		

		DateTime now = DateTime.currentTime();
		
		DataValue defaultDataValue = new DataValue(defaultValue, StatusCode.GOOD,now,now);			
		
		final NodeId valueVariableId = new NodeId(nodeManager.getNamespaceIndex(),name+"."+VALUE_VARIABLE_NAME1);
		CacheVariable valueVariable = new CacheVariable(nodeManager, 
				                                        valueVariableId, 
				                                        new QualifiedName(nodeManager.getNamespaceIndex(),VALUE_VARIABLE_NAME1),
				                                        new LocalizedText(VALUE_VARIABLE_CHINESE_NAME1,Locale.CHINESE));
		
		valueVariable.setDataTypeId(Identifiers.Float);
		valueVariable.setValue(defaultDataValue);
		valueVariable.setAccessLevel(NodeManager.READ_HISTORYREAD);
		nodeManager.addNodeAndReference(rootNode, valueVariable, Identifiers.HasComponent);
		
		nodeManager.registrateHistoryNode(valueVariable);
		
	}	
	
	static void createValueY(NodeManager nodeManager, UaNode rootNode, String name) throws StatusException
	{	
		Variant defaultValue = new Variant(new Float(0.0));		

		DateTime now = DateTime.currentTime();
		
		DataValue defaultDataValue = new DataValue(defaultValue, StatusCode.GOOD,now,now);			
		
		final NodeId valueVariableId = new NodeId(nodeManager.getNamespaceIndex(),name+"."+VALUE_VARIABLE_NAME2);
		CacheVariable valueVariable = new CacheVariable(nodeManager, 
				                                        valueVariableId, 
				                                        new QualifiedName(nodeManager.getNamespaceIndex(),VALUE_VARIABLE_NAME2),
				                                        new LocalizedText(VALUE_VARIABLE_CHINESE_NAME2,Locale.CHINESE));
		
		valueVariable.setDataTypeId(Identifiers.Float);
		valueVariable.setValue(defaultDataValue);	
		valueVariable.setAccessLevel(NodeManager.READ_HISTORYREAD);
		nodeManager.addNodeAndReference(rootNode, valueVariable, Identifiers.HasComponent);
		
		nodeManager.registrateHistoryNode(valueVariable);
		
	}	
	
	static void createValueZ(NodeManager nodeManager, UaNode rootNode, String name) throws StatusException
	{	
		Variant defaultValue = new Variant(new Float(0.0));		

		DateTime now = DateTime.currentTime();
		
		DataValue defaultDataValue = new DataValue(defaultValue, StatusCode.GOOD,now,now);			
		
		final NodeId valueVariableId = new NodeId(nodeManager.getNamespaceIndex(),name+"."+VALUE_VARIABLE_NAME3);
		CacheVariable valueVariable = new CacheVariable(nodeManager, 
				                                        valueVariableId, 
				                                        new QualifiedName(nodeManager.getNamespaceIndex(),VALUE_VARIABLE_NAME3),
				                                        new LocalizedText(VALUE_VARIABLE_CHINESE_NAME3,Locale.CHINESE));
		
		valueVariable.setDataTypeId(Identifiers.Float);
		valueVariable.setValue(defaultDataValue);	
		valueVariable.setAccessLevel(NodeManager.READ_HISTORYREAD);
		nodeManager.addNodeAndReference(rootNode, valueVariable, Identifiers.HasComponent);
		
		nodeManager.registrateHistoryNode(valueVariable);
		
	}	
	private void loadMember()
	{
		UaNode[] variables = this.getComponents();
	
		for (int i=0; i<variables.length; i++)
		{
			if (variables[i].getBrowseName().getName() == VALUE_VARIABLE_NAME1)
			{
				m_x = (CacheVariable)variables[i];
			}
			
			if (variables[i].getBrowseName().getName() == VALUE_VARIABLE_NAME2)
			{
				m_y = (CacheVariable)variables[i];
			}
			
			if (variables[i].getBrowseName().getName() == VALUE_VARIABLE_NAME3)
			{
				m_z = (CacheVariable)variables[i];
			}
		}
	}
	
	public void datachange(float a,float b,float c) {
		Variant valuex = new Variant(a);
		Variant valuey = new Variant(b);
		Variant valuez = new Variant(c);
		StatusCode status = StatusCode.GOOD;
		DateTime now = DateTime.currentTime();
		DataValue datavaluex = new DataValue(valuex, status, now, now);
		DataValue datavaluey = new DataValue(valuey, status, now, now);
		DataValue datavaluez = new DataValue(valuez, status, now, now);
		try {
			m_x.setValue(datavaluex);
		} catch (StatusException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			m_y.setValue(datavaluey);
		} catch (StatusException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			m_z.setValue(datavaluez);
		} catch (StatusException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
	
}


