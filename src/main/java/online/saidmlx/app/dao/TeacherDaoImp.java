package online.saidmlx.app.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import online.saidmlx.app.model.Teacher;
import online.saidmlx.app.model.TeacherSocialMedia;
@Repository
@Transactional
public class TeacherDaoImp extends AbstractSession implements TeacherDao {

	
	public void saveTeacher(Teacher teacher) {
		getSession().persist(teacher);
	}

	public void deleteTeacherById(Long idTeacher) {
		
		Teacher teacher = findById(idTeacher);
		
		//--delete all Teacher Social Media
		if(teacher != null) {
			for( TeacherSocialMedia teacherSocialMedia:teacher.getTeacherSocialMedia()) {
				getSession().delete(teacherSocialMedia);
			}
			teacher.getTeacherSocialMedia().clear();
			//--delete teacher
			getSession().delete(teacher);
		}
	}

	public void updateTeacher(Teacher teacher) {
		getSession().update(teacher);
		
	}

	public List<Teacher> findAllTeacher() {
		return getSession().createQuery("from Teacher").list();
	}

	public Teacher findById(Long idTeacher) {
		return (Teacher)getSession().get(Teacher.class, idTeacher);
	}

	public Teacher findByName(String name) {
		return (Teacher)getSession().createQuery("from Teacher where name = :name").setParameter("name", name).uniqueResult();
	}

	
}
