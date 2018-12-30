package online.saidmlx.app.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.util.UriComponentsBuilder;

import javassist.expr.NewArray;
import online.saidmlx.app.model.SocialMedia;
import online.saidmlx.app.service.SocialMediaService;
import online.saidmlx.util.CustomErrorType;


@Controller
@RequestMapping("/v1")
public class SocialMediaController {

	@Autowired
	SocialMediaService socialMediaService;
	
	@RequestMapping(value="/socialMedias", method= RequestMethod.GET, headers="Accept= application/json")
	public ResponseEntity<List<SocialMedia>>  getSocialMedias( @RequestParam(value="name", required=false ) String name	 ){
		List<SocialMedia> socialMedias = new ArrayList<SocialMedia>();
		if(name == null) {
			socialMedias = socialMediaService.findAllSocialMedia();
			if(socialMedias.isEmpty()) {
				return new ResponseEntity(new CustomErrorType("The SocialMedias are empty "),HttpStatus.NO_CONTENT);
			}
			return new ResponseEntity<List<SocialMedia>>(socialMedias,HttpStatus.OK);
		}else {
			SocialMedia socialMedia= socialMediaService.findByName(name);
			if(socialMedia==null) {
				return new ResponseEntity(HttpStatus.NOT_FOUND);
			}
			else {
				socialMedias.add(socialMedia);
				return new ResponseEntity<List<SocialMedia>>(socialMedias,HttpStatus.OK);
			}
		}
	}
	
	@RequestMapping(value="/socialMedia/{id}", method= RequestMethod.GET, headers="Accept= application/json")
	public ResponseEntity<SocialMedia>  getSocialMedia(@PathVariable("id") Long idSocialMedia  ){
		
		if(idSocialMedia == null || idSocialMedia <=0 ) {
			return new ResponseEntity( new CustomErrorType("idSocialMedia is required"), HttpStatus.CONFLICT);
		}
		SocialMedia socialMedia = socialMediaService.findById(idSocialMedia);
		if(socialMedia == null) {
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}else {
			return new ResponseEntity<SocialMedia>(socialMedia,HttpStatus.OK);
		}
	}
	
	
	@RequestMapping(value="/socialMedia/{id}", method= RequestMethod.PATCH, headers="Accept= application/json")
	public ResponseEntity<SocialMedia>  updateSocialMedia(@PathVariable("id") Long idSocialMedia,   @RequestBody SocialMedia socialMedia){
		if(idSocialMedia == null || socialMedia == null || idSocialMedia <=0 ) {
			return new ResponseEntity( new CustomErrorType("idSocialMedia is required"), HttpStatus.CONFLICT);
		}
		SocialMedia currentSocialMedia = this.socialMediaService.findById(idSocialMedia);
		if(currentSocialMedia == null) {
			return new ResponseEntity(HttpStatus.NOT_FOUND);
		}else {
			currentSocialMedia.setName(socialMedia.getName());
			currentSocialMedia.setIcono(socialMedia.getIcon());
			this.socialMediaService.updateSocialMedia(currentSocialMedia);
			return new ResponseEntity<SocialMedia>(currentSocialMedia,HttpStatus.OK);
		}
		
	}
	@RequestMapping(value="/socialMedia/{id}", method= RequestMethod.DELETE, headers="Accept= application/json")
	public ResponseEntity<SocialMedia>  deleteSocialMedia(@PathVariable("id") Long idSocialMedia){
		if(idSocialMedia == null ) {
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}
		SocialMedia currentSocialMedia = this.socialMediaService.findById(idSocialMedia);
		if(currentSocialMedia == null) {
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}else {
			this.socialMediaService.deleteSocialMediaById(idSocialMedia);
			return new ResponseEntity<SocialMedia>(HttpStatus.OK);
		}
	}
	
	@RequestMapping(value="/socialMedia", method=RequestMethod.POST , headers="Accept= Application/json")
	public ResponseEntity<?> save(@RequestBody SocialMedia socialMedia, UriComponentsBuilder uriComponent ) {
		if(socialMedia.getName().equals(null) || socialMedia.getName().isEmpty()) {
			return new ResponseEntity(new CustomErrorType("The values are empty"),HttpStatus.BAD_REQUEST);
		}
		if(socialMediaService.findByName(socialMedia.getName()) != null) {
			return new ResponseEntity<>(new CustomErrorType("The SocialMedia exists"), HttpStatus.BAD_REQUEST);
		}
		socialMediaService.saveSocialMedia(socialMedia);
		SocialMedia socialMediaSaved = socialMediaService.findByName(socialMedia.getName());
		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(uriComponent.path("v1/socialMedias/{id}")
				.buildAndExpand(socialMediaSaved.getIdSocialmedia())
				.toUri());
		return new ResponseEntity<String>(headers, HttpStatus.CREATED );
	}
	
	
}
