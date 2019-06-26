package com.dri.filter;



import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.dri.properties.SecurityProperties;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

public class DriFilter extends ZuulFilter {
	
	private static final Logger log = LoggerFactory.getLogger(DriFilter.class);

	@Autowired
	private SecurityProperties securityProperties;

	@Override
	public boolean shouldFilter() {
		RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();
        if(request.getRequestURI().endsWith("/oauth/token")) {
        	return false;
        }
		return true;
	}

	@Override
	public Object run() throws ZuulException {
		RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();
        Object authorizationInfo = request.getHeader("Authorization");
        if (authorizationInfo==null){
        	log.info("Authorization info is empty!");
        	ctx.setSendZuulResponse(false);
        	ctx.setResponseStatusCode(401);
        	ctx.setResponseBody("Authorization info is empty");
            return null;
        }
        String token = StringUtils.substringAfter(request.getHeader("Authorization"), "Bearer ");
		try {
			Claims claims = Jwts.parser().setSigningKey(
					securityProperties.getOauth2().getJwtSigningKey().getBytes("UTF-8")).
					parseClaimsJws(token.toString()).getBody();
			long expirationTime = claims.getExpiration().getTime();
			long currentTime = System.currentTimeMillis();
			if(expirationTime>currentTime) {
				log.info("令牌当前有效! expirationTime:"+expirationTime+"===>currentTime:"+currentTime);
			}
			
			String dateExpiration = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(claims.getExpiration());
			
			log.info("Expirate date:"+dateExpiration);
			log.info("authorities:"+claims.get("authorities"));
			log.info("client_id:"+claims.get("client_id"));
		
		} catch (ExpiredJwtException e) {
			log.info("抱歉====>令牌已过期。。。。。");
			ctx.setSendZuulResponse(false);
        	ctx.setResponseStatusCode(401);
        	ctx.setResponseBody("jwt token is Expired");
        	return null;
		} catch (UnsupportedJwtException e) {
			e.printStackTrace();
		} catch (MalformedJwtException e) {
			e.printStackTrace();
		} catch (SignatureException e) {
			log.info("JWT signature does not match locally computed signature.");
			ctx.setSendZuulResponse(false);
        	ctx.setResponseStatusCode(401);
        	ctx.setResponseBody("JWT validity cannot be asserted and should not be trusted.");
        	return null;
		} catch (IllegalArgumentException e) {
			log.info("JWT String argument cannot be null or empty");
			ctx.setSendZuulResponse(false);
        	ctx.setResponseStatusCode(401);
        	ctx.setResponseBody("JWT String argument cannot be null or empty");
        	return null;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
        log.info("access token is ok");
        return null;
	}

	/**
	 * filterType：返回一个字符串代表过滤器的类型，在zuul中定义了四种不同生命周期的过滤器类型，
	 * 具体如下：自定义过滤器的实现，需要继承ZuulFilter，需要重写实现下面四个方法
	 * 	pre：可以在请求被路由之前调用
	 *	routing：在路由请求时候被调用
	 *	post：在routing和error过滤器之后被调用
	 *	error：处理请求时发生错误时被调用
	 *	
	 *	filterOrder：通过int值来定义过滤器的执行顺序
	 *	shouldFilter：返回一个boolean类型来判断该过滤器是否要执行，所以通过此函数可实现过滤器的开关。在上例中，我们直接返回true，所以该过滤器总是生效。
	 * 
	 */
	@Override
	public String filterType() {
		// TODO Auto-generated method stub
		return "pre";
	}

	@Override
	public int filterOrder() {
		// TODO Auto-generated method stub
		return 0;
	}

}
