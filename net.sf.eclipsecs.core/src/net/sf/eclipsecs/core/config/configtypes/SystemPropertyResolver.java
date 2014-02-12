//============================================================================
//
// Copyright (C) 2002-2014  David Schneider, Lars K�dderitzsch
//
// This library is free software; you can redistribute it and/or
// modify it under the terms of the GNU Lesser General Public
// License as published by the Free Software Foundation; either
// version 2.1 of the License, or (at your option) any later version.
//
// This library is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
// Lesser General Public License for more details.
//
// You should have received a copy of the GNU Lesser General Public
// License along with this library; if not, write to the Free Software
// Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
//
//============================================================================

package net.sf.eclipsecs.core.config.configtypes;

import com.puppycrawl.tools.checkstyle.PropertyResolver;

/**
 * Property resolver implementation that resolves system properties.
 * 
 * @author Lars K�dderitzsch
 */
public class SystemPropertyResolver implements PropertyResolver {

    /**
     * {@inheritDoc}
     */
    public String resolve(String property) {
        // get the system property
        return System.getProperty(property);
    }
}
