package com.proiect.licenta.service;

import com.proiect.licenta.exception.ResourceNotFoundException;
import com.proiect.licenta.model.LoginResponse;
import com.proiect.licenta.model.Role;
import com.proiect.licenta.model.User;
import com.proiect.licenta.repository.RoleRepository;
import com.proiect.licenta.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private EmailService emailService;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    private ConfirmationTokenService confirmationTokenService;

    public LoginResponse login(User user) {
        return authenticationService.authenticateUser(user)
                .orElseThrow(() -> new ResourceNotFoundException(user.getUsername()));
    }

    public User register(User user) throws MessagingException {

        user.setRegistrationDate(LocalDateTime.now());
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        manageRoles(user);

        var repoUser = userRepository.save(user);

        var token = userDetailsService.handleConfirmationToken(repoUser);

        var link = "http://localhost:8090/api/user/confirm?token=" + token;

        emailService.send(repoUser.getEmail(), buildEmail( repoUser.getUsername(),
                "Confirm your email",
                " Thank you for registering. Please click on the below link to activate your account: ",
                link,
                "Activate Now",
                "\n Link will expire in 15 minutes. ",
                "See you soon"));

        return repoUser;
    }

    public LoginResponse updateUserDetails(User user) {

        var actualUser = userRepository.findById(user.getId())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                String.format("User with username: %s, was not found", user.getUsername())));

        actualUser.setUsername(user.getUsername());
        actualUser.setEmail(user.getEmail());
        actualUser.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        actualUser.setFirstname(user.getFirstname());
        actualUser.setLastname(user.getLastname());
        var updatedUser = userRepository.save(actualUser);

        var jwtToken = authenticationService.updateSecurityContext(user);

        var loginResponse = new LoginResponse();
        loginResponse.setUser(updatedUser);
        loginResponse.setToken(jwtToken);

        return loginResponse;
    }

    public boolean logout() {

        return authenticationService.logout();
    }

    public String confirmToken(String token, boolean isPasswordRecoveryToken) {

        var confirmationToken = confirmationTokenService.getToken(token)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Token with value %s not found!", token)));

        if (confirmationToken.getConfirmedAt() != null) {

            throw new ResourceNotFoundException("Email already confirmed!");
        }

        var expiredAt = confirmationToken.getExpiresAt();

        if (expiredAt.isBefore(LocalDateTime.now())) {

            throw new ResourceNotFoundException("Token expired!");
        }

        confirmationTokenService.setConfirmedAt(token);

        if (!isPasswordRecoveryToken) {

            userDetailsService.enableUser(confirmationToken.getUser().getEmail());
        }

        return "confirmed";
    }

    public String sendPasswordRecoveryMail(String email) throws MessagingException {

        var user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException(email));

        var token = userDetailsService.handleConfirmationToken(user);

        emailService.send(email, buildEmail(user.getUsername(),
                "Password recovery",
                "Glad you contacted us. Please use the token below to reset your password: ",
                "",
                "Reset Now: " + token,
                "\n Token will expire in 15 minutes. ",
                "Good lock to you"));

        return user.getUsername();
    }

    public void updatePassword(User user) {

        userDetailsService.updatePassword(user.getUsername(),
                bCryptPasswordEncoder.encode(user.getPassword()));
    }

    public User getUserDetails() {

        return authenticationService.getCurrentUser();
    }

    public User refreshUser() {

        return authenticationService.refreshCurrentUser();
    }

    private void manageRoles(User user) throws ResourceNotFoundException {

        if (user.getRoles() == null) {

            var userRole = new Role();
            userRole.setName("USER");
            userRole.setUser(user);

            user.setRoles(new HashSet<>());
            user.getRoles().add(userRole);
        }
        else {

            var userRoles = new HashSet<Role>();

            for (var role : user.getRoles()) {

                role.setUser(user);
                userRoles.add(role);
            }

            user.setRoles(userRoles);
        }
    }

    private String buildEmail(String name,
                              String title,
                              String textStrip1,
                              String link,
                              String textStrip2,
                              String textStrip3,
                              String textStrip4) {

        return "<div style=\"font-family:Helvetica,Arial,sans-serif;font-size:16px;margin:0;color:#0b0c0c\">\n" +
                "\n" +
                "<span style=\"display:none;font-size:1px;color:#fff;max-height:0\"></span>\n" +
                "\n" +
                "  <table role=\"presentation\" width=\"100%\" style=\"border-collapse:collapse;min-width:100%;width:100%!important\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\">\n" +
                "    <tbody><tr>\n" +
                "      <td width=\"100%\" height=\"53\" bgcolor=\"#0b0c0c\">\n" +
                "        \n" +
                "        <table role=\"presentation\" width=\"100%\" style=\"border-collapse:collapse;max-width:580px\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" align=\"center\">\n" +
                "          <tbody><tr>\n" +
                "            <td width=\"70\" bgcolor=\"#0b0c0c\" valign=\"middle\">\n" +
                "                <table role=\"presentation\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse\">\n" +
                "                  <tbody><tr>\n" +
                "                    <td style=\"padding-left:10px\">\n" +
                "                  \n" +
                "                    </td>\n" +
                "                    <td style=\"font-size:28px;line-height:1.315789474;Margin-top:4px;padding-left:10px\">\n" +
                "                      <span style=\"font-family:Helvetica,Arial,sans-serif;font-weight:700;color:#ffffff;text-decoration:none;vertical-align:top;display:inline-block\">" +  title + "</span>\n" +
                "                    </td>\n" +
                "                  </tr>\n" +
                "                </tbody></table>\n" +
                "              </a>\n" +
                "            </td>\n" +
                "          </tr>\n" +
                "        </tbody></table>\n" +
                "        \n" +
                "      </td>\n" +
                "    </tr>\n" +
                "  </tbody></table>\n" +
                "  <table role=\"presentation\" class=\"m_-6186904992287805515content\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse;max-width:580px;width:100%!important\" width=\"100%\">\n" +
                "    <tbody><tr>\n" +
                "      <td width=\"10\" height=\"10\" valign=\"middle\"></td>\n" +
                "      <td>\n" +
                "        \n" +
                "                <table role=\"presentation\" width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse\">\n" +
                "                  <tbody><tr>\n" +
                "                    <td bgcolor=\"#1D70B8\" width=\"100%\" height=\"10\"></td>\n" +
                "                  </tr>\n" +
                "                </tbody></table>\n" +
                "        \n" +
                "      </td>\n" +
                "      <td width=\"10\" valign=\"middle\" height=\"10\"></td>\n" +
                "    </tr>\n" +
                "  </tbody></table>\n" +
                "\n" +
                "\n" +
                "\n" +
                "  <table role=\"presentation\" class=\"m_-6186904992287805515content\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse;max-width:580px;width:100%!important\" width=\"100%\">\n" +
                "    <tbody><tr>\n" +
                "      <td height=\"30\"><br></td>\n" +
                "    </tr>\n" +
                "    <tr>\n" +
                "      <td width=\"10\" valign=\"middle\"><br></td>\n" +
                "      <td style=\"font-family:Helvetica,Arial,sans-serif;font-size:19px;line-height:1.315789474;max-width:560px\">\n" +
                "        \n" +
                "            <p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\">Hi " + name + ",</p><p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\">" + textStrip1 + "</p><blockquote style=\"Margin:0 0 20px 0;border-left:10px solid #b1b4b6;padding:15px 0 0.1px 15px;font-size:19px;line-height:25px\"><p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\"> <a href=\"" + link + "\">" + textStrip2 +"</a> </p></blockquote>"+ textStrip3 +"<p>" + textStrip4 + "</p>" +
                "        \n" +
                "      </td>\n" +
                "      <td width=\"10\" valign=\"middle\"><br></td>\n" +
                "    </tr>\n" +
                "    <tr>\n" +
                "      <td height=\"30\"><br></td>\n" +
                "    </tr>\n" +
                "  </tbody></table><div class=\"yj6qo\"></div><div class=\"adL\">\n" +
                "\n" +
                "</div></div>";
    }
}
