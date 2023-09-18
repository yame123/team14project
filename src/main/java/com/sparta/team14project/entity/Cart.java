package com.sparta.team14project.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "store_id")
    private Store store;

    @OneToMany(mappedBy = "cart",cascade = {CascadeType.PERSIST, CascadeType.REMOVE},orphanRemoval = true,fetch=FetchType.EAGER)
    private List<AddedMenu> addedMenuList ;

    public Cart(User user) {
        this.user = user;
    }

    public void resetCart(){
        this.store = null;
        this.addedMenuList.removeAll(this.addedMenuList);
    }
}
