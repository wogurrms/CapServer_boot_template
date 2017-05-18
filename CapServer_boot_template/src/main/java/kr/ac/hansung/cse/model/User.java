package kr.ac.hansung.cse.model;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class User {
	@Id
	@GeneratedValue
	@Column(name="user_id")
	private int uid;
	private String nick;
	private String password;
	private int enabled;
	private String authority;
	private Date joinDate;
	
	@OneToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="tobac_id")
	private Tobacco tobac;
}
