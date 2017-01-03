package com.coreservices;

import com.coreservices.servlets.ProfileServlet;
import com.coreservices.utils.RpcLogger;
import io.swagger.servlet.config.DefaultServletConfig;
import io.swagger.servlet.listing.ApiDeclarationServlet;
import org.apache.log4j.Logger;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

/**
 * Created by janet on 1/3/17.
 */
public class Main {
    private static Logger logger = Logger.getLogger(Main.class.getSimpleName());
    public static RpcLogger loggerService;


    public static void main(String[] args){
        Properties properties = new Properties();
        loggerService = new RpcLogger();
        try {
            properties.load(new FileInputStream("src/main/resources/swaggertest.properties"));
            Server server = initializeServer(properties);
            server.start();
            server.join();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static Server initializeServer(Properties properties){
        Server server = new Server(Integer.parseInt(properties.getProperty(Config.JETTY_SERVICE_PORT)));
        ServletContextHandler servletContextHandler = new ServletContextHandler(ServletContextHandler.SESSIONS);
        servletContextHandler.setContextPath("/");
        server.setHandler(servletContextHandler);

        //Custom servlet
        ServletHolder apiservlet = servletContextHandler.addServlet(ProfileServlet.class, "/user/*");
        apiservlet.setInitOrder(3);
        logger.info("User profile server initialized.");

        // Swagger servlet reader
        ServletHolder swaggerServlet = servletContextHandler.addServlet(DefaultServletConfig.class, "/swagger-core");
        swaggerServlet.setInitOrder(2);
        swaggerServlet.setInitParameter("api.version", "1.0.0");
        //swaggerServlet.setInitParameter("swagger.resource.package", "com.coreservices.servlets,com.coreservices.datatypes");
        swaggerServlet.setInitParameter("swagger.resource.package","com.coreservices.servlets");
        swaggerServlet.setInitParameter("swagger.api.basepath", "http://localhost:7000");

        // Swagger api declaration
        servletContextHandler.addServlet(ApiDeclarationServlet.class, "/api/*");
        return server;
    }
}
