package com.fastcampus.fastcampusprojectboardreview.domain;

import java.security.Identity;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@ToString(callSuper = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(indexes = {
	@Index(columnList = "userId"),
	@Index(columnList = "email", unique = true),
	@Index(columnList = "createdAt"),
	@Index(columnList = "createdBy")
})
@Entity
public class UserAccount extends AuditingFields {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Setter @Column(nullable = false, length = 50) private String userId;

	@Setter
	@Column(nullable = false)
	private String userPassword;

	@Setter
	@Column(length = 100)
	private String email;

	@Setter
	@Column(length = 100)
	private String nickname;

	@Setter
	private String memo;

	private UserAccount(String userId, String userPassword, String email, String nickname, String memo) {
		this.userId = userId;
		this.userPassword = userPassword;
		this.email = email;
		this.nickname = nickname;
		this.memo = memo;
	}

	public static UserAccount of(String userId, String userPassword, String email, String nickname, String memo) {
		return new UserAccount(userId, userPassword, email, nickname, memo);
	}


	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (!(o instanceof UserAccount that))
			return false;
		return this.getUserId() != null && Objects.equals(userId, that.userId);
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(userId);
	}
}
