package com.officerschool.courselottery.web.filter;

import com.auth0.jwt.interfaces.Claim;
import com.officerschool.courselottery.common.Utils.JwtUtil;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

/**
 * @author : create by xiangwenchao@zhejianglab.com
 * @version : v1.0
 * @date : 2024/4/3
 */

@WebFilter(filterName = "jwtFilter", urlPatterns = "/*")
public class JwtFilter implements Filter {
    private static final Set<String> ALLOWED_PATHS = Collections.unmodifiableSet(new HashSet<>(Arrays.asList("/api/user/login")));

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        final HttpServletRequest request = (HttpServletRequest) servletRequest;
        final HttpServletResponse response = (HttpServletResponse) servletResponse;

        String path = request.getRequestURI().substring(request.getContextPath().length()).replaceAll("[/]+$", "");
        boolean allowedPath = ALLOWED_PATHS.contains(path);

        if (allowedPath) {
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }

        response.setCharacterEncoding("UTF-8");
        //获取 header里的token
        final String token = request.getHeader("authorization");

        if ("OPTIONS".equals(request.getMethod())) {
            response.setStatus(HttpServletResponse.SC_OK);
            filterChain.doFilter(request, response);
        } else {
            if (token == null) {
                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");
                response.getWriter().write("{\"code\":401, \"msg\":\"token not found\"}");
                return;
            }
            Map<String, Claim> userData = JwtUtil.verifyToken(token);
            if (userData == null) {
                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");
                response.getWriter().write("{\"code\":401, \"msg\":\"token invalid\"}");
                return;
            }
            String id = userData.get("id").asString();
            String name = userData.get("name").asString();
            String phone = userData.get("phone").asString();
            Integer roleId = userData.get("roleId").asInt();
            //拦截器 拿到用户信息，放到request中
            request.setAttribute("id", id);
            request.setAttribute("name", name);
            request.setAttribute("phone", phone);
            request.setAttribute("roleId", roleId);
            try {
//                UserDO userDO = userMapper.selectById(id);
//                if (userDO == null || !token.equals(userDO.getToken())) {
//                    response.setContentType("application/json");
//                    response.setCharacterEncoding("UTF-8");
//                    response.getWriter().write("{\"code\":401, \"msg\":\"token invalid\"}");
//                    return;
//                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            filterChain.doFilter(servletRequest, servletResponse);
        }
    }
}
