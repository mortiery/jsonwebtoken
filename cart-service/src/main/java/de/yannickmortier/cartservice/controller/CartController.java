package de.yannickmortier.cartservice.controller;

import de.yannickmortier.cartservice.domain.CartProduct;
import de.yannickmortier.cartservice.domain.User;
import de.yannickmortier.cartservice.dto.ProductDto;
import de.yannickmortier.cartservice.service.CartProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor(onConstructor = @__({@Autowired}))
@RequestMapping("/cart")
public class CartController {

    private final CartProductService cartProductService;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public List<CartProduct> addProductToCart(@AuthenticationPrincipal User user) {
        return cartProductService.findProductsForUser(user);
    }


    @RequestMapping(value = "", method = RequestMethod.POST)
    public ResponseEntity<CartProduct> addProductToCart(@Valid @RequestBody ProductDto productDto,
                                                        BindingResult bindingResult,
                                                        @AuthenticationPrincipal User user) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().build();
        }

        if(!cartProductService.productIsValidForUser(user, productDto.getId())) {
            return ResponseEntity.badRequest().build();
        }

        try {
            return  ResponseEntity.ok(cartProductService.addProductToCart(user.getId(), productDto.getId()));
        } catch (CartProductService.ProductAlreadyInCartException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @RequestMapping(value = "{productId}", method = RequestMethod.DELETE)
    public ResponseEntity addProductToCart(@PathVariable UUID productId,
                                              @AuthenticationPrincipal User user) {
        try {
            cartProductService.removeProductFromCart(user.getId(), productId);
        } catch (CartProductService.ProductNotInCartException e) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }

    @RequestMapping(value = "", method = RequestMethod.DELETE)
    public ResponseEntity emptyCart(@AuthenticationPrincipal User user) {
        cartProductService.emptyCartForUser(user);
        return ResponseEntity.noContent().build();
    }
}
