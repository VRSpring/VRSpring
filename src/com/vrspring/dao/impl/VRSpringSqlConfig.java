package com.vrspring.dao.impl;

import com.ibatis.common.resources.Resources;
import com.ibatis.sqlmap.client.SqlMapClient;
import com.ibatis.sqlmap.client.SqlMapClientBuilder;

import java.io.Reader;

public class VRSpringSqlConfig
{
	private SqlMapClient sqlMap;

	public SqlMapClient getSqlMapInstance()
	{
		try
		{
			String resource = "SqlMapConfig.xml";
			Reader reader = Resources.getResourceAsReader(resource);
			sqlMap = SqlMapClientBuilder.buildSqlMapClient(reader);
		} catch (Exception e)
		{
			e.printStackTrace();
			throw new RuntimeException(
					"Error initializing VRSpringSqlConfig class. Cause: " + e);
		}
		return sqlMap;
	}
}
