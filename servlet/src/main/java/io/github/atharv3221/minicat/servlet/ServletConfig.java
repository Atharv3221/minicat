package io.github.atharv3221.minicat.servlet;

import java.util.Enumeration;

/**
 * A servlet configuration object used by a servlet container to pass information to a servlet during initialization.
 */
public interface ServletConfig {

    /**
     * Returns the name of this servlet instance.
     */
    String getServletName();

    /**
     * Returns a reference to the {@link ServletContext} in which the caller is executing.
     * @return a {@link ServletContext} object, used by the caller to interact with its servlet container
     */
    ServletContext getServletContext();

    /**
     * Gets the value of the initialization parameter with the given name.
     *
     * @param name the name of the initialization parameter whose value to get
     *
     * @return a {@code String} containing the value of the initialization parameter, or {@code null} if the
     * initialization parameter does not exist
     */
    String getInitParameter(String name);

    /**
     *
     */
    Enumeration<String> getInitParameterNames();
}
