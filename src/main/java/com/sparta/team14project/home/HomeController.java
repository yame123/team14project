package com.sparta.team14project.home;

import com.sparta.team14project.user.entity.UserRoleEnum;
import com.sparta.team14project.user.login.security.UserDetailsImpl;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HomeController {

    @GetMapping("/")
    public String home() {
        return "index";
    }

    @GetMapping("/user/login-page")
    public String loginPage() {
        return "login"; // 로그인 페이지의 뷰 이름 (templates 디렉토리 아래의 HTML 파일 이름)
    }

    @GetMapping("/user/signup")
    public String signupPage() {
        return "signup"; // 로그인 페이지의 뷰 이름 (templates 디렉토리 아래의 HTML 파일 이름)
    }

    @GetMapping("/user/store/cud") // 상점 수정 및 삭제
    public String CUDStore() {
        return "store/storeCUD";
    }

    @GetMapping("/user/store") // 모든 상점 + 상점별 리뷰
    public String getStores() {
        return "store/allStoreInfo";
    }

    @GetMapping("/user/store-rank") // 상점 매출액 순위
    public String getStoresRank() {
        return "store/storeRanking";
    }

    @GetMapping("/user/store/store-detail/{storeId}")
    public String getStore(@PathVariable Long storeId, Model model) {
        // storeId를 이용하여 상점 정보를 조회하거나 다른 작업을 수행
        // 조회한 정보를 모델에 추가
        model.addAttribute("storeId", storeId);

        return "store/storeDetail";
    }

    @GetMapping("/user/menu/cud") // 상점 수정 및 삭제
    public String CUDMenu() {
        return "menu/menuCUD";
    }

    @GetMapping("/user/menu") // 메뉴 상세 정보 조회
    public String showMenuDetails() {
        return "menu/menuDetails";
    }

    @GetMapping("/api/user-info")
    @ResponseBody
    public UserInfoDto getUserInfo(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        Long userid = userDetails.getUser().getId();
        String username = userDetails.getUser().getUsername();
        UserRoleEnum role = userDetails.getUser().getUserRole();
        boolean isOwner = (role == UserRoleEnum.OWNER);

        return new UserInfoDto(userid,username, isOwner);
    }
  
    @GetMapping("/user/cart")
    public String userCartPage() {
        return "cart";
    }
    @GetMapping("/user/order")
    public String userOrderPage() {
        return "userorder";
    }
    @GetMapping("/user/review")
    public String userReviewPage() {
        return "review"; // - 이거 그냥 한데 박을수 있지 않을까
    }


    @GetMapping("/owner/order")
    public String ownerOrderPage() {
        return "ownerorder";
    }

}
