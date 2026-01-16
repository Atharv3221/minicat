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

import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Set;

/**
 * Defines a set of methods that a servlet uses to communicate with its
 * servlet container, for example to obtain the MIME type of a file,
 * dispatch requests, or write to the application log.
 *
 * <p>There is one {@code ServletContext} per web application per Java
 * Virtual Machine. A web application is a collection of servlets and
 * content installed under a specific subset of the server's URL
 * namespace (such as {@code /catalog}) and typically packaged as a
 * {@code .war} file.</p>
 *
 * <p>If a web application is marked as {@code distributed} in its
 * deployment descriptor, a separate {@code ServletContext} instance
 * is created for each JVM. In this case, the context must not be used
 * to share global state, since the data will not be truly global.
 * An external shared resource such as a database should be used
 * instead.</p>
 *
 * <p>The {@code ServletContext} object is made available to a servlet
 * through its {@link ServletConfig}, which is provided by the container
 * during servlet initialization.</p>
 *
 * @see Servlet#getServletConfig
 * @see ServletConfig#getServletContext
 */
public interface ServletContext {

    /**
     * The name of the {@link ServletContext} attribute under which the servlet
     * container stores a {@link java.io.File} representing a private temporary
     * directory for the web application.
     *
     * <p>This constant is a lookup key only; the actual directory is created
     * and managed by the servlet container.</p>
     */
    String TEMPDIR = "jakarta.servlet.context.tempdir";

    /**
     * The name of the {@link ServletContext} attribute whose value (of type
     * {@code java.util.List<String>}) contains the names of JAR files in
     * {@code WEB-INF/lib}, ordered according to their web fragment names.
     *
     * <p>The list may exclude certain JARs when an {@code <absolute-ordering>}
     * element without {@code <others/>} is specified in {@code web.xml}.
     * If neither absolute nor relative ordering is defined, the value of this
     * attribute is {@code null}.</p>
     */
    String ORDERED_LIBS = "jakarta.servlet.context.orderedLibs";

    /**
     * Returns the context path of the web application.
     *
     * <p>The context path is the portion of the request URI that is used to select
     * the context of the request. It always comes first in the request URI.
     * If this context is the "root" context of the server, this method returns
     * an empty string. Otherwise, the path starts with a {@code /} and does not
     * end with a {@code /}.</p>
     *
     * <p>Note: Some servlet containers may allow multiple context paths to point
     * to the same web application. In such cases, this method returns the
     * preferred or primary context path, which may differ from the context path
     * returned by
     * {@link io.github.atharv3221.minicat.servlet.http.HttpServletRequest#getContextPath()}
     * for a specific request.</p>
     *
     * <p><b>Example:</b></p>
     * <pre>{@code
     * // Suppose the web application is deployed at "/shop"
     * ServletContext context = getServletContext();
     * String ctxPath = context.getContextPath(); // returns "/shop"
     *
     * // If the application is deployed at the root context "/"
     * String rootPath = context.getContextPath(); // returns ""
     * }</pre>
     *
     * @return the context path of the web application, or "" for the root context
     *
     * @see jakarta.servlet.http.HttpServletRequest#getContextPath()
     */
    String getContextPath();

    /**
     * Returns a {@code ServletContext} object that corresponds to a specified URL path on
     * the server.
     *
     * <p>This method allows a servlet to obtain access to the context of another web application
     * running in the same servlet container. From the returned context, a servlet can, for example,
     * get {@link RequestDispatcher} objects to forward or include requests.</p>
     *
     * <p>The given path must begin with a {@code /} and is interpreted relative to the
     * server's root. It is matched against the context roots of other web applications
     * hosted on the container.</p>
     *
     * <p>In a security-conscious environment, the container may return {@code null} to
     * restrict access to a particular web application.</p>
     *
     * <p><b>Example:</b></p>
     * <pre>{@code
     * // Access the ServletContext of another web application deployed at "/shop"
     * ServletContext shopContext = getServletContext().getContext("/shop");
     * if (shopContext != null) {
     *     RequestDispatcher dispatcher = shopContext.getRequestDispatcher("/login");
     *     dispatcher.forward(request, response);
     * }
     * }</pre>
     *
     * @param uripath a {@code String} specifying the context path of another web application
     *                in the container (must begin with {@code /})
     * @return the {@code ServletContext} corresponding to the given path, or {@code null}
     *         if no such context exists or access is restricted
     *
     * @see RequestDispatcher
     * @see ServletContext#getRequestDispatcher(String)
     */
    ServletContext getContext(String uripath);

    /**
     * Returns the major version of the Jakarta Servlet specification that this
     * servlet container supports.
     *
     * <p>For example, a container that implements Servlet 4.0 should return {@code 4}
     * as the major version.
     * All implementations that comply with version {@code X.Y} of the specification must
     * return {@code X}.</p>
     *
     * <p>This can be used by servlets or frameworks to check for API compatibility at runtime.</p>
     *
     * @return the major version of the Jakarta Servlet specification supported by this container
     */
    int getMajorVersion();

    /**
     * Returns the minor version of Jakarta Servlet specification that this container supports.
     * All implementations that comply with version X.Y of the specification,
     * must return the integer Y.
     *
     * @return The minor version of Jakarta Servlet specification that this container supports
     */
    int getMinorVersion();

    /**
     * Gets the major version of the Servlet specification that the application represented
     * by this {@code ServletContext} is based on.
     *
     * <p>The value returned may be different from {@link #getMajorVersion}, which returns the
     * major version of the Servlet specification supported by the Servlet container.
     *
     * <p>The effective version is determined from the application's deployment configuration
     * (for example, the {@code version} declared in {@code web.xml} or equivalent metadata).
     *
     * @return the major version of the Servlet specification that the application represented
     *         by this {@code ServletContext} is based on
     */
    int getEffectiveMajorVersion();

    /**
     * Gets the minor version of the Servlet specification that the application represented by this
     * ServletContext is based on.
     *
     * <p>The value returned may be different from {@link #getMinorVersion}, which returns the
     * minor version of the Servlet specification supported by the Servlet container.
     *
     * @return the minor version of the Servlet specification that the application
     *     represented by this ServletContext is based on
     */
    int getEffectiveMinorVersion();

    /**
     * Returns the MIME type of the specified file name, or {@code null} if the
     * MIME type cannot be determined.
     *
     * <p>The MIME type is determined by the servlet container using its configured
     * file extension to MIME type mappings, which may include default container
     * mappings and mappings defined in the application's deployment descriptor.
     *
     * <p>This method is intended for determining the content type of static
     * resources based on their file extensions and does not perform any
     * request-specific or servlet mapping resolution.
     *
     * <p>Common MIME types include {@code text/html}, {@code application/json},
     * and {@code image/gif}.
     *
     * @param file a {@code String} specifying a file name or path, from which the
     *             file extension will be used to determine the MIME type
     *
     * @return a {@code String} specifying the file's MIME type, or {@code null}
     *         if the MIME type is not known
     */
    String getMimeType(String file);

    /**
     * Returns a set of paths to resources within the web application whose longest
     * sub-path matches the specified path. The returned set represents a
     * directory-like listing of the immediate resources under the given path.
     *
     * <p>Paths representing subdirectories end with a {@code /}. The paths are
     * relative to the root of the web application or to the
     * {@code /META-INF/resources} directory of JARs in {@code /WEB-INF/lib},
     * and always start with a {@code /}.</p>
     *
     * <p>The returned set is not backed by the {@code ServletContext}, so
     * modifications to it do not affect the context and vice versa.</p>
     *
     * <p>For example, given a web application containing:
     * <pre>{@code
     * /welcome.html
     * /catalog/index.html
     * /catalog/products.html
     * /catalog/offers/books.html
     * /customer/login.jsp
     * /WEB-INF/web.xml
     * /WEB-INF/classes/com.acme.OrderServlet.class
     * /WEB-INF/lib/catalog.jar!/META-INF/resources/catalog/moreOffers/books.html
     * }</pre>
     * Calling {@code getResourcePaths("/")} would return
     * {@code {"/welcome.html", "/catalog/", "/customer/", "/WEB-INF/"}}, and
     * {@code getResourcePaths("/catalog/")} would return
     * {@code {"/catalog/index.html", "/catalog/products.html", "/catalog/offers/",
     * "/catalog/moreOffers/"}}.</p>
     *
     * <p><b>Security note:</b> This method bypasses both implicit (no direct access
     * to WEB-INF or META-INF) and explicit (defined by the web application)
     * security constraints. Ensure that any paths provided or returned are
     * properly sanitized to avoid security vulnerabilities.</p>
     *
     * @param path a {@code String} specifying the base path to search, which must
     *     start with {@code /}
     * @return a {@code Set<String>} containing the immediate resource paths
     *     under the specified path, or {@code null} if no resources exist
     *
     */
    Set<String> getResourcePaths(String path);

    /**
     * Returns a {@link URL} to the resource mapped to the specified path.
     *
     * <p>The path must begin with a {@code /} and is interpreted as relative to
     * the current context root or to the {@code /META-INF/resources} directory
     * of a JAR file inside the web application's {@code /WEB-INF/lib} directory.
     * The container searches the web application document root first, then the
     * JAR files in {@code /WEB-INF/lib}. The order of searching JAR files is
     * undefined.</p>
     *
     * <p>This allows the servlet container to expose resources from any source,
     * such as the local or remote filesystem, a database, or a {@code .war} file.
     * The container must implement the necessary {@link URL} handlers and
     * {@link java.net.URLConnection} objects to access the resource.</p>
     *
     * <p>This method returns {@code null} if no resource is mapped to the specified path.</p>
     *
     * <p>Requesting a JSP page via this method returns the JSP source code.
     * To obtain the result of executing a JSP, use a {@link
     * RequestDispatcher} instead.</p>
     *
     * <p>This method does <b>not</b> use class loaders and differs from
     * {@code java.lang.Class.getResource}, which loads resources relative to a class.</p>
     *
     * <p><b>Security note:</b> This method bypasses both implicit (e.g., no direct access
     * to {@code WEB-INF} or {@code META-INF}) and explicit (defined by the web
     * application) security constraints. Paths should be sanitized to avoid
     * security vulnerabilities.</p>
     *
     * @param path a {@code String} specifying the path to the resource, starting with {@code /}
     * @return a {@link URL} pointing to the resource at the specified path,
     *     or {@code null} if none exists
     * @throws MalformedURLException if the pathname is not in a valid URL form
     */
    URL getResource(String path) throws MalformedURLException;

    /**
     * Returns an {@link InputStream} to the resource at the specified path.
     *
     * <p>The data in the returned {@code InputStream} can be of any type or length.
     * The path must follow the same rules as {@link #getResource(String)}, and this
     * method returns {@code null} if no resource exists at the specified path.</p>
     *
     * <p>Unlike {@link #getResource(String)}, meta-information such as content length
     * or MIME type is <b>not available</b> when using this method.</p>
     *
     * <p>This method allows the servlet container to provide resources to a servlet
     * from any location, including the local or remote filesystem, a database, or
     * inside a JAR file. It does <b>not</b> use a class loader, unlike
     * {@code java.lang.Class.getResourceAsStream}.</p>
     *
     * <p><b>Security note:</b> This method bypasses both implicit (e.g., no direct
     * access to {@code WEB-INF} or {@code META-INF}) and explicit (defined by the
     * web application) security constraints. Ensure paths are properly sanitized
     * to avoid security vulnerabilities.</p>
     *
     * @param path a {@code String} specifying the path to the resource, starting with {@code /}
     * @return an {@link InputStream} to read the resource, or {@code null} if
     *     the resource does not exist
     */
    InputStream getResourceAsStream(String path);

    /**
     * Returns a {@link RequestDispatcher} for the resource at the specified path.
     *
     * <p>The {@code RequestDispatcher} acts as a wrapper around the resource and
     * can be used to either:
     * <ul>
     *   <li>Forward a request to the resource (control is transferred, response is
     *   generated by the target)</li>
     *   <li>Include the resource in the current response (output is merged into
     *   current response)</li>
     * </ul>
     * The resource may be static (e.g., HTML, CSS) or dynamic (e.g., another servlet or JSP).</p>
     *
     * <p>The provided {@code path} must begin with a {@code /} and is interpreted
     * relative to the current context root. To obtain a {@code RequestDispatcher}
     * for a resource in another context, use {@link ServletContext#getContext} first.</p>
     *
     * <p>This method returns {@code null} if the {@code ServletContext} cannot
     * provide a dispatcher for the specified path.</p>
     *
     * <p><b>Security note:</b> This method bypasses both implicit (no direct access
     * to {@code WEB-INF} or {@code META-INF}) and explicit (defined by the web
     * application) security constraints. Paths should be sanitized to avoid
     * security vulnerabilities.</p>
     *
     * @param path a {@code String} specifying the pathname of the resource, starting with {@code /}
     * @return a {@link RequestDispatcher} that wraps the resource at the specified path,
     *         or {@code null} if no dispatcher can be returned
     *
     * @see RequestDispatcher
     * @see ServletContext#getContext
     */
    RequestDispatcher getRequestDispatcher(String path);

    /**
     * Returns a {@link RequestDispatcher} for the servlet with the specified name.
     *
     * <p>The {@code RequestDispatcher} acts as a wrapper around the named servlet
     * and can be used to forward a request to it or include its output in the
     * response.</p>
     *
     * <p>Servlets (and JSP pages) may be given names via server configuration or
     * the web application's deployment descriptor. A servlet can determine its
     * own name using {@link ServletConfig#getServletName}.</p>
     *
     * <p>This method returns {@code null} if no servlet with the specified name
     * exists, or if the {@code ServletContext} cannot provide a dispatcher for it.</p>
     *
     * <p><b>Security note:</b> Like other dispatcher methods, this bypasses both
     * implicit (no direct access to {@code WEB-INF} or {@code META-INF}) and
     * explicit (defined by the web application) security constraints. Ensure
     * that servlet names are not derived from unsanitized user input.</p>
     *
     * @param name a {@code String} specifying the name of the servlet
     * @return a {@link RequestDispatcher} that wraps the named servlet,
     *         or {@code null} if no dispatcher can be returned
     *
     * @see RequestDispatcher
     * @see ServletContext#getContext
     * @see ServletConfig#getServletName
     */
    RequestDispatcher getNamedDispatcher(String name);

    /**
     * Writes the specified message to a servlet log file, usually an event log.
     * The name and type of the servlet log file is specific to the servlet container.
     *
     * @param msg a {@code String} specifying the message to be written to the log file
     */
    void log(String msg);

    /**
     * Logs an explanatory message along with a stack trace for a given {@link Throwable}.
     *
     * <p>The message and stack trace are written to the servlet container's
     * log file. The location, name, and type of this log file are implementation-specific,
     * but typically it is the server’s event or error log.</p>
     *
     * <p>This method is useful for recording unexpected errors or exceptions
     * encountered by a servlet during request processing.</p>
     *
     * @param message a {@code String} describing the error or exception
     * @param throwable the {@link Throwable} instance representing the error or exception
     */
    void log(String message, Throwable throwable);

}
