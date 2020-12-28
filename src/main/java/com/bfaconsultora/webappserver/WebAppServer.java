package com.bfaconsultora.webappserver;

import java.util.Properties;

import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.servlet.FilterHolder;
import org.eclipse.jetty.servlets.CrossOriginFilter;
import org.eclipse.jetty.webapp.WebAppContext;

import org.apache.commons.cli.*;

public class WebAppServer {
	public static void main(String[] args) throws Exception {
		final Properties properties = new Properties();
		properties.load(WebAppServer.class.getResourceAsStream("/project.properties"));

		System.out.println();
		System.out.println("Basisty, Fuentes & Assoc.");
		System.out.println("We turn your ideas into Code...");
		System.out.println("http://www.basisty.com");
		System.out.println("info@basisty.com");
		System.out.println();
		System.out.println("This is open source software and it comes with ABSOLUTELY NO WARRANTY.");
		System.out.println("For licencing information please visit https://www.gnu.org/licenses/gpl-3.0.html");

		System.out.println("\nwebappserver version " + properties.getProperty("version") + " starting...\n");
		Options options = new Options();

		Option warfileOption = new Option("war", true, "war file");
		warfileOption.setRequired(false);
		options.addOption(warfileOption);

		Option dirOption = new Option("dir", true, "webapp root directory");
		dirOption.setRequired(false);
		options.addOption(dirOption);

		Option portOption = new Option("port", true, "port");
		portOption.setRequired(false);
		options.addOption(portOption);

		Option ipOption = new Option("ip", true, "ip address");
		ipOption.setRequired(false);
		options.addOption(ipOption);

		CommandLineParser parser = new DefaultParser();
		HelpFormatter formatter = new HelpFormatter();
		CommandLine cmd;

		try {
			cmd = parser.parse(options, args);
		} catch (ParseException e) {
			System.out.println(e.getMessage());
			formatter.printHelp("webappserver", options);

			System.exit(1);
			return;
		}

		int port;
		if(cmd.hasOption("port")) {
			port = Integer.parseInt(cmd.getOptionValue("port"));
		} else {
			port = 8080;
		}

		String ip;
		if(cmd.hasOption("ip")) {
			ip = cmd.getOptionValue("ip");
		} else {
			ip = "0.0.0.0";
		}

		Server server = new Server();
		server.setAttribute("org.eclipse.jetty.server.Request.maxFormContentSize", -1);
                                     
	    ServerConnector connector=new ServerConnector(server);
    	connector.setPort(port);
    	connector.setHost(ip);        
    	server.setConnectors(new Connector[]{connector});

		WebAppContext webapp = new WebAppContext();
		webapp.setContextPath("/");

		if(cmd.hasOption("war")) {
			webapp.setWar(cmd.getOptionValue("war"));
		} else if(cmd.hasOption("dir")) {
			webapp.setResourceBase(cmd.getOptionValue("dir"));
			webapp.setParentLoaderPriority(true);
		} else {
			formatter.printHelp("java -jar webappserver.jar", options);

			System.exit(1);
			return;
		}

		FilterHolder filterHolder = new FilterHolder(CrossOriginFilter.class);
		
		filterHolder.setInitParameter(CrossOriginFilter.ALLOWED_ORIGINS_PARAM, "*");
		
		filterHolder.setInitParameter(CrossOriginFilter.ACCESS_CONTROL_ALLOW_ORIGIN_HEADER, "*");
		
		filterHolder.setInitParameter (
			CrossOriginFilter.ALLOWED_METHODS_PARAM, 
			"GET,POST,PATCH,PUT,DELETE,OPTIONS,HEAD"
		);
		
		filterHolder.setInitParameter (
			CrossOriginFilter.ALLOWED_HEADERS_PARAM, 
			"*"
		);

		filterHolder.setName("cross-origin");

		webapp.addFilter(filterHolder, "*", null);
		
		server.setHandler(webapp);

		server.start();

		server.join();
    }
}
