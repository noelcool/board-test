package noel.example.board.config.resolver;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    private final AdminResolver adminResolver;
    private final UserResolver userResolver;

    public WebConfig(AdminResolver adminResolver, UserResolver userResolver) {
        this.adminResolver = adminResolver;
        this.userResolver = userResolver;
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(adminResolver);
        resolvers.add(userResolver);
    }

}
