package prosjektGruppe5.Utilities;

import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;

@Service
public class SessionUtil {
    public boolean ValidateSession(HttpSession session) {
        return session != null && session.getAttribute("user") != null;
    }

}
