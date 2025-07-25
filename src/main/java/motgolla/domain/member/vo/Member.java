package motgolla.domain.member.vo;


import java.util.Collection;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import motgolla.domain.member.dto.response.MemberInfoResponse;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;


@Getter
@Setter
public class Member implements UserDetails {
	private Long id;
	private String name;
	private String oauthId;
	private String profile;
	private String birthday;
	private String gender;
	private String refreshToken;
	private String createdAt;
	private String createdBy;
	private String updatedAt;
	private String updatedBy;
	private int isDeleted;

    @Override
    public String getUsername() {
        return String.valueOf(id);
    }

    @Override
    public String getPassword() {
        // Placeholder: implement as needed
        return null;
    }

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return List.of(new SimpleGrantedAuthority("ROLE_USER"));
	}

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

	public MemberInfoResponse toDto(){
		return new MemberInfoResponse(id, name, birthday, gender, profile, createdAt);
	}
}
