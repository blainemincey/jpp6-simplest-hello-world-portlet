/*
 * JBoss, Home of Professional Open Source
 * Copyright 2010, Red Hat Middleware LLC, and individual contributors
 * by the @authors tag. See the copyright.txt in the distribution for a
 * full listing of individual contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.jboss.portal.portlet.samples;

import java.io.IOException;
import java.io.PrintWriter;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.GenericPortlet;
import javax.portlet.PortletException;
import javax.portlet.PortletMode;
import javax.portlet.PortletPreferences;
import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.WindowState;

/**
 * The simplest posible Portlet.
 * 
 * @author Peter Palaga
 */
public class SimplestHelloWorldPortlet extends GenericPortlet {

	@Override
	public void processAction(ActionRequest request, ActionResponse response)
			throws PortletException {

		String displayText = request.getParameter("DISPLAYTEXT");

		try {
			PortletPreferences pref = request.getPreferences();
			pref.setValue("displaytext", displayText);
			pref.store();
		} catch (Exception e) {
			throw new PortletException(e.getMessage());
		}
		// return the user back to the view mode and normal state
		response.setPortletMode(PortletMode.VIEW);
		response.setWindowState(WindowState.NORMAL);
	}

	@Override
	public void doView(RenderRequest request, RenderResponse response)
			throws IOException {
		PortletPreferences pref = request.getPreferences();

		String displayText = pref.getValue("displaytext",
				"MISSING: display-text");

		PrintWriter writer = response.getWriter();
		writer.write(displayText);
		writer.close();
	}

	@Override
	public void doEdit(RenderRequest request, RenderResponse response)
			throws PortletException, IOException {
		PortletURL actionURL = response.createActionURL();
		response.setContentType(request.getResponseContentType());
		PrintWriter writer = response.getWriter();
		writer.print("<form method=\"post\" action=\"" + actionURL.toString());
		writer.println("\">");
		writer.println("<center><p>DisplayText: <input type=\"text\" name=\"DISPLAYTEXT\"></p>");
		writer.println("<input type=\"submit\" value=\"Submit\">");
		writer.println("</form>");
	}
}
