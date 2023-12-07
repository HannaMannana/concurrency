package org.example;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Data
public class Server {
    private List<Integer> numbersResult = new ArrayList<>();

    public Server() {
    }

    public Server(List<Integer> numbersResult) {

        this.numbersResult = numbersResult;
    }

    public Integer getListSize(Integer number) throws InterruptedException {
        int rand = ThreadLocalRandom.current().nextInt(100, 1000);
        Thread.sleep(rand);
        numbersResult.add(number);
        System.out.println("Имя потока в методе getListSize: " + Thread.currentThread().getName());
        return numbersResult.size();

    }


}
