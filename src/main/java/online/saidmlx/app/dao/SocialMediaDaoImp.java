package online.saidmlx.app.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import online.saidmlx.app.model.SocialMedia;
import online.saidmlx.app.model.TeacherSocialMedia;
@Repository
@Transactional
public class SocialMediaDaoImp extends AbstractSession implements SocialMediaDao {

	@Override
	public void saveSocialMedia(SocialMedia socialMedia) {
		getSession().persist(socialMedia);
		
	}

	@Override
	public void deleteSocialMediaById(Long idSocialMedia) {
		SocialMedia socialMedia = findById(idSocialMedia);
		if(socialMedia != null) {
			getSession().delete(socialMedia);
		}
		
	}

	@Override
	public void updateSocialMedia(SocialMedia socialMedia) {
		getSession().update(socialMedia);
		
	}

	@Override
	public List<SocialMedia> findAllSocialMedia() {
		return getSession().createQuery("from SocialMedia").list();
	}

	@Override
	public SocialMedia findById(Long idSocialMedia) {
		System.out.println("public SocialMedia findById(Long idSocialMedia) "+idSocialMedia);
		return (SocialMedia)getSession().get(SocialMedia.class, idSocialMedia);
	}

	@Override
	public SocialMedia findByName(String name) {
		// TODO Auto-generated method stub
		return (SocialMedia)getSession().createQuery("from SocialMedia where name = :name").setParameter("name", name).uniqueResult();
	}
	
	
	/*
	 * todos los profesores 
	 * @see online.saidmlx.app.dao.SocialMediaDao#findSocialMediaByIdAndName(java.lang.Long, java.lang.String)
	 */
	@Override
	public TeacherSocialMedia findSocialMediaByIdAndName(Long idSocialMedia, String nickname) {
		
		List<Object[]> result= getSession().createQuery("from TeacherSocialMedia tsm join SocialMedia sm where sm = : idSocialMedia and tsm.nickname = : nickname ")
				.setParameter("idSocialMedia", idSocialMedia)
				.setParameter("nickname", nickname).list();
		if (result.size() > 0){
			for(Object[] item: result) {
				for(Object it: item) {
					if(it instanceof TeacherSocialMedia ) {
						return (TeacherSocialMedia)it;
					}
				}
			}
		}
		return null;
	}

	
}
