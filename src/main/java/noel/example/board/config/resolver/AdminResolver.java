package noel.example.board.config.resolver;

import noel.example.board.exception.BusinessException;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import static noel.example.board.exception.ErrorCode.ADMIN_NOT_FOUND;

@Component
public class AdminResolver implements HandlerMethodArgumentResolver {

    private static final String X_ADMIN_NO = "X_ADMIN_NO";

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(Admin.class)
                && parameter.getParameterType().equals(Long.class);
    }

    @Override
    public Object resolveArgument(
            MethodParameter parameter,
            ModelAndViewContainer mavContainer,
            NativeWebRequest webRequest,
            WebDataBinderFactory binderFactory) {

        String adminNo = webRequest.getHeader(X_ADMIN_NO);
        if (adminNo == null) {
            throw new BusinessException(ADMIN_NOT_FOUND);
        }
        return Long.parseLong(adminNo);
    }

}
