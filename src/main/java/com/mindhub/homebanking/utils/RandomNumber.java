package com.mindhub.homebanking.utils;

import org.springframework.stereotype.Component;

@Component
public class RandomNumber {
    public static int getRandomNumber (int min, int max){
        return (int) ((Math.random() * (max - min)) + min);
    }
}
