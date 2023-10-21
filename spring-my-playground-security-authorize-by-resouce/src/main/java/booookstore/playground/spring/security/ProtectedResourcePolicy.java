package booookstore.playground.spring.security;

import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.access.intercept.RequestAuthorizationContext;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.function.Supplier;

@Component
public class ProtectedResourcePolicy {

    private final Map<String, String> permissions = Map.of(
            "bookstore1", "1",
            "bookstore2", "2"
    );

    public AuthorizationDecision isPermitted(Supplier<Authentication> authentication, RequestAuthorizationContext context) {
        var id = permissions.get(authentication.get().getName());
        return new AuthorizationDecision(id.equals(context.getVariables().get("id")));
    }

}
