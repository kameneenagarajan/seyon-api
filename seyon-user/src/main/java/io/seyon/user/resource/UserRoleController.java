package io.seyon.user.resource;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.seyon.user.entity.UserInfo;
import io.seyon.user.entity.UserRole;
import io.seyon.user.model.SeyonResponse;
import io.seyon.user.service.UserService;

@RestController
@RequestMapping(path = "/api/userrole")
public class UserRoleController {
	
	final String COMPANY_ID="x-company-id";
	final String USER_EMAIL_HEADER="x-user-email";

	@Autowired
	UserService userService;

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@RequestMapping(method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, path = "/adduserrole")
	public SeyonResponse addUserRole(@RequestParam(required = true) String email,
			@RequestParam(required = true) String roleCode) {
		return userService.addUserRole(email, roleCode);
	}

	@RequestMapping(method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
	public SeyonResponse deleteUserRole(@RequestParam(required = false) Long roleId) {
		return userService.deleteUserRole(roleId);
	}

	
	@RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<UserRole> getUserRoles(@RequestParam(required = true) String email) {
		return userService.getUserRoles(email);
	}
	
	@RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE,path = "/getRolesOpen")
	public List<UserRole> getUserRolesOpen(@RequestParam(required = true) String email) {
		return userService.getUserRoles(email);
	}
	
	@RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE,path="/authenticated")
	public List<String> getLoggedInUser(@RequestHeader(name = "x-user-email", required = true) String userId) {
		log.info("user Id"+userId);
		List<String> roleCodes=userService.getUserRoles(userId).stream()
				.map(role->role.getRoleCode()).collect(Collectors.toList());
		return roleCodes;
	}
}
