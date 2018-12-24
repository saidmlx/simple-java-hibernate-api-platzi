package online.saidmlx.app.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import online.saidmlx.app.model.Course;

@Repository
@Transactional
public class CourseDaoImp extends AbstractSession implements CourseDao {

	@Override
	public void saveCourse(Course course) {
		getSession().persist(course);
	}

	@Override
	public void deleteCourseById(Long idCourse) {
		Course course = findById(idCourse);
		if(course != null) {
			getSession().delete(course);
		}
	}

	@Override
	public void updateCourse(Course course) {
		getSession().update(course);
	}

	@Override
	public List<Course> findAllCourse() {
		return getSession().createQuery("from Course").list();
	}

	@Override
	public Course findById(Long idCourse) {
		return (Course)getSession().get(Course.class, idCourse);
	}

	@Override
	public Course findByName(String name) {
		return (Course)getSession().createQuery("from Course where name = :name").setParameter("name", name).uniqueResult();
	}

	@Override
	public List<Course> findByIdTeacher(Long idTeacher) {
		// TODO Auto-generated method stub
		return (List<Course>) getSession().createQuery(" from Course c join c.teacher where idteacher = : idTeacher")
				.setParameter("idTeacher", idTeacher).list();
	}

}
