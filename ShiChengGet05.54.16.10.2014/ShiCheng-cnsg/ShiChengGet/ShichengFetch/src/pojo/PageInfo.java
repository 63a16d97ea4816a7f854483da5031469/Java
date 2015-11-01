package pojo;
/**
 * Code writer:Caolei
 */
public class PageInfo {
	private int id;
	private String link;
	private String sourcecode;
	private String dateTime;
	private String sourceWeb;
	private long lengthofcode;
	private String dateofcontent;
	private int listingTableReferenceID;

 
	public int getListingTableReferenceID() {
		return listingTableReferenceID;
	}

	public void setListingTableReferenceID(int listingTableReferenceID) {
		this.listingTableReferenceID = listingTableReferenceID;
	}

	public String getDateofcontent() {
		return dateofcontent;
	}

	public void setDateofcontent(String dateofcontent) {
		this.dateofcontent = dateofcontent;
	}

	public String getDateTime() {
		return dateTime;
	}

	public void setDateTime(String dateTime) {
		this.dateTime = dateTime;
	}

	public long getLengthofcode() {
		return lengthofcode;
	}

	public void setLengthofcode(long lengthofcode) {
		this.lengthofcode = lengthofcode;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public String getSourcecode() {
		return sourcecode;
	}

	public void setSourcecode(String sourcecode) {
		this.sourcecode = sourcecode;
	}
 
	public String getSourceWeb() {
		return sourceWeb;
	}

	public void setSourceWeb(String sourceWeb) {
		this.sourceWeb = sourceWeb;
	}
	
	public String toString(){
		return "id: "+this.id+",link: "+this.link+",datetime: "+this.dateTime+",sourceWeb: "+this.sourceWeb+",sourcecode: "+this.getSourcecode()+",lengthofcontent:"+this.lengthofcode+",dateofcontent:"+this.dateofcontent;
	}
	
	
}
