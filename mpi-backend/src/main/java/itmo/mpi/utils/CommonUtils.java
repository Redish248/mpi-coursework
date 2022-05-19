package itmo.mpi.utils;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class CommonUtils {
    public Authentication getCurrentUser() {
        return SecurityContextHolder.getContext().getAuthentication();
    }
}