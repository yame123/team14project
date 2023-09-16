package com.sparta.team14project.dto;

import com.sparta.team14project.entity.Cart;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToOne;

import java.awt.*;

public class AddedMenuResponseDto {


    private  Menu menu;

    private Long count;

}
