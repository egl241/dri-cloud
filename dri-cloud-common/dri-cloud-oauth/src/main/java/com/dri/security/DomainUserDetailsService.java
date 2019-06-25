package com.dri.security;

import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.dri.entity.Account;

@Service
public class DomainUserDetailsService implements UserDetailsService{

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		Account account = this.findByName(username);
		
		
		BCryptPasswordEncoder password = new BCryptPasswordEncoder();
		if(account!=null) {
			return new User(
					account.getUserName(), 
					//password.encode(account.getPassWord()),
					account.getPassWord(), 
					AuthorityUtils.createAuthorityList(account.getRoles()));
		}else {
			throw  new UsernameNotFoundException("用户["+username+"]不存在");
		}
		
	}

	
	
	private Account findByName(String username) {
		Account ac=new Account();
		
		if(username.equals("guest")) {
			ac.setId("89");
			ac.setUserName(username);
			ac.setPassWord("guest");
			String[] roles= {"ROLE_USER","ROLE_ADMIN","ROLE_GUEST"};
			ac.setRoles(roles);
			return ac;
		}
		
		if(!username.equals("admin")) {
			return ac;
		}
		ac.setId("88");
		ac.setUserName(username);
		ac.setPassWord("admin");
		String[] roles= {"ROLE_USER","ROLE_ADMIN"};
		ac.setRoles(roles);
		return ac;
	}
}
