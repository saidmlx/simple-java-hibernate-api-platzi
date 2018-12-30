package online.saidmlx.app.controller;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponentsBuilder;

import online.saidmlx.app.model.Course;
import online.saidmlx.app.model.SocialMedia;
import online.saidmlx.app.model.Teacher;
import online.saidmlx.app.model.TeacherSocialMedia;
import online.saidmlx.app.service.SocialMediaService;
import online.saidmlx.app.service.TeacherService;
import online.saidmlx.util.CustomErrorType;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
@RequestMapping("/v1")
public class TeacherController {

	@Autowired
	TeacherService teacherService;
	
	@Autowired
	SocialMediaService 	socialMediaService;
	
	@RequestMapping(value="/teachers", method=RequestMethod.GET, headers="Accept= application/json")
	public ResponseEntity<List<Teacher>> teachers(){
		List<Teacher> teachers=teacherService.findAllTeacher();
		if(teachers==null) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<Teacher>>(teachers,HttpStatus.OK);
	}
	
	@RequestMapping(value="/teacher/{id}", method=RequestMethod.GET, headers="Accept= application/json")
	public ResponseEntity<Teacher> teacher(@PathVariable("id") Long idTeacher){

		if(idTeacher == null || idTeacher <=0) {

			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}else{

			Teacher teacher=teacherService.findById(idTeacher);

			if(teacher==null) {

				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}else{

				return new ResponseEntity<Teacher>(teacher,HttpStatus.OK);
			}
		}
	}
	
	@RequestMapping(value="/teacher", method=RequestMethod.POST, headers="Accept= application/json")
	public ResponseEntity<String> addTeacher(@RequestBody Teacher teacher, UriComponentsBuilder uriComponent ){
		
		if(teacher.getName().equals(null) || teacher.getName().isEmpty()) {
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}
		if(teacherService.findByName(teacher.getName()) != null) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		teacherService.saveTeacher(teacher);
		Teacher teacherSaved = teacherService.findByName(teacher.getName());
		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(uriComponent.path("v1/teacher/{id}")
				.buildAndExpand(teacherSaved.getIdTeacher())
				.toUri());
		return new ResponseEntity<String>(headers, HttpStatus.CREATED );
		
	}
	
	//-- UPDATE course
	@RequestMapping(value="/teacher/{id}", method=RequestMethod.PATCH, headers="Accept= application/json")
	public ResponseEntity<Teacher> updateTeacher(@RequestBody Teacher teacher, @PathVariable("id") Long idTeacher ) {
		
		if(teacher == null) {
			return new ResponseEntity(HttpStatus.BAD_REQUEST);
		}else{
			Teacher teacherSaved =teacherService.findById(idTeacher);
			if(teacherSaved== null) {
				return new ResponseEntity(  new CustomErrorType("The Teacher with id "+idTeacher+" not exist" )  , HttpStatus.NO_CONTENT);
			}else {
				teacherSaved.setName(teacher.getName());
				teacherSaved.setAvatar(teacher.getAvatar());
				teacherService.updateTeacher(teacherSaved);
				return new ResponseEntity<Teacher>(teacherSaved, HttpStatus.OK);
			}
		}
		
	}
	
	//-- DELETE course 
	@RequestMapping(value="/teacher/{id}", method=RequestMethod.DELETE, headers="Accept= application/json")	
	public ResponseEntity<Teacher> deleteTeacher( @PathVariable("id") Long idTeacher ) {
		
		if(idTeacher == null) {
			return new ResponseEntity(HttpStatus.BAD_REQUEST);
		}else if(teacherService.findById(idTeacher) == null){
			
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}else {
				
			teacherService.deleteTeacherById(idTeacher);
			return new ResponseEntity(HttpStatus.OK);
		}
	}
	
	//-- upload imagen
	@RequestMapping(value="/teacher/image", method=RequestMethod.POST, headers=("content-type=multipart/form-data"))
	public ResponseEntity<byte[]> uploadTeacher(@RequestParam( "id_teacher") Long idTeacher, 
			@RequestParam("file") MultipartFile multipartFile ,
			UriComponentsBuilder uriComponent ){
		
		if(idTeacher == (null)  ) {
			return new ResponseEntity(HttpStatus.BAD_REQUEST);
		}
		
		if(multipartFile == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
		Teacher teacher = teacherService.findById(idTeacher);
		if( teacher == null) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		
		if(!teacher.getAvatar().isEmpty() || teacher.getAvatar() != null) {
			String fileName = teacher.getAvatar();
			Path path=Paths.get(fileName);
			File f = path.toFile();
			if(f.exists()) {
				f.delete();
			}
		}
		
		try {
			Date date = new Date();
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
			String dateName = dateFormat.format(date);
			
			String fileName = String.valueOf(idTeacher)+"-pictureTeacher-"+dateName+"."+multipartFile.getContentType().split("/")[1];

			teacher.setAvatar("images/teachers/"+fileName);
			byte[] bytes = multipartFile.getBytes(); 
			Path path = Paths.get("images/teachers/"+fileName);
			Files.write(path, bytes);
			
			teacherService.updateTeacher(teacher);
			
			return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(bytes);
			
		}catch(Exception ex) {
			return new ResponseEntity(HttpStatus.CONFLICT);
		}
		
	}
	
	
	
	@RequestMapping(value="/teacher/{id}/image", method= RequestMethod.GET, headers="Accept=application/json")
	public ResponseEntity<byte[]> getImage(@PathVariable("id") Long idTeacher){
		if(idTeacher==null) {
			return new ResponseEntity(HttpStatus.BAD_REQUEST);
		}
		Teacher teacher = teacherService.findById(idTeacher);

		if(teacher == null) {
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}
		try{
			String fileName = teacher.getAvatar();

			Path path=Paths.get(fileName);
			File f = path.toFile();
			if(!f.exists()) {
				return new ResponseEntity(HttpStatus.NO_CONTENT);
			}
			byte[] bytes =  Files.readAllBytes(path);

			return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(bytes);
			
		}catch(Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.CONFLICT);
		}
	}
	
	@RequestMapping(value="/teacher/{id}/image", method= RequestMethod.DELETE, headers="Accept= application/json")
	public ResponseEntity<?> deleteImage(@PathVariable("id") Long idTeacher){

		if(idTeacher==null) {

			return new ResponseEntity(HttpStatus.BAD_REQUEST);
		}
		Teacher teacher = teacherService.findById(idTeacher);

		if(teacher == null) {

			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}

		if(teacher.getAvatar().isEmpty() || teacher.getAvatar() == null) {

			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}

		String fileName = teacher.getAvatar();
		Path path=Paths.get(fileName);
		File f = path.toFile();

		if(f.exists()) {
			f.delete();
		}

		teacher.setAvatar("");

		teacherService.updateTeacher(teacher);
		
		return new ResponseEntity(HttpStatus.OK);
		
		
		
	}
	
	@RequestMapping(value="teacher/socialMedias", method=RequestMethod.PATCH)
	public ResponseEntity<Teacher> asignTeacherSocialMedia(@RequestBody Teacher teacher, UriComponentsBuilder uriComponentsBuilder){
		
		

		if(teacher.getIdTeacher() == null) {
			return new ResponseEntity(new CustomErrorType("idTeacher should be a valid id"), HttpStatus.BAD_REQUEST);
		}

		Teacher teacherSaved = teacherService.findById(teacher.getIdTeacher());

		if(teacherSaved == null) {			
			return new ResponseEntity(new CustomErrorType("The Teacher with idTeacher="+teacher.getIdTeacher()
			+" is not present"),HttpStatus.BAD_REQUEST);
		}

		if(teacher.getTeacherSocialMedia().size() == 0 ) {

			return new ResponseEntity(new CustomErrorType("There isn't present SocialMedias"), HttpStatus.BAD_REQUEST);
		}else{

			Iterator<TeacherSocialMedia> i = teacher.getTeacherSocialMedia().iterator();
			while(i.hasNext()) {

				TeacherSocialMedia teacherSocialMedia = i.next();
				if(teacherSocialMedia.getSocialMedia().getIdSocialmedia() == null || teacherSocialMedia.getNickname()== null ) {

					return new ResponseEntity(new CustomErrorType("There isn't present idSocialMedia"),HttpStatus.NO_CONTENT);
				}else {

					TeacherSocialMedia teacherSocialMediaSaved= socialMediaService.findSocialMediaByIdAndName(teacherSocialMedia.getSocialMedia().getIdSocialmedia(), teacherSocialMedia.getNickname());

					if(teacherSocialMediaSaved != null) {

						return new ResponseEntity(new CustomErrorType("There are one item as TeacherSocialMedia Id "+teacherSocialMedia.getSocialMedia().getIdSocialmedia()),HttpStatus.BAD_REQUEST);
					}

					SocialMedia socialMediaSaved = socialMediaService.findById(teacherSocialMedia.getSocialMedia().getIdSocialmedia());

					if(socialMediaSaved == null) {

						return new ResponseEntity(new CustomErrorType("There no exist SocialMedia "+teacherSocialMedia.getSocialMedia().getIdSocialmedia()),HttpStatus.NO_CONTENT);
					}

					teacherSocialMedia.setSocialMedia(socialMediaSaved);

					teacherSocialMedia.setTeacher(teacherSaved);

					if(teacherSocialMediaSaved == null) {

						teacherSaved.getTeacherSocialMedia().add(teacherSocialMedia);

					}else {

						LinkedList<TeacherSocialMedia> teacherSocialMedias = new LinkedList<>();
						teacherSocialMedias.addAll(teacherSaved.getTeacherSocialMedia());

						for(int j=0 ; j<teacherSocialMedias.size();j++) {
							TeacherSocialMedia tsm = teacherSocialMedias.get(j);
							if(teacherSocialMedia.getTeacher().getIdTeacher()==tsm.getTeacher().getIdTeacher() 
									&& teacherSocialMedia.getSocialMedia().getIdSocialmedia()==tsm.getSocialMedia().getIdSocialmedia()){
								
								tsm.setNickname(teacherSocialMedia.getNickname());
								teacherSocialMedias.set(j, tsm);
							}else {
								teacherSocialMedias.set(j, tsm);
							}
						}//--end for

						teacherSaved.getTeacherSocialMedia().clear();

						teacherSaved.getTeacherSocialMedia().addAll(teacherSocialMedias);
						
					}
					
				
				}
				
			}//--end while
		}
		teacherService.updateTeacher(teacherSaved);
		return new ResponseEntity<Teacher>(teacherSaved,HttpStatus.OK);
		
	}
	
}
