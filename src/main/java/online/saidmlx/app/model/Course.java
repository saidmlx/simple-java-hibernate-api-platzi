package online.saidmlx.app.model;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.type.TrueFalseType;

@Entity
@Table(name="course")
public class Course implements Serializable {

	@Id
	@Column(name="id_course")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long idCourse;
	
	@Column(name="name")
	private String name;
	
	@Column(name="themes")
	private String themes;
	
	@Column(name="project")
	private String project;
	
	@ManyToOne( fetch=FetchType.EAGER)
	@JoinColumn(name="id_teacher")
	private Teacher teacher;
	

	public Course() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Course( String name, String themes, String project) {
		super();
		//this.teacher = teacher;
		this.name = name;
		this.themes = themes;
		this.project = project;
	}

	public Teacher getIdTeacher() {
		return teacher;
	}

	public void setTeacher(Teacher teacher) {
		this.teacher = teacher;
	}

	public long getIdCourse() {
		return idCourse;
	}

	public void setIdCourse(long idCourse) {
		this.idCourse = idCourse;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getThemes() {
		return themes;
	}

	public void setThemes(String themes) {
		this.themes = themes;
	}

	public String getProject() {
		return project;
	}

	public void setProject(String project) {
		this.project = project;
	}
	
	
	
}