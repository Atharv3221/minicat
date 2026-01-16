///////////////////////////////////////////////////////////////////////////////////////////////
// MIT License
//
// Copyright (c) 2026 Atharv Chavan
//
// Permission is hereby granted, free of charge, to any person obtaining a copy
// of this software and associated documentation files (the "Software"), to deal
// in the Software without restriction, including without limitation the rights
// to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
// copies of the Software, and to permit persons to whom the Software is
// furnished to do so, subject to the following conditions:

// The above copyright notice and this permission notice shall be included in all
// copies or substantial portions of the Software.

// THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
// IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
// FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
// AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
// LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
// OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
// SOFTWARE.
///////////////////////////////////////////////////////////////////////////////////////////////

package io.github.atharv3221.minicat.servlet;

import java.util.Enumeration;

/**
 * A servlet configuration object used by a servlet container to pass
 * information to a servlet during initialization.
 */
public interface ServletConfig {

    /**
     * Returns the name of this servlet instance. The name may be provided via
     * server administration, assigned in the web application deployment descriptor,
     * or for an unregistered (and thus unnamed) servlet instance it will be the
     * servlet's class name.
     *
     * @return the name of servlet instance
     */
    String getServletName();

    /**
     * Returns the {@link ServletContext} for the web application in which this
     * servlet is running.
     *
     * <p>The {@code ServletContext} provides access to application-wide resources
     * and configuration information, and allows servlets to interact with the
     * servlet container and share data with other components such as servlets,
     * filters, and listeners.</p>
     *
     * <p>There is exactly one {@code ServletContext} per web application, and it
     * is shared by all components deployed within that application.</p>
     *
     * @return the {@code ServletContext} associated with this servlet
     *
     * @see ServletContext
     */
    ServletContext getServletContext();

    /**
     * Gets the value of the initialization parameter with the given name.
     *
     * @param name the name of the initialization parameter whose
     *     value to get
     *
     * @return a {@code String} containing the value of the initialization parameter,
     *     or {@code null} if the initialization parameter does not exist
     */
    String getInitParameter(String name);

    /**
     * Returns the names of the servlet's initialization parameters as an {@code Enumeration}
     * of {@code String} objects, or an empty {@code Enumeration} if the servlet has no
     * initialization paramters.
     *
     * @return an {@code Enumeration} of {@code String} objects containing the names of the the
     *     servlet's initialization parameters.
     */
    Enumeration<String> getInitParameterNames();
}
