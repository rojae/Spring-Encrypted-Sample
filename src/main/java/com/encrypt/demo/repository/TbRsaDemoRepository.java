package com.encrypt.demo.repository;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.encrypt.demo.domain.TbRsaDemo;

@Repository
public class TbRsaDemoRepository {
	
	@Autowired
	private SqlSession sqlSession;
	
	private final String NAMESPACE = "com.encrypt.demo.mapper.TbRsaDemoMapper.";
	
	public List<TbRsaDemo> getAll(){
		return sqlSession.selectList(NAMESPACE + "selectAll");
	}
	
	public TbRsaDemo getOne(String id) {
		return sqlSession.selectOne(NAMESPACE + "selectByPrimaryKey", id);
	}
	
	public boolean insertOne(TbRsaDemo tb) {
		if(sqlSession.insert(NAMESPACE + "insert", tb) == 1)
			return true;
		else
			return false;
	}
	
	public boolean updateOne(TbRsaDemo tb) {
		if(sqlSession.insert(NAMESPACE + "updateByPrimaryKey", tb) == 1)
			return true;
		else
			return false;
	}
}
