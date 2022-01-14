package SharedPhotos.SharedPhotosMediaService;

import java.util.Base64;
import java.util.Map;
import java.util.HashMap;
import java.util.List;

import javax.xml.bind.DatatypeConverter;
import java.io.*;
import java.nio.file.*;

import MediaServiceUtils.*;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

//import com.google.api.core.ApiFuture;
//import com.google.auth.oauth2.GoogleCredentials;
//import com.google.cloud.firestore.DocumentReference;
//import com.google.cloud.firestore.DocumentSnapshot;
//import com.google.cloud.firestore.Firestore;
//import com.google.cloud.firestore.FirestoreOptions;
//import com.google.cloud.firestore.QueryDocumentSnapshot;
//import com.google.cloud.firestore.QuerySnapshot;
//import com.google.cloud.firestore.WriteResult;

@RestController
public class SharedPhotosMediaServiceController {
	
//	private Firestore db;
//
//	public SharedPhotosMediaServiceController() throws Exception {
//		// [START firestore_setup_client_create]
//		// Option 1: Initialize a Firestore client with a specific `projectId` and
//		//           authorization credential.
//		// [START fs_initialize_project_id]
//		// [START firestore_setup_client_create_with_project_id]
//		FirestoreOptions firestoreOptions =
//		    FirestoreOptions.getDefaultInstance().toBuilder()
//		        .setProjectId("sharedphotos")
//		        .setCredentials(GoogleCredentials.getApplicationDefault())
//		        .build();
//		Firestore db = firestoreOptions.getService();
//		// [END fs_initialize_project_id]
//		// [END firestore_setup_client_create_with_project_id]
//		// [END firestore_setup_client_create]
//		this.db = db;
//	}
//	
//	Firestore getDb() {
//		return db;
//	}
	
	@PostMapping("/photos/upload")
	public Photo uploadPhoto(@RequestParam Map<String, String> photoReqBody) throws Exception{
		
//		String[] strings = photoReqBody.get("base64Encoding").split(",");
//        String extension;
//        switch (strings[0]) {//check image's extension
//            case "data:image/jpeg;base64":
//                extension = "jpeg";
//                break;
//            case "data:image/png;base64":
//                extension = "png";
//                break;
//            case "data:image/jpg;base64"://should write cases for more images types
//                extension = "jpg";
//                break;
//            default:
//            	return null;
//        }
//		
//		DocumentReference docRef = db.collection("accountName").document(photoReqBody.get("accountName"))
//										.collection("albumName").document(photoReqBody.get("albumName"))
//										.collection("images").document(photoReqBody.get("photoName"));
//		
//		Map<String, Object> data = new HashMap<>();
//		data.put("imageEnc", photoReqBody.get("base64Encoding"));
//		data.put("imageExt", extension);
//		
//		ApiFuture<WriteResult> result = docRef.set(data);
//		
//		System.out.println(result.get());
//		
//		Photo ret = new Photo();
//        ret.setPhotoExtension(extension);
//        
//    	return ret;
		
		// -----------------------------------------------------
		
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
	public Photo getPhoto(@RequestParam Map<String, String> photoReqBody) throws Exception{
		
//		DocumentReference docRef = db.collection("accountName").document(photoReqBody.get("accountName"))
//											.collection("albumName").document(photoReqBody.get("albumName"))
//											.collection("images").document(photoReqBody.get("photoName"));
//		
//		ApiFuture<DocumentSnapshot> data = docRef.get();
//		DocumentSnapshot document = data.get();
//
//		Photo ret = new Photo();
//		
//		System.out.println(document.getString("imageEnc"));
//	    
//	    ret.setBase64Encoding(document.getString("imageEnc"));
//	    ret.setAccountName(photoReqBody.get("accountName"));
//	    ret.setAlbumName(photoReqBody.get("albumName"));
//	    ret.setPhotoName(photoReqBody.get("photoName"));
//	    
//	    if (document.getString("imageEnc") == null) {
//	    	return null;
//	    }
//		
//		return ret;
		
		
		// ---------------------------------------------------------------
		
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
	public String deletePhoto(@RequestParam Map<String, String> photoReqBody) throws Exception{
		
//		DocumentReference docRef = db.collection("accountName").document(photoReqBody.get("accountName"))
//										.collection("albumName").document(photoReqBody.get("albumName"))
//										.collection("images").document(photoReqBody.get("photoName"));
//		
//		docRef.delete();
//		
//		if(true) {
//			System.out.println("File deleted successfully"); 
//			return "{ \"resp\" : \"0\" }";
//		}
//		else {
//			System.out.println("Failed to delete the file");
//			return "{ \"resp\" : \"1\" }";
//		}
		
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
