package pojo;
/**
 * Code writer:Caolei
 */
public class PreUnit {
	private String id;
	private String title;
	private String date;
	private String phone;
	private String price;
	private String link;
	private String sourceWeb;
	private String sourcePageLink;
	private String pageReferenceId;

 
	public String getPageReferenceId() {
		return pageReferenceId;
	}

	public void setPageReferenceId(String pageReferenceId) {
		this.pageReferenceId = pageReferenceId;
	}

	public String getSourcePageLink() {
		return sourcePageLink;
	}

	public void setSourcePageLink(String sourcePageLink) {
		this.sourcePageLink = sourcePageLink;
	}

	public String getSourceWeb() {
		return sourceWeb;
	}

	public void setSourceWeb(String sourceWeb) {
		this.sourceWeb = sourceWeb;
	}

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
	public String toString(){
		
		return "id:"+this.getId()+", link:"+this.getLink()+", title:"+this.getTitle()+", price:"+this.getPrice()+", date:"+this.getDate()+", phone:"+this.getPhone()+",sourceWeb:"+this.getSourceWeb()+",sourcePageLink:"+this.getSourcePageLink()+",pageReferenceId:"+this.pageReferenceId;
		
	}
}
