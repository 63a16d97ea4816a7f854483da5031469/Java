package pojo;
/**
 * Code writer:Caolei
 */
public class DetailInfo {
	private String id;
	private String title;
	private String date;
	private String phone;
	private String price;
	private String link;
	private String detail;
	private String sourceWeb;
	private String location;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link;
	}
	public String getDetail() {
		return detail;
	}
	public void setDetail(String detail) {
		this.detail = detail;
	}
	public String getSourceWeb() {
		return sourceWeb;
	}
	public void setSourceWeb(String sourceWeb) {
		this.sourceWeb = sourceWeb;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	
	public String toString(){
		return "id:"+this.id+",title: "+this.title+",location: "+this.location+",price: "+this.price+",sourceWeb: "+this.sourceWeb+",detail: "+this.detail+",date: "+this.date+",link: "+this.link+",phone: "+this.phone;
	}
	
	
	
	
}
