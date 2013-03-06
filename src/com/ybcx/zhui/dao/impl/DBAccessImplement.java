package com.ybcx.zhui.dao.impl;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;

import com.ybcx.zhui.beans.Case;
import com.ybcx.zhui.beans.Dialogue;
import com.ybcx.zhui.beans.Memory;
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
	public int deleteTemplate(String templateId) {
		String sql = "update t_template set t_enable = 0 where t_id ='"+templateId+"'";
		int rows = jdbcTemplate.update(sql);
		return rows;
	}

	@Override
	public int deleteShotByTemplate(String templateId) {
		String sql = "update t_shot set s_enable = 0 where s_template ='"+templateId+"'";
		int rows = jdbcTemplate.update(sql);
		return rows;
	}

	@Override
	public int saveShot(final Shot shot) {
		String sql = "INSERT INTO t_shot "
				+ "(s_id,s_name, s_swf, s_thumbnail, s_template, s_frame,s_bubble,s_bubbleSize, s_createTime,s_enable ,s_memo) "
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
	public int deleteShot(String shotId) {
		String sql = "update t_shot set s_enable = 0 where s_id ='"+shotId+"'";
		int rows = jdbcTemplate.update(sql);
		return rows;
	}

	@Override
	public int saveDialogue(final Dialogue dialogue) {
		String sql = "INSERT INTO t_dialogue "
				+ "(d_id, d_content, d_image, d_shot, d_frame,d_createTime,d_memo) "
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

	@Override
	public List<Template> getTemplateByCategory(String type, int pageNum,
			int pageSize) {
		List<Template> resList = new ArrayList<Template>();
		int startLine = (pageNum -1)*pageSize;
		String sql = "select * from t_template where t_type='"+type+"' and t_enable=1 " +
				"order by t_createTime desc limit "+startLine+","+pageSize;
		List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql);
		if (rows != null && rows.size() > 0) {
			for (int i = 0; i < rows.size(); i++) {
				Map<String, Object> map = (Map<String, Object>) rows.get(i);
				Template template = new Template();
				template.setId(map.get("t_id").toString());
				template.setName(map.get("t_name").toString());
				template.setSwf(map.get("t_swf").toString());
				template.setThumbnail(map.get("t_thumbnail").toString());
				template.setType(map.get("t_type").toString());
				template.setCreateTime(map.get("t_createTime").toString());
				template.setEnable(Integer.parseInt(map.get("t_enable").toString()));
				resList.add(template);
			}
		}
		return resList;
	}

	@Override
	public List<Shot> getShotByTemplate(String templateId) {
		List<Shot> resList = new ArrayList<Shot>();
		String sql = "select * from t_shot where s_template='"+templateId+"' and s_enable=1 " +
				"order by s_frame ";
		List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql);
		if (rows != null && rows.size() > 0) {
			for (int i = 0; i < rows.size(); i++) {
				Map<String, Object> map = (Map<String, Object>) rows.get(i);
				Shot shot = new Shot();
				shot.setId(map.get("s_id").toString());
				shot.setName(map.get("s_name").toString());
				shot.setSwf(map.get("s_swf").toString());
				shot.setThumbnail(map.get("s_thumbnail").toString());
				shot.setTemplate(map.get("s_template").toString());
				shot.setFrame(Integer.parseInt(map.get("s_frame").toString()));
				shot.setBubble(Integer.parseInt(map.get("s_bubble").toString()));
				shot.setBubbleSize(map.get("s_bubbleSize").toString());
				shot.setCreateTime(map.get("s_createTime").toString());
				shot.setEnable(Integer.parseInt(map.get("s_enable").toString()));
				resList.add(shot);
			}
		}
		return resList;
	}

	@Override
	public Shot getShotById(String shotId) {
		Shot shot = new Shot();
		String sql = "select * from t_shot where s_id='"+shotId+"' and s_enable=1";
		List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql);
		if (rows != null && rows.size() > 0) {
			Map<String, Object> map = (Map<String, Object>) rows.get(0);
			shot.setId(map.get("s_id").toString());
			shot.setName(map.get("s_name").toString());
			shot.setSwf(map.get("s_swf").toString());
			shot.setThumbnail(map.get("s_thumbnail").toString());
			shot.setTemplate(map.get("s_template").toString());
			shot.setFrame(Integer.parseInt(map.get("s_frame").toString()));
			shot.setBubble(Integer.parseInt(map.get("s_bubble").toString()));
			shot.setBubbleSize(map.get("s_bubbleSize").toString());
			shot.setCreateTime(map.get("s_createTime").toString());
			shot.setEnable(Integer.parseInt(map.get("s_enable").toString()));
		}
		return shot;
	}

	@Override
	public int saveMemory(final Memory memory) {
		String sql = "INSERT INTO t_memory "
				+ "(m_id, m_user, m_template, m_dialogues,m_frames,m_createTime,m_enable,m_memo) "
				+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
		
		int res =jdbcTemplate.update(sql, new PreparedStatementSetter() {
			public void setValues(PreparedStatement ps) {
				try {
					ps.setString(1, memory.getId());
					ps.setString(2, memory.getUser());
					ps.setString(3, memory.getTemplate());
					ps.setString(4, memory.getDialogues());
					ps.setString(5, memory.getFrames());
					ps.setString(6, memory.getCreateTime());
					ps.setInt(7, memory.getEnable());
					ps.setString(8, "");
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		});

		return res;
	}

	@Override
	public Memory getDialogueAnimation(String memoryId) {
		Memory memory = new Memory();
		String sql = "select * from t_memory where m_id='"+memoryId+"' and m_enable=1";
		List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql);
		if (rows != null && rows.size() > 0) {
			Map<String, Object> map = (Map<String, Object>) rows.get(0);
			memory.setId(map.get("m_id").toString());
			memory.setUser(map.get("m_user").toString());
			memory.setTemplate(map.get("m_template").toString());
			memory.setDialogues(map.get("m_dialogues").toString());
			memory.setFrames(map.get("m_frames").toString());
			memory.setCreateTime(map.get("m_createTime").toString());
			memory.setEnable(Integer.parseInt(map.get("m_enable").toString()));
		}
		return memory;
	}

	@Override
	public String getTemplateFilePath(String resId) {
		String swfPath = "";
		String sql = "select t_swf from t_template where t_id='"+resId+"'";
		Map<String,Object> map = jdbcTemplate.queryForMap(sql);
		swfPath = map.get("t_swf").toString();
		return swfPath;
	}

	@Override
	public String getShotFilePath(String resId) {
		String swfPath = "";
		String sql = "select s_swf from t_shot where s_id='"+resId+"'";
		Map<String,Object> map = jdbcTemplate.queryForMap(sql);
		swfPath = map.get("s_swf").toString();
		return swfPath;
	}

	@Override
	public String getDialogueFilePath(String resId) {
		String imgPath = "";
		String sql = "select d_image from t_dialogue where d_id='"+resId+"'";
		Map<String,Object> map = jdbcTemplate.queryForMap(sql);
		imgPath = map.get("d_image").toString();
		return imgPath;
	}

	@Override
	public List<Memory> getMemoryByUser(String userId, int pageNum, int pageSize) {
		List<Memory> resList = new ArrayList<Memory>();
		int startLine = (pageNum -1)*pageSize;
		String sql = "select * from t_memory where m_user='"+userId+"' and m_enable=1 " +
				"order by m_createTime desc limit "+startLine+","+pageSize;
		List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql);
		if (rows != null && rows.size() > 0) {
			for (int i = 0; i < rows.size(); i++) {
				Map<String, Object> map = (Map<String, Object>) rows.get(i);
				Memory memory = new Memory();
				memory.setId(map.get("m_id").toString());
				memory.setUser(map.get("m_user").toString());
				memory.setTemplate(map.get("m_template").toString());
				memory.setDialogues(map.get("m_dialogues").toString());
				memory.setFrames(map.get("m_frames").toString());
				memory.setCreateTime(map.get("m_createTime").toString());
				memory.setEnable(Integer.parseInt(map.get("m_enable").toString()));
				resList.add(memory);
			}
		}
		return resList;
	}

	@Override
	public int saveCase(final Case cas) {
		String sql = "INSERT INTO t_case "
				+ "(c_id, c_name, c_swf, c_thumbnail, c_description, c_deliverTime,c_enable,c_memo) "
				+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
		
		int res =jdbcTemplate.update(sql, new PreparedStatementSetter() {
			public void setValues(PreparedStatement ps) {
				try {
					ps.setString(1, cas.getId());
					ps.setString(2, cas.getName());
					ps.setString(3, cas.getSwf());
					ps.setString(4, cas.getThumbnail());
					ps.setString(5, cas.getDescription());
					ps.setString(6, cas.getDeliverTime());
					ps.setInt(7, cas.getEnable());
					ps.setString(8, "");
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		});

		return res;
	}

	@Override
	public int deleteCase(String caseId) {
		String sql = "update t_case set c_enable = 0 where c_id ='"+caseId+"'";
		int rows = jdbcTemplate.update(sql);
		return rows;
	}

	@Override
	public List<Case> getCase(int pageNum, int pageSize) {
		List<Case> resList = new ArrayList<Case>();
		int startLine = (pageNum -1)*pageSize;
		String sql = "select * from t_case where c_enable=1 " +
				"order by c_deliverTime desc limit "+startLine+","+pageSize;
		List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql);
		if (rows != null && rows.size() > 0) {
			for (int i = 0; i < rows.size(); i++) {
				Map<String, Object> map = (Map<String, Object>) rows.get(i);
				Case cas = new Case();
				cas.setId(map.get("c_id").toString());
				cas.setName(map.get("c_name").toString());
				cas.setDescription(map.get("c_description").toString());
				cas.setSwf(map.get("c_swf").toString());
				cas.setThumbnail(map.get("c_thumbnail").toString());
				cas.setDeliverTime(map.get("c_deliverTime").toString());
				cas.setEnable(Integer.parseInt(map.get("c_enable").toString()));
				resList.add(cas);
			}
		}
		return resList;
	}

}
