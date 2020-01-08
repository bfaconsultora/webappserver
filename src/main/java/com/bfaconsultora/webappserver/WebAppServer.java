package com.bfaconsultora.webappserver;

import java.util.Properties;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.FilterHolder;
import org.eclipse.jetty.servlets.CrossOriginFilter;
import org.eclipse.jetty.webapp.WebAppContext;
import org.eclipse.jetty.webapp.Configuration;

import org.apache.commons.cli.*;

public class WebAppServer {
	public static void main(String[] args) throws Exception {
		final Properties properties = new Properties();
		properties.load(WebAppServer.class.getResourceAsStream("/project.properties"));

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

		Server server = new Server(port);
		server.setAttribute("org.eclipse.jetty.server.Request.maxFormContentSize", -1);

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

		Configuration.ClassList classlist = Configuration.ClassList.setServerDefault(server);
		classlist.addBefore("org.eclipse.jetty.webapp.JettyWebXmlConfiguration",
			"org.eclipse.jetty.annotations.AnnotationConfiguration" );

		FilterHolder filterHolder = new FilterHolder(CrossOriginFilter.class);
		
		filterHolder.setInitParameter(CrossOriginFilter.ALLOWED_ORIGINS_PARAM, "*");
		
		filterHolder.setInitParameter(CrossOriginFilter.ACCESS_CONTROL_ALLOW_ORIGIN_HEADER, "*");
		
		filterHolder.setInitParameter (
			CrossOriginFilter.ALLOWED_METHODS_PARAM, 
			"GET,POST,PATCH,PUT,DELETE,OPTIONS,HEAD"
		);
		
		filterHolder.setInitParameter (
			CrossOriginFilter.ALLOWED_HEADERS_PARAM, 
			"Authorization, X-Requested-With,Content-Type,Accept,Origin"
		);
		filterHolder.setName("cross-origin");

		webapp.addFilter(filterHolder, "*", null);
		
		server.setHandler(webapp);

		server.start();

		server.join();
    }
}
