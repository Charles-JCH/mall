package com.mall.concurrent;

import lombok.Data;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class AsyncThread {
    public static void main(String[] args) {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(5, 10, 30,
                TimeUnit.SECONDS, new SynchronousQueue<>());
        asyncThread(executor);
    }

    public static void asyncThread(ThreadPoolExecutor executor) {
        CompletableFuture<User> future = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            User u = new User();
            u.setName("张三");
            u.setFirstName("张");
            u.setLastName("三");
            return u;
        }, executor).exceptionally((e) -> {
            User user = new User();
            user.setName("失败");
            user.setFirstName("失败");
            user.setLastName("失败");
            return user;
        });

        CompletableFuture<String> future2 = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(1500);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            return "123456";
        }, executor).exceptionally(e -> "失败");

        CompletableFuture<String> future3 = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            return "123456@qq.com";
        }, executor).exceptionally(e -> "失败");

        future.thenCombine(future2, (user, password) -> {
            user.setPassword(password);
            return user;
        }).thenCombine(future3, (user, email) -> {
            user.setEmail(email);
            return user;
        }).thenAcceptAsync((user) -> {
            // userservice.save(user);
            System.out.println(user);
        }).whenComplete((user, e) -> {
            executor.shutdown();
        });
        System.out.println("成功");
    }
}

@Data
class User {
    private String name;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
}
