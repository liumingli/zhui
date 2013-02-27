package com.ybcx.zhui.dao.impl;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;

import com.ybcx.zhui.beans.Dialogue;
import com.ybcx.zhui.beans.Shot;
import com.ybcx.zhui.beans.Template;
import com.ybcx.zhui.dao.DBAccessInterface;

public class DBAccessImplement  implements DBAccessInterface{
	private JdbcTemplate jdbcTemplate;

	// Inject by Spring
	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}
	
	@Override
	public int saveTemplate(final Template template) {
		String sql = "INSERT INTO t_template "
				+ "(t_id, t_name, t_swf, t_thumbnail, t_type, t_createTime,t_enable ,t_memo) "
				+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
		
		int res =jdbcTemplate.update(sql, new PreparedStatementSetter() {
			public void setValues(PreparedStatement ps) {
				try {
					ps.setString(1, template.getId());
					ps.setString(2, template.getName());
					ps.setString(3, template.getSwf());
					ps.setString(4, template.getThumbnail());
					ps.setString(5, template.getType());
					ps.setString(6, template.getCreateTime());
					ps.setInt(7, template.getEnable());
					ps.setString(8, "");
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		});

		return res;
	}

	@Override
	public int deleteTemplate(String id) {
		String sql = "update t_template set t_enable = 0 where t_id ='"+id+"'";
		int rows = jdbcTemplate.update(sql);
		return rows;
	}

	@Override
	public int deleteShotByTemplate(String id) {
		String sql = "update t_shot set s_enable = 0 where s_template ='"+id+"'";
		int rows = jdbcTemplate.update(sql);
		return rows;
	}

	@Override
	public int saveShot(final Shot shot) {
		String sql = "INSERT INTO t_shot "
				+ "(s_id,s_name, s_swf, s_thumbnail, s_template, s_frame,s_bubble,s_bubbleSize, s_createTime,s_enable ,t_memo) "
				+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
		
		int res =jdbcTemplate.update(sql, new PreparedStatementSetter() {
			public void setValues(PreparedStatement ps) {
				try {
					ps.setString(1, shot.getId());
					ps.setString(2, shot.getName());
					ps.setString(3, shot.getSwf());
					ps.setString(4, shot.getThumbnail());
					ps.setString(5, shot.getTemplate());
					ps.setInt(6, shot.getFrame());
					ps.setInt(7, shot.getBubble());
					ps.setString(8, shot.getBubbleSize());
					ps.setString(9, shot.getCreateTime());
					ps.setInt(10, shot.getEnable());
					ps.setString(11, "");
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		});

		return res;
	}

	@Override
	public int deleteShot(String id) {
		String sql = "update t_shot set s_enable = 0 where s_id ='"+id+"'";
		int rows = jdbcTemplate.update(sql);
		return rows;
	}

	@Override
	public int saveDialogue(final Dialogue dialogue) {
		String sql = "INSERT INTO t_dialogue "
				+ "(d_id, d_content, d_image, d_shot, d_frame,d_createTime,t_memo) "
				+ "VALUES (?, ?, ?, ?, ?, ?, ?)";
		
		int res =jdbcTemplate.update(sql, new PreparedStatementSetter() {
			public void setValues(PreparedStatement ps) {
				try {
					ps.setString(1, dialogue.getId());
					ps.setString(2, dialogue.getContent());
					ps.setString(3, dialogue.getImage());
					ps.setString(4, dialogue.getShot());
					ps.setInt(5, dialogue.getFrame());
					ps.setString(6, dialogue.getCreateTime());
					ps.setString(7, "");
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		});

		return res;
	}
}
