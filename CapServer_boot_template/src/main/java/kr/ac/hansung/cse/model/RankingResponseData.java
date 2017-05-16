package kr.ac.hansung.cse.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RankingResponseData {
	private String username;
	private int todayAmount;
	private float avg; 
}
