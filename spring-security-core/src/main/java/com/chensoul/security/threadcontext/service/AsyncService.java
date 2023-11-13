package com.chensoul.security.threadcontext.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.concurrent.DelegatingSecurityContextCallable;
import org.springframework.security.concurrent.DelegatingSecurityContextExecutorService;
import org.springframework.security.concurrent.DelegatingSecurityContextRunnable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
public class AsyncService {
    Logger log = LoggerFactory.getLogger(AsyncService.class);

    @Async
    public void checkIfPrincipalPropagated() {
        String username = SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();

        log.info("In checkIfPrincipalPropagated: "
                + username);
    }

    public void checkIfPrincipalPropagatedToNewRunnable() {
        Runnable task = () -> {
            String username = SecurityContextHolder.getContext()
                    .getAuthentication()
                    .getName();

            log.info("In checkIfPrincipalPropagatedToNewRunnable inside Runnable: "
                    + username);
        };

        ExecutorService e = Executors.newCachedThreadPool();
        try {
            Runnable contextTask = new DelegatingSecurityContextRunnable(task);
            e.submit(contextTask);
        } finally {
            e.shutdown();
        }
    }

    public void checkIfPrincipalPropagatedToNewCallable()
            throws ExecutionException, InterruptedException {
        Callable<String> task = () -> {
            String username = SecurityContextHolder.getContext()
                    .getAuthentication()
                    .getName();
            log.info("In checkIfPrincipalPropagatedToNewCallable inside Callable: "
                    + username);
            return username;
        };
        ExecutorService e = Executors.newCachedThreadPool();
        try {
            Callable contextTask = new DelegatingSecurityContextCallable<>(task);
            e.submit(contextTask);
        } finally {
            e.shutdown();
        }
    }

    public void checkIfPrincipalPropagatedToExecutorService() {
        Runnable task = () -> {
            String username = SecurityContextHolder.getContext()
                    .getAuthentication()
                    .getName();
            log.info("In checkIfPrincipalPropagatedToExecutorService inside " +
                    "Runnable: "
                    + username);
        };
        ExecutorService e = Executors.newCachedThreadPool();
        e = new DelegatingSecurityContextExecutorService(e);
        try {
            e.submit(task);
        } finally {
            e.shutdown();
        }
    }
}

