package online.saidmlx.app.dao;

import java.util.List;

import online.saidmlx.app.model.Course;
import online.saidmlx.app.model.Teacher;

public interface CourseDao {

	public void saveCourse(Course course);

	public void deleteCourseById(Long idCourse);
	
	public void updateCourse(Course course);
	
	public List<Course> findAllCourse();
	
	public Course findById(Long idCourse);
	
	public Course findByName(String name);
	
	public List<Course> findByIdTeacher(Long idTeacher);
}
