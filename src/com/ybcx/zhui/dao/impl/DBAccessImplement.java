package com.ybcx.zhui.dao.impl;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;

import com.ybcx.zhui.dao.DBAccessInterface;

public class DBAccessImplement  implements DBAccessInterface{
	private JdbcTemplate jdbcTemplate;

	// Inject by Spring
	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}
	
	// Constructor
	public DBAccessImplement() {

	}
}
