package SharedPhotos.SharedPhotosMediaService;

import java.util.Base64;
import java.util.Map;

import javax.xml.bind.DatatypeConverter;
import java.io.*;
import java.nio.file.*;

import MediaServiceUtils.*;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
public class SharedPhotosMediaServiceController {
	
	@PostMapping("/photos/upload")
	public Photo uploadPhoto(@RequestParam Map<String, String> photoReqBody) {
		
		ConfigReader config = new ConfigReader();
		
		String[] strings = photoReqBody.get("base64Encoding").split(",");
        String extension;
        switch (strings[0]) {//check image's extension
            case "data:image/jpeg;base64":
                extension = "jpeg";
                break;
            case "data:image/png;base64":
                extension = "png";
                break;
            case "data:image/jpg;base64"://should write cases for more images types
                extension = "jpg";
                break;
            default:
            	return null;
        }
        //photoReqBody.setPhotoExtension(extension);
        //convert base64 string to binary data
        byte[] data = DatatypeConverter.parseBase64Binary(strings[1]);
        String path = config.getProperty("mediaServiceRootDir") + photoReqBody.get("accountName") + "/" +
        		photoReqBody.get("albumName") + "/" + photoReqBody.get("photoName") + "." + extension;
        File directory = new File(config.getProperty("mediaServiceRootDir") + photoReqBody.get("accountName") + "/" +
        		photoReqBody.get("albumName") + "/");
        if (! directory.exists()){
            directory.mkdirs();
            // If you require it to make the entire directory path including parents,
            // use directory.mkdirs(); here instead.
        }
        
        File file = new File(path);
        try {
			file.createNewFile();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} // if file already exists will do nothing 
        try (OutputStream outputStream = new BufferedOutputStream(new FileOutputStream(file, false))) {
            outputStream.write(data);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        
        Photo ret = new Photo();
        ret.setPhotoExtension(extension);
        
    	return ret;
	}
	
	
	@PostMapping("/photos/get")
	public Photo getPhoto(@RequestParam Map<String, String> photoReqBody) {
		ConfigReader config = new ConfigReader();
		
		String photoPath = config.getProperty("mediaServiceRootDir") + photoReqBody.get("accountName") + "/" +
        		photoReqBody.get("albumName") + "/" + photoReqBody.get("photoName") + "." + photoReqBody.get("photoExtension");
		
		Path path = Paths.get(photoPath);
	    byte[] data;
		try {
			System.out.println(photoPath);
			data = Files.readAllBytes(path);
		    String encodedString = Base64.getEncoder().encodeToString(data);
		    
		    Photo ret = new Photo();
		    ret.setBase64Encoding(encodedString);
		    ret.setAccountName(photoReqBody.get("accountName"));
		    ret.setAlbumName(photoReqBody.get("albumName"));
		    ret.setPhotoName(photoReqBody.get("photoName"));
		    
		    return ret;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@PostMapping("/photos/delete")
	public String deletePhoto(@RequestParam Map<String, String> photoReqBody) {
		ConfigReader config = new ConfigReader();
		
		String path = config.getProperty("mediaServiceRootDir") + photoReqBody.get("accountName") + "/" +
        		photoReqBody.get("albumName") + "/" + photoReqBody.get("photoName") + "." + photoReqBody.get("photoExtension");
		File file = new File(path); 
        
        if(file.delete()) 
        { 
            //System.out.println("File deleted successfully"); 
            return "{ \"resp\" : \"0\" }";
        } 
        else
        { 
            //System.out.println("Failed to delete the file");
            return "{ \"resp\" : \"1\" }";
        } 
	}
}
