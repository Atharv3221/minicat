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

import java.io.IOException;

/**
 * Defines methods that all servlets must implement.
 *
 * <p>A servlet is a small Java program that runs within a Web server. Servlets receive and respond
 * to requests from Web clients, usually across HTTP, the HyperText Transfer Protocol.</p>
 *
 */
public interface Servlet {

    /**
     * Called by container to indicate to a servlet that the servlet is being placed into service..
     *
     * <p>The servlet container calls the init method exactly once after instantiating the servlet.
     * This method should be completed successfully before the servlet receive any requests.
     * </p>
     *
     * @param config object containing the servlet's configuration and initialization parameters.
     * @throws ServletException if an exception has occurred that interferes with the servlet's
     *     normal operation.
     */
    void init(ServletConfig config) throws ServletException;

    /**
     * Returns a {@link ServletConfig} object, which contains initialization and startup
     * parameters for this servlet.
     *
     * @return {@code ServletConfig} object that initializes the servlet.
     */
    ServletConfig getServletConfig();

    /**
     * Handles a client request and generates a response.
     *
     * <p>This method is called automatically by the servlet container
     * (such as Tomcat or Jetty) whenever a request is made to the servlet.
     * It is invoked only after the servlet has been successfully initialized
     * by the {@code init()} method.</p>
     *
     * <p>The {@link ServletRequest} object contains data sent by the client,
     * such as request parameters and input streams. The {@link ServletResponse}
     * object is used to send data back to the client, such as HTML, JSON,
     * headers, and status codes.</p>
     *
     * <p>Servlet containers may call this method concurrently for multiple
     * requests. Since a single servlet instance can serve many requests at
     * the same time, any shared data (instance variables, files, or network
     * resources) must be accessed in a thread-safe manner.</p>
     *
     * <p>If an error occurs while processing the request, the servlet should
     * set an appropriate response status code or send an error response.</p>
     *
     * @param request the request object containing information sent by the client
     * @param response the response object used to send data back to the client
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an input or output error occurs
     */
    void service(ServletRequest request, ServletResponse response) throws ServletException,
            IOException;

    /**
     * Returns information about the servlet, such as author, version, and copyright.
     *
     * <p>The string that this method returns should be plain text and not markup of any
     * kind (such as HTML, XML, etc.).
     * </p>
     *
     * @return a{@code String} containing servlet information
     */
    String getServletInfo();

    /**
     * Called by the servlet container to indicate to a servlet that the servlet is being
     * taken out of service. This method is only called once all threads within the servlet's
     * {@code service} method have exited or after a timeout period has passed. After the
     * servlet container calls this method, it will not call the {@code service} method again
     * on this servlet.
     *
     * <p>This method gives cleans up any resources that are being held (for example, memory, file
     * handlers, threads) and make sure that any persistent state is synchronized with the
     * servlet's current state in memory.
     * </p>
     */
    void destroy();
}
