package com.sparta.team14project.order.entity;

import com.sparta.team14project.store.entity.Store;
import com.sparta.team14project.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Table(name = "cart")
@NoArgsConstructor
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id")
    private Store store;

    @OneToMany(mappedBy = "cart")
    private List<AddedMenu> addedMenuList = new ArrayList<>();

    public Cart(User user) {
        this.user = user;
    }

    public void resetStore(){
        this.store = null;
    }

    public void updateStore(Store store) {
        this.store = store;
    }
    public void addMenu(AddedMenu addedMenu){
        this.addedMenuList.add(addedMenu);
    }

    public void removeMenu(AddedMenu addedMenu) {
        this.addedMenuList.remove(addedMenu);
    }
}
