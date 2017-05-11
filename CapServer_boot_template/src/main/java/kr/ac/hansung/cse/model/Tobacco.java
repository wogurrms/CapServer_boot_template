package kr.ac.hansung.cse.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Tobacco {
	@Id
	@GeneratedValue
	@Column(name = "tobac_id")
	private int tobac_id;

	private String tobaccoBrand;
	private String tobaccoName;
	private int tobaccoPrice;;
	private double tobaccoNicotine;
}

