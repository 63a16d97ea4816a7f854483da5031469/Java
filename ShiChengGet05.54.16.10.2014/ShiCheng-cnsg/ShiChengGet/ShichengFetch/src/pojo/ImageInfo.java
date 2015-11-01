package pojo;
/**
 * Code writer:Caolei
 */
import java.io.InputStream;

public class ImageInfo {
	private String id;
	private String detailPageLink;
	private String piclink;
	private String localpath;
	private String type;
	private InputStream conetent;

	public String getPiclink() {
		return piclink;
	}

	public void setPiclink(String piclink) {
		this.piclink = piclink;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public InputStream getConetent() {
		return conetent;
	}

	public void setConetent(InputStream conetent) {
		this.conetent = conetent;
	}

	public String getDetailPageLink() {
		return detailPageLink;
	}

	public void setDetailPageLink(String detailPageLink) {
		this.detailPageLink = detailPageLink;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getLocalpath() {
		return localpath;
	}

	public void setLocalpath(String localpath) {
		this.localpath = localpath;
	}

	public String toString() {
		return "id:" + this.id + ",link:" + this.piclink + ",detailPageLink:"
				+ this.detailPageLink + ",localpath:" + this.localpath+",type:"+this.type;
	}
}
