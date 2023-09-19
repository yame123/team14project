package com.sparta.team14project.aop;

import com.sparta.team14project.menu.entity.Menu;
import com.sparta.team14project.menu.repository.MenuRepository;
import com.sparta.team14project.store.entity.Store;
import com.sparta.team14project.store.repository.StoreRepository;
import com.sparta.team14project.user.entity.UserRoleEnum;
import com.sparta.team14project.user.login.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Slf4j(topic = "StoreMenuAop")
@Aspect
@Component
@RequiredArgsConstructor
public class StoreMenuAop {

    private final MenuRepository menuRepository;

    private final StoreRepository storeRepository;

    @Pointcut("execution(* com.sparta.team14project.store.StoreController.createStore(..)) " +
            "|| execution(* com.sparta.team14project.store.StoreController.updateStore(..)) " +
            "|| execution(* com.sparta.team14project.store.StoreController.deleteStore(..)) " +
            "|| execution(* com.sparta.team14project.store.StoreController.deliveryCheck(..)) " +
            "|| execution(* com.sparta.team14project.store.StoreController.deliveryDone(..)) " +
            "|| execution(* com.sparta.team14project.menu.MenuController.createMenu(..)) " +
            "|| execution(* com.sparta.team14project.menu.MenuController.updateMenu(..)) " +
            "|| execution(* com.sparta.team14project.menu.MenuController.deleteMenu(..))")
    private void allStoreAndMenuOperations() {}
    @Pointcut("execution(* com.sparta.team14project.store.StoreController.updateStore(..))")
    private void updateStore() {}
    @Pointcut("execution(* com.sparta.team14project.store.StoreController.deleteStore(..))")
    private void deleteStore() {}
    @Pointcut("execution(* com.sparta.team14project.store.StoreController.deliveryCheck(..))")
    private void deliveryCheck() {}
    @Pointcut("execution(* com.sparta.team14project.store.StoreController.deliveryDone(..))")
    private void deliveryDone() {}
    @Pointcut("execution(* com.sparta.team14project.menu.MenuController.updateMenu(..))")
    private void updateMenu() {}
    @Pointcut("execution(* com.sparta.team14project.menu.MenuController.deleteMenu(..))")
    private void deleteMenu() {}

    // 메인 메서드들
    @Before("allStoreAndMenuOperations()")
    public void checkCurrentUserOwner() {
        UserRoleEnum currentUserRole = getCurrentUserRole();

        if(currentUserRole != UserRoleEnum.OWNER) {
            throw new IllegalArgumentException("접근 권한이 없습니다.");
        }
    }

    @Before("updateStore() || deleteStore() || deliveryCheck()")
    public void checkStoreMine(JoinPoint joinPoint) {
        Long currentUserId = getCurrentUserId();

        Long storeId = getObjectId(joinPoint);

        if (!isStoreMine(storeId, currentUserId)) {
            throw new IllegalArgumentException("당신의 Store가 아닙니다.");
        }
    }

    @Before("updateMenu() || deleteMenu()")
    public void checkMenuMine(JoinPoint joinPoint) {
        Long currentUserId = getCurrentUserId();

        Long menuId = getObjectId(joinPoint);

        Menu menu = menuRepository.findById(menuId).orElseThrow(() ->
                new IllegalArgumentException("존재하지 않는 메뉴입니다."));
        Long storeId = menu.getStore().getId();
        if (!isStoreMine(storeId, currentUserId)) {
            throw new IllegalArgumentException("당신의 Store가 아닙니다.");
        }
    }


    // 보조 메서드들
    private boolean isStoreMine(Long storeId, Long userId) {
        Store store = storeRepository.findStoreById(storeId);
        return userId.equals(store.getUser().getId());
    }

    private Long getObjectId(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        Long objectId = 0L;
        if (args.length > 0) {
            Object arg = args[0];
            if (arg instanceof Long) {
                objectId = (Long) arg;
            }
        }
        return objectId;
    }

    private Long getCurrentUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        return userDetails.getUser().getId();
    }

    private UserRoleEnum getCurrentUserRole() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        return userDetails.getUser().getUserRole();
    }

}
