package com.bfaconsultora.webappserver;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletHandler;
import org.eclipse.jetty.webapp.WebAppContext;
import org.eclipse.jetty.webapp.Configuration;

import org.apache.commons.cli.*;

public class WebAppServer {
	public static void main(String[] args) throws Exception {
		Options options = new Options();
		
		Option warfileOption = new Option("war", true, "war file");
		warfileOption.setRequired(true);
		options.addOption(warfileOption);
		
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
			formatter.printHelp("java -jar webappserver-1.0.jar", options);

			System.exit(1);
			return;
		}
		
		int port;
		if (cmd.hasOption("port")) {
			port = Integer.parseInt(cmd.getOptionValue("port"));
		} else {
			port = 8080;
		}
			
		String warfile = cmd.getOptionValue("war");
		
		Server server = new Server(port);
		
		WebAppContext webapp = new WebAppContext();
		webapp.setContextPath("/");
		webapp.setWar(warfile);
		
		Configuration.ClassList classlist = Configuration.ClassList.setServerDefault(server);
		classlist.addBefore("org.eclipse.jetty.webapp.JettyWebXmlConfiguration", 
			"org.eclipse.jetty.annotations.AnnotationConfiguration" );
		
		webapp.setAttribute("org.eclipse.jetty.server.webapp.ContainerIncludeJarPattern",
			".*/[^/]*servlet-api-[^/]*\\.jar$|.*/javax.servlet.jsp.jstl-.*\\.jar$|.*/[^/]*taglibs.*\\.jar$" );
		
		server.setHandler(webapp);
		
		server.start();
		server.join();
    }
}
