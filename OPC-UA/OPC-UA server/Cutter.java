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
import com.prosysopc.ua.server.NodeManagerUaNode;
import com.prosysopc.ua.server.nodes.CacheVariable;
import com.prosysopc.ua.server.nodes.UaObjectNode;
import com.prosysopc.ua.server.nodes.UaObjectTypeNode;

public class Cutter extends UaObjectNode {
	
	static String TYPE_NAME                    = "Cutter";	
	static String TYPE_CHINESE_NAME            = "µ¶¾ß";	
	static String VALUE_VARIABLE_NAME1          = "Number";
	static String VALUE_VARIABLE_CHINESE_NAME1  = "±àºÅ";
	
	static String VALUE_VARIABLE_NAME2          = "Productype";
	static String VALUE_VARIABLE_CHINESE_NAME2  = "ÀàÐÍ";
	
	static String VALUE_VARIABLE_NAME3          = "State";
	static String VALUE_VARIABLE_CHINESE_NAME3  = "×´Ì¬";
	
	static UaObjectTypeNode s_typeNode;

	public Cutter(NodeManagerUaNode nodeManager,String name,String displayName) throws StatusException {
		super(nodeManager, 
			  new NodeId(nodeManager.getNamespaceIndex(), name), 
			  new QualifiedName(nodeManager.getNamespaceIndex(), name),
			  new LocalizedText(displayName,Locale.CHINESE));

		createObject(nodeManager, name);
	}
	
	static void createType(NodeManagerUaNode nodeManager) throws StatusException
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
	
	void createObject(NodeManagerUaNode nodeManager, String name) throws StatusException
	{		
		this.setTypeDefinition(s_typeNode);				
		createMember(nodeManager, this, name);
	}
	
	static void createMember(NodeManagerUaNode nodeManager, UaNode rootNode, String objectName) throws StatusException
	{
		createValueX(nodeManager, rootNode, objectName);
		createValueY(nodeManager, rootNode, objectName);
		createValueZ(nodeManager, rootNode, objectName);
	}
	
	static void createValueX(NodeManagerUaNode nodeManager, UaNode rootNode, String name) throws StatusException
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
		valueVariable.setAccessLevel(NodeManager.READ_ONLY);
		nodeManager.addNodeAndReference(rootNode, valueVariable, Identifiers.HasComponent);
	}	
	
	static void createValueY(NodeManagerUaNode nodeManager, UaNode rootNode, String name) throws StatusException
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
		valueVariable.setAccessLevel(NodeManager.READ_ONLY);
		nodeManager.addNodeAndReference(rootNode, valueVariable, Identifiers.HasComponent);
	}	
	
	static void createValueZ(NodeManagerUaNode nodeManager, UaNode rootNode, String name) throws StatusException
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
		valueVariable.setAccessLevel(NodeManager.READ_ONLY);
		nodeManager.addNodeAndReference(rootNode, valueVariable, Identifiers.HasComponent);
	}	
}


