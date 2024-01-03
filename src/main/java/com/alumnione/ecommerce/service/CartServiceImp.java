package com.alumnione.ecommerce.service;

import com.alumnione.ecommerce.dto.CartReturnDto;
import com.alumnione.ecommerce.entity.Cart;
import com.alumnione.ecommerce.entity.Cellphone;
import com.alumnione.ecommerce.repository.CartRepository;
import com.alumnione.ecommerce.repository.CellphoneRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CartServiceImp implements CartService {
    private final CartRepository cartRepository;
    private final CellphoneRepository cellRepository;

    @Override
    public ResponseEntity<CartReturnDto> get(long id) {
        Cart cart = getEntity(cartRepository, id);
        if (cart == null) return notFound("Cart");

        return ResponseEntity.ok(new CartReturnDto(cart));
    }

    @Transactional(rollbackOn = Exception.class)
    @Override
    public ResponseEntity<CartReturnDto> deleteAll(long id) {
        Cart cart = getEntity(cartRepository, id);
        if (cart == null) return notFound("Cart");

        cart.getCellphones().clear();
        cartRepository.save(cart);
        return ResponseEntity.ok(new CartReturnDto(cart));
    }

    @Transactional(rollbackOn = Exception.class)
    @Override
    public ResponseEntity<CartReturnDto> addItem(long cartId, long cellId) {
        Cart cart = getEntity(cartRepository, cartId);
        Cellphone cell = getEntity(cellRepository, cellId);
        if (cart == null) return notFound("Cart");
        else if (cell == null) return notFound("Cellphone");

        List<Cellphone> list = cart.getCellphones();
        int index = list.indexOf(cell);
        if (index != -1) {
            cell.setQuantity(cell.getQuantity() + 1);
            list.set(index, cell);
        } else {
            cell.setQuantity(1);
            list.add(cell);
        }

        cart.setLastUpdated(LocalDateTime.now());
        cartRepository.save(cart);
        return ResponseEntity.ok(new CartReturnDto(cart));
    }

    @Transactional(rollbackOn = Exception.class)
    @Override
    public ResponseEntity<CartReturnDto> deleteItem(long id, long cellId) {
        Cart cart = getEntity(cartRepository, id);
        if (cart == null) return notFound("Cart");

        List<Cellphone> list = cart.getCellphones();
        Cellphone cell = list.stream()
                .filter(c -> c.getId().equals(cellId))
                .findFirst().orElse(null);
        if (cell == null) return notFound("Cellphone");

        list.remove(cell);
        cart.setLastUpdated(LocalDateTime.now());
        cartRepository.save(cart);
        return ResponseEntity.ok(new CartReturnDto(cart));
    }

    private <E> E getEntity(JpaRepository<E, Long> jpaRepository, long id) {
        return jpaRepository.findById(id).orElse(null);
    }

    private ResponseEntity<CartReturnDto> notFound(String entity) {
        return ResponseEntity.notFound().header("message", entity + " id not found").build();
    }

    //TODO: make this thing works
    private ResponseEntity<CartReturnDto> entityNotFound(String message, Object entity) {
        if (entity == null) return ResponseEntity.notFound().header("message", message).build();
        return null;
    }
}
