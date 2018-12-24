package online.saidmlx.app.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import online.saidmlx.app.dao.TeacherDao;
import online.saidmlx.app.model.Teacher;

@Service("teacherService")
@Transactional
public class TeacherServiceImp implements TeacherService {

	@Autowired
	TeacherDao teacherDao;

	@Override
	public void saveTeacher(Teacher teacher) {
		// TODO Auto-generated method stub
		teacherDao.saveTeacher(teacher);
		
	}

	@Override
	public void deleteTeacherById(Long idTeacher) {
		// TODO Auto-generated method stub
		teacherDao.deleteTeacherById(idTeacher);
		
	}

	@Override
	public void updateTeacher(Teacher teacher) {
		// TODO Auto-generated method stub
		teacherDao.updateTeacher(teacher);
		
	}

	@Override
	public List<Teacher> findAllTeacher() {
		// TODO Auto-generated method stub
		return teacherDao.findAllTeacher();
	}

	@Override
	public Teacher findById(Long idTeacher) {
		// TODO Auto-generated method stub
		return teacherDao.findById(idTeacher);
	}

	@Override
	public Teacher findByName(String name) {
		// TODO Auto-generated method stub
		return teacherDao.findByName(name);
	}


}
