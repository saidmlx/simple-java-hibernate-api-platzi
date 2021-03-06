package online.saidmlx.app.dao;

import java.util.List;

import online.saidmlx.app.model.Teacher;

public interface TeacherDao {

	public void saveTeacher(Teacher teacher);
	
	public void deleteTeacherById(Long idTeacher);
	
	public void updateTeacher(Teacher teacher);
	
	public List<Teacher> findAllTeacher();
	
	public Teacher findById(Long idTeacher);
	
	public Teacher findByName(String name);
	
	
	
}
