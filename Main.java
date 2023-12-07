package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class Main {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        List<Integer> test = new ArrayList<>();
        test.add(2);
        test.add(453);
        test.add(6376);

        User user = new User();
        Server server = new Server();
        user.setNumbers(test);

        ExecutorService executor = Executors.newFixedThreadPool(5);
        Callable<Integer> callableOne = user::getNumber;
        while (!user.getNumbers().isEmpty()) {
            System.out.println("Коллекция клиента до удаления элемента: " + user.getNumbers());
            Future<Integer> futureOne = executor.submit(callableOne);
            Callable<Integer> callableTwo = () -> {
                return server.getListSize((Integer) futureOne.get());
            };
            Future<Integer> futureTwo = executor.submit(callableTwo);
            System.out.println("Элемент который удалили: " + futureOne.get());
            System.out.println("Размер коллекции после добавления элемента на сервер: " + futureTwo.get());
            System.out.println("Коллекция сервера: " + server.getNumbersResult());
            user.setAccumulator(user.getAccumulator() + futureTwo.get());
            System.out.println("accumulator = " + user.getAccumulator());
            System.out.println();
        }

        executor.shutdown();
        try {
            if (!executor.awaitTermination(800, TimeUnit.MILLISECONDS)) {
                executor.shutdownNow();
            }
        } catch (InterruptedException e) {
            executor.shutdownNow();
        }
    }
}


