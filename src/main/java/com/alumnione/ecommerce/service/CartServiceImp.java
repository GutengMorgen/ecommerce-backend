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
        entityNotFound(cart);
        if (cart == null)
            return ResponseEntity.notFound().build();
//        return ResponseEntity.notFound().header("message","item not found").build();

        return ResponseEntity.ok(new CartReturnDto(cart));
    }

    private ResponseEntity<CartReturnDto> entityNotFound(Object entity) {
        if (entity == null)
            return ResponseEntity.notFound().build();
        return null;
    }

    @Transactional(rollbackOn = Exception.class)
    @Override
    public ResponseEntity<CartReturnDto> deleteAll(long id) {
        Cart cart = getEntity(cartRepository, id);
        if (cart == null)
            return ResponseEntity.notFound().build();

        cart.getCellphones().clear();
        cartRepository.save(cart);
        return ResponseEntity.ok(new CartReturnDto(cart));
    }

    @Transactional(rollbackOn = Exception.class)
    @Override
    public ResponseEntity<CartReturnDto> addItem(long cartId, long cellId) {
        //TODO: only add if no exits in the current list
        //TODO: update the datetime when a item is add or delete
        Cart cart = getEntity(cartRepository, cartId);
        Cellphone cell = getEntity(cellRepository, cellId);
        if(cart == null || cell == null)
            return ResponseEntity.notFound().build();

        if (cart.getCellphones().contains(cell))
            //TODO: modify the quantity of the item
            cart.getCellphones().set(cart.getCellphones().indexOf(cell), cell);
        else
            cart.getCellphones().add(cell);

        cart.setLastUpdated(LocalDateTime.now());
        cartRepository.save(cart);
        return ResponseEntity.ok(new CartReturnDto(cart));
    }

    //FIX: validate the index instead of the id of the cell
    @Transactional(rollbackOn = Exception.class)
    @Override
    public ResponseEntity<CartReturnDto> deleteItem(long id, short index) {
        Cart cart = getEntity(cartRepository, id);
        if(cart == null)
            return ResponseEntity.notFound().build();

        List<Cellphone> cellphones = cart.getCellphones();
        if(index < 0 || index >= cellphones.size())
            return ResponseEntity.notFound().build();

        cellphones.remove(index);
        cart.setLastUpdated(LocalDateTime.now());
        cartRepository.save(cart);
        return ResponseEntity.ok(new CartReturnDto(cart));
    }

    private <E> E getEntity(JpaRepository<E, Long> jpaRepository, long id) {
        return jpaRepository.findById(id).orElse(null);
    }
}
