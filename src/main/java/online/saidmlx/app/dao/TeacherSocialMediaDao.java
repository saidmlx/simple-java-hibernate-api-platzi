package online.saidmlx.app.dao;

import java.util.List;


import online.saidmlx.app.model.TeacherSocialMedia;

public interface TeacherSocialMediaDao {

	
	public void saveTeacherSocialMedia(TeacherSocialMedia teacherSocialMedia);

	public void deleteTeacherSocialMediaById(Long idTeacherSocialMedia);
	
	public void updateTeacherSocialMedia(TeacherSocialMedia teacherSocialMedia);
	
	public List<TeacherSocialMedia> findAllTeacherSocialMedia();
	
	public TeacherSocialMedia findById(Long idTeacherSocialMedia);
	
}
