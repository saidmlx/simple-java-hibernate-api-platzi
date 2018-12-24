package online.saidmlx.app.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import online.saidmlx.app.dao.TeacherSocialMediaDao;
import online.saidmlx.app.model.TeacherSocialMedia;

public class TeacherSocialMediaServiceImp implements TeacherSocialMediaService {

	
	@Autowired
	TeacherSocialMediaDao teacherSocialMediaDao ; 
	
	@Override
	public void saveTeacherSocialMedia(TeacherSocialMedia teacherSocialMedia) {
		// TODO Auto-generated method stub
		teacherSocialMediaDao.saveTeacherSocialMedia(teacherSocialMedia);
		
	}

	@Override
	public void deleteTeacherSocialMediaById(Long idTeacherSocialMedia) {
		// TODO Auto-generated method stub
		teacherSocialMediaDao.deleteTeacherSocialMediaById(idTeacherSocialMedia);
		
	}

	@Override
	public void updateTeacherSocialMedia(TeacherSocialMedia teacherSocialMedia) {
		// TODO Auto-generated method stub
		teacherSocialMediaDao.updateTeacherSocialMedia(teacherSocialMedia);
		
	}

	@Override
	public List<TeacherSocialMedia> findAllTeacherSocialMedia() {
		// TODO Auto-generated method stub
		return teacherSocialMediaDao.findAllTeacherSocialMedia();
	}

	@Override
	public TeacherSocialMedia findById(Long idTeacherSocialMedia) {
		// TODO Auto-generated method stub
		return teacherSocialMediaDao.findById(idTeacherSocialMedia);
	}

}
