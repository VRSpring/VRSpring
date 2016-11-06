package com.vrspring.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ibatis.SqlMapClientTemplate;

public class BaseDAO
{
	@Autowired
	protected SqlMapClientTemplate sql;
}
