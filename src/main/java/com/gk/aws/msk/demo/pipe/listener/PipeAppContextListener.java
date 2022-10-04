package com.gk.aws.msk.demo.pipe.listener;

//import jakarta.servlet.ServletContextEvent;
//import jakarta.servlet.ServletContextListener;
// import jakarta.servlet.annotation.WebListener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gk.aws.msk.demo.pipe.service.PipeStreamService;

// Using Component annotation works
// In previous version of spring boot starter (2.3), only the WebListener annotation was needed.
// See:  https://github.com/spring-projects/spring-boot/issues/25059
//@WebListener
@Component
public class PipeAppContextListener implements ServletContextListener {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(PipeAppContextListener.class);


	@Autowired
	private PipeStreamService pipeStreamService;

	public PipeAppContextListener() {

	}

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		LOGGER.info("Pipe Stream Listener started.");
		pipeStreamService.start();	
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		LOGGER.info("Pipe Stream Listener stopped.");
		pipeStreamService.stop();
	}	


}
	
	
