package br.com.nstest.user.entity;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@SequenceGenerator(name = "SQ_USER", sequenceName = "SEQ_USER", allocationSize = 1)
public class User implements Serializable {

	private static final long serialVersionUID = 5676143151416948449L;

	@Id
	@GeneratedValue(generator = "SQ_USER", strategy = GenerationType.SEQUENCE)
	private Integer userId;
	
	@ManyToOne
	@JoinColumn(name = "ID_HEART_TEAM")
	private HeartTeam heartTeam;
	
	@Column
	private String name;
	
	@Column
	private String email;
	
	@Column
	@JsonFormat(pattern="yyyy-MM-dd")
	private LocalDate birthday;
	
	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	public LocalDate getBirthday() {
		return birthday;
	}

	public void setBirthday(LocalDate birthday) {
		this.birthday = birthday;
	}

	public HeartTeam getHeartTeam() {
		return heartTeam;
	}

	public void setHeartTeam(HeartTeam heartTeam) {
		this.heartTeam = heartTeam;
	}
	
}