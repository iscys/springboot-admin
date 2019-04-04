package com.project.config.resolve;

import com.project.config.annotation.SessionAttr;
import org.springframework.core.MethodParameter;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class SessionAttrArgumentResolve implements HandlerMethodArgumentResolver {
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
//得到参数的类型
        Class<?> paramType = parameter.getParameterType();

        //参数是否有注解
        //  if (parameter.hasParameterAnnotation(RequestParam.class)) {

        // }
        SessionAttr sessionAttribute = parameter.getParameterAnnotation(SessionAttr.class);
        if(null==sessionAttribute)
            return false;

        else
            return true;
    }

    /**
     * 先从注解的value 中寻找session value
     * 如果为空，那么就对应参数名寻找session value
     * @param parameter
     * @param mavContainer
     * @param webRequest
     * @param binderFactory
     * @return
     * @throws Exception
     */
    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        SessionAttr sessionAttribute = parameter.getParameterAnnotation(SessionAttr.class);
        //参数名称
        String parameterName = parameter.getParameterName();
        String value = sessionAttribute.value();//定义的session key
        HttpServletRequest http = webRequest.getNativeRequest(HttpServletRequest.class);
        HttpSession session = http.getSession();
        Object attribute;
        attribute = session.getAttribute(value);
        if(ObjectUtils.isEmpty(attribute))
            attribute =session.getAttribute(parameterName);
        return attribute;
    }
}
