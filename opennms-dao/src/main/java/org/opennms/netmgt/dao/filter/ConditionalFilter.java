/*******************************************************************************
 * This file is part of OpenNMS(R).
 *
 * Copyright (C) 2009-2012 The OpenNMS Group, Inc.
 * OpenNMS(R) is Copyright (C) 1999-2012 The OpenNMS Group, Inc.
 *
 * OpenNMS(R) is a registered trademark of The OpenNMS Group, Inc.
 *
 * OpenNMS(R) is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published
 * by the Free Software Foundation, either version 3 of the License,
 * or (at your option) any later version.
 *
 * OpenNMS(R) is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with OpenNMS(R).  If not, see:
 *      http://www.gnu.org/licenses/
 *
 * For more information contact:
 *     OpenNMS(R) Licensing <license@opennms.org>
 *     http://www.opennms.org/
 *     http://www.opennms.com/
 *******************************************************************************/

package org.opennms.netmgt.dao.filter;

import org.apache.commons.lang.builder.ToStringBuilder;


/**
 * <p>Abstract ConditionalFilter class.</p>
 *
 * @author ranger
 * @version $Id: $
 * @since 1.8.1
 */
public abstract class ConditionalFilter implements Filter {
    /** Constant <code>TYPE="conditionalFilter"</code> */
    public static final String TYPE = "conditionalFilter";
    
    private String m_conditionType;
    private Filter[] m_filters;
    
    /**
     * <p>Constructor for ConditionalFilter.</p>
     *
     * @param conditionType a {@link java.lang.String} object.
     * @param filters a {@link Filter} object.
     */
    public ConditionalFilter(String conditionType, Filter... filters){
        if (filters.length == 0) {
            throw new IllegalArgumentException("You must pass at least one filter");
        }
        m_conditionType = conditionType;
        m_filters = filters;
    }
    
    /**
     * <p>getFilters</p>
     *
     * @return an array of {@link Filter} objects.
     */
    public Filter[] getFilters() {
        return m_filters;
    }

    /**
     * <p>getDescription</p>
     *
     * @return a {@link java.lang.String} object.
     */
    @Override
    public String getDescription() {
        if (m_filters.length == 1) {
            return m_filters[0].getDescription();
        }
        
        StringBuilder buf = new StringBuilder(TYPE);
        buf.append("=");
        buf.append(m_conditionType);
        for(Filter filter : m_filters) {
            buf.append('(');
            buf.append(filter.getDescription());
            buf.append(')');
        }
        return buf.toString();
    }

    /**
     * <p>getTextDescription</p>
     *
     * @return a {@link java.lang.String} object.
     */
    @Override
    public String getTextDescription() {
        if (m_filters.length == 1) {
            return m_filters[0].getTextDescription();
        }
        
        StringBuilder buf = new StringBuilder("( ");
        for(int i = 0; i < m_filters.length; i++){
            if (i != 0) {
                buf.append(m_conditionType);
                buf.append(" ");
            }
            buf.append(m_filters[i].getTextDescription());
        }
        buf.append(")");
        return buf.toString();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .append("description", getDescription())
            .append("text description", getTextDescription())
            .toString();
    }

}