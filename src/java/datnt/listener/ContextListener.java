/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datnt.listener;

import datnt.ultis.PropertiesFileHelper;
import java.io.File;
import java.util.Properties;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import org.apache.log4j.PropertyConfigurator;

/**
 * Web application lifecycle listener.
 *
 * @author NTD
 */
@WebListener("application context listener")
public class ContextListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent event) {
        ServletContext context = event.getServletContext();
        String log4jConfigFile = context.getInitParameter("log4j-config-location");
        String fullPath = context.getRealPath("/") + File.separator + log4jConfigFile;
//        System.out.println(fullPath);
        System.setProperty("PATH", context.getRealPath("/"));
        PropertyConfigurator.configure(fullPath);

        String siteMapLocation = context.getInitParameter("SITEMAP_PROPERTIES_FILE_LOCATION");
        Properties siteMapProperty = PropertiesFileHelper.getProperties(context, siteMapLocation);
        context.setAttribute("SITE_MAP", siteMapProperty);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {

    }
}
