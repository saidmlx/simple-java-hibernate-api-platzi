package online.saidmlx.app.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import online.saidmlx.app.model.TeacherSocialMedia;
@Repository
@Transactional
public class TeacherSocialMediaDaoImp extends AbstractSession implements TeacherSocialMediaDao {

	@Override
	public void saveTeacherSocialMedia(TeacherSocialMedia teacherSocialMedia) {
		getSession().persist(teacherSocialMedia);

	}

	@Override
	public void deleteTeacherSocialMediaById(Long idTeacherSocialMedia) {
		TeacherSocialMedia teacherSocialMedia = findById(idTeacherSocialMedia);
		if(teacherSocialMedia != null) {
			
			
			
			getSession().delete(teacherSocialMedia);
			
			
		}
	}

	@Override
	public void updateTeacherSocialMedia(TeacherSocialMedia teacherSocialMedia) {
		getSession().update(teacherSocialMedia);
	}

	@Override
	public List<TeacherSocialMedia> findAllTeacherSocialMedia() {
		// TODO Auto-generated method stub
		return getSession().createQuery("from TeacherSocialMedia").list();
	}

	@Override
	public TeacherSocialMedia findById(Long idTeacherSocialMedia) {
		// TODO Auto-generated method stub
		return (TeacherSocialMedia)getSession().get(TeacherSocialMedia.class, idTeacherSocialMedia);
	}

}
