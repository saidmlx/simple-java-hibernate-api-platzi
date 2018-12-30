package online.saidmlx.app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.util.UriComponentsBuilder;

import online.saidmlx.app.model.Course;
import online.saidmlx.app.model.SocialMedia;
import online.saidmlx.app.model.Teacher;
import online.saidmlx.app.service.CourseService;
import online.saidmlx.app.service.TeacherService;
import online.saidmlx.util.CustomErrorType;

@Controller
@RequestMapping("/v1")
public class CourseController {
	
	@Autowired
	CourseService courseService; 

	@Autowired
	TeacherService teacherService;
	
	//- courses
	@RequestMapping(value="/courses", method= RequestMethod.GET, headers="Accept= application/json")
	public ResponseEntity<List<Course>> getCourses( ) {
		{
			List<Course> course = courseService.findAllCourse();
			if(course==null ) {
				return new ResponseEntity(HttpStatus.BAD_REQUEST);
			}else {
				return new ResponseEntity<List<Course>>(course,HttpStatus.OK);
			}
		}
	}
	
	//-- GET course
	@RequestMapping(value="/course/{id}", method= RequestMethod.GET, headers="Accept= application/json")
	public ResponseEntity<Course> getCourse(@PathVariable("id") Long idCourse ) {
		
		if(idCourse == null || idCourse<=0) {
			return new ResponseEntity(HttpStatus.BAD_REQUEST);
		}else {
			Course course = courseService.findById(idCourse);
			if(course==null ) {
				return new ResponseEntity(HttpStatus.BAD_REQUEST);
			}else {
				return new ResponseEntity<Course>(course,HttpStatus.OK);
			}
		}
	}
	
	//-- POST course
	@RequestMapping(value="/course", method=RequestMethod.POST, headers="Accept= application/json")
	public ResponseEntity<String> saveCourse(@RequestBody Course course, UriComponentsBuilder uri) {

		if(course.getName() == null || course.getName().isEmpty()) {
		
			return new ResponseEntity(HttpStatus.BAD_REQUEST);
		}else if(courseService.findByName(course.getName() ) != null){
		
			return new ResponseEntity(HttpStatus.BAD_REQUEST);
		}else {
		
			courseService.saveCourse(course);
			Course savedCourse = courseService.findByName(course.getName());
			HttpHeaders httpHeaders = new HttpHeaders();
			httpHeaders.setLocation(uri.path("/v1/course/{id}")
					.buildAndExpand(savedCourse.getIdCourse())
					.toUri()
					);
			return new ResponseEntity<String>(httpHeaders,HttpStatus.OK);
		}
	}
	@RequestMapping(value="/course/teacher", method=RequestMethod.PATCH, headers="Accept= application/json")
	public ResponseEntity<?> addTeacher(@RequestBody Course course) {

		if(course.getIdCourse() == null ) {
		
			return new ResponseEntity<>(new CustomErrorType("The idCourse is needed"), HttpStatus.BAD_REQUEST);
		}
		
		if(course.getTeacher().getIdTeacher() == null) {
			return new ResponseEntity<>(new CustomErrorType("There isn't teacher to add"), HttpStatus.BAD_REQUEST);
		}
		
		Course courseSaved = courseService.findById(course.getIdCourse());
		
		if( courseSaved == null){
			return new ResponseEntity<>( new CustomErrorType("Te Course not exist") ,HttpStatus.BAD_REQUEST);
		}
		
		Teacher teacherSaved = teacherService.findById(course.getTeacher().getIdTeacher());
		
		if(teacherSaved==null) {
			return new ResponseEntity<>( new CustomErrorType("Te teacher not exist") ,HttpStatus.BAD_REQUEST);
		}
		
		
		courseSaved.setTeacher(teacherSaved);
		
		courseService.updateCourse(courseSaved);
		
		return new ResponseEntity<Course>( courseSaved, HttpStatus.OK);
		
	}
	
	//-- UPDATE course
	@RequestMapping(value="/course/{id}", method=RequestMethod.PATCH)
	public ResponseEntity<Course> updateCourse(@RequestBody Course course, @PathVariable("id") Long idCourse ) {
		
		if(course == null) {
			return new ResponseEntity(HttpStatus.BAD_REQUEST);
		}else{
			Course currentCourse =courseService.findById(idCourse);
			if(currentCourse== null) {
				return new ResponseEntity(HttpStatus.NO_CONTENT);
			}else {
				currentCourse.setName(course.getName());
				courseService.updateCourse(currentCourse);
				return new ResponseEntity<Course>(currentCourse,HttpStatus.OK);
			}
		}
		
	}
	
	//-- DELETE course 
	@RequestMapping(value="/course/{id}", method=RequestMethod.DELETE)	
	public ResponseEntity<Course> deleteCourse( @PathVariable("id") Long idCourse ) {
		
		if(idCourse == null) {
			return new ResponseEntity(HttpStatus.BAD_REQUEST);
		}else if(courseService.findById(idCourse) == null){
			
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}else {
				
			courseService.deleteCourseById(idCourse);
			return new ResponseEntity(HttpStatus.OK);
		}
	}
		
		
	
	
}
