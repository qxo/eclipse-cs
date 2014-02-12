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

package net.sf.eclipsecs.core.projectconfig.filters;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;

/**
 * filters resources that lie within excluded packages. This filter is used for
 * the checkstyle audit funtion of this plugin.
 * 
 * @author Lars K�dderitzsch
 */
public class PackageFilter extends AbstractFilter {

    /**
     * Marker string in the filter data, if present the subpackes of a filtered
     * package are not recursivly excluded, but only the filtered package
     * itself.
     */
    public static final String RECURSE_OFF_MARKER = "<recurse=false>";

    private List<String> mData = new ArrayList<String>();

    private boolean mExcludeSubPackages = true;

    /**
     * {@inheritDoc}
     */
    public boolean accept(Object element) {

        boolean goesThrough = true;

        if (element instanceof IResource) {

            IResource resource = (IResource) element;

            IContainer folder = null;

            if (resource instanceof IContainer) {
                folder = (IContainer) resource;
            }
            else {
                folder = resource.getParent();
            }

            IPath projRelativPath = folder.getProjectRelativePath();

            int size = mData != null ? mData.size() : 0;
            for (int i = 0; i < size; i++) {

                String el = mData.get(i);

                if (RECURSE_OFF_MARKER.equals(el)) {
                    continue;
                }

                IPath filteredPath = new Path(el);
                if (mExcludeSubPackages && filteredPath.isPrefixOf(projRelativPath)) {
                    goesThrough = false;
                    break;
                }
                else if (!mExcludeSubPackages && filteredPath.equals(projRelativPath)) {
                    goesThrough = false;
                    break;
                }
            }
        }
        return goesThrough;
    }

    /**
     * {@inheritDoc}
     */
    public void setFilterData(List<String> filterData) {
        if (filterData == null) {
            mData = new ArrayList<String>();
        }

        mData = filterData;

        if (mData.contains(RECURSE_OFF_MARKER)) {
            mExcludeSubPackages = false;
        }
    }

    /**
     * {@inheritDoc}
     */
    public List<String> getFilterData() {
        return mData;
    }

    /**
     * {@inheritDoc}
     */
    public String getPresentableFilterData() {

        StringBuffer buf = new StringBuffer();

        int size = mData != null ? mData.size() : 0;
        for (int i = 0; i < size; i++) {
            if (i > 0) {
                buf.append(", "); //$NON-NLS-1$
            }

            buf.append(mData.get(i));
        }

        return buf.toString();
    }

    /**
     * {@inheritDoc}
     */
    public boolean equals(Object o) {

        if (o == null || !(o instanceof PackageFilter)) {
            return false;
        }
        if (this == o) {
            return true;
        }

        PackageFilter rhs = (PackageFilter) o;
        return new EqualsBuilder().appendSuper(super.equals(o)).append(mData, rhs.mData).isEquals();
    }

    /**
     * {@inheritDoc}
     */
    public int hashCode() {
        return new HashCodeBuilder(7834681, 1000003).appendSuper(super.hashCode()).append(mData)
                .toHashCode();
    }
}