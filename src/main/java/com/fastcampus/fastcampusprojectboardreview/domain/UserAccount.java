package com.fastcampus.fastcampusprojectboardreview.domain;

import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
	@Index(columnList = "email", unique = true),
	@Index(columnList = "createdAt"),
	@Index(columnList = "createdBy")
})
@Entity
public class UserAccount extends AuditingFields {
	@Id
	@Column(length = 50)
	private String userId;

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

	private UserAccount(String userId, String userPassword, String email, String nickname, String memo, String createdBy) {
		this.userId = userId;
		this.userPassword = userPassword;
		this.email = email;
		this.nickname = nickname;
		this.memo = memo;
		this.createdBy = createdBy;
		this.modifiedBy = createdBy;
	}

	public static UserAccount of(String userId, String userPassword, String email, String nickname, String memo) {
		return UserAccount.of(userId, userPassword, email, nickname, memo, null);
	}

	public static UserAccount of(String userId, String userPassword, String email, String nickname, String memo, String createdBy) {
		return new UserAccount(userId, userPassword, email, nickname, memo, createdBy);
	}


	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (!(o instanceof UserAccount that))
			return false;
		return this.getUserId() != null && Objects.equals(this.getUserId(), that.userId);
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(this.getUserId());
	}
}
