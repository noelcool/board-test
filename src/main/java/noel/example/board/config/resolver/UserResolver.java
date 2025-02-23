package noel.example.board.config.resolver;

import noel.example.board.exception.BusinessException;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import static noel.example.board.exception.ErrorCode.USER_NOT_FOUND;

@Component
public class UserResolver implements HandlerMethodArgumentResolver {

    private static final String X_USER_NO = "X_USER_NO";

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(User.class)
                && parameter.getParameterType().equals(Long.class);
    }

    @Override
    public Object resolveArgument(
            MethodParameter parameter,
            ModelAndViewContainer mavContainer,
            NativeWebRequest webRequest,
            WebDataBinderFactory binderFactory) {

        String userNo = webRequest.getHeader(X_USER_NO);
        if (userNo == null) {
            throw new BusinessException(USER_NOT_FOUND);
        }
        return Long.parseLong(userNo);
    }

}
