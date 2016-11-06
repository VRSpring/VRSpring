package com.vrspring.dao.impl;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.vrspring.dao.TestDAO;

@Repository
@Component("testDAO")
public class TestIbatisDAO extends BaseDAO implements TestDAO
{
	@Override
	public String queryId()
	{
		return (String)sql.queryForObject("test.getId","");
	}
}
