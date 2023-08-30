/**
 * The logical application module order implemented as a multi-package module. Internal components located in nested
 * packages are prevented from being accessed by the {@link org.springframework.modulith.core.ApplicationModules} type.
 *
 * @see example.ModularityTests
 */
@NonNullApi
// WORKSHOP:
// <3> only allow access to the catalog
//@ApplicationModule(allowedDependencies = "catalog::spi")
package example.order;

import org.springframework.lang.NonNullApi;
import org.springframework.modulith.ApplicationModule;