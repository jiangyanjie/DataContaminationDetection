package com.sg.mycallsheet.users.service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.jdbc.JdbcDaoImpl;

public class CustomerUserDetailsService extends JdbcDaoImpl{
	
	@Override
	public void setUsersByUsernameQuery(String usersByUsernameQueryString){
		super.setUsersByUsernameQuery(usersByUsernameQueryString);
	}
	
	@Override
	public void setAuthoritiesByUsernameQuery(String queryString){
		super.setAuthoritiesByUsernameQuery(queryString);
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.springframework.security.core.userdetails.jdbc.JdbcDaoImpl#loadUsersByUsername(java.lang.String)
	 * @Over ride to pass get accountNonLocked
	 */
	@Override
	public List<UserDetails> loadUsersByUsername(String userName){
		return getJdbcTemplate().query(super.getUsersByUsernameQuery(),
			new String[]{userName},
			new RowMapper<UserDetails>(){
				@Override
				public UserDetails mapRow(ResultSet rs, int rowNum)
						throws SQLException {
					String userName = rs.getString("username");
					String password = rs.getString("password");
					boolean enabled = rs.getBoolean("enabled");
					boolean accountNonExpired = rs.getBoolean("accountNonExpired");
					boolean credentialsNonExpired = rs.getBoolean("credentialsNonExpired");
					boolean accountNonLocked = rs.getBoolean("accountNonLocked");
					
					return new User(userName, password, enabled, accountNonExpired,
							credentialsNonExpired, accountNonLocked,AuthorityUtils.NO_AUTHORITIES);
				}				
			});
	}
	
	@Override
	public UserDetails createUserDetails(String userName,UserDetails userFromUserQuery,
			List<GrantedAuthority> combinedAuthorities){
		String returnUserName = userFromUserQuery.getUsername();
		if(super.isUsernameBasedPrimaryKey()){
			returnUserName = userName;
		}
		
		return new User(returnUserName, userFromUserQuery.getPassword(), userFromUserQuery.isEnabled(),
				userFromUserQuery.isAccountNonExpired(),userFromUserQuery.isCredentialsNonExpired(),
				userFromUserQuery.isAccountNonLocked(),combinedAuthorities);
	}
}
