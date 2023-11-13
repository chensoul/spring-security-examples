package com.chensoul.security.threadcontext.controller;

import com.chensoul.security.threadcontext.service.AsyncService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ExecutionException;

@RestController
class WebController {

    Logger log = LoggerFactory.getLogger(WebController.class);

    @Autowired
    AsyncService asyncService;

    @GetMapping(value = "/async")
    public void executeWithInternalThread() throws Exception {

        log.info("In executeWithInternalThread - before call: "
                + SecurityContextHolder.getContext().getAuthentication().getName());

        asyncService.checkIfPrincipalPropagated();

        log.info("In executeWithInternalThread - after call: "
                + SecurityContextHolder.getContext().getAuthentication().getName());
    }

    @GetMapping(value = "/asyncRunnable")
    public void executeWithExternalThread_Runnable() {
        log.info("In executeWithExternalThread_Runnable: "
                + SecurityContextHolder.getContext().getAuthentication().getName());

        asyncService.checkIfPrincipalPropagatedToNewRunnable();
    }

    @GetMapping(value = "/asyncCallable")
    public void executeWithExternalThread_Callable() throws ExecutionException, InterruptedException {
        log.info("In executeWithExternalThread_Callable: "
                + SecurityContextHolder.getContext().getAuthentication().getName());

        asyncService.checkIfPrincipalPropagatedToNewCallable();
    }

    @GetMapping(value = "/asyncService")
    public void executeWithExternalThread_Service() throws ExecutionException, InterruptedException {
        log.info("In executeWithExternalThread_Service: "
                + SecurityContextHolder.getContext().getAuthentication().getName());

        asyncService.checkIfPrincipalPropagatedToExecutorService();
    }
}
