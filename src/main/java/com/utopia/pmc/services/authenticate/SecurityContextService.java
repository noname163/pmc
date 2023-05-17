package com.utopia.pmc.services.authenticate;

import com.utopia.pmc.data.entities.User;

public interface SecurityContextService {
    public void setSecurityContext(String username);

    public User getCurrentUser();
}
