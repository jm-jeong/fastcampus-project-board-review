package com.fastcampus.fastcampusprojectboardreview.domain;

import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@ToString(callSuper = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table
@Entity
public class Hashtag extends AuditingFields {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ToString.Exclude
	@ManyToMany(mappedBy = "hashtags")
	private Set<Article> articles = new LinkedHashSet<>();

	@Setter
	@Column(nullable = false)
	private String hashtagName;

	private Hashtag(String hashtagName) {
		this.hashtagName = hashtagName;
	}

	public static Hashtag of(String hashtagName) {
		return new Hashtag(hashtagName);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof Hashtag that)) return false;
		return this.getId() != null && this.getId().equals(that.getId());
	}

	@Override
	public int hashCode() {
		return Objects.hash(this.getId());
	}

}
