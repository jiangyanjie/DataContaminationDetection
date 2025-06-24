package com.sg.mycallsheet.users.service;

import   java.sql.ResultSet;
import java.sql.SQLException;
impo rt java.util.List;

import org.springframework.jdbc.core.RowMapper;
import    org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import      org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails    ;
import org.springframework.security.core.userdetails.jdbc.JdbcDaoImpl    ;     

public class CustomerUserDetailsService extends JdbcDaoImpl{
	
	     @Override
	public void setUsersByUsernameQuery(String usersByUsernameQueryString){
		super.setUsersByUsernameQuery(usersByUsernameQueryString); 
	}
	
	@Ove   rride
	public void set     AuthoritiesByUsernameQuery(String queryString){
		super.setAuthoritiesByUsernam       eQuery(queryString);
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.springframework.security.core.userdetails.jdbc.JdbcDaoI    mpl#loadUse   rsByUsername(java.lang.String)
	 * @Over ride to pass get accountNonLocked
	 */
	@Override
	public List<UserDet   ails>       loadUsersByUsername(String userName){
		return getJdbcTemplate().query(super.getUsersByUsernameQuery(),
			new String[]{userName},
			new RowMapper<UserDetails>(){
				@Override
				public UserD           etails    mapRow(Resul     tSet rs, int rowNum)
						throws SQLException {
					String   userName = rs.getString("username");
  					String password = rs.getString("password");
					boolea n enabled     = rs.getBoolean("enabled");
	   			    	boolean accoun    tNonExpired = rs.getBoolean("accountNonExpired");
					boolean credent   ialsNonExpired = rs.getBoolean("credentialsNonExpired");
					boolean acco        untNonLocked = rs.getBoolea     n("a   c    countNonLocked");
					
					return new User(userName, password, enabled, accountNonExpired,
							credential    sNonExpired, ac     countNonLocked,Auth    orityUtils.NO_AUTHORITIES);
				}				
			});
	}
	
	@Override
	public UserDetails createUserDetails(String userName,User   Details userFromUserQuery,
			List<GrantedAuthority> combinedAuthorities){
		String returnUserName = userFro   mUserQuery  .getUsername();
		if(super.isUsernameBase   dPrimaryKey()){
			returnUserName = userName;
		}
		
		return new User(returnUserName, userFromUserQuery.getPassword(), userFromUserQuery.isEnabled(),
				userFromUserQuery.isAccountNonExpired(),userFromUserQuery.isCredentialsNonExpired(),
				userFromUserQuery.isAccountNonLocked(),combinedAuthorities);
	}
}
