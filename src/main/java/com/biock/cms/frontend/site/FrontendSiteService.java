package com.biock.cms.frontend.site;

import com.biock.cms.frontend.site.dto.LoginDTO;
import com.biock.cms.page.PageRepository;
import com.biock.cms.security.JwtService;
import com.biock.cms.site.Site;
import com.biock.cms.site.SiteRepository;
import com.biock.cms.user.User;
import com.biock.cms.user.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class FrontendSiteService {

    private final SiteRepository siteRepository;
    private final PageRepository pageRepository;
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    public FrontendSiteService(
            final SiteRepository siteRepository,
            final PageRepository pageRepository,
            final UserRepository userRepository,
            final JwtService jwtService,
            final PasswordEncoder passwordEncoder) {

        this.siteRepository = siteRepository;
        this.pageRepository = pageRepository;
        this.userRepository = userRepository;
        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder;
    }

    public String getDefaultLanguageOfSite(final String siteId) {

        return this.siteRepository.getDefaultLanguageOfSite(siteId);
    }

    public Optional<Site> getSite(final String siteId) {

        final Optional<Site> site = this.siteRepository.findSiteById(siteId);
        site.ifPresent(s -> s.buildNavigation(this.pageRepository));
        return site;
    }

    public Optional<LoginDTO> login(final String siteId, final String username, final String password) {

        final Optional<User> user = this.userRepository.getUserByEmail(siteId, username);
        if (user.isPresent() && this.passwordEncoder.matches(password, user.get().getPassword())) {
            return Optional.of(LoginDTO.of(this.jwtService.generateToken(user.get()), user.get()));
        }
        return Optional.empty();
    }

    public String encode(final String password) {

        return this.passwordEncoder.encode(password);
    }
}
