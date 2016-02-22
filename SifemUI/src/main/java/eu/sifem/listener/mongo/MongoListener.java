package eu.sifem.listener.mongo;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.mongodb.MongoClient;

import eu.sifem.utils.CommandLineTools;

/**
 * Application Lifecycle Listener implementation class MongoListener
 *
 */
public class MongoListener implements ServletContextListener {

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        MongoClient mongo = (MongoClient) sce.getServletContext()
                            .getAttribute("MONGO_CLIENT");
        mongo.close();
        System.out.println("MongoClient closed successfully");
    }
 
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        try {
        	new CommandLineTools().runCommand("mongod --dbpath=C:/SifemWindowsResourceFiles/dataset/appconfig/db");
            ServletContext ctx = sce.getServletContext();
            MongoClient mongo = new MongoClient(ctx.getInitParameter("MONGODB_HOST"), 
            Integer.parseInt(ctx.getInitParameter("MONGODB_PORT")));
            System.out.println("MongoClient initialized successfully");
            sce.getServletContext().setAttribute("MONGO_CLIENT", mongo);
        } catch (Exception e) {
            throw new RuntimeException("MongoClient init failed: "+e.getMessage());
        }
    }
	
}
