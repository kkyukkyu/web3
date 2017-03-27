package org.zerock.domain;

import java.sql.Date;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Getter 
@Setter
@ToString

public class Board {

	private int bno;
	private String title;
	private String content;
	private String writer;
	private Date regdate;
	private Date updateDate;

}
