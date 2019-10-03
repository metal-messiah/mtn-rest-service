package com.mtn.model.view;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mtn.constant.CrudAction;
import com.mtn.model.domain.Permission;
import com.mtn.model.simpleView.SimpleRoleView;
import com.mtn.model.simpleView.SimpleUserProfileView;

import java.util.List;
import java.util.stream.Collectors;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PermissionView extends AuditingEntityView {

	private String systemName;
	private String displayName;
	private String description;
	private String subject;
	private CrudAction action;

	private List<SimpleRoleView> roles;
	private List<SimpleUserProfileView> users;

	public PermissionView() {
	}

	public PermissionView(Permission permission) {
		super(permission);

		this.systemName = permission.getSystemName();
		this.displayName = permission.getDisplayName();
		this.description = permission.getDescription();
		this.subject = permission.getSubject();
		this.action = permission.getAction();
		this.roles = permission.getRoles().stream().filter(role -> role.getDeletedDate() == null).map(SimpleRoleView::new).collect(Collectors.toList());
		this.users = permission.getUsers().stream().filter(user -> user.getDeletedDate() == null).map(SimpleUserProfileView::new).collect(Collectors.toList());
	}

	public void setSystemName(String systemName) {
		this.systemName = systemName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public void setAction(CrudAction action) {
		this.action = action;
	}

	public void setRoles(List<SimpleRoleView> roles) {
		this.roles = roles;
	}

	public String getSystemName() {
		return systemName;
	}

	public String getDisplayName() {
		return displayName;
	}

	public String getDescription() {
		return description;
	}

	public String getSubject() {
		return subject;
	}

	public CrudAction getAction() {
		return action;
	}

	public List<SimpleRoleView> getRoles() {
		return roles;
	}

	public List<SimpleUserProfileView> getUsers() {
		return users;
	}

	public void setUsers(List<SimpleUserProfileView> users) {
		this.users = users;
	}
}
