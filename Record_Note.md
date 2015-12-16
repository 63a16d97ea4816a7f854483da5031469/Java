# Java Note

http://www.yiibai.com/java/util/java_util_arraydeque.html


##transient
https://en.wikibooks.org/wiki/Java_Programming/Keywords/transient

http://stackoverflow.com/questions/910374/why-does-java-have-transient-variables

transient is a Java keyword which marks a member variable not to be serialized when it is persisted to streams of bytes. When an object is transferred through the network, the object needs to be 'serialized'. Serialization converts the object state to serial bytes. Those bytes are sent over the network and the object is recreated from those bytes. Member variables marked by the java transient keyword are not transferred; they are lost intentionally.

Syntax:

	private transient <member-variable>;
	or 
	transient private <member-variable>;


For example: 

	public class Foo implements Serializable
	 {
	   private String saveMe;
	   private transient String dontSaveMe;
	   private transient String password;
	   //...
	 }