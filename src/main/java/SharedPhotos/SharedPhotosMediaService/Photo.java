package SharedPhotos.SharedPhotosMediaService;

public class Photo {
	private String accountName;
	private String albumName;
	private String photoName;
	private String base64Encoding;
	private String photoExtension;
	
	public String getBase64Encoding() {
		return base64Encoding;
	}
	public void setBase64Encoding(String base64Encoding) {
		this.base64Encoding = base64Encoding;
	}
	public String getAccountName() {
		return accountName;
	}
	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}
	public String getAlbumName() {
		return albumName;
	}
	public void setAlbumName(String albumName) {
		this.albumName = albumName;
	}
	public String getPhotoName() {
		return photoName;
	}
	public void setPhotoName(String photoName) {
		this.photoName = photoName;
	}
	public String getPhotoExtension() {
		return photoExtension;
	}
	public void setPhotoExtension(String photoExtension) {
		this.photoExtension = photoExtension;
	}


}
