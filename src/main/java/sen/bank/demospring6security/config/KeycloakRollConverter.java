package sen.bank.demospring6security.config;

import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;

import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

public class KeycloakRollConverter implements Converter<Jwt, Collection<GrantedAuthority>> {

    /**
     * @param source the source object to convert, which must be an instance of {@code S} (never {@code null})
     * @return
     */
    @Override
    public Collection<GrantedAuthority> convert(Jwt source) {
//        Map<String, Object> realmAccess = (Map<String, Object>) source.getClaims().get("realm_access");
//        if (realmAccess == null || realmAccess.isEmpty()) {
//            return new ArrayList<>();
//        }
//
//        Collection<GrantedAuthority> returnValue = ((List<String>)realmAccess.get("roles"))
//                .stream().map(roleName -> "ROLE_" + roleName)
//                .map(SimpleGrantedAuthority::new)
//                .collect(Collectors.toList());
//        return returnValue;
//    }
        ArrayList<String> roles = (ArrayList<String>) source.getClaims().get("roles");
        if (roles == null || roles.isEmpty()) {
            return new ArrayList<>();
        }

        Collection<GrantedAuthority> returnValue = roles.stream().map(roleName -> "ROLE_" + roleName)
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
        return returnValue;
    }
}

