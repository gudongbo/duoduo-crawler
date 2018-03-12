package com.duoduo.crawler.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.duoduo.crawler.dao.PageInfoDao;
import com.duoduo.crawler.entity.PageInfo;

@Service
public class PageInfoDaoImpl implements PageInfoDao {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public int add(PageInfo info) {
		return jdbcTemplate.update("insert into page_info(page_url, create_time, is_upload, is_zip) values(?, ?, ?, ?)",
				info.getPageUrl(), info.getCreateTime(), info.getIsUpload(), info.getIsZip());
	}

}
