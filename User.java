package org.example;

import lombok.Data;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@Data
public class User {
    private List<Integer> numbers = new ArrayList<>();
    private Integer accumulator = 0;

    public User() {
    }

    public User(List<Integer> numbers, Integer accumulator) {
        this.numbers = numbers;
        this.accumulator = accumulator;
    }


    public Integer getNumber() throws InterruptedException{
        Lock lock = new ReentrantLock();
        try {
            lock.lock();
            Random random = SecureRandom.getInstanceStrong();
            int randIndex = random.nextInt(getNumbers().size());
            int number = getNumbers().get(randIndex);
            getNumbers().remove(randIndex);
            return number;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return 0;
        } finally {
            lock.unlock();
            int rand = ThreadLocalRandom.current().nextInt(100, 500);
            Thread.sleep(rand);
            System.out.println("Имя потока в методе getNumber: " + Thread.currentThread().getName());
        }
    }

}