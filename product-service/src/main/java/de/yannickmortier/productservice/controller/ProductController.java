package de.yannickmortier.productservice.controller;

import de.yannickmortier.productservice.domain.Product;
import de.yannickmortier.productservice.domain.User;
import de.yannickmortier.productservice.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor(onConstructor = @__({@Autowired}))
public class ProductController {

    private final ProductService productService;


    @RequestMapping("")
    public List<Product> getProducts(@AuthenticationPrincipal User user) {
        boolean isVerified = false;
        if (user != null) {
            isVerified = loggedInUserIsVerified();
        }

        return productService.getProducts(isVerified);
    }

    private boolean loggedInUserIsVerified() {
        Collection<GrantedAuthority> authorities = (Collection<GrantedAuthority>)SecurityContextHolder.getContext().getAuthentication().getAuthorities();
        return authorities.stream().anyMatch(authority -> authority.getAuthority().equals("ROLE_VERIFIED"));
    }

}
