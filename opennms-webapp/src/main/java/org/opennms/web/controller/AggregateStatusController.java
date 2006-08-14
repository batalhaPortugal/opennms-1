//
// This file is part of the OpenNMS(R) Application.
//
// OpenNMS(R) is Copyright (C) 2006 The OpenNMS Group, Inc.  All rights reserved.
// OpenNMS(R) is a derivative work, containing both original code, included code and modified
// code that was published under the GNU General Public License. Copyrights for modified 
// and included code are below.
//
// OpenNMS(R) is a registered trademark of The OpenNMS Group, Inc.
//
// Original code base Copyright (C) 1999-2001 Oculan Corp.  All rights reserved.
//
// This program is free software; you can redistribute it and/or modify
// it under the terms of the GNU General Public License as published by
// the Free Software Foundation; either version 2 of the License, or
// (at your option) any later version.
//
// This program is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
// GNU General Public License for more details.
//
// You should have received a copy of the GNU General Public License
// along with this program; if not, write to the Free Software
// Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA 02111-1307, USA.
//
// For more information contact:
// OpenNMS Licensing       <license@opennms.org>
//     http://www.opennms.org/
//     http://www.opennms.com/
//

package org.opennms.web.controller;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.opennms.web.svclayer.AggregateStatus;
import org.opennms.web.svclayer.AggregateStatusService;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

/**
 * Controller servlet for presenting aggregate (propogated) status of nodes
 * using a list of aggregate status definitions (container object for lists
 * of categories).
 * 
 * @author david
 *
 */
public class AggregateStatusController extends AbstractController {

    private static final int FIVE_MINUTES = 5*60;
    
    private static AggregateStatusService m_service;

    public AggregateStatusController() {
        setSupportedMethods(new String[] {METHOD_GET});
        setCacheSeconds(FIVE_MINUTES);
    }
    
    public void setService(AggregateStatusService svc) {
        m_service = svc;
    }
    
    @Override
    protected ModelAndView handleRequestInternal(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        ModelAndView mav = new ModelAndView("aggregateStatus");
        String statusView = req.getParameter("statusView");
        Collection<AggregateStatus> aggrStati = m_service.createAggregateStatusView(statusView);
        mav.addObject("stati", aggrStati);
        return mav;
    }

}
