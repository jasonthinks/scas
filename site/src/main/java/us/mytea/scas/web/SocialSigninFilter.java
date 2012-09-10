package us.mytea.scas.web;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jasig.cas.client.authentication.AttributePrincipal;
import org.jasig.cas.client.util.AbstractCasFilter;
import org.jasig.cas.client.validation.Assertion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.social.oauth1.OAuthToken;

import us.mytea.scas.service.UserAccountService;
import us.mytea.social.OAuthAuthenticationToken;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.security.Principal;
import java.util.Arrays;
import java.util.Collection;

public class SocialSigninFilter implements Filter {
	
	private boolean ignoreCase;
	protected final Log log = LogFactory.getLog(getClass());
	
	private UserAccountService accountService = null;

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		if(SecurityContextHolder.getContext().getAuthentication() == null) {
			SecurityContextHolder.setStrategyName(SecurityContextHolder.MODE_GLOBAL);
			final AttributePrincipal principal = retrievePrincipalFromSessionOrRequest(request);
			CasHttpServletRequestWrapper req = new CasHttpServletRequestWrapper((HttpServletRequest)request, principal);
			UserDetails user = accountService.loadUserByUsername(req.getRemoteUser());
			if(user == null) {
				throw new ServletException("");
			}
			Collection<GrantedAuthority> authorities = Arrays.asList(new GrantedAuthority[] {new SimpleGrantedAuthority("ROLE_USER")});
			OAuthToken token = null;
			String providerAccountId = req.getRemoteUser().substring(req.getRemoteUser().indexOf("#") + 1);
			String serviceProviderId = req.getRemoteUser().substring(0, req.getRemoteUser().indexOf("#"));;
			OAuthAuthenticationToken authenticate = new OAuthAuthenticationToken(user, authorities, token, providerAccountId, serviceProviderId);
			SecurityContextHolder.getContext().setAuthentication(authenticate);	
		}
		chain.doFilter(request, response);
	}
	protected AttributePrincipal retrievePrincipalFromSessionOrRequest(final ServletRequest servletRequest) {
        final HttpServletRequest request = (HttpServletRequest) servletRequest;
        final HttpSession session = request.getSession(false);
        final Assertion assertion = (Assertion) (session == null ? request.getAttribute(AbstractCasFilter.CONST_CAS_ASSERTION) : session.getAttribute(AbstractCasFilter.CONST_CAS_ASSERTION));

        return assertion == null ? null : assertion.getPrincipal();
    }
	@Override
	public void destroy() {

	}
	final class CasHttpServletRequestWrapper extends HttpServletRequestWrapper {

        private final AttributePrincipal principal;

        CasHttpServletRequestWrapper(final HttpServletRequest request, final AttributePrincipal principal) {
            super(request);
            this.principal = principal;
        }

        public Principal getUserPrincipal() {
            return this.principal;
        }

        public String getRemoteUser() {
            return principal != null ? this.principal.getName() : null;
        }

//        public boolean isUserInRole(final String role) {
//            if (CommonUtils.isBlank(role)) {
//                log.debug("No valid role provided.  Returning false.");
//                return false;
//            }
//
//            if (this.principal == null) {
//                log.debug("No Principal in Request.  Returning false.");
//                return false;
//            }
//
//            if (CommonUtils.isBlank(roleAttribute)) {
//                log.debug("No Role Attribute Configured. Returning false.");
//                return false;
//            }
//
//            final Object value = this.principal.getAttributes().get(roleAttribute);
//            
//            if (value instanceof Collection<?>) {
//                for (final Object o : (Collection<?>) value) {
//                    if (rolesEqual(role, o)) {
//                        log.debug("User [" + getRemoteUser() + "] is in role [" + role + "]: " + true);
//                        return true;
//                    }
//                }
//            }
//
//            final boolean isMember = rolesEqual(role, value);
//            log.debug("User [" + getRemoteUser() + "] is in role [" + role + "]: " + isMember);
//            return isMember;
//        }
        
        /**
         * Determines whether the given role is equal to the candidate
         * role attribute taking into account case sensitivity.
         *
         * @param given  Role under consideration.
         * @param candidate Role that the current user possesses.
         *
         * @return True if roles are equal, false otherwise.
         */
        private boolean rolesEqual(final String given, final Object candidate) {
            return ignoreCase ? given.equalsIgnoreCase(candidate.toString()) : given.equals(candidate);
        }
        
        
    }
}