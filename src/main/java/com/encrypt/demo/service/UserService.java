package com.encrypt.demo.service;

import com.encrypt.demo.repository.TbRsaDemoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.encrypt.demo.domain.TbRsaDemo;
import com.encrypt.demo.model.User;

@Service
public class UserService {

	@Autowired
	private TbRsaDemoRepository tbRsaRepository;
	
	public User getUser(String userId) {
		TbRsaDemo user = tbRsaRepository.getOne(userId);
		User returnUser = new User(user.getId(), user.getName(), user.getRsapublickey(), user.getRsaprivatekey(), user.getAeskey());
		return returnUser;
	}

	public void updateUser(User user) {
		TbRsaDemo tb = new TbRsaDemo();
		tb.setId(user.getId());
		tb.setName(user.getName());
		tb.setRsapublickey(user.getRsaPublicKey());
		tb.setRsaprivatekey(user.getRsaPrivateKey());
		tb.setAeskey(user.getAesKey());
		tbRsaRepository.updateOne(tb);
	}
	
	
}
