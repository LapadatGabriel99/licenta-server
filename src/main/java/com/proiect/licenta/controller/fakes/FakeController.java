package com.proiect.licenta.controller.fakes;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/fake")
public class FakeController {

    @GetMapping("/allow-all")
    public String allowAll() {

        return "This route can be accessed by anyone";
    }

    @GetMapping("/allow-only-authenticated")
    public String allowOnlyAuthenticated() {

        return "This route can be accessed only by authenticated users";
    }

    @GetMapping("/allow-only-role-user-basic")
    @PreAuthorize("hasAuthority('USER')")
    public String allowOnlyRoleUserBasic() {

        return "This route can only be accessed by users that have basic authority";
    }

    @GetMapping("/allow-only-role-user-player")
    @PreAuthorize("hasAuthority('PLAYER')")
    public String allowOnlyRoleUserPlayer() {

        return "This route can only be accessed by users that have player authority";
    }

    @GetMapping("/allow-only-admin")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String allowOnlyRoleAdmin() {

        return "This route can only be accessed by admin";
    }
}
