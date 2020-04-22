package com.huijiewei.agile.serve.admin.aspect;

import com.huijiewei.agile.core.admin.entity.AdminLog;
import com.huijiewei.agile.core.admin.repository.AdminLogRepository;
import com.huijiewei.agile.core.until.HttpUtils;
import com.huijiewei.agile.serve.admin.security.AdminUserDetails;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

@Aspect
@Component
public class AdminLogAspect {
    private final AdminLogRepository adminLogRepository;

    @Autowired
    public AdminLogAspect(AdminLogRepository adminLogRepository) {
        this.adminLogRepository = adminLogRepository;
    }

    @Pointcut("@annotation(org.springframework.security.access.prepost.PreAuthorize)")
    public void preAuthorize() {
    }

    private void setAdminLog(AdminLog adminLog, ProceedingJoinPoint joinPoint) {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();

        StringBuilder queryString = new StringBuilder();

        if (requestAttributes != null) {
            HttpServletRequest request = requestAttributes.getRequest();

            String requestMethod = request.getMethod();

            adminLog.setType(requestMethod.equals("GET") ? AdminLog.TYPE_VISIT : AdminLog.TYPE_OPERATE);
            adminLog.setMethod(requestMethod);
            adminLog.setUserAgent(HttpUtils.getUserAgent(request));
            adminLog.setRemoteAddr(HttpUtils.getRemoteAddr(request));

            if (StringUtils.isNotEmpty(request.getQueryString())) {
                queryString.append(request.getQueryString());
            }
        }

        Class<?> joinPointClass = joinPoint.getTarget().getClass();
        Method joinPointMethod = ((MethodSignature) joinPoint.getSignature()).getMethod();

        adminLog.setAction(joinPointClass.getSimpleName() + "." + joinPointMethod.getName());

        Object[][] parameterAnnotations = joinPointMethod.getParameterAnnotations();
        Object[] args = joinPoint.getArgs();

        for (int i = 0; i < parameterAnnotations.length; i++) {
            Object[] parameterAnnotation = parameterAnnotations[i];

            for (Object object : parameterAnnotation) {
                Annotation annotation = (Annotation) object;

                if (annotation.annotationType().equals(PathVariable.class)) {
                    PathVariable pathVariableAnnotation = (PathVariable) annotation;

                    String queryName = pathVariableAnnotation.name();

                    if (StringUtils.isEmpty(queryName)) {
                        queryName = pathVariableAnnotation.value();
                    }

                    if (StringUtils.isNotEmpty(queryString)) {
                        queryString.append("&");
                    }

                    queryString
                            .append(queryName)
                            .append("=")
                            .append(args[i].toString());
                }
            }
        }

        adminLog.setParams(queryString.toString());
    }

    @Around(value = "preAuthorize()")
    public Object doAround(ProceedingJoinPoint joinPoint) throws Throwable {
        AdminLog adminLog = AdminUserDetails.getCurrentAdminIdentity().getAdmin().createLog();

        if (adminLog != null) {
            this.setAdminLog(adminLog, joinPoint);
        }

        try {
            if (adminLog != null) {
                adminLog.setStatus(AdminLog.STATUS_SUCCESS);
            }

            return joinPoint.proceed();
        } catch (Exception ex) {
            if (adminLog != null) {
                adminLog.setStatus(AdminLog.STATUS_FAIL);
                adminLog.setException(ex.getMessage());
            }

            throw ex;
        } finally {
            if (adminLog != null) {
                this.adminLogRepository.save(adminLog);
            }
        }
    }
}
