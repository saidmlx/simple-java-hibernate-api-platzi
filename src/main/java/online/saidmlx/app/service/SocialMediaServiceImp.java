package online.saidmlx.app.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import online.saidmlx.app.dao.SocialMediaDao;
import online.saidmlx.app.model.SocialMedia;
import online.saidmlx.app.model.TeacherSocialMedia;

@Service("socialMedia")
@Transactional
public class SocialMediaServiceImp implements SocialMediaService {

	@Autowired
	SocialMediaDao socialMediaDao;
	
	@Override
	public void saveSocialMedia(SocialMedia socialMedia) {
		System.out.println("SocialMediaServiceImp > saveSocialMedia ");
		socialMediaDao.saveSocialMedia(socialMedia);
	}

	@Override
	public void deleteSocialMediaById(Long idSocialMedia) {
		// TODO Auto-generated method stub
		socialMediaDao.deleteSocialMediaById(idSocialMedia);
		
	}

	@Override
	public void updateSocialMedia(SocialMedia socialMedia) {
		// TODO Auto-generated method stub
		socialMediaDao.updateSocialMedia(socialMedia);
		
	}

	@Override
	public List<SocialMedia> findAllSocialMedia() {
		System.out.println("SocialMediaServiceImp > findAllSocialMedia() ");
		return socialMediaDao.findAllSocialMedia();
	}

	@Override
	public SocialMedia findById(Long idSocialMedia) {
		// TODO Auto-generated method stub
		return socialMediaDao.findById(idSocialMedia);
	}

	@Override
	public SocialMedia findByName(String name) {
		// TODO Auto-generated method stub
		return socialMediaDao.findByName(name);
	}

	@Override
	public TeacherSocialMedia findSocialMediaByIdAndName(Long idSocialMedia, String nickname) {
		// TODO Auto-generated method stub
		return socialMediaDao.findSocialMediaByIdAndName(idSocialMedia, nickname);
	}

}
