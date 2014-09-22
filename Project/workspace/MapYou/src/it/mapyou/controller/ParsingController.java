/**
 * 
 */
package it.mapyou.controller;

import it.mapyou.controller.parsing.ParsingChatMessage;
import it.mapyou.controller.parsing.ParsingMapMe;
import it.mapyou.controller.parsing.ParsingMapping;
import it.mapyou.controller.parsing.ParsingNotification;
import it.mapyou.controller.parsing.ParsingPoint;
import it.mapyou.controller.parsing.ParsingUser;

/**
 * @author mapyou (mapyouu@gmail.com)
 *
 */
public class ParsingController implements Controller{
	
	
	private static ParsingController parser;
	protected ParsingUser parsingUser;
	protected ParsingMapMe parsingMapme;
	protected ParsingChatMessage parsingChatMessage;
	protected ParsingPoint parsingPoint;
	protected ParsingMapping parsingMapping;
	protected ParsingNotification parsingNotification;
		
	 
	private ParsingController() {
		 
	}
	public static ParsingController getParser() {
		if(parser==null)
			return parser= new ParsingController();
		return parser;
	}
	
	
	public ParsingUser getUserParser(){
		if(parsingUser==null)
			return parsingUser = new ParsingUser();
		return parsingUser;
	}
	
	public ParsingMapMe getMapmeParser(){
		if(parsingMapme==null)
			return parsingMapme = new ParsingMapMe();
		return parsingMapme;
	}
	
	public ParsingChatMessage getChatMessageParser(){
		if(parsingChatMessage==null)
			return parsingChatMessage = new ParsingChatMessage();
		return parsingChatMessage;
	}
	
	public ParsingPoint getPointParser(){
		if(parsingPoint==null)
			return parsingPoint = new ParsingPoint();
		return parsingPoint;
	}
	
	public ParsingMapping getMappingParser(){
		if(parsingMapping==null)
			return parsingMapping = new ParsingMapping();
		return parsingMapping;
	}
	
	public ParsingNotification getNotificationParser(){
		if(parsingNotification==null)
			return parsingNotification = new ParsingNotification();
		return parsingNotification;
	}
	/* (non-Javadoc)
	 * @see it.mapyou.controller.Controller#init(java.lang.Object[])
	 */
	@Override
	public void init(Object... parameters) throws Exception {
		// TODO Auto-generated method stub
		
	}
	/* (non-Javadoc)
	 * @see it.mapyou.controller.Controller#isInitialized()
	 */
	@Override
	public boolean isInitialized() throws Exception {
		// TODO Auto-generated method stub
		return true;
	}
	
	

}
