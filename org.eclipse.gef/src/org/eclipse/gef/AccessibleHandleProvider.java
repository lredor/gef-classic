/*******************************************************************************
 * Copyright (c) 2000, 2024 IBM Corporation and others.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.gef;

import java.util.List;

import org.eclipse.draw2d.geometry.Point;

/**
 * This class provides keyboard accessibility support for the {@link Handle
 * Handles} of the primary-selected {@link EditPart}. Handles are grabbable
 * regions on the screen. Typically the user will grab a handle by pressing and
 * holding the mouse button. This function must also be available via the
 * keyboard for accessibility reasons. Even mouse Users will appreciate the
 * fine-grained control offered by the keyboard for operations like "nudging".
 * <P>
 * The SelectionTool is the primary client for Handles. When it is active, the
 * PERIOD keystroke will cycle through the list of Accessible locations (Points)
 * supplied by the current EditPart. The "current EditPart" is defined as the
 * EditPart with primary selection.
 */

public interface AccessibleHandleProvider {

	/**
	 * Returns a list of Points in <B>absolute</B> coordinates where {@link Handle
	 * Handles} are located. {@link Tool Tools} that work with Handles should use
	 * these locations when operating in accessible keyboard modes.
	 *
	 * @return A list of absolute locations (Points relative to the Viewer's
	 *         Control); cannot be <code>null</code>
	 */
	List<Point> getAccessibleHandleLocations();

}
