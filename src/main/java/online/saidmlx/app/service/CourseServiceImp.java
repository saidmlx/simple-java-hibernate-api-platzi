package online.saidmlx.app.service;

import java.util.List;


import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import online.saidmlx.app.model.Course;
import online.saidmlx.app.dao.CourseDao;

@Service("courseService")
@Transactional
public class CourseServiceImp  implements CourseService  {

	@Autowired
	CourseDao courseDao;
	
	@Override
	public void saveCourse(Course course) {

		courseDao.saveCourse(course);
	}

	@Override
	public void deleteCourseById(Long idCourse) {
		
		courseDao.deleteCourseById(idCourse);
		
	}

	@Override
	public void updateCourse(Course course) {
		
		courseDao.updateCourse(course);
		
	}

	@Override
	public List<Course> findAllCourse() {
		
		return courseDao.findAllCourse();
	}

	@Override
	public Course findById(Long idCourse) {

		return courseDao.findById(idCourse);
	}

	@Override
	public Course findByName(String name) {

		return courseDao.findByName(name);
	}

	@Override
	public List<Course> findByIdTeacher(Long idTeacher) {

		return courseDao.findByIdTeacher(idTeacher);
	}

}
