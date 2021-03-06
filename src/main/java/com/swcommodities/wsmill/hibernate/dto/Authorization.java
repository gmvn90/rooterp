package com.swcommodities.wsmill.hibernate.dto;
// Generated Feb 18, 2014 3:38:53 PM by Hibernate Tools 3.2.1.GA

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Authorization generated by hbm2java
 */
@Entity
@Table(name = "authorization")
public class Authorization implements java.io.Serializable {

	private AuthorizationId id;
	private User user;
	private Page page;
	private Byte permission;

	public Authorization() {
	}

	public Authorization(AuthorizationId id, User user, Page page) {
		this.id = id;
		this.user = user;
		this.page = page;
	}

	public Authorization(AuthorizationId id, User user, Page page, Byte permission) {
		this.id = id;
		this.user = user;
		this.page = page;
		this.permission = permission;
	}

	@EmbeddedId

	@AttributeOverrides({ @AttributeOverride(name = "userId", column = @Column(name = "user_id", nullable = false)),
			@AttributeOverride(name = "pageId", column = @Column(name = "page_id", nullable = false)) })
	public AuthorizationId getId() {
		return this.id;
	}

	public void setId(AuthorizationId id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", nullable = false, insertable = false, updatable = false)
	@JsonIgnore
	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "page_id", nullable = false, insertable = false, updatable = false)
	public Page getPage() {
		return this.page;
	}

	public void setPage(Page page) {
		this.page = page;
	}

	@Column(name = "permission")
	public Byte getPermission() {
		return this.permission;
	}

	public void setPermission(Byte permission) {
		this.permission = permission;
	}

}
